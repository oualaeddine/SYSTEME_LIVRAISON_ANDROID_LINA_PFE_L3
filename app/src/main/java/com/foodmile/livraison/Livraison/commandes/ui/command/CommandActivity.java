package com.foodmile.livraison.Livraison.commandes.ui.command;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.foodmile.livraison.Livraison.commandes.system.models.Command;
import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.SimpleFragmentPagerAdapter;
import com.foodmile.livraison.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class CommandActivity extends AppCompatActivity {

    public static Command currentCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        Toolbar toolbar = findViewById(R.id.toolbar_command);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.vp_command);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tl_tabs);
        tabLayout.setupWithViewPager(viewPager);

        currentCommand = new Command();
    }
}
