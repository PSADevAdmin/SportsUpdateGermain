package com.prasaurus.app.psa_b2c_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.NumberPicker;

interface NumberPickerValueAdapter {
    void numberPickerValueSelected(Integer value);
}

public class NumberPickerDialogFragment extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    public NumberPickerValueAdapter numberPickerValueAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(26);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Number of players in each team");
        builder.setMessage("Choose a number :");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                valueChangeListener.onValueChange(numberPicker,
//                        numberPicker.getValue(), numberPicker.getValue());
                if(numberPickerValueAdapter != null) {
                    numberPickerValueAdapter.numberPickerValueSelected(numberPicker.getValue());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                valueChangeListener.onValueChange(numberPicker,
//                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}
