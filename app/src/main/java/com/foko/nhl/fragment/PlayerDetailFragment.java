package com.foko.nhl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.foko.nhl.R;

public class PlayerDetailFragment extends DialogFragment {

    public static final String TAG = "dialog";
    private static final String ARG_PARAM1 = "param1";
    private String countryName;

    public PlayerDetailFragment(){}


    public static PlayerDetailFragment newInstance(String country) {
        PlayerDetailFragment fragment = new PlayerDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
        if (getArguments() != null) {
            countryName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_detail_fragment,container,false);
        TextView countryNameText = view.findViewById(R.id.country_name);
        countryNameText.setText(countryName);
        return view;
    }
}
