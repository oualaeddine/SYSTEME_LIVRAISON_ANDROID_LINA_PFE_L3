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

import com.foodmile.livraison.Livraison.Classes.Vehicule;
import com.foodmile.livraison.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewVehiculeAdapter extends RecyclerView.Adapter<RecyclerViewVehiculeAdapter.MyVehiculeViewHolder> {
    private Context context;
    private List<Vehicule> list;
    private static Typeface face;
    private OnClickListener m;

    public RecyclerViewVehiculeAdapter(Context context, List<Vehicule> list) {
        this.context = context;
        this.list = list;
        //this.produitListFiltered = list;
        face = Typeface.createFromAsset(context.getAssets(), "fonts/text.otf");

    }



    public interface OnClickListener {
        void OnEdit(int position);
        void OnDelete(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        m = listener;

    }

    @NonNull
    @Override
    public MyVehiculeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.item_vehicule, viewGroup, false);
        MyVehiculeViewHolder m = new MyVehiculeViewHolder(view);

        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVehiculeViewHolder myVehiculeViewHolder, final int i) {
        Picasso.get().load(list.get(i).getPhoto()).into(myVehiculeViewHolder.imageView);
        myVehiculeViewHolder.nom.setText(list.get(i).getNomvehicule());
        myVehiculeViewHolder.matricule.setText(list.get(i).getMatricule());
        myVehiculeViewHolder.type.setText(list.get(i).getType());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyVehiculeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nom;
        TextView matricule;
        TextView type;
        Button btn1;
        Button btn2;


        public MyVehiculeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagevehicule);
            nom = (TextView) itemView.findViewById(R.id.nomvehicule);
            matricule = (TextView) itemView.findViewById(R.id.matricule);
            type = (TextView) itemView.findViewById(R.id.type);
            btn1 = (Button) itemView.findViewById(R.id.ModifierVehicule);
            btn2 = (Button) itemView.findViewById(R.id.SupprimerVehicule);
            nom.setTypeface(face);
            matricule.setTypeface(face);
            type.setTypeface(face);
            btn1.setTypeface(face);
            btn2.setTypeface(face);

          btn1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 if (m != null) {
                      int position = getAdapterPosition();
                      if (position != RecyclerView.NO_POSITION) {
                          m.OnEdit(position);
                      }
                  }

              }
          });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            m.OnDelete(position);
                        }
                    }
                }
            });
        }
    }
}