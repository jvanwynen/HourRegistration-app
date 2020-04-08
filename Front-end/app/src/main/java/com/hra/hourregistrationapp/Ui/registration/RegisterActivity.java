package com.hra.hourregistrationapp.Ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.Activity;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity implements View.OnClickListener {


    private ArrayAdapter<Company> mAdapter;
    private Spinner mSpinner;
    private Button mSaveButton;
    private TextView mPasswordTextView;
    private LoginViewModel mLoginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        if (!mLoginViewModel.userLoggedInSuccessful()) {
            showPopUp(getString(R.string.main_popup_title), getString(R.string.registration_popup_body));
        }

        mSpinner = findViewById(R.id.registration_spinner_companylist);
        findViewById(R.id.registration_text_add).setOnClickListener(this);
        mSaveButton = findViewById(R.id.registration_save_button);
        mPasswordTextView = findViewById(R.id.registration_input_password);
        mSaveButton.setOnClickListener(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mLoginViewModel.getLocalCompanies());
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(mAdapter);

    }

    private void createUser(Company company) {
        validatePassword(company, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                //mLoginViewModel.createUser();
            }

            @Override
            public void onFailure() {
//            showPopUp(getString(R.string.main_popup_title), "Incorrect password");
            }
        });
    }


    private void validatePassword(Company company, RetrofitResponseListener retrofitResponseListener) {
        mLoginViewModel.validateCompanyPassword(company, retrofitResponseListener);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.registration_text_add:
                intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
                startActivity(intent);
            case R.id.registration_save_button:
                Company newCompany = new Company(mSpinner.getSelectedItem().toString(), mPasswordTextView.getText().toString());
                createUser(newCompany);
//                intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
        }
    }

}