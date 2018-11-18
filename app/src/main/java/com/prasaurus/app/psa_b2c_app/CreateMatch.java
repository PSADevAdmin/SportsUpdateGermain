package com.prasaurus.app.psa_b2c_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateMatch extends Fragment
        implements NumberPickerValueAdapter {

    PlayerProfileActivity rootActivity;
    Button playerNumberButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_create_match,container,false);
        ((PlayerProfileActivity) getActivity()).getSupportActionBar().setTitle("Create Match");

        setupImageButton(rootView);
        setupNumberOfPlayersButton(rootView);
        setupHalfTimeButton(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            rootActivity = (PlayerProfileActivity)context;
        } catch (Exception exception) {
            rootActivity = null;
        }
    }

    private void setupImageButton(View rootView) {
        // Since the value of width is going to change according to different screen sizes
        // we need to ensure that width and height are same
        ImageButton homeTeamImageButton = rootView.findViewById(R.id.homeTeamImageButton);
        homeTeamImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ImageButton awayTeamImageButton = rootView.findViewById(R.id.awayTeamImageButton);
        awayTeamImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void setupNumberOfPlayersButton(final View rootView) {
        final NumberPickerValueAdapter valueAdapter = this;
        playerNumberButton = rootView.findViewById(R.id.numberOfPlayersPicker);
        playerNumberButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rootActivity != null) {
                    rootActivity.presentNumberPicker(valueAdapter);
                }
            }
        });
    }

    private void setupHalfTimeButton(View rootView) {
        final Button halfTimeButton = rootView.findViewById(R.id.halfTimePicker);
        halfTimeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                halfTimeButton.setText("10 mins (per half)");
            }
        });
    }


    public void numberPickerValueSelected(Integer value) {
        if (value == 0) {
            playerNumberButton.setText("Number of players per team");
        } else{
            playerNumberButton.setText(value + " (Players in each team)");
        }
    }
}
