package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.utils.StringConstants;
import com.foodmile.livraison.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_EDIT_Livreur;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_UPLAOD_Livreur;

public class EditProfilLivr extends Fragment {
    View v;
    SessionManagerLivr sessionManagerLivr;
    EditText usern,pass;
    Button btnupdate,btnimage;
    ImageView imageView;
    String getIdLivreur;
    String getImage;
    String getUsername;
    String Password;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_edit_profil,container,false);
        usern = v.findViewById(R.id.UpdateUsernameLivr);
        pass =  v.findViewById(R.id.UpdatePasswordLivr);
        sessionManagerLivr = new SessionManagerLivr(getContext());
        sessionManagerLivr.checkLogin();
        HashMap<String,String> user = sessionManagerLivr.getUserDetails();
     getIdLivreur = user.get(sessionManagerLivr.IDliv);
        imageView =v.findViewById(R.id.UpdateImageProfilLiv);
        Picasso.get().load(StringConstants.PHOTO_LIVREUR + getIdLivreur + ".jpeg").noFade().into(imageView);
        btnupdate = v.findViewById(R.id.btnUpdateProfil);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SaveEditDetails();
            }
        });
        btnimage = v.findViewById(R.id.btnUpdateImageProfil);
        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        return v;

    }
    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode == Activity.RESULT_OK && data != null && data.getData() !=null){
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(getIdLivreur,getStringImage(bitmap));
        }
    }
    private void UploadPicture(final String getId, final String stringImage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLAOD_Livreur, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kjlsldls",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(getContext(),"Success!",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext()," not Success!",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parms = new HashMap<>();
                parms.put("idliv",getIdLivreur);
                parms.put("Image",stringImage);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] arrayImage = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(arrayImage,Base64.DEFAULT);

        return encodedImage;
    }


    private void getUserDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, StringConstants.URL_READ_Livreur, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gfgdf",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");
                    if(success.equals("1")){
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String unamee = object.getString("Username").trim();
                            String upass = object.getString("Password").trim();
                            String imagee = object.getString("Image").trim();
                            Toast.makeText(getContext(),"Data login",Toast.LENGTH_SHORT).show();
                            usern.setText(unamee);
                            pass.setText(upass);                       }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Data not login",Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<>();
                parms.put("idliv",getIdLivreur);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


   private void SaveEditDetails() {

        final String username = this.usern.getText().toString().trim();
        final String password = this.pass.getText().toString().trim();
          final String id = getIdLivreur;
          final  String image = getImage;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_Livreur, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("hhdhd",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(getContext(),"Succes!",Toast.LENGTH_SHORT).show();
                       sessionManagerLivr.createSession(username,password,id,image);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Repeat!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("idliv",id);
                parms.put("Username",username);
                parms.put("Password",password);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDetails();
    }
}





