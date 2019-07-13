package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.selection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.foodmile.livraison.Livraison.Classes.Categorie;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/13/2019.
 * Project : Livraison.
 */
public class CategoriesFragmentPager extends FragmentPagerAdapter {
    private LinkedList<Categorie> categories;

    CategoriesFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        CategoryProductsFragment f = new CategoryProductsFragment();
        Bundle args = new Bundle();
        args.putInt("c", Integer.parseInt(categories.get(position).idcat));
        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    public void setData(LinkedList<Categorie> categories) {
        this.categories = categories;
    }
}