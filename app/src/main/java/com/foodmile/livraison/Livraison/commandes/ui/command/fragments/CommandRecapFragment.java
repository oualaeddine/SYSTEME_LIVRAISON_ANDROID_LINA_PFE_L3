package com.foodmile.livraison.Livraison.commandes.ui.command.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv.ProductsRecyclerViewAdapter;
import com.foodmile.livraison.R;

public class CommandRecapFragment extends Fragment {

    ProductsRecyclerViewAdapter mAdapter;

    public CommandRecapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_command_recap, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_products_recap);
        mAdapter = new ProductsRecyclerViewAdapter(ProductsRecyclerViewAdapter.RECAP);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        mAdapter.refreshRecapData();
    }
}
