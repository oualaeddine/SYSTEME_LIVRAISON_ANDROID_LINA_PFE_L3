package com.foodmile.livraison.Livraison.Classes;

public class Livreur {
    private String idlivr;
    private String nom;
    private  String prenom;
    private  String numtele;
    public Livreur(){}

    public Livreur(String idliv, String nom, String prenom, String numtele) {
        this.idlivr = idliv;
        this.nom = nom;
        this.prenom = prenom;
        this.numtele = numtele;

    }

    public String getIdliv() {
        return idlivr;
    }

    public void setIdliv(String idliv) {
        this.idlivr = idliv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumtele() {
        return numtele;
    }

    public void setNumtele(String numtele) {
        this.numtele = numtele;
    }


}
