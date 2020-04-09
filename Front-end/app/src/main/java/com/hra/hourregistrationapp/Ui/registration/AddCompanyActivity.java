package com.hra.hourregistrationapp.Ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.ViewModel.CompanyViewModel;

public class AddCompanyActivity extends AppCompatActivity{

    CompanyViewModel mCompanyViewModel;
    AppCompatEditText nameInput, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);


        mCompanyViewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);

        nameInput = findViewById(R.id.addcompany_input_name);
        passwordInput = findViewById(R.id.addcompany_input_password);

        //findViewById(R.id.addcompany_button_save).setOnClickListener(this);
    }

  /*  @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addcompany_button_save:
                Company company = new Company(nameInput.getText().toString()
                        ,passwordInput.getText().toString());
                mCompanyViewModel.createNewCompany(company);
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
        }
    }*/
}