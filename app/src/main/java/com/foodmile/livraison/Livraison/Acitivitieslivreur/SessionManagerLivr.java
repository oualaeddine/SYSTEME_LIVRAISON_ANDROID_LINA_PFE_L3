package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.foodmile.livraison.Livraison.Activitieslogin.LoginActivity;

import java.util.HashMap;

public class SessionManagerLivr {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0 ;
    public  static final String IDliv ="IDliv";
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN ="IS_LOGIN";
    public static final String USERNAMELIV = "USERNAME";
    public static final String PASSWORDLIV = "PASSWORD";
    public static final String IMAGELIV = "IMAGE";

    public static final String IdGer = "IDger";
    public SessionManagerLivr(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession( String idlivr,String username,String password,String image){
        editor.putBoolean("LOGIN",true);
        editor.putString(IDliv,idlivr);
        editor.putString(USERNAMELIV,username);
        editor.putString(PASSWORDLIV,password);
        editor.putString(IMAGELIV,image);


        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,true);
    }


    public void checkLogin(){
        if(!this.isLogin()) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((HomeLivreurActivity) context).finish();
        }

    }

    public HashMap<String,String> getUserDetails() {
        HashMap<String,String> user = new HashMap<>();
        user.put(IDliv,sharedPreferences.getString(IDliv,null));
        user.put(USERNAMELIV,sharedPreferences.getString(USERNAMELIV,null));
        user.put(PASSWORDLIV,sharedPreferences.getString(PASSWORDLIV,null));
        user.put(IMAGELIV,sharedPreferences.getString(IMAGELIV,null));
        user.put(IdGer,sharedPreferences.getString(IdGer,null));
        return user;

    }


    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((HomeLivreurActivity) context).finish();
    }
}
