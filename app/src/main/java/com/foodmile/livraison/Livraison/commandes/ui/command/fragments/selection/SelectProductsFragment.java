package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.selection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.Livraison.commandes.system.api.ProductsApi;
import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.CategoriesCallback;
import com.foodmile.livraison.R;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;

import es.dmoral.toasty.Toasty;

public class SelectProductsFragment extends Fragment {

    public SelectProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_select_products, container, false);

        new ProductsApi(getContext()).getCategories(new CategoriesCallback() {
            @Override
            public void onError(String error) {
                Toasty.error(getContext(), error).show();
            }

            @Override
            public void onResult(LinkedList<Categorie> categories) {
                ViewPager viewPager = rootView.findViewById(R.id.vp_categories);
                CategoriesFragmentPager adapter = new CategoriesFragmentPager(getActivity().getSupportFragmentManager());
                adapter.setData(categories);
                viewPager.setAdapter(adapter);

                TabLayout tabLayout = rootView.findViewById(R.id.tl_categories);
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;
    }
}
