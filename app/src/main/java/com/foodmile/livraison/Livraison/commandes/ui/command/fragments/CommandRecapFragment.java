package com.foodmile.livraison.Livraison.commandes.ui.command.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.commandes.system.api.CommandsApi;
import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.IntCallback;
import com.foodmile.livraison.Livraison.commandes.system.models.Command;
import com.foodmile.livraison.Livraison.commandes.system.models.CommandProduct;
import com.foodmile.livraison.Livraison.commandes.ui.command.CommandActivity;
import com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv.ProductsRecyclerViewAdapter;
import com.foodmile.livraison.Livraison.commandes.ui.livreur.SelectLivreurActivity;
import com.foodmile.livraison.R;

import java.util.LinkedList;

public class CommandRecapFragment extends Fragment {

    private ProductsRecyclerViewAdapter mAdapter;

    public CommandRecapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_command_recap, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_products_recap);
        mAdapter = new ProductsRecyclerViewAdapter(ProductsRecyclerViewAdapter.RECAP, getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinkedList<CommandProduct> prod = CommandActivity.currentCommand.getProducts();

        int total = 0;

        for (CommandProduct cp : prod) {
            try {
                total = total + Integer.parseInt(cp.getProduit().getPrixproduit()) * cp.getQuantity();
            } catch (Exception e) {

            }
        }
        ((TextView) rootView.findViewById(R.id.total)).setText(total + " DA");

        rootView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Command command;
                command = CommandActivity.currentCommand;
                new CommandsApi(getContext()).sendCommand(command, new IntCallback() {
                    @Override
                    public void onError(String error) {

                    }

                    @Override
                    public void onResult(int done) {
                        CommandActivity.currentCommand.clearCommand();
                        Intent intent = new Intent(getContext(), SelectLivreurActivity.class);
                        intent.putExtra("id", done);
                        startActivity(intent);
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        mAdapter.refreshRecapData();
    }
}
