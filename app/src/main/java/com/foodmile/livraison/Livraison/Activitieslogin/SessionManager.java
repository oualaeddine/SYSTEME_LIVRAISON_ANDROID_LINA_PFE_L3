package com.foodmile.livraison.Livraison.Activitieslogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0 ;
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN ="IS_LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String IMAGE = "IMAGE";
    public static final String IDGer ="IDGERNT";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN",PRIVATE_MODE);
         editor = sharedPreferences.edit();
    }
    public void createSession( String username,String password,String image,String idger){
        editor.putBoolean("LOGIN",true);
        editor.putString(USERNAME,username);
        editor.putString(PASSWORD,password);
     //   editor.putString(ID,id);
        editor.putString(IMAGE,image);
        editor.putString(IDGer,idger);

        editor.apply();
    }



    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,true);
    }
    public void checkLogin(){
        if(!this.isLogin()) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();
        }

        }



        public HashMap<String,String> getUserDetails() {
       HashMap<String,String> user = new HashMap<>();
       user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
            user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));
      user.put(IMAGE,sharedPreferences.getString(IMAGE,null));
      user.put(IDGer,sharedPreferences.getString(IDGer,null));
       return user;

        }


        public void logout(){
        editor.clear();
        editor.commit();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();
        }

}
