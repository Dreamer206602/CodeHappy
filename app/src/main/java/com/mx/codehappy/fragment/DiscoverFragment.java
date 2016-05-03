package com.mx.codehappy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.codehappy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    public static final String ARG_TITLE="title";
    private String mTitle;



    public static DiscoverFragment getInstance(String title){

        DiscoverFragment discoverFragment=new DiscoverFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARG_TITLE,title);
        discoverFragment.setArguments(bundle);
        return discoverFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        mTitle=bundle.getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

}
