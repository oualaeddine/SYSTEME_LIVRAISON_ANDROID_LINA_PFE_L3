package com.foodmile.livraison.Livraison.Classes;

public class Vehicule {

    String id_vehicule;
    String nomvehicule;
    String matricule;
    String type;
    String Photo;

    public Vehicule() {
    }

    public Vehicule(String id_vehicule, String nomvehicule, String matricule, String type, String photo) {
        this.id_vehicule = id_vehicule;
        this.nomvehicule = nomvehicule;
        this.matricule = matricule;
        this.type = type;
        Photo = photo;
    }

    public String getId_vehicule() {
        return id_vehicule;
    }

    public void setId_vehicule(String id_vehicule) {
        this.id_vehicule = id_vehicule;
    }

    public String getNomvehicule() {
        return nomvehicule;
    }

    public void setNomvehicule(String nomvehicule) {
        this.nomvehicule = nomvehicule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
