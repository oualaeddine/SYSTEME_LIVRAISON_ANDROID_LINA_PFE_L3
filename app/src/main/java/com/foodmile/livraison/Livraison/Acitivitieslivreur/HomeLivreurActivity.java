package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.foodmile.livraison.Livraison.Activitieslogin.SessionManager;
import com.foodmile.livraison.Livraison.MapsFragment;
import com.foodmile.livraison.Livraison.utils.StringConstants;
import com.foodmile.livraison.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeLivreurActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SessionManagerLivr sessionManager;
    CircularImageView image;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_livreur);
        sessionManager = new SessionManagerLivr(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        String username = user.get(sessionManager.USERNAMELIV);
        String Password = user.get(sessionManager.PASSWORDLIV);
        String id = user.get(sessionManager.IDliv);
        String Image = user.get(sessionManager.IMAGELIV);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() > 0 ) {
                for (int j=0; j <subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        Typeface mycusto =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/text.otf");
        View hView =  navigationView.getHeaderView(0);
        textView = (TextView)hView.findViewById(R.id.UsernameLivreurr);
        textView.setText(username);
        textView.setTypeface(mycusto);

        image =(CircularImageView)hView.findViewById(R.id.ImageProfilLivreur);
        Picasso.with(this).load(StringConstants.PHOTO_LIVREUR+ id +".jpeg").noFade().into(image);
        navigationView.setNavigationItemSelectedListener(this);

if(savedInstanceState == null){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapsFragment()).commit();
    navigationView.setCheckedItem(R.id.nav_gps);
}


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_gps) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MapsFragment()).commit();
        } else if (id == R.id.nav_share) {

           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EditProfilLivr()).commit();

        } else if (id == R.id.nav_send) {
          sessionManager.logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/text.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}
