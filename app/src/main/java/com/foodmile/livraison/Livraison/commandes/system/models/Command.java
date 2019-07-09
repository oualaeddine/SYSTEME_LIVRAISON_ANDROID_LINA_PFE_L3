package com.foodmile.livraison.Livraison.commandes.system.models;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class Command {

    LinkedList<CommandProduct> products;

    public LinkedList<CommandProduct> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<CommandProduct> products) {
        this.products = products;
    }
}
