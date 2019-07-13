package com.foodmile.livraison.Livraison.commandes.system.api.callbacks;

import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.Livraison.commandes.system.models.Livreur;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/12/2019.
 * Project : Livraison.
 */
public interface CategoriesCallback {
    void onError(String error);

    void onResult(LinkedList<Categorie> category);
}
