package com.hra.hourregistrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hra.hourregistrationapp.Controller.LoginService;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.Retrofit.RetrofitClient;
import com.hra.hourregistrationapp.ViewModel.CompanyViewModel;
import com.hra.hourregistrationapp.ViewModel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 6969;
    private static final String TAG = "tag";

    private GoogleSignInClient mGoogleSignInClient;
    private ArrayList<String> mCompaniesnames;
    private CompanyViewModel mCompanyViewModel;
    private ArrayAdapter adapter;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.companylist_spinner_login);
        //spinner.setOnClickListener(this);


        mCompaniesnames = new ArrayList<>();
        mCompanyViewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);

            mCompanyViewModel.getAllCompanies()
                    .observe(this, new Observer<List<Company>>() {
                @Override
                public void onChanged(@Nullable final List<Company> companies) {
                    for (Company company : companies) {
                        mCompaniesnames.add(company.getName());
                    }
                }
            });


        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, mCompaniesnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.server_client_id))
//                .requestEmail()
//                .build();
//
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        //check if user can sign in silently
//        googleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
//            @Override
//            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
//                handleSignInResult(task);
//            }
//        });
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

//    protected void onStart() {
//        super.onStart();
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        //updateUI(account);
//    }

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

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            sendToken(idToken);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:error", e);
            //  updateUI(null);
        }
    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                getIdToken();
                break;

        }
    }

    private void sendToken(String idToken){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getLoginService()
                .CreatePost(idToken);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("http", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("httpFailure", t.getMessage());
            }
        });
        // updateUI(account);

    }
}