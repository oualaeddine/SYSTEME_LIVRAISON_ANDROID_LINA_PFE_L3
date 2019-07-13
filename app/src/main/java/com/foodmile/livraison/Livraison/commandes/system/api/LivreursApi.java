package com.foodmile.livraison.Livraison.commandes.system.api;

import android.content.Context;

import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.LivreursCallback;
import com.foodmile.livraison.Livraison.commandes.system.models.Livreur;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class LivreursApi extends MyApi {
    public LivreursApi(Context context) {
        super(context);
    }

    public void getAll(LivreursCallback callback) {
        LinkedList<Livreur> livreurs = new LinkedList<>();
        callback.onResult(livreurs);
    }
}
