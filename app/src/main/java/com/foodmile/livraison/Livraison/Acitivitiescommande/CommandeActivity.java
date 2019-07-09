package com.foodmile.livraison.Livraison.Acitivitiescommande;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import com.foodmile.livraison.R;

public class CommandeActivity extends AppCompatActivity {
    TextView text1;
    TextView text2;
    TextView text3;
    GridLayout maingrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        getSupportActionBar().setTitle("Commande Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        text1 = (TextView)findViewById(R.id.textcomm1);
        Typeface mycustom1 =Typeface.createFromAsset(getAssets(),"fonts/matilda.ttf");
        text1.setTypeface(mycustom1);
        text2 = (TextView)findViewById(R.id.textcomm2);
        Typeface mycustom2 =Typeface.createFromAsset(getAssets(),"fonts/matilda.ttf");
        text2.setTypeface(mycustom2);
        text3 = (TextView)findViewById(R.id.textcomm3);
        Typeface mycustom3 =Typeface.createFromAsset(getAssets(),"fonts/matilda.ttf");
        text3.setTypeface(mycustom3);

    }
}
