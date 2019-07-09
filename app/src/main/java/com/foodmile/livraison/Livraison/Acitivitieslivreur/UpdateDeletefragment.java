package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.foodmile.livraison.Livraison.Adapter.RecyclerViewLivreurAdapter;
import com.foodmile.livraison.Livraison.Classes.Livreur;
import com.foodmile.livraison.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_DELETE_Livreur;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_SHOW_livreur;
import static com.foodmile.livraison.Livraison.utils.StringConstants.URL_Update_livreur;

public class UpdateDeletefragment extends Fragment implements RecyclerViewLivreurAdapter.OnItemClickListener,  SwipeRefreshLayout.OnRefreshListener{
    View v;
    SwipeRefreshLayout swipe;
    private RecyclerViewLivreurAdapter adapter;
private RecyclerView recyclerView;
private List<Livreur>list;
    private MenuItem mSearchItem;
    private SearchView sv;

    AlertDialog.Builder dialog;
    EditText idx,nomx;
    EditText prenomx;
    EditText numtelex;
    Button Updatebtn,Deletebtn;
    private ShimmerFrameLayout mShimmerViewContainer;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.update_delete_livreur, container, false);


        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerLivreur);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewLivreurAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        swipe =(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout_livreur);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           list.clear();
                           adapter.notifyDataSetChanged();
                           ShowLivreur();
                       }
                   }
        );

        ShowLivreur();
        return v;


    }


    private void ShowLivreur() {
        list.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_SHOW_livreur, (JSONObject) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Log.d("hjjkhk", response.toString());
                        JSONArray jsonArray = response.getJSONArray("livreur");
                        for (int i = 0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Livreur c = new Livreur();
                            c.setIdliv(object.getString("idliv"));
                            c.setNom(object.getString("nom"));
                            c.setPrenom(object.getString("prenom"));
                            c.setNumtele(object.getString("numtele"));
                            list.add(c);
                            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();


                        }
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Data not login", Toast.LENGTH_SHORT).show();
                        swipe.setRefreshing(false);

                }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list =new ArrayList<>();
setHasOptionsMenu(true);
    }

    @Override
    public void onRefresh() {
        list.clear();
        adapter.notifyDataSetChanged();
        ShowLivreur();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_livreur,menu);
        mSearchItem = menu.findItem(R.id.action_search_livreur);
        sv= (SearchView) mSearchItem.getActionView();
        sv.setIconified(true);
        SearchManager searchManager = (SearchManager)  getActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s=s.toLowerCase();
                ArrayList<Livreur> newList=new ArrayList<>();
                for(Livreur listItem : list){
                    String name=listItem.getNom().toLowerCase();
                    if(name.contains(s)){
                        newList.add(listItem);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

        }






    private void UpdateLivreur(int position) {
        final String idliv = idx.getText().toString();
        final String nomliv = nomx.getText().toString();
        final String prenomliv = prenomx.getText().toString();
        final String numteleliv = numtelex.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Update_livreur, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(getContext(),"Succes!",Toast.LENGTH_SHORT).show();

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
                parms.put("idliv",idliv);
                parms.put("nom",nomliv);
                parms.put("prenom",prenomliv);
                parms.put("numtele",numteleliv);



                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private void DeleteLivreur(int position) {
        final String id = idx.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_Livreur, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("mmmmm",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(getContext(),"Succes!",Toast.LENGTH_SHORT).show();

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



                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);



    }

    @Override
    public void OnDisplay(final int position) {
        Typeface mycusto =Typeface.createFromAsset(getActivity().getAssets(),"fonts/text.otf");
        final String idxx = list.get(position).getIdliv();
        final  String nomxx = list.get(position).getNom();
        final  String prenomxx = list.get(position).getPrenom();
        final  String numxx = list.get(position).getNumtele();
        final androidx.appcompat.app.AlertDialog.Builder mbuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        View vm = getLayoutInflater().inflate(R.layout.update_livreur, null);
        idx = (EditText)vm.findViewById(R.id.updateid);
        idx.setText(idxx);
        nomx = (EditText) vm.findViewById(R.id.updatenom);
        nomx.setText(nomxx);
        nomx.setTypeface(mycusto);
        prenomx = (EditText) vm.findViewById(R.id.updateprenom);
        prenomx.setText(prenomxx);
        prenomx.setTypeface(mycusto);
        numtelex = (EditText) vm.findViewById(R.id.updatenumtele);
        numtelex.setText(numxx);
        numtelex.setTypeface(mycusto);
        Updatebtn = (Button) vm.findViewById(R.id.updatebtnliv);
        Updatebtn.setTypeface(mycusto);
        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateLivreur(position);
            }
        });
        Deletebtn =(Button)vm.findViewById(R.id.deletebtnliv);
        Deletebtn.setTypeface(mycusto);
        Deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteLivreur(position);
            }
        });
        mbuilder.setView(vm);
        mbuilder.setTitle("update livreur");
        final androidx.appcompat.app.AlertDialog alertDialog = mbuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.getWindow().setLayout(700, 1000);



    }
}
