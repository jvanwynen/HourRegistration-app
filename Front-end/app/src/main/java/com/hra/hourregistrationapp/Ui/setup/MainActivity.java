package com.hra.hourregistrationapp.Ui.setup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

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
import com.hra.hourregistrationapp.Ui.popup.Popup;
import com.hra.hourregistrationapp.Ui.projects.ProjectsFragment;
import com.hra.hourregistrationapp.Ui.registration.RegisterActivity;
import com.hra.hourregistrationapp.ViewModel.MainViewModel;

import butterknife.internal.ListenerClass;

public class MainActivity extends Activity {

    private static final int RC_SIGN_IN = 6969;

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton mSignInButton;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        if (!isNetworkAvailable()) {
            showPopUp(getString(R.string.main_popup_title), getString(R.string.main_popup_text));
        }

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mMainViewModel.loadDataFromRemote(new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                mMainViewModel.upsertCompaniesLocally();
            }

            @Override
            public void onFailure() {
                showPopUp(getString(R.string.main_popup_title), getString(R.string.main_popup_companyerrrortext));
            }
        });

        mSignInButton = findViewById(R.id.setup_button_signin);

        mSignInButton.setOnClickListener(view -> getIdToken());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //check if user can sign in silently
        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this, this::handleSignInResult);
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            mMainViewModel.verifyIdToken(idToken, new RetrofitResponseListener() {
                @Override
                public void onSuccess() {
                    mMainViewModel.setSignedInUser();
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure() {
                    showPopUp(getString(R.string.main_popup_title), getString(R.string.registration_popup_body));
                }
            });

        } catch (ApiException e) {
            System.out.println("handleSignInResult:error" + e);
            showPopUp(getString(R.string.main_popup_title), e.toString());
        }
    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        mSignInButton.setVisibility(View.GONE);
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
