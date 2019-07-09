package com.foodmile.livraison.Livraison.Activitiesproduct;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Adapter.RecyclerViewProduitAdapter;
import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.Livraison.Classes.Produit;
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_INSERT_Produit;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_SHOW_Product;

public class ProduitActivity extends AppCompatActivity implements RecyclerViewProduitAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {
FloatingActionButton button;
    ImageView image;
    Button btnimage;
    EditText nom;
    EditText ingrediants;
    Button btnAdd;
    private Bitmap bitmap;
    SwipeRefreshLayout swipe;
    Button btnAnnuler;
    private String encoding;
    EditText id;
    EditText prix;
    RecyclerView product;
    String idd;
    private SearchView searchView;
    private List<Produit> list;

    SwipeRefreshLayout mSwipeRefreshLayout;
   RecyclerViewProduitAdapter adapter;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
        swipe   = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = new ArrayList<>();
        product = (RecyclerView)findViewById(R.id.recyclerProduct);
        product.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        product.setHasFixedSize(true);
        adapter = new RecyclerViewProduitAdapter(getApplicationContext(),list);
        product.setAdapter(adapter);
        // menamilkan widget refresh
        swipe.setOnRefreshListener(ProduitActivity.this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           list.clear();
                           adapter.notifyDataSetChanged();
                           ShowProduct();
                       }
                   }
        );
        adapter.setOnItemClickListener(ProduitActivity.this);

        button = (FloatingActionButton)findViewById(R.id.addnewproduct);

        Intent i = getIntent();
        idd = i.getStringExtra("idcat");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(ProduitActivity.this);
                View vm = getLayoutInflater().inflate(R.layout.activity_ajouter_produit,null);
                Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
                image = (ImageView)vm.findViewById(R.id.addimageproduct);
                btnimage = (Button)vm.findViewById(R.id.addimagepro);
                btnimage.setTypeface(mycustom1);
                nom = (EditText)vm.findViewById(R.id.addnomproduct);
                nom.setTypeface(mycustom1);
                ingrediants = (EditText)vm.findViewById(R.id.addingrediantsproduct);
                ingrediants.setTypeface(mycustom1);
                prix = (EditText)vm.findViewById(R.id.addprixproduct);
                prix.setTypeface(mycustom1);
                id =(EditText)vm.findViewById(R.id.idcat);
                id.setText(idd);
                btnAdd = (Button)vm.findViewById(R.id.addproduct);
                btnAdd.setTypeface(mycustom1);
                btnAnnuler = (Button)vm.findViewById(R.id.annuleraddproduct);
                btnAnnuler.setTypeface(mycustom1);
                btnimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 100);
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InsertProduit();



                    }
                });




                mbuilder.setView(vm);
                mbuilder.setTitle("Ajouter Produit");
                final AlertDialog alertDialog = mbuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                alertDialog.getWindow().setLayout(700,800);
                btnAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        ShowProduct();
       // searchProduct = (EditText)findViewById(R.id.searchProduct);

    }
    private void ShowProduct() {
        list.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_SHOW_Product+"?id_cat="+idd , (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("hjjkhk", response.toString());
                    JSONArray jsonArray = response.getJSONArray("product");
                        for (int i = 0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Produit c = new Produit();
                            c.setIdproduit(object.getString("idproduit"));
                            c.setNomproduit(object.getString("nomproduit"));
                            c.setIngrediants(object.getString("ingrediants"));
                            c.setPrixproduit(object.getString("prixproduit"));
                            c.setImageproduit(object.getString("imageproduit"));
                            list.add(c);
                            Toast.makeText(ProduitActivity.this, "success", Toast.LENGTH_SHORT).show();

                        }
                        adapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProduitActivity.this, "erreur", Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProduitActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void InsertProduit() {

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        final String name = nom.getText().toString().trim();
        final String ingr = ingrediants.getText().toString().trim();
        final String pr = prix.getText().toString().trim();
        final String idcat = id.getText().toString().trim();

        if (!name.isEmpty() || !ingr.isEmpty() || !pr.isEmpty()  ) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT_Produit, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                     Log.d("gg",response.toString());
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(ProduitActivity.this, "le produit est bien ajouté", Toast.LENGTH_SHORT).show();
                        }else {
                            if (success.equals("2")){
                                Toast.makeText(ProduitActivity.this, "cet produit est déja existé", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ProduitActivity.this, "le produit n'est pas ajouté", Toast.LENGTH_SHORT).show();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProduitActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("imageproduit", encoding);
                    parms.put("nomproduit", name);
                    parms.put("ingrediants", ingr);
                    parms.put("prixproduit",pr);
                    parms.put("id_cat", idcat);

                    return parms;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            nom.setError("entrer le nom de produit");
            ingrediants.setError("entrer les ingredients de produit ");
            prix.setError("entrer le prix de produit");

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            image.setImageURI(uri);
        }
    }


    @Override
    public void OnIntent(int position) {
        Intent i = new Intent(ProduitActivity.this,OperationProduitActivity.class);
        Produit c = list.get(position);
        i.putExtra("idproduit",c.getIdproduit());
        i.putExtra("nomproduit",c.getNomproduit());
        i.putExtra("ingrediants",c.getIngrediants());
        i.putExtra("prixproduit",c.getPrixproduit());
        i.putExtra("imageproduit",c.getImageproduit());
        i.putExtra("id_cat",c.getId_cat());
        startActivity(i);
    }


    @Override
    public void onRefresh() {
        list.clear();
        adapter.notifyDataSetChanged();
        ShowProduct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(ProduitActivity.this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Produit> newList=new ArrayList<>();
        for(Produit listItem : list){
            String name=listItem.getNomproduit().toLowerCase();
            if(name.contains(newText)){
                newList.add(listItem);
            }
        }
        adapter.setFilter(newList);
        return true;
    }

}
