package com.foodmile.livraison.Livraison.Classes;

import java.util.Objects;

public class Produit {
    String idproduit;
    String nomproduit;
    String ingrediants;
    String prixproduit;
    String imageproduit;
    String id_cat;

    public Produit() {

    }

    public Produit(String idproduit, String nomproduit, String ingrediants, String prixproduit, String imageproduit, String id_cat) {
        this.idproduit = idproduit;
        this.nomproduit = nomproduit;
        this.ingrediants = ingrediants;
        this.prixproduit = prixproduit;
        this.imageproduit = imageproduit;
        this.id_cat = id_cat;
    }

    public String getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(String idproduit) {
        this.idproduit = idproduit;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public String getIngrediants() {
        return ingrediants;
    }

    public void setIngrediants(String ingrediants) {
        this.ingrediants = ingrediants;
    }

    public String getPrixproduit() {
        return prixproduit;
    }

    public void setPrixproduit(String prixproduit) {
        this.prixproduit = prixproduit;
    }

    public String getImageproduit() {
        return imageproduit;
    }

    public void setImageproduit(String imageproduit) {
        this.imageproduit = imageproduit;
    }

    public String getId_cat() {
        return id_cat;
    }

    public void setId_cat(String id_cat) {
        this.id_cat = id_cat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return idproduit.equals(produit.idproduit);
    }


}