package com.foodmile.livraison.Livraison.utils;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class StringConstants {

    private static final String API_URL = "http://192.168.0.155:8080/Projet/";

    public static final String
            URL_INSERT_Livreur = API_URL + "Livreur/InsertLivreur.php",
            URL_UPLAOD_Livreur = API_URL + "Livreur/UpdatePhotoLivreur.php",
            URL_READ_Livreur = API_URL + "Livreur/ReadLivreur.php",
            URL_EDIT_Livreur = API_URL + "Livreur/ModifierProfilLivreur.php",
            PHOTO_LIVREUR = API_URL + "Livreur/Photo/",
            URL_SHOW_livreur = API_URL + "Livreur/ShowLivreur.php",
            URL_Update_livreur = API_URL + "Livreur/UpdateLivreur.php",
            URL_DELETE_Livreur = API_URL + "Livreur/DeleteLivreur.php",
            URL_INSERT_Vehicule = API_URL + "Vehicule/InsertVehicule.php",

            URL_SHOW_Vehicule = API_URL + "Vehicule/ShowVehicule.php",
            URL_Update_Vehicule = API_URL + "Vehicule/UpdateVehicule.php",
            URL_DELETE_Vehicule = API_URL + "Vehicule/DeleteVehicule.php",

            URL_READ_profile = API_URL + "Login/ReadLogin.php",
            URL_EDIT_profile = API_URL + "Login/EditProfile.php",
            URL_UPLAOD_profile = API_URL + "Login/PhotoProfil.php",
            PHOTO_PROFILE = API_URL + "Login/photo/",

            URL_LOGIN = API_URL + "Login/Login.php",
            URL_LOGIN_liv = API_URL + "Livreur/LoginLivreur.php",

            URL_Update_cat = API_URL + "Produit/UpdateCategorie.php",
            URL_SHOW_CAT = API_URL + "Produit/ShowCategorie.php",
            URL_INSERT_CATEGORIE = API_URL + "Produit/InsertCategorie.php",
            URL_DELETE_Cat = API_URL + "Produit/DeleteCategorie.php",

            URL_Update_Product = API_URL + "Produit/UpdateProduit.php",
            URL_DELETE_Product = API_URL + "Produit/DeleteProduit.php",
            URL_UPLAOD_Product = API_URL + "Produit/UpdatePhotoProduit.php",
            URL_SHOW_Product = API_URL + "Produit/ShowProduit.php",
            URL_INSERT_Produit = API_URL + "Produit/InsertProduit.php";

}
