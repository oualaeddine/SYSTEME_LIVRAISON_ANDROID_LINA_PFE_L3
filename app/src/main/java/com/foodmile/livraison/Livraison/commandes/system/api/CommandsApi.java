package com.foodmile.livraison.Livraison.commandes.system.api;

import android.content.Context;

import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.BooleanCallback;
import com.foodmile.livraison.Livraison.commandes.system.models.Command;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class CommandsApi extends MyApi {
    public CommandsApi(Context context) {
        super(context);
    }


    public void sendCommand(Command command, BooleanCallback callback) {

    }

}
