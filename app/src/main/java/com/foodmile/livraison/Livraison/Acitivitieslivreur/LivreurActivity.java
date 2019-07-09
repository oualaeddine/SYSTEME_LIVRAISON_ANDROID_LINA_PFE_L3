package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.graphics.Typeface;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodmile.livraison.R;

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
