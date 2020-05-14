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

        mSpinner = findViewById(R.id.registration_spinner_companylist);
        findViewById(R.id.registration_text_add).setOnClickListener(this);
        mSaveButton = findViewById(R.id.registration_save_button);
        mPasswordTextView = findViewById(R.id.registration_input_password);
        mSaveButton.setOnClickListener(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mLoginViewModel.getLocalCompanies());
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginViewModel.upsertCompaniesLocally();
    }

    private void createUser(Company company) {
        validatePassword(company, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                addCompanyToUser(company);
            }

            @Override
            public void onFailure() {
            showPopUp(getString(R.string.main_popup_title), "Incorrect password", true);
            }
        });
    }

    private void addCompanyToUser(Company company){
        mLoginViewModel.addCompanyToUser(company, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }

            @Override
            public void onFailure() {
                showPopUp(getString(R.string.main_popup_title), "Login not Successful", true);
            }
        });
    }

    private void validatePassword(Company company, RetrofitResponseListener retrofitResponseListener) {
        mLoginViewModel.validateCompanyPassword(company, retrofitResponseListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_text_add:
                Intent intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
                startActivity(intent);
            case R.id.registration_save_button:
                Company company = (Company) mSpinner.getSelectedItem();
                createUser(new Company(company.getId(), company.getCompanyname(), mPasswordTextView.getText().toString()));
        }
    }

    @Override
    public void onBackPressed() {
    }

}