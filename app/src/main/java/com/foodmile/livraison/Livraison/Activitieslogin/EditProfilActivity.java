package com.foodmile.livraison.Livraison.Activitieslogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_EDIT_profile;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_READ_profile;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_UPLAOD_profile;

public class EditProfilActivity extends AppCompatActivity {
    private static final String TAG = EditProfilActivity.class.getSimpleName();
    SessionManager sessionManager;
    EditText username;
    private Menu action;
    EditText password;
    EditText number;
    ImageView image;
    Button imagebtn;
    String getImage;
 private Bitmap bitmap;

    String getIdGerant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Profil Acitivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_profil);
        username =  findViewById(R.id.editusername);
        password =  findViewById(R.id.editpassword);
        image = (ImageView)findViewById(R.id.imageprofil);
        imagebtn = (Button)findViewById(R.id.editimage);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetails();
       // getIdCompte = user.get(sessionManager.ID);
       getIdGerant = user.get(sessionManager.IDGer);

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              chooseFile();
            }
        });

        Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        username.setTypeface(mycustom1);

        Typeface mycustom2 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        password.setTypeface(mycustom2);

        Typeface mycustom4 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        imagebtn.setTypeface(mycustom4);
    }
    private void getUserDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response.toString());
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
                  username.setText(unamee);
                  password.setText(upass);                       }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfilActivity.this,"Data not login",Toast.LENGTH_SHORT).show();
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
                parms.put("idger",getIdGerant);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_profil,menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                username.setFocusableInTouchMode(true);
                password.setFocusableInTouchMode(true);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(username,InputMethodManager.SHOW_IMPLICIT);
                inputMethodManager.showSoftInput(password,inputMethodManager.SHOW_IMPLICIT);
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                return true;
            case R.id.menu_save:
                SaveEditDetails();
                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
                username.setFocusableInTouchMode(false);
                password.setFocusableInTouchMode(false);
                username.setFocusable(false);
               password.setFocusable(false);

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void SaveEditDetails() {

        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String id= getIdGerant;

         final  String image = getImage;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("hhdhd",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                     Toast.makeText(EditProfilActivity.this,"Succes!",Toast.LENGTH_SHORT).show();
                     sessionManager.createSession(username,password,image,id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfilActivity.this,"Repeat!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfilActivity.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("Username",username);
                parms.put("Password",password);
                parms.put("idger",id);

                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 &&resultCode == RESULT_OK && data != null && data.getData() !=null){
            Uri filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
               image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(getIdGerant,getStringImage(bitmap));
        }
    }
    private void UploadPicture(final String getId, final String stringImage) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLAOD_profile, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          Log.d(TAG,response.toString());
          try {
              JSONObject jsonObject = new JSONObject(response);
              String success = jsonObject.getString("success");
              if (success.equals("1")){
                  Toast.makeText(EditProfilActivity.this,"Success!",Toast.LENGTH_SHORT).show();
              }


          } catch (JSONException e) {
              e.printStackTrace();
              Toast.makeText(EditProfilActivity.this," not Success!",Toast.LENGTH_SHORT).show();
          }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(EditProfilActivity.this,"error!",Toast.LENGTH_SHORT).show();
        }
    }){
        @Override
        protected Map<String, String> getParams() {
            Map<String,String> parms = new HashMap<>();
            parms.put("idger",getIdGerant);
            parms.put("Image",stringImage);
            return parms;
        }
    };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] arrayImage = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(arrayImage,Base64.DEFAULT);

        return encodedImage;
    }


}
