package com.weiruanit.lifepro.module.travel.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiruanit.lifepro.R;


public class TravelBaseFragment extends Fragment {

    public static TravelBaseFragment newInstance() {
        TravelBaseFragment fragment = new TravelBaseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_base, container, false);
    }

}
