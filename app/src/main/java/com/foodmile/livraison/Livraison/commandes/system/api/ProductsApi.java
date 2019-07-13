package com.foodmile.livraison.Livraison.commandes.system.api;

import android.content.Context;

import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.CategoriesCallback;
import com.foodmile.livraison.Livraison.commandes.system.api.callbacks.ProductsCallback;

import java.util.LinkedList;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class ProductsApi extends MyApi {
    public ProductsApi(Context context) {
        super(context);
    }

    public void getByCategory(ProductsCallback callback, int categoryId) {
       /* switch (categoryId) {
            case 1 : callback.onResult();break;
            case 2 : break;
            case 3 : break;
        }*/
    }

    public void getCategories(CategoriesCallback callback) {
        LinkedList<Categorie> cat = new LinkedList<>();
        Categorie c = new Categorie();
        c.setIdcat(1 + "");
        c.setNomcat("Sandwiches");
        cat.add(c);

        c.setIdcat(2 + "");
        c.setNomcat("Plats");
        cat.add(c);

        c.setIdcat(3 + "");
        c.setNomcat("Boissons");
        cat.add(c);

        callback.onResult(cat);
    }
}
