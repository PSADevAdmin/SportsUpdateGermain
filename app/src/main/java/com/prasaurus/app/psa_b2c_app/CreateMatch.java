package com.prasaurus.app.psa_b2c_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateMatch extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_create_match,container,false);

        Toolbar toolbar = v.findViewById(R.id.nav_toolbar);
        ((PlayerProfileActivity) getActivity()).getSupportActionBar().setTitle("Create Match");
        return v;
    }
}
