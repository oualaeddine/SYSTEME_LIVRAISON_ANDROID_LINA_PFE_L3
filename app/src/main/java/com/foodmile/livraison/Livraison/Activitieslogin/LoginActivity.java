package com.foodmile.livraison.Livraison.Activitieslogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Acitivitieslivreur.HomeLivreurActivity;
import com.foodmile.livraison.Livraison.Acitivitieslivreur.SessionManagerLivr;
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_LOGIN;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_LOGIN_liv;

public class LoginActivity extends AppCompatActivity {
private EditText username;
private EditText password;
private Button btn ;
TextView text,rest,adress,telephone ;
SessionManager sessionManager;
SessionManagerLivr sessionManagerLivr;
Animation btnanim,edituser,editpass;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typeface mycusto =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        sessionManager = new SessionManager(this);
        sessionManagerLivr =new SessionManagerLivr(this);
        text =(TextView)findViewById(R.id.imageView3);
        text.setTypeface(mycusto);
        rest = (TextView)findViewById(R.id.res);
        rest.setTypeface(mycusto);
        adress = (TextView)findViewById(R.id.adress);
        adress.setTypeface(mycusto);
        telephone =(TextView)findViewById(R.id.telephone);
        telephone.setTypeface(mycusto);
        username = (EditText)findViewById(R.id.username);
        username.setTypeface(mycusto);
        password =(EditText)findViewById(R.id.password);
        password.setTypeface(mycusto);
        btn = (Button)findViewById(R.id.login);
        btn.setTypeface(mycusto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnanim = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.anim_button);
                btn.setAnimation(btnanim);

                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(!user.isEmpty() || !pass.isEmpty()){
                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("Logging in. Please wait.");
                    dialog.show();
                   Login(user,pass);
                LoginLivreur(user,pass);
                }else {
                    username.setError("please insert your username");
                    password.setError("please insert your password");
            }
        }


            });
    }

    private void LoginLivreur(final String usern, final String passw) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN_liv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("loginliv");
                    if(success.equals("1")){
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String idm= object.getString("idliv");
                            String namee = object.getString("Username").trim();
                            String motpasss = object.getString("Password").trim();
                            String imagee = object.getString("Image").trim();
                            sessionManagerLivr.createSession(idm,namee,motpasss,imagee);
                            Toast.makeText(LoginActivity.this,"",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeLivreurActivity.class);
                            intent.putExtra("Username",namee);
                            intent.putExtra("Image",imagee);
                            startActivity(intent);


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"Data not login",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> parms = new HashMap<>();
                parms.put("Username",usern);
                parms.put("Password",passw);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void Login(final String usernam, final String passwor) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if(success.equals("1")){
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String idGerant = object.getString("idger");
                            String uname = object.getString("Username").trim();
                            String motpass = object.getString("Password").trim();
                            String image = object.getString("Image").trim();
                           sessionManager.createSession(uname,motpass,image,idGerant);
                           // Toast.makeText(LoginActivity.this,"Data login",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("Username",uname);
                            intent.putExtra("Image",image);
                            startActivity(intent);


                        }
                    }else{
                        Toast.makeText(LoginActivity.this,"username ou password ou les deux sont fautes",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"Data not login",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> parms = new HashMap<>();
                parms.put("Username",usernam);
                parms.put("Password",passwor);

                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
