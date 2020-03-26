package com.hra.hourregistrationapp.Ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hra.hourregistrationapp.Persistence.LocalDatabase;
import com.hra.hourregistrationapp.Repository.CompanyRepository;
import com.hra.hourregistrationapp.Repository.LoginRepository;
import com.hra.hourregistrationapp.Ui.home.HomeActivity;
import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.CompanyViewModel;
import com.hra.hourregistrationapp.ViewModel.LoginViewModel;
import com.hra.hourregistrationapp.ViewModel.MainViewModel;

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

//        findViewById(R.id.setup_button_signin).setOnClickListener(this);
        mSpinner = findViewById(R.id.registration_spinner_companylist);
        findViewById(R.id.registration_text_add).setOnClickListener(this);
        mSaveButton = findViewById(R.id.registration_save_button);
        mPasswordTextView = findViewById(R.id.registration_input_password);
        mSaveButton.setOnClickListener(this);
        CompanyRepository mCompanyRepository = new CompanyRepository(this);

        mCompanyNames = new ArrayList<>();
        mCompanies = localDatabase.companyDao().getAll();




//            mCompanyViewModel.getAllCompanies()
//                    .observe(this, new Observer<List<Company>>() {
//                @Override
//                public void onChanged(@Nullable final List<Company> companies) {
//                    for (Company company : companies) {
//                        mCompanyNames.add(company.getName());
//                    }
//                }
//            });

       // mCompanies = mCompanyRepository.getCompaniesLocalDB();

        for(Company c : mCompanies ){
            mCompanyNames.add(c.getName());
        }

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCompanyNames);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void createUser(){

    }

    private void validatePassword(){

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.registration_text_add:
                intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
                startActivity(intent);
            case R.id.registration_save_button:
                validatePassword();
                //createUser();
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
        }
    }
}