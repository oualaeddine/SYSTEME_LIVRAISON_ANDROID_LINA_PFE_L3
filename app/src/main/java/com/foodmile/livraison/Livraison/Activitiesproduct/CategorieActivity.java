package com.foodmile.livraison.Livraison.Activitiesproduct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Activitieslogin.SessionManager;
import com.foodmile.livraison.Livraison.Adapter.RecyclerViewAdapter;
import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_DELETE_Cat;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_INSERT_CATEGORIE;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_SHOW_CAT;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_Update_cat;

public class CategorieActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener  {
    ViewFlipper v;
    EditText Nomcat;
    Button SuppCat;
    SwipeRefreshLayout swipe;
    Button AnnulerCat;

    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private FloatingActionButton buttonAdd;
    private List<Categorie> list;
    String getId;
    private Bitmap bitmap;
    private String encoding;
    TextView t;
    EditText nom;
    ImageView image;
    Button btnImage;
    private static int c = 100;
    private static int m = 200;
    RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    int[] images = {R.drawable.pizza, R.drawable.tacos, R.drawable.panini, R.drawable.jus};
    ImageView ModifImagecat;
    Button ModifierImageCat;
    EditText NameCat;
    Button ModifierCat;
    Button AnnModif;
    EditText idUpdateCat;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorie);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetails();
        getId = user.get(sessionManager.IDGer);
       // getSupportActionBar().setTitle("Ajouter Produit Activity");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshCat);
        v = (ViewFlipper)findViewById(R.id.viewFlip);
        list = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
         recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(recyclerViewAdapter);
        swipe.setOnRefreshListener(CategorieActivity.this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           list.clear();
                           recyclerViewAdapter.notifyDataSetChanged();
                           jsonrequest();
                       }
                   }
        );

   recyclerViewAdapter.setOnItemClickListener(CategorieActivity.this);
        jsonrequest();
      for(int i = 0;i<images.length;i++){
          flip_image(images[i]);
      }


    buttonAdd = (FloatingActionButton) findViewById(R.id.ajoutCat);
      buttonAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           final AlertDialog.Builder mbuilder = new AlertDialog.Builder(CategorieActivity.this);
              View vm = getLayoutInflater().inflate(R.layout.acitivity_add_cat,null);
              nom =(EditText)vm.findViewById(R.id.addnomcat);
              Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
              nom.setTypeface(mycustom1);
              image = (ImageView)vm.findViewById(R.id.imagecategorie);
              btnImage = (Button)vm.findViewById(R.id.addimagecategorie);
              btnImage.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                      startActivityForResult(i, 100);
                  }
              });
              Button btn =(Button)vm.findViewById(R.id.addbuttoncat);
              Typeface mycustom3 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
              btnImage.setTypeface(mycustom1);
              btn.setTypeface(mycustom1);
              btn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      InsertCategorie();
                  }
              });

              mbuilder.setView(vm);
              mbuilder.setTitle("Ajouter Categorie");
              final AlertDialog alertDialog = mbuilder.create();
              alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
              alertDialog.show();

              Button btn1 = (Button)vm.findViewById(R.id.anuuleradd);
              Typeface mycustom4 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
              btn1.setTypeface(mycustom4);
              btn1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      alertDialog.dismiss();
                  }
              });
          }
      });




          }







    private void UpdateCategorie(int position) {
        final String id = idUpdateCat.getText().toString().trim();
        final String nom = NameCat.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update_cat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(CategorieActivity.this,"categorie est bien modifié!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CategorieActivity.this,"n'est pas modifieé",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategorieActivity.this,"Erreur!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("idcat",id);
                parms.put("nomcat",nom);


                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void jsonrequest() {
       list.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        JsonObjectRequest  stringRequest = new JsonObjectRequest(Request.Method.GET, URL_SHOW_CAT, (JSONObject) null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("hdhdh",response.toString());
                    JSONArray jsonArray = response.getJSONArray("categorie");
                        for (int i =0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Categorie c = new Categorie();
                            c.setIdcat(object.getString("idcat"));
                            c.setNomcat(object.getString("nomcat"));
                            c.setImagecat(object.getString("imagecat"));
                            list.add(c);
                           Toast.makeText(CategorieActivity.this, "success", Toast.LENGTH_SHORT).show();

                        }
                    recyclerViewAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CategorieActivity.this,"Data not login",Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategorieActivity.this,"error!",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





    private void flip_image(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v.addView(imageView);
        v.setFlipInterval(4000);
        v.setAutoStart(true);
        v.setInAnimation(this,R.anim.slider_in_left);


    }


    private void InsertCategorie() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        final String name = nom.getText().toString().trim();

        if (!name.isEmpty() || encoding.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT_CATEGORIE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(CategorieActivity.this, "categorie est bien ajouté", Toast.LENGTH_SHORT).show();
                        }else {
                            if(success.equals("2")){
                                Toast.makeText(CategorieActivity.this, "cet categorie deja existé", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CategorieActivity.this, "categoria n'est pas ajouté", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CategorieActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("imagecat", encoding);
                    parms.put("nomcat", name);
                    parms.put("id_gerant",getId);


                    return parms;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
       nom.setError("entrer le nom de categorie");

    }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == c && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            image.setImageURI(uri);
        }


    }





    @Override
    public void OnUpdate(final int position) {
        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(CategorieActivity.this);
        View vm = getLayoutInflater().inflate(R.layout.activity_update_cat,null);
        idUpdateCat =(EditText)vm.findViewById(R.id.id_catUpdate);
        idUpdateCat.setText(list.get(position).getIdcat());
        Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        NameCat =(EditText)vm.findViewById(R.id.modifierNomCat);
        NameCat.setText(list.get(position).getNomcat());
        NameCat.setTypeface(mycustom1);
        ModifierCat =(Button)vm.findViewById(R.id.ModifierCategories);
        ModifierCat.setTypeface(mycustom1);
        ModifierCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCategorie(position);
            }
        });
        mbuilder.setView(vm);
        mbuilder.setTitle("Modifier Categorie");
        final AlertDialog alertDialog = mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        AnnModif = (Button)vm.findViewById(R.id.AnnulerUpdate);
        AnnModif.setTypeface(mycustom1);
        AnnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void OnIntent(int position) {
        Intent i = new Intent(CategorieActivity.this,ProduitActivity.class);
        Categorie c = list.get(position);
        i.putExtra("idcat",c.getIdcat());
        startActivity(i);
    }

    @Override
    public void OnDelete(final int position) {
        final String id = list.get(position).idcat;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_Cat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("ll",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(CategorieActivity.this,"categorie est bien supprimé!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CategorieActivity.this,"categorie n'est pas supprimer!",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategorieActivity.this,"Erreur!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parms = new HashMap<>();
                parms.put("idcat",id);



                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onRefresh() {
        list.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        jsonrequest();
    }








}

