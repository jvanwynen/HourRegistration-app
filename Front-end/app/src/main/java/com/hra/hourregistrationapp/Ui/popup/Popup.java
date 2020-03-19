package com.hra.hourregistrationapp.Ui.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hra.hourregistrationapp.R;


public class Popup extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        Button closeButton = findViewById(R.id.button_popup_sluiten);
        Button crossButton = findViewById(R.id.button_popup_cross);

        TextView helpTitle = findViewById(R.id.text_popup_title);
        TextView helpText = findViewById(R.id.text_popup_text);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        //reduce to size so make it look like a popup
        getWindow().setLayout((int) (width * .7), (int) (height * .6));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //assert extras != null;
        if (extras.getString("help_title") != null || extras.getString("help_text") != null) {
            helpTitle.setText(extras.getString("help_title"));
            helpText.setText(extras.getString("help_text"));
        } else {
            helpTitle.setText(getString(R.string.Popup_tile));
            helpText.setText(getString(R.string.popup_text));
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

    }


    private void close(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}