package com.foodmile.livraison.Livraison.Acitivitiesvehicule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Adapter.RecyclerViewVehiculeAdapter;
import com.foodmile.livraison.Livraison.Classes.Vehicule;
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_DELETE_Livreur;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_DELETE_Vehicule;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_INSERT_Vehicule;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_SHOW_Vehicule;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_Update_Vehicule;

public class VehiculeActivity extends AppCompatActivity implements RecyclerViewVehiculeAdapter.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    FloatingActionButton button;
    ImageView image;
    Button btnImage;
    EditText name, nameu;
    EditText matricule, matriculeu;
    Spinner sp;
    Button btnadd;
    Button btnann;
    String encoding;
    Button updatebtn;
    EditText idvehiculee;
    RecyclerView recyclerView;
    RecyclerViewVehiculeAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Vehicule> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicule);
        getSupportActionBar().setTitle("Vehicule Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerVehicles);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_vehicule);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewVehiculeAdapter(VehiculeActivity.this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(VehiculeActivity.this);
        swipeRefreshLayout.setOnRefreshListener(VehiculeActivity.this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        list.clear();
                                        adapter.notifyDataSetChanged();
                                        ShowVehicule();
                                    }
                                }
        );
        button = (FloatingActionButton) findViewById(R.id.addnewvehicles);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(VehiculeActivity.this);
                View vm = getLayoutInflater().inflate(R.layout.addvehicule, null);
                Typeface mycustom1 = Typeface.createFromAsset(getAssets(), "fonts/text.otf");
                image = (ImageView) vm.findViewById(R.id.addimagevehicles);
                btnImage = (Button) vm.findViewById(R.id.btnaddimagevehi);
                btnImage.setTypeface(mycustom1);
                name = (EditText) vm.findViewById(R.id.addnomvehicule);
                name.setTypeface(mycustom1);
                matricule = (EditText) vm.findViewById(R.id.addmatriculevehicule);
                matricule.setTypeface(mycustom1);
                sp = (Spinner) vm.findViewById(R.id.addtype);
                btnadd = (Button) vm.findViewById(R.id.addvehicles);
                btnann = (Button) vm.findViewById(R.id.annuleraddvehicles);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(VehiculeActivity.this, R.array.vehicule, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                sp.setAdapter(adapter);
                btnImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 100);
                    }
                });
                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InsertVehicule();
                    }
                });

                mbuilder.setView(vm);
                mbuilder.setTitle("Ajouter Produit");
                final AlertDialog alertDialog = mbuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                alertDialog.getWindow().setLayout(700, 800);
                btnann.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

    }

    private void ShowVehicule() {
        list.clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_SHOW_Vehicule, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("kjklj", response.toString());
                    JSONArray jsonArray = response.getJSONArray("vehicule");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Vehicule c = new Vehicule();
                        c.setNomvehicule(object.getString("nomvehicule"));
                        c.setMatricule(object.getString("matricule"));
                        c.setType(object.getString("type"));
                        c.setPhoto(object.getString("Photo"));
                        list.add(c);
                        Toast.makeText(VehiculeActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VehiculeActivity.this, "erreur", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VehiculeActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void InsertVehicule() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        encoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        final String nomveh = name.getText().toString().trim();
        final String mat = matricule.getText().toString().trim();
        final String size = sp.getSelectedItem().toString();
        if (!nomveh.isEmpty() || !mat.isEmpty() || !size.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT_Vehicule, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        Log.d("dssd", response.toString());
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(VehiculeActivity.this, "le vehicule est bien ajouté", Toast.LENGTH_SHORT).show();
                        } else {
                            if (success.equals("2")) {
                                Toast.makeText(VehiculeActivity.this, "cet vehicule est déja existé", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(VehiculeActivity.this, "le vehicule n'est pas ajouté", Toast.LENGTH_SHORT).show();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VehiculeActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("Photo", encoding);
                    parms.put("nomvehicule", nomveh);
                    parms.put("matricule", mat);
                    parms.put("type", size);


                    return parms;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            name.setError("entrer le nom de produit");
            matricule.setError("entrer la quantite de produit");
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
    public void onRefresh() {
        list.clear();
        adapter.notifyDataSetChanged();
        ShowVehicule();
    }


    private void UpdateVehicule(int position) {
        final String typee = idvehiculee.getText().toString().trim();
        final String nomv = nameu.getText().toString();
        final String mat = matriculeu.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update_Vehicule, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("trrt", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(VehiculeActivity.this, "la vehicule est bien modifié!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VehiculeActivity.this, "la vehicule n'est pas modifier!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VehiculeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<>();
                parms.put("type", typee);
                parms.put("nomvehicule", nomv);
                parms.put("matricule", mat);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    public void OnEdit(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(VehiculeActivity.this);
        View vm = getLayoutInflater().inflate(R.layout.update_vehicule, null);
        Typeface mycustom1 = Typeface.createFromAsset(getAssets(), "fonts/text.otf");
        idvehiculee = (EditText) vm.findViewById(R.id.idve);
        idvehiculee.setText(list.get(position).getType());
        nameu = (EditText) vm.findViewById(R.id.editnomvehicule);
        nameu.setText(list.get(position).getNomvehicule());
        matriculeu = (EditText) vm.findViewById(R.id.editmatricule);
        matriculeu.setText(list.get(position).getMatricule());
        updatebtn = (Button) vm.findViewById(R.id.editvehicule);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateVehicule(position);
            }
        });
        builder.setView(vm);
        builder.setTitle("Modifier Vehicule");
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.getWindow().setLayout(700, 800);
    }

    @Override
    public void OnDelete(int position) {
        String nom = list.get(position).getNomvehicule();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DELETE_Vehicule + "?nomvehicule=" + nom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(VehiculeActivity.this, "Succes!", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VehiculeActivity.this, "Repeat!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VehiculeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(VehiculeActivity.this);
        requestQueue.add(stringRequest);


    }
}
