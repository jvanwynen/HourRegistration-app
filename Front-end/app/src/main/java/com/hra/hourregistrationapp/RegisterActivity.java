package com.hra.hourregistrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hra.hourregistrationapp.Controller.LoginInterface;
import com.hra.hourregistrationapp.Model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 6969;
    private static final String TAG = "tag";
    LoginInterface loginInterface;
    private Button mLoginButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
//        //check if user can sign in silently
//        googleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
//            @Override
//            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
//                handleSignInResult(task);
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.187/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginInterface = retrofit.create(LoginInterface.class);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
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

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            Login login = new Login(idToken);

            Call<Login> call = loginInterface.CreatePost(login);
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    if (!response.isSuccessful()) {
                        Log.d("HTTP", "No succesfull response" + response.code());
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Log.d("HTTPFailure", t.getMessage());
                }
            });

//            String[] strings = new String[1];
//            strings[0] = idToken;
//
//           new LoginController(new LoginController.AsyncResponse() {
//               @Override
//               public void processFinish(boolean output) {
//                   if(output){
//                       Log.w(TAG, "Jippie1");
//                   } else {
//                       Log.w(TAG, "Nope1");
//                   }
//               }
//           }).execute(strings);

            // updateUI(account);
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
}