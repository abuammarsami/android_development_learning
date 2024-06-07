package com.ammar.topgamesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    // 4. handling click event
    private ItemClickListener itemClickListener;

    // 1. Data
    private ArrayList<GameModel> gameModelArrayList;
    private Context context;

    // 2. Constructor
    public MyAdapter(ArrayList<GameModel> gameModelArrayList, Context context) {
        this.gameModelArrayList = gameModelArrayList;
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }




    // 3. Create ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // 3.1. Data
        private ImageView gameImage;
        private TextView gameName;

        // 3.2. Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.cardViewImage);
            gameName = itemView.findViewById(R.id.cardViewTitle);
            itemView.setOnClickListener(this);
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            // 3.3. Handle click event
            itemClickListener.OnClick(v, getAdapterPosition());

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameModel gameModel = gameModelArrayList.get(position);
        holder.gameImage.setImageResource(gameModel.getGameImage());
        holder.gameName.setText(gameModel.getGameName());

    }

    @Override
    public int getItemCount() {
        return gameModelArrayList.size();
    }




}
