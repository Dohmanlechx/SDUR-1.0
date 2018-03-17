package com.dohman.sdur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dohman on 2018-03-17.
 */

public class Tab1Membership extends Fragment {
    private static final String TAG = "Tab1Membership";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Connects to the right layout file.
        View view = inflater.inflate(R.layout.tab1_membership, container, false);

        return view;
    }
}
