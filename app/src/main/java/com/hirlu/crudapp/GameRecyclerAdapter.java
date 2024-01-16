package com.hirlu.crudapp;

import android.app.GameManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.BitSet;
import java.util.List;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
     List<Game> lGames;
    Context context = null;


    public GameRecyclerAdapter(Context context, List<Game> lGames, RecyclerViewInterface recyclerViewInterface){
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.lGames = lGames;

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView description;
        private TextView year;
        private TextView pegiAge;

        private ImageView Imagen;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.name = itemView.findViewById(R.id.Name);
            this.description = itemView.findViewById(R.id.Description);
            this.year = itemView.findViewById(R.id.Year);
            this.pegiAge = itemView.findViewById(R.id.PegiAge);
            Imagen = itemView.findViewById(R.id.Imagen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(v, pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
    @NonNull
    @Override
    public GameRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_adapter, parent, false);

        return new GameRecyclerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GameRecyclerAdapter.MyViewHolder holder, int position) {
        holder.name.setText(lGames.get(position).getName());
        holder.description.setText(lGames.get(position).getDescription());
        holder.year.setText(String.valueOf(lGames.get(position).getYear()));

        byte[] image = lGames.get(position).getImageByte();
        if (image == null) holder.Imagen.setImageResource(R.drawable.ic_launcher_background);
        else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.Imagen.setImageBitmap(bitmap);
        }

        holder.pegiAge.setText(String.valueOf(lGames.get(position).getPegiAge()));
    }

    @Override
    public int getItemCount() {
        return lGames.size();
    }

    public void addGame(Game game){
        lGames.add(game);
        notifyDataSetChanged();
    }
    public void addGamewithpos(Game game, int pos){
        lGames.set(pos, game);
        notifyDataSetChanged();
    }

    public void removeGame(int pos){
        lGames.remove(pos);
        notifyDataSetChanged();
    }
}
