package com.foodmile.livraison.Livraison.Classes;

public class Categorie {
    public String idcat;
    public String nomcat;
    public String imagecat;
public Categorie(){}
    public Categorie(String idcat, String nomcat, String imagecat) {
        this.idcat = idcat;
        this.nomcat = nomcat;
        this.imagecat = imagecat;
    }

    public String getIdcat() {
        return idcat;
    }

    public void setIdcat(String idcat) {
        this.idcat = idcat;
    }

    public String getNomcat() {
        return nomcat;
    }

    public void setNomcat(String nomcat) {
        this.nomcat = nomcat;
    }

    public String getImagecat() {
        return imagecat;
    }

    public void setImagecat(String imagecat) {
        this.imagecat = imagecat;
    }
}
