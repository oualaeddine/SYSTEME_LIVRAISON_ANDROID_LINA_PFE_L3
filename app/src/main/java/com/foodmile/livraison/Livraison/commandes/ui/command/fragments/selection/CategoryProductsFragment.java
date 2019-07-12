package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.selection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv.ProductsRecyclerViewAdapter;
import com.foodmile.livraison.R;

public class CategoryProductsFragment extends Fragment {

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
        recyclerView.setAdapter(new ProductsRecyclerViewAdapter(ProductsRecyclerViewAdapter.SELECTION));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}
