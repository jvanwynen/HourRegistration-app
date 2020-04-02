package com.hra.hourregistrationapp.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hra.hourregistrationapp.Ui.popup.Popup;

//parent activity for general methods needed in more activities
public class Activity extends AppCompatActivity {

    //method that puts title and body in bundle and start the pop-up activity
    public void showPopUp(String title, String body){
        Bundle extras = new Bundle();
        Intent intent =  new Intent(this, Popup.class);
        extras.putString("help_title", title);
        extras.putString("help_text", body);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
