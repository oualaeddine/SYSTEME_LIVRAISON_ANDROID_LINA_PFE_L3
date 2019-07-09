package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.foodmile.livraison.Livraison.Activitieslogin.SessionManager;
import com.foodmile.livraison.Livraison.Activitiesproduct.ProduitActivity;
import com.foodmile.livraison.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class LivreurActivity extends AppCompatActivity {
private TabLayout tabLayout;
private ViewPager viewPager;
private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livreur);

        getSupportActionBar().setTitle("Livreur Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
tabLayout = (TabLayout)findViewById(R.id.tablayoutlivreur);

viewPager =(ViewPager)findViewById(R.id.viewpager_id);
adapter = new ViewPagerAdapter(getSupportFragmentManager());
adapter.AddFragment(new addfragment(),"ajouter livreur");
adapter.AddFragment(new UpdateDeletefragment(),"Modifier ou Supprimer livreur");
viewPager.setAdapter(adapter);
tabLayout.setupWithViewPager(viewPager);
tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_add_black_24dp);
tabLayout.getTabAt(1).setIcon(R.drawable.ic_edit_black_24dp);
setCustomFont();

    }
    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/text.otf"));
                }
            }
        }
    }
}
