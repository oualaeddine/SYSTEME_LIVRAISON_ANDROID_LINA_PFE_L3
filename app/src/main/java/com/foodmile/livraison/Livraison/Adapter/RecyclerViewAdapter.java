package com.foodmile.livraison.Livraison.Adapter;

import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodmile.livraison.Livraison.Classes.Categorie;
import com.foodmile.livraison.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Categorie> list;
    private OnItemClickListener m;
    private static Typeface face;


    public interface OnItemClickListener {


        void OnUpdate(int position);

        void OnIntent(int position);

        void OnDelete(int position);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        m = listener;
    }



    public RecyclerViewAdapter(Context context, List<Categorie> list) {
        this.context = context;
        this.list = list;
        face = Typeface.createFromAsset(context.getAssets(), "fonts/text.otf");
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        view = layoutInflater.inflate(R.layout.layout_categorie_item, viewGroup, false);
        MyViewHolder m = new MyViewHolder(view);

        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(list.get(i).getNomcat());
        Picasso.get().load(list.get(i).getImagecat()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView image;
        ImageView imageD;
        ImageView imageU;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.namecat);
            textView.setTypeface(face);
            imageView = itemView.findViewById(R.id.imagecat);
            image = itemView.findViewById(R.id.product_page);
            imageD = itemView.findViewById(R.id.cat_delete);
            imageU = itemView.findViewById(R.id.cat_update);



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
            imageU.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (m != null) {
                                                  int position = getAdapterPosition();
                                                  if (position != RecyclerView.NO_POSITION) {
                                                      m.OnUpdate(position);
                                                  }

                                              }

                                          }
                                      }
            );
            imageD.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (m != null) {
                                                  int position = getAdapterPosition();
                                                  if (position != RecyclerView.NO_POSITION) {
                                                      m.OnDelete(position);
                                                  }

                                              }

                                          }
                                      }
            );
        }
    }
}

