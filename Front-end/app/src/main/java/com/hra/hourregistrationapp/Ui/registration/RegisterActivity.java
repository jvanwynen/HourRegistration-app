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
import com.hra.hourregistrationapp.Ui.Activity;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> mCompanyNames;
    private List<Company> mCompanies;
    private ArrayAdapter<String> mAdapter;
    private Spinner mSpinner;
    private Button mSaveButton;
    private TextView mPasswordTextView;
    private LoginViewModel mLoginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        LocalDatabase localDatabase = LocalDatabase.getInstance(this);

        mSpinner = findViewById(R.id.registration_spinner_companylist);
        findViewById(R.id.registration_text_add).setOnClickListener(this);
        mSaveButton = findViewById(R.id.registration_save_button);
        mPasswordTextView = findViewById(R.id.registration_input_password);
        mSaveButton.setOnClickListener(this);

        mCompanyNames = new ArrayList<>();
        mCompanies = localDatabase.companyDao().getAll();

        for(Company c : mCompanies ){
            mCompanyNames.add(c.getCompanyname());
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCompanyNames);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

    }

    private void createUser(String CompanyName, String CompanyPassword){
        if(!validatePassword(CompanyName, CompanyPassword)){
            //mLoginViewModel.createUser();
        }
        else{
//            showPopUp(getString(R.string.main_popup_title), "Incorrect password");
        }
    }

    private boolean validatePassword(String CompanyName, String CompanyPassword){
    return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.registration_text_add:
                intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
                startActivity(intent);
            case R.id.registration_save_button:

               // createUser();

                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
        }
    }
}