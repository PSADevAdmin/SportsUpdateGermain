package com.prasaurus.app.psa_b2c_app;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    RequestQueue requestQueue;
    StringRequest request;

    static String resp = "";
    static String success = "";

    Date DOB;
    String user_id;
    TextView DOB_textView;
    EditText reg_user_name, reg_user_fullname, reg_user_email, reg_user_mobile, reg_user_city, reg_user_password;
    String reg_user_DOB, reg_user_gender;
    Button date_picker, main_registerbn;
    Spinner reg_gender_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DOB_textView = (TextView) findViewById(R.id.dob_textView);

        reg_user_name = (EditText) findViewById(R.id.register_username);
        reg_user_fullname = (EditText) findViewById(R.id.register_fullname);
        reg_user_email = (EditText) findViewById(R.id.register_email);
        reg_user_mobile = (EditText) findViewById(R.id.register_mobile);
        reg_user_city = (EditText) findViewById(R.id.register_city);
        reg_user_password = (EditText) findViewById(R.id.register_password);

        reg_gender_spinner = (Spinner) findViewById(R.id.register_gender_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reg_gender_spinner.setAdapter(adapter);

        reg_gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reg_user_gender = "" + parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        date_picker = (Button) findViewById(R.id.register_DOB);
        main_registerbn = (Button) findViewById(R.id.main_registerbn);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DOB_textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        reg_user_DOB = DOB_textView.getText().toString();

        final int random = new Random().nextInt(100000);

        user_id = reg_user_city.getText().toString() + random;


        requestQueue = Volley.newRequestQueue(this);


        main_registerbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = 1;

                if (TextUtils.isEmpty(reg_user_fullname.getText())) {
                    reg_user_fullname.setError("Full name is required");
                    flag = 0;
                } else if (TextUtils.isEmpty(reg_user_email.getText())) {
                    reg_user_email.setError("Email is required");
                    flag = 0;
                } else if (TextUtils.isEmpty(reg_user_mobile.getText())) {
                    reg_user_mobile.setError("Mobile Number is required");
                    flag = 0;
                } else if (TextUtils.isEmpty(reg_user_city.getText())) {
                    reg_user_city.setError("City is Required");
                    flag = 0;
                } else if (TextUtils.isEmpty(reg_user_name.getText())) {
                    reg_user_name.setError("Username is required");
                    flag = 0;
                } else if (TextUtils.isEmpty(reg_user_password.getText())) {
                    reg_user_password.setError("Password is required");
                    flag = 0;
                }

                if (flag == 1) {
                    request = new StringRequest(Request.Method.POST, "http://13.232.207.9/php/register.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    String message = jsonObject.getString("Message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else if (success == 0) {
                                    String message = jsonObject.getString("Message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("user_id", user_id);
                            hashMap.put("user_fullname", reg_user_fullname.getText().toString());
                            hashMap.put("user_name", reg_user_name.getText().toString());
                            hashMap.put("user_email", reg_user_email.getText().toString());
                            hashMap.put("user_mobile", reg_user_mobile.getText().toString());
                            hashMap.put("user_city", reg_user_city.getText().toString());
                            hashMap.put("user_password", reg_user_password.getText().toString());
                            hashMap.put("user_dob", DOB_textView.getText().toString());
                            hashMap.put("user_gender", reg_user_gender);

                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }}
            });

    }
}
