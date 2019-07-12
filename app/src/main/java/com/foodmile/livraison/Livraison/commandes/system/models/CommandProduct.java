package com.foodmile.livraison.Livraison.commandes.system.models;

import com.foodmile.livraison.Livraison.Classes.Produit;

import java.util.Objects;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class CommandProduct {
    private int quantity;
    private Produit produit;

    public CommandProduct(Produit produit, int qnt) {
        this.produit = produit;
        this.quantity = qnt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || Produit.class != o.getClass()) return false;
        if (Produit.class == o.getClass()) {
            Produit that = (Produit) o;
            return Objects.equals(produit, that);
        } else {
            CommandProduct that = (CommandProduct) o;
            return Objects.equals(produit, that.produit);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(produit);
    }
}
