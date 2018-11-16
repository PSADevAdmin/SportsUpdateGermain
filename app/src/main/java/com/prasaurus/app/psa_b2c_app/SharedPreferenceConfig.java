package com.prasaurus.app.psa_b2c_app;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {

    private SharedPreferences sharedPreferences;

    private Context context;

    public SharedPreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference),Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference),status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference),false);
        return status;
    }

    public void write_user_data(String user_id,String user_name, String user_fullname, String user_email, String user_mobile, String user_dob, String user_city, String user_pro){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id",user_id);
        editor.putString("user_name",user_name);
        editor.putString("user_fullname",user_fullname);
        editor.putString("user_email",user_email);
        editor.putString("user_mobile",user_mobile);
        editor.putString("user_dob",user_dob);
        editor.putString("user_city",user_city);
        editor.putString("user_pro",user_pro);
        editor.apply();
        editor.commit();
    }

    public String[] read_user_data(){
        String arr[] = new String[8];
        arr[0] = sharedPreferences.getString("user_id","");
        arr[1] = sharedPreferences.getString("user_name","");
        arr[2] = sharedPreferences.getString("user_fullname","");
        arr[3] = sharedPreferences.getString("user_email","");
        arr[4] = sharedPreferences.getString("user_mobile","");
        arr[5] = sharedPreferences.getString("user_dob","");
        arr[6] = sharedPreferences.getString("user_city","");
        arr[7] = sharedPreferences.getString("user_pro","");
        return arr;
    }

    public void write_user_stats_matches(String matches){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_matches",matches);
        editor.apply();
        editor.commit();
    }

    public String read_user_stats_matches(){
        String matches;
        matches = sharedPreferences.getString("user_matches","0");
        return matches;
    }

    public void write_user_stats_goals(String goals){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_goals",goals);
        editor.apply();
        editor.commit();
    }

    public String read_user_stats_goals(){
        String goals;
        goals = sharedPreferences.getString("user_goals","0");
        return goals;
    }

    public void write_user_stats_assists(String assists){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_assists",assists);
        editor.apply();
        editor.commit();
    }

    public String read_user_stats_assists(){
        String assists;
        assists = sharedPreferences.getString("user_assists","0");
        return assists;
    }

    public void write_user_stats_positon(String position){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_position",position);
        editor.apply();
        editor.commit();
    }

    public String read_user_stats_position(){
        String position;
        position = sharedPreferences.getString("user_position","ST");
        return position;
    }

    public void write_user_stats_jersey(int jersey){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_jersey",jersey);
        editor.apply();
        editor.commit();
    }

    public int read_user_stats_jersey(){
        int jersey;
        jersey = sharedPreferences.getInt("user_jersey",0);
        return jersey;
    }

}
