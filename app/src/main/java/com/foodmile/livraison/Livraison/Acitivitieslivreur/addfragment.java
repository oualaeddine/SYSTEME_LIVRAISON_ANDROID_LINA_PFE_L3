package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Activitieslogin.SessionManager;
import com.foodmile.livraison.Livraison.Activitiesproduct.CategorieActivity;
import com.foodmile.livraison.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_INSERT_Livreur;

public class addfragment extends Fragment{
    View v ;
    TextView nom;
    TextView prenom;
    TextView numtelephone;
    TextView UserName;
    TextView PassWord;
    EditText name;
    EditText surname;
    EditText numtele;
    EditText username;
    EditText password;
    Button btn;
    SessionManager sessionManager;
    String getId;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currenthour;
    int currentminute;
    String m;

    public addfragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.add_livreur,container,false);
        Typeface mycusto =Typeface.createFromAsset(getActivity().getAssets(),"fonts/text.otf");
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetails();
        // getIdCompte = user.get(sessionManager.ID);
        getId = user.get(sessionManager.IDGer);
        nom =(TextView)v.findViewById(R.id.nomlivreur);
        nom.setTypeface(mycusto);
        prenom =(TextView)v.findViewById(R.id.prenomlivreur);
        prenom.setTypeface(mycusto);
        numtelephone =(TextView)v.findViewById(R.id.numtelelivreur);
        numtelephone.setTypeface(mycusto);
        UserName =(TextView)v.findViewById(R.id.usernamelivreur);
        UserName.setTypeface(mycusto);
        PassWord =(TextView)v.findViewById(R.id.passwordlivreur);
        PassWord.setTypeface(mycusto);
        name=(EditText)v.findViewById(R.id.addnom);
        name.setTypeface(mycusto);
        surname=(EditText)v.findViewById(R.id.addprenom);
        surname.setTypeface(mycusto);
        numtele=(EditText)v.findViewById(R.id.addnumtele);
        numtele.setTypeface(mycusto);

        username =(EditText)v.findViewById(R.id.addusername);
        username.setTypeface(mycusto);
        password=(EditText)v.findViewById(R.id.addpassword);
        password.setTypeface(mycusto);
;
        btn = (Button)v.findViewById(R.id.addnewlivreur);
        btn.setTypeface(mycusto);
        btn.animate().setDuration(250).start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertLivreur();
            }
        });

        return v;
    }

    private void InsertLivreur() {
        final String nom = name.getText().toString().trim();
        final  String prenom = surname.getText().toString().trim();
        final  String numtel = numtele.getText().toString().trim();
        final  String users = username.getText().toString().trim();
        final  String pass = password.getText().toString().trim();

        if (!nom.isEmpty() || !prenom.isEmpty() || !numtel.isEmpty()|| !users.isEmpty() || !pass.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT_Livreur, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(getContext(), "le livreur est bien ajouté", Toast.LENGTH_SHORT).show();

                        }else {
                            if(success.equals("2")){
                                Toast.makeText(getContext(), "ce livreur existe déja", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "le livreur n'est pas bien ajouté", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Registererror", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("nom", nom);
                    parms.put("prenom", prenom);
                    parms.put("numtele", numtel);
                    parms.put("Username", users);
                    parms.put("Password", pass);
                    parms.put("idGerant",getId);


                    return parms;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }else {
            name.setError("");

        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





}
