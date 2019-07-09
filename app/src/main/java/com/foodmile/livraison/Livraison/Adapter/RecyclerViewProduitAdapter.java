package com.foodmile.livraison.Livraison.Adapter;

import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodmile.livraison.Livraison.Classes.Produit;
import com.foodmile.livraison.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewProduitAdapter extends RecyclerView.Adapter<RecyclerViewProduitAdapter.MyProduitViewHolder>{

    private Context context;
    private List<Produit> list;
    private List<Produit> produitListFiltered;
    private OnItemClickListener m;
    private static Typeface face;



    public interface OnItemClickListener {
        void OnIntent(int position);}

    public void setOnItemClickListener(OnItemClickListener listener) {
        m = listener;

    }

    public RecyclerViewProduitAdapter(Context context, List<Produit> list) {
        this.context = context;
        this.list = list;
        this.produitListFiltered = list;
        face = Typeface.createFromAsset(context.getAssets(),"fonts/text.otf");
    }

    @NonNull
    @Override
    public MyProduitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.layout_produit_item, viewGroup, false);
       MyProduitViewHolder m = new MyProduitViewHolder(view);

        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull MyProduitViewHolder myViewHolder, int i) {

        myViewHolder.nom.setText(list.get(i).getNomproduit());
        myViewHolder.ing.setText(list.get(i).getIngrediants());
        myViewHolder.prix.setText((list.get(i).getPrixproduit()));
        Picasso.get().load(list.get(i).getImageproduit()).into(myViewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nom;

        TextView prix;
        TextView ing;
        TextView da;
        Button Add_Commande;
        ImageView image;

        public MyProduitViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageproduitt);
            nom = (TextView)itemView.findViewById(R.id.nomproduitt) ;
            nom.setTypeface(face);
            prix =(TextView)itemView.findViewById(R.id.prixprod);
            prix.setTypeface(face);
            ing = (TextView)itemView.findViewById(R.id.ingrediantspod);
            ing.setTypeface(face);
            da=(TextView)itemView.findViewById(R.id.da);
            da.setTypeface(face);
           // Add_Commande = (Button)itemView.findViewById(R.id.ajouteracommande);
            image = (ImageView)itemView.findViewById(R.id.ic_produit);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            m.OnIntent(position);
                        }
                    }
                }
            });
        }

    }
    public void setFilter(ArrayList<Produit> newList){
        list=new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();//refresh the adapter
    }
}
