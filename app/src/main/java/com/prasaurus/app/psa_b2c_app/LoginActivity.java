package com.prasaurus.app.psa_b2c_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static String user_ID = "";
    public static String user_name = "";
    public static String user_fullName = "";
    public static String user_emailID = "";
    public static String user_mobile = "";
    public static String user_DOB = "";
    public static String user_city = "";
    public static String user_pro = "";

    static final String mypref = "mypref";
    static final String id = "id";

    Button login;
    Button Reg;
    RequestQueue requestQueue;
    StringRequest request;
    String URL = "http://13.232.207.9/php/user_login.php";
    SharedPreferenceConfig preferenceConfig;

    EditText login_user_name, login_user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());


        login_user_name = (EditText) findViewById(R.id.login_username);
        login_user_password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.main_loginbn);
        Reg = (Button) findViewById(R.id.login_register);

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if (preferenceConfig.readLoginStatus()) {
            if (preferenceConfig.read_user_data() != null) {
                Intent intent = new Intent(LoginActivity.this, PlayerProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }

        requestQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int flag = 1;

                if (TextUtils.isEmpty(login_user_name.getText())) {
                    login_user_name.setError("Username is required");
                    flag = 0;
                } else if (TextUtils.isEmpty(login_user_password.getText())) {
                    login_user_password.setError("Username is required");
                    flag = 0;
                }

                if (flag == 1) {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                if (success == 1) {
                                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject_current = jsonArray.getJSONObject(i);
                                        user_ID = jsonObject_current.getString("User Id");
                                        user_name = jsonObject_current.getString("Username");
                                        user_fullName = jsonObject_current.getString("Name");
                                        user_emailID = jsonObject_current.getString("Email Id");
                                        user_mobile = jsonObject_current.getString("Mobile");
                                        user_DOB = jsonObject_current.getString("DOB");
                                        user_city = jsonObject_current.getString("City");
                                        user_pro = jsonObject_current.getString("Pro");
                                    }
                                    Intent intent = new Intent(LoginActivity.this, PlayerProfileActivity.class);
                                    preferenceConfig.writeLoginStatus(true);
                                    preferenceConfig.write_user_data(user_ID, user_name, user_fullName, user_emailID, user_mobile, user_DOB, user_city, user_pro);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "User not found!!!" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
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
                            hashMap.put("username", login_user_name.getText().toString());
                            hashMap.put("password", login_user_password.getText().toString());

                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
    }
}


