package com.foodmile.livraison.Livraison.Activitiesproduct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_DELETE_Product;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_UPLAOD_Product;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_Update_Product;

public class OperationProduitActivity extends AppCompatActivity {
String id;
String NameProduct;
String ingr;
String prix;
String image;
ImageView imageProduct;
Button EditImage;
Button editImage;
String encoding;
EditText NomProduct;
EditText ingred;
EditText prixProduit;
Button updateProduct;
String idd;
Button btnSupp;
Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_produit);
        Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        NomProduct = (EditText)findViewById(R.id.editnomproduit);
        NomProduct.setTypeface(mycustom1);
ingred = (EditText)findViewById(R.id.editingrediants);
        ingred.setTypeface(mycustom1);
prixProduit =(EditText)findViewById(R.id.editprixproduit);
        prixProduit.setTypeface(mycustom1);
imageProduct =(ImageView)findViewById(R.id.editimageproduct);

editImage =(Button)findViewById(R.id.editImage);
        editImage.setTypeface(mycustom1);
updateProduct =(Button)findViewById(R.id.Modifierproduct);
        updateProduct.setTypeface(mycustom1);
btnSupp = (Button)findViewById(R.id.supprimerProduit);
        btnSupp.setTypeface(mycustom1);
EditImage = (Button)findViewById(R.id.editImage);
        EditImage.setTypeface(mycustom1);
EditImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }
});
        Intent i = getIntent();
        id = i.getStringExtra("idproduit");
        NameProduct =i.getStringExtra("nomproduit");

        ingr = i.getStringExtra("ingrediants");
        prix = i.getStringExtra("prixproduit");
        image = i.getStringExtra("imageproduit");
        idd = i.getStringExtra("id_cat");
        NomProduct.setText(NameProduct);

        ingred.setText(ingr);
        prixProduit.setText(prix);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProduct();
            }
        });
        btnSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupprimerProduit();
            }
        });

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
                imageProduct.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPicture(id,getStringImage(bitmap));
        }
    }
    private void UploadPicture(final String getId, final String stringImage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLAOD_Product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kjkjkls",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(OperationProduitActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OperationProduitActivity.this," not Success!",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OperationProduitActivity.this,"error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parms = new HashMap<>();
                parms.put("idproduit",id);
                parms.put("imageproduit",stringImage);
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
    private void SupprimerProduit() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_Product+"?idproduit" +id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(OperationProduitActivity.this,"le produit est supprimé!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OperationProduitActivity.this,"le produit n'est pas supprimé!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OperationProduitActivity.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("idproduit",id);



                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    private void EditProduct() {
        Bitmap bitmap = ((BitmapDrawable) imageProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        final String NameP = NomProduct.getText().toString();

final String ing = ingred.getText().toString();
final String prix = prixProduit.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update_Product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(OperationProduitActivity.this,"le produit est bien modifié!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OperationProduitActivity.this,"le produit n'est pas modifier!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OperationProduitActivity.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("idproduit",id);
                parms.put("nomproduit",NameP);
               parms.put("ingrediants",ing);
                parms.put("prixproduit",prix);
                parms.put("imageproduit",encoding);


                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
