package com.hra.hourregistrationapp.Ui.hours;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hra.hourregistrationapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HourDetailFragment extends Fragment {

    public HourDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_detail, container, false);
    }
}
