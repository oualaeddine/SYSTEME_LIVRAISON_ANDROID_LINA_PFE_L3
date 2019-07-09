package com.foodmile.livraison.Livraison.commandes.system.api.callbacks;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public interface BooleanCallback {
    void onError(String error);

    void onResult(Boolean done);
}
