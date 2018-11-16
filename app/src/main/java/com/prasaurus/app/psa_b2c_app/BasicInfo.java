package com.prasaurus.app.psa_b2c_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class BasicInfo extends Fragment implements View.OnClickListener{

    public static String user_position="";
    public static int user_jersey_number;

    private final int IMG_REQUEST=1;

    private Bitmap bitmap;

    ImageView profile_user_photo;

    TextView t1;
    TextView t2;

    SharedPreferenceConfig preferenceConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.basic_info, container, false);

        t1 = v.findViewById(R.id.testing1);
        t2 = v.findViewById(R.id.testing2);


        preferenceConfig = new SharedPreferenceConfig(this.getContext());

        profile_user_photo = v.findViewById(R.id.profile_user_photo);

        Button choose_image = v.findViewById(R.id.choose_profile_photo);
        choose_image.setOnClickListener(this);
        Button upload_image = v.findViewById(R.id.upload_profile_photo);

        TextView profile_matches_played = v.findViewById(R.id.profile_matches);
        TextView profile_goals_scored = v.findViewById(R.id.profile_goals);
        TextView profile_assists_given = v.findViewById(R.id.profile_assists);
        TextView profile_MoM = v.findViewById(R.id.profile_MoM);

        Spinner profile_position = v.findViewById(R.id.profile_position_spinner);
        Spinner profile_jersey_number = v.findViewById(R.id.profile_jersey_spinner);

        ArrayAdapter<CharSequence> adapter_position = ArrayAdapter.createFromResource(this.getContext(),R.array.positions_array,
                android.R.layout.simple_spinner_item);
        adapter_position.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_position.setAdapter(adapter_position);

        profile_position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_position = parent.getItemAtPosition(position)+"";
                preferenceConfig.write_user_stats_positon(user_position);
                Log.e("Postion",user_position);
                Log.e("SP_position",preferenceConfig.read_user_stats_position());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter_jersey = ArrayAdapter.createFromResource(this.getContext(),R.array.jersey_number_array,
                android.R.layout.simple_spinner_item);
        adapter_jersey.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_jersey_number.setAdapter(adapter_jersey);

        profile_jersey_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_jersey_number = parent.getSelectedItemPosition();
                preferenceConfig.write_user_stats_jersey(user_jersey_number);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //t1.setText(preferenceConfig.read_user_stats_position());
        //t2.setText(preferenceConfig.read_user_stats_jersey());

        profile_jersey_number.setSelection(preferenceConfig.read_user_stats_jersey());

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_profile_photo:
                select_image();
                break;
        }
    }

    private void select_image(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                profile_user_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
