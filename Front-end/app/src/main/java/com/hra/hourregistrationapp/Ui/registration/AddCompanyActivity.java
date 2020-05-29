package com.hra.hourregistrationapp.Ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import com.hra.hourregistrationapp.Model.Company;
import com.hra.hourregistrationapp.R;
import com.hra.hourregistrationapp.Retrofit.RetrofitResponseListener;
import com.hra.hourregistrationapp.Ui.Activity;
import com.hra.hourregistrationapp.ViewModel.CompanyViewModel;

public class AddCompanyActivity extends Activity implements View.OnClickListener {

    CompanyViewModel mCompanyViewModel;
    AppCompatEditText nameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        mCompanyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);

        nameInput = findViewById(R.id.addcompany_input_name);
        passwordInput = findViewById(R.id.addcompany_input_password);

        findViewById(R.id.addcompany_button_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addcompany_button_save) {
            storeInput();
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void storeInput() {
        if (nameInput.getText() != null && passwordInput.getText() != null) {
           sendCompany(new Company(nameInput.getText().toString() ,passwordInput.getText().toString()));
        } else {
            showPopUp(getString(R.string.main_popup_title), "No fields can be empty", true);
        }
    }

    private void sendCompany(Company company){
        mCompanyViewModel.createNewCompany(company, new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                loadDataFromRemote();
            }

            @Override
            public void onFailure() {
                showPopUp(getString(R.string.main_popup_title), "server did not accept new company", true);
            }
        });
    }

    private void loadDataFromRemote(){
        mCompanyViewModel.loadDataFromRemote(new RetrofitResponseListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }

            @Override
            public void onFailure() {

            }
        });

    }
}