package com.foodmile.livraison.Livraison.Activitieslogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.foodmile.livraison.Livraison.Acitivitieslivreur.LivreurActivity;
import com.foodmile.livraison.Livraison.Acitivitiesvehicule.VehiculeActivity;
import com.foodmile.livraison.Livraison.Activitiesproduct.CategorieActivity;
import com.foodmile.livraison.Livraison.utils.StringConstants;
import com.foodmile.livraison.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView textView;
    private RequestQueue mRequestQueue;
    ImageView imageView;
    GridLayout gridLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        textView = (TextView) findViewById(R.id.usernamee);
        Typeface mycusto = Typeface.createFromAsset(getAssets(), "fonts/text.otf");
        imageView = (ImageView) findViewById(R.id.imageGerant);
        HashMap<String, String> user = sessionManager.getUserDetails();
        String Username = user.get(sessionManager.USERNAME);
        String Password = user.get(sessionManager.PASSWORD);
        String Image = user.get(sessionManager.IMAGE);
        String id = user.get(sessionManager.IDGer);
        textView.setText(Username);
        Picasso.get().load(StringConstants.PHOTO_PROFILE + id + ".jpeg").noFade().into(imageView);
        textView.setTypeface(mycusto);
        gridLayout =(GridLayout)findViewById(R.id.gridlayoutgerant);
        setSingleGrid(gridLayout);
        /*CircleMenu circle = (CircleMenu)findViewById(R.id.menu);
        circle.setMainMenu(Color.parseColor("#FFFFFF"),R.drawable.ic_add,R.drawable.ic_close)
                .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.produit)
                .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.commande)
                .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.livreur)
                .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.vehicule)
                .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.gpsc)
                .setOnMenuSelectedListener(new OnMenuSelectedListener(){
                    @Override
                    public void onMenuSelected(int i) {
                        switch (i){
                            case 0:
                                Toast toast = new Toast(getApplicationContext());
                                View v = LayoutInflater.from(HomeActivity.this).inflate(R.layout.customtoast,null);
                                TextView text = v.findViewById(R.id.toasticon);
                                text.setText("Gerer les produits");
                                toast.setView(v);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.show();
                                Intent m = new Intent(HomeActivity.this, CategorieActivity.class);
                                startActivity(m);
                                break;
                            case 1:
                                Toast toast1 = new Toast(getApplicationContext());
                                View v1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.customtoast,null);
                                TextView text1 = v1.findViewById(R.id.toasticon);
                                text1.setText("Gerer les commandes");
                                toast1.setView(v1);
                                toast1.setDuration(Toast.LENGTH_LONG);
                                toast1.show();
                                Intent mm = new Intent(HomeActivity.this, CommandeActivity.class);
                                startActivity(mm);
                                break;
                            case 2:
                                Toast toastt = new Toast(getApplicationContext());
                                View vv = LayoutInflater.from(HomeActivity.this).inflate(R.layout.customtoast,null);
                                TextView textt = vv.findViewById(R.id.toasticon);
                                textt.setText("Gerer les livreurs");
                                toastt.setView(vv);
                                toastt.setDuration(Toast.LENGTH_LONG);
                                toastt.show();
                                Intent n = new Intent(HomeActivity.this, LivreurActivity.class);
                                startActivity(n);
                                break;
                            case 3:
                                Toast toast2 = new Toast(getApplicationContext());
                                View vv2 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.customtoast,null);
                                TextView textt2 = vv2.findViewById(R.id.toasticon);
                                textt2.setText("Gerer les véhicules");
                                toast2.setView(vv2);
                                toast2.setDuration(Toast.LENGTH_LONG);
                                toast2.show();
                                Intent nn = new Intent(HomeActivity.this, VehiculeActivity.class);
                                startActivity(nn);
                                break;
                        }


                    }
                });


    }*/
    }

    private void setSingleGrid(GridLayout gridLayout) {
        for (int i = 0; i<gridLayout.getChildCount();i++){
            final CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0 ){
                        if (cardView.getCardBackgroundColor().getDefaultColor() == -1){
                            cardView.setCardBackgroundColor(Color.parseColor("#FF5D51"));
                            Intent i = new Intent(HomeActivity.this,CategorieActivity.class);
                            startActivity(i);
                        }else {
                            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                        }
                    }
                    if (finalI == 1 ){
                        if (cardView.getCardBackgroundColor().getDefaultColor() == -1){
                            cardView.setCardBackgroundColor(Color.parseColor("#FF5D51"));
                            Intent i = new Intent(HomeActivity.this, LivreurActivity.class);
                            startActivity(i);
                        }else {
                            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                        }
                    }
                    if (finalI == 2 ){
                        if (cardView.getCardBackgroundColor().getDefaultColor() == -1){
                            cardView.setCardBackgroundColor(Color.parseColor("#FF5D51"));
                            Intent i = new Intent(HomeActivity.this, VehiculeActivity.class);
                            startActivity(i);
                        }else {
                            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                        }
                    }

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(HomeActivity.this, EditProfilActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                sessionManager.logout();
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}