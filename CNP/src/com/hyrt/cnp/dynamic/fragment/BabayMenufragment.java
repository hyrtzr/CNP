package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyrt.cnp.R;

/**
 * Created by GYH on 14-1-20.
 */
public class BabayMenufragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_babaymenu, container, false);
        return rootView;
    }
}
