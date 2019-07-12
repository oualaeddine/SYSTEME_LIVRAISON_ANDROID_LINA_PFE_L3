package com.foodmile.livraison.Livraison.commandes.ui.command.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.CommandRecapFragment;
import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.selection.SelectProductsFragment;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new CommandRecapFragment();
        } else {
            return new SelectProductsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}