package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.Classes.Produit;
import com.foodmile.livraison.Livraison.commandes.system.models.CommandProduct;
import com.foodmile.livraison.Livraison.commandes.ui.command.CommandActivity;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    public static final int SELECTION = 0, RECAP = 1;
    private final int type;
    private LinkedList<CommandProduct> data;
    private Context context;

    public ProductsRecyclerViewAdapter(int type, Context context) {
        this.type = type;
        this.context = context;
        if (type == RECAP)
            data = CommandActivity.currentCommand.getProducts();
        else
            data = new LinkedList<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(parent, type, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        CommandProduct currentProduct = data.get(position);
        if (type == SELECTION)
            holder.setProduct(currentProduct);
        else
            holder.setRecapProduct(currentProduct);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refreshRecapData() {
        data = CommandActivity.currentCommand.getProducts();
        notifyDataSetChanged();
    }

    public void setData(LinkedList<Produit> products) {
        data = new LinkedList<>();
        for (Produit p : products) {
            data.add(new CommandProduct(p, 0));
        }
        notifyDataSetChanged();
    }
}
