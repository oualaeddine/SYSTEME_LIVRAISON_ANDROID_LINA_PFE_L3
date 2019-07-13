package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.selection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.Classes.Produit;
import com.foodmile.livraison.Livraison.commandes.system.api.ProductsApi;
import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.ProductsCallback;
import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv.ProductsRecyclerViewAdapter;
import com.foodmile.livraison.R;

import java.util.LinkedList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class CategoryProductsFragment extends Fragment {

    private ProductsRecyclerViewAdapter mAdapter;

    public CategoryProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category_products, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_products);
        mAdapter = new ProductsRecyclerViewAdapter(ProductsRecyclerViewAdapter.SELECTION, getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int categoryId = 0;

        new ProductsApi(getContext()).getByCategory(new ProductsCallback() {
            @Override
            public void onResult(LinkedList<Produit> products) {
                mAdapter.setData(products);
            }

            @Override
            public void onError(String error) {
                Toasty.error(Objects.requireNonNull(getContext()), "erreur recuperation des produits : " + error).show();
            }

        }, categoryId);

        return rootView;
    }
}
