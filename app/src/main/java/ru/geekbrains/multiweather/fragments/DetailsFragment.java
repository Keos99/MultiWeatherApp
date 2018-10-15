package ru.geekbrains.multiweather.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.geekbrains.multiweather.R;

public class DetailsFragment extends Fragment {
    public static Fragment newInstance(String data) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    TextView tvDetailsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, null);
        tvDetailsText = view.findViewById(R.id.tv_details);
        String data = getArguments().getString("data");
        tvDetailsText.setText(data);
        return view;
    }
}
