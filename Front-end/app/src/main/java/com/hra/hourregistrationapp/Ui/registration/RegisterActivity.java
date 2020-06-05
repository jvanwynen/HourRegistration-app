package com.hra.hourregistrationapp.Ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.Activity;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity implements View.OnClickListener {



    private Spinner mSpinner;
    private Button mSaveButton;
    private TextView mPasswordTextView;
    private LoginViewModel mLoginViewModel;
    ArrayAdapter<Company> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mSpinner = findViewById(R.id.registration_spinner_companylist);
        findViewById(R.id.registration_text_add).setOnClickListener(this);
        mSaveButton = findViewById(R.id.registration_save_button);
        mPasswordTextView = findViewById(R.id.registration_input_password);
        mSaveButton.setOnClickListener(this);

        List<Company> companyList = new ArrayList<>();

//        Company company = new Company("test1","test1");
//        companyList.add(company);
//        Company company2 = new Company("test2","test1");
//        companyList.add(company2);
//        Company company3 = new Company("test3","test1");
//        companyList.add(company3);
//        Company company4 = new Company("test4","test1");
//        companyList.add(company4);

         mLoginViewModel.getLocalCompanies().observe(this, new Observer<List<Company>>() {
             @Override
             public void onChanged(List<Company> companies) {
                 companyList.addAll(companies);
                 mAdapter.notifyDataSetChanged();
             }
         });

         mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, companyList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(mAdapter);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mLoginViewModel.upsertCompaniesLocally();
//    }

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
                showPopUp(getString(R.string.main_popup_title), getString(R.string.error_loginunsuccesfull), true);
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

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}