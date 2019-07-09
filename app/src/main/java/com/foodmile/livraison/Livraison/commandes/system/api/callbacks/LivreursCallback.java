package com.foodmile.livraison.Livraison.commandes.system.api.callbacks;

import com.foodmile.livraison.Livraison.commandes.system.models.Livreur;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public interface LivreursCallback {
    void onError(String error);

    void onResult(LinkedList<Livreur> livreurs);
}
