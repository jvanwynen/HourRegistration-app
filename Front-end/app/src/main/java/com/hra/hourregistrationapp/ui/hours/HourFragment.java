package com.hra.hourregistrationapp.ui.hours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hra.hourregistrationapp.R;

public class HourFragment extends Fragment {

    private HourViewModel hourViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hourViewModel =
                ViewModelProviders.of(this).get(HourViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hours, container, false);
        final TextView textView = root.findViewById(R.id.hour_text_hours);
        hourViewModel.getText().observe(this, s -> textView.setText(s));
        return root;
    }
}