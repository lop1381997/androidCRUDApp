package com.hirlu.crudapp;

import android.app.GameManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.MyViewHolder> {
    private List<Game> lGames;
    Context context = null;

    public GameRecyclerAdapter(Context context, List<Game> lGames){
        this.context = context;
        this.lGames = lGames;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView description;
        private TextView year;

        private ImageView Imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.Name);
            this.description = itemView.findViewById(R.id.Description);
            this.year = itemView.findViewById(R.id.Year);
            Imagen = itemView.findViewById(R.id.Imagen);
        }
    }
    @NonNull
    @Override
    public GameRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_adapter, parent, false);

        return new GameRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(lGames.get(position).getName());
        holder.description.setText(lGames.get(position).getDescription());
        holder.year.setText(String.valueOf(lGames.get(position).getYear()));
        holder.Imagen.setImageResource(lGames.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return lGames.size();
    }
}
