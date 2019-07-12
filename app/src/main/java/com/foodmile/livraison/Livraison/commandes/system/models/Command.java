package com.foodmile.livraison.Livraison.commandes.system.models;

import com.foodmile.livraison.Livraison.Classes.Produit;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class Command {

    private final LinkedList<CommandProduct> products;

    public Command() {
        products = new LinkedList<>();
    }

    public LinkedList<CommandProduct> getProducts() {
        return products;
    }

    public void addProduct(CommandProduct p) {
        if (products.contains(p)) {
            updateProduct(p);
        } else {
            products.add(p);
        }
    }

    private void updateProduct(CommandProduct p) {
        products.set(products.indexOf(p), p);
    }

    public void removeProduct(CommandProduct produit) {
        products.remove(produit);
    }

    public void clearCommand() {
        products.clear();
    }

    public boolean has(CommandProduct produit) {
        return products.contains(produit);
    }
}
