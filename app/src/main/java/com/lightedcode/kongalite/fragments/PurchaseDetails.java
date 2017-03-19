package com.lightedcode.kongalite.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lightedcode.kongalite.R;

/**
 * Created by joebuntu on 3/12/17.
 */

public class PurchaseDetails extends Fragment{
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView  = inflater.inflate(R.layout.fragment_setings, container, false);

        return myView;
    }
}
