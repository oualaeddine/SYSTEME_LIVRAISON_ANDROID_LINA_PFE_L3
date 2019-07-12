package com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmile.livraison.Livraison.commandes.system.models.CommandProduct;
import com.foodmile.livraison.Livraison.commandes.ui.command.CommandActivity;
import com.foodmile.livraison.R;
import com.marcoscg.dialogsheet.DialogSheet;

import static com.foodmile.livraison.Livraison.commandes.ui.command.fragments.rv.ProductsRecyclerViewAdapter.SELECTION;

/**
 * Created by ouala_eddine on 7/9/2019.
 * Project : Livraison.
 */
public class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView productName, productPrice, productQuantity;
    private Button incrementBtn, decrementBtn;
    private LinearLayout selectItemLinearLayout;

    private final int type;
    private Context context;

    public ProductViewHolder(@NonNull View itemView, int type, Context context) {
        super(itemView);
        this.type = type;
        productName = itemView.findViewById(R.id.product_name);
        selectItemLinearLayout = itemView.findViewById(R.id.item_select_ll);
        this.context = context;

        if (type == SELECTION) {
            productPrice = itemView.findViewById(R.id.product_price);
        } else {
            productQuantity = itemView.findViewById(R.id.product_quantity);
            incrementBtn = itemView.findViewById(R.id.btn_inc);
            decrementBtn = itemView.findViewById(R.id.btn_dec);
        }

    }

    public void setRecapProduct(final CommandProduct p) {
        productQuantity.setText(0);
        decrementBtn.setEnabled(false);
        /*removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommandActivity.currentCommand.removeProduct(p);
            }
        });*/
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setQuantity(p.getQuantity() + 1);
                decrementBtn.setEnabled(true);
                CommandActivity.currentCommand.addProduct(p);
            }
        });
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.getQuantity() > 0)
                    p.setQuantity(p.getQuantity() - 1);
                else {
                    decrementBtn.setEnabled(false);
                }
                CommandActivity.currentCommand.addProduct(p);
            }
        });
        setProduct(p);
    }

    public void setProduct(final CommandProduct p) {
        productName.setText(p.getProduit().getNomproduit());
        productPrice.setText(p.getProduit().getPrixproduit());
        if (this.type == SELECTION)
            selectItemLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSelectionDialog(p);
                }
            });
    }

    private void openSelectionDialog(CommandProduct p) {

        DialogSheet dialogSheet = new DialogSheet(context);
        dialogSheet.setView(R.layout.select_product_sheet);

    }


}
