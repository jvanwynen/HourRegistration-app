package com.hra.hourregistrationapp.Ui.setup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.Activity;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.Ui.registration.RegisterActivity;
import com.hra.hourregistrationapp.ViewModel.MainViewModel;

public class MainActivity extends Activity {

    private static final int RC_SIGN_IN = 6969;

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton mSignInButton;
    private TextView mTitle;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        if (!isNetworkAvailable()) {
            showPopUp(getString(R.string.main_popup_title), getString(R.string.main_popup_text), false);
        }

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mMainViewModel.loadDataFromRemote(new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure() {
                showPopUp(getString(R.string.main_popup_title), getString(R.string.main_popup_companyerrrortext), false);
            }
        });

        mSignInButton = findViewById(R.id.setup_button_signin);
        mTitle = findViewById(R.id.setup_text_title);

        mSignInButton.setOnClickListener(view -> getIdToken());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //check if user can sign in silently
        //mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, this::handleSignInResult);
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        String idToken = null;
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                idToken = account.getIdToken();
            }
        } catch (ApiException e) {
            System.out.println("handleSignInResult:error" + e);
            showPopUp(getString(R.string.main_popup_title), e.toString(), false);
        }
        System.out.println(idToken);
        mMainViewModel.verifyIdToken(idToken, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent();
                if (mMainViewModel.userHasCompany()) {
                    mMainViewModel.sendGetProjectByCompanyRequest();
                    intent.setClass(getApplicationContext(), HomeActivity.class);
                } else {
                    intent.setClass(getApplicationContext(), RegisterActivity.class);
                }
                startActivity(intent);
            }

            @Override
            public void onFailure() {
                showPopUp(getString(R.string.main_popup_title), getString(R.string.registration_popup_body), true);
            }
        });
    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        mSignInButton.setVisibility(View.GONE);
        mTitle.setVisibility(View.GONE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // This task is always completed immediately, there is no need to attach an
            // asynchronous listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * @return true if any network is available otherwise false
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
