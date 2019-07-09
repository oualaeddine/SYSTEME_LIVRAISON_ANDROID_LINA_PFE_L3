package com.foodmile.livraison.Livraison.Acitivitieslivreur;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> lst = new ArrayList<>();
    private  final List<String> listtitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return lst.get(i);
    }

    @Override
    public int getCount() {
        return listtitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitle.get(position);
    }
    public void AddFragment(Fragment fragment, String title){
        lst.add(fragment);
        listtitle.add(title);

    }
}
