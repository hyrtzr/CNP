package com.hyrt.cnp.school.fragment;

/**
 * Created by GYH on 14-1-12.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyrt.cnp.R;

public class FragmentPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_fragment_schoolphoto, null);
    }
}
