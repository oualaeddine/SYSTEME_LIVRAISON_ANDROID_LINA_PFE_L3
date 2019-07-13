package com.foodmile.livraison.Livraison.commandes.system.api.callbacks;

/**
 * Created by ouala_eddine on 7/13/2019.
 * Project : Livraison.
 */
public interface IntCallback {
    void onError(String error);

    void onResult(int done);
}
