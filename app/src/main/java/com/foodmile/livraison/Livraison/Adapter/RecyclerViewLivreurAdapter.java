package com.foodmile.livraison.Livraison.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodmile.livraison.Livraison.Classes.Livreur;
import com.foodmile.livraison.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLivreurAdapter extends RecyclerView.Adapter<RecyclerViewLivreurAdapter.MyLivreurViewHolder>{

    private Context context;
    private List<Livreur> list;
    private OnItemClickListener m;
    private static Typeface face;

    public void setFilter(ArrayList<Livreur> newList) {
        list=new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();//refresh the adapter
    }


    public interface OnItemClickListener {
        void OnDisplay(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        m = listener;

    }

    public RecyclerViewLivreurAdapter(Context context, List<Livreur> list) {
        this.context = context;
        this.list = list;
        face = Typeface.createFromAsset(context.getAssets(),"fonts/text.otf");

    }

    @NonNull
    @Override
    public MyLivreurViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.item1_livreur, viewGroup, false);
        MyLivreurViewHolder m = new MyLivreurViewHolder(view);

        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLivreurViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(list.get(i).getNom());
        myViewHolder.surname.setText(list.get(i).getPrenom());
        myViewHolder.numtele.setText(list.get(i).getNumtele());

    myViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.animation_livreur));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyLivreurViewHolder extends RecyclerView.ViewHolder {
       TextView name;
       TextView surname;
       TextView numtele;
       CardView container;
       ImageView image;
        public MyLivreurViewHolder(@NonNull View itemView) {
            super(itemView);
       container=(CardView)itemView.findViewById(R.id.action_container);
            name=(TextView)itemView.findViewById(R.id.itemname);
            name.setTypeface(face);
            surname=(TextView)itemView.findViewById(R.id.itemsurname);
            surname.setTypeface(face);
            numtele=(TextView)itemView.findViewById(R.id.itemnumtele);
            numtele.setTypeface(face);
            image =(ImageView)itemView.findViewById(R.id.image_update_livreur);
          image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (m != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            m.OnDisplay(position);
                        }
                    }
                }
            });
        }

    }

}
