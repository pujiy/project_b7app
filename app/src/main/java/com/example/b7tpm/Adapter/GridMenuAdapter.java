package com.example.b7tpm.Adapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.b7tpm.Model.MenuGrid;
import com.example.b7tpm.R;

import java.util.ArrayList;

public class GridMenuAdapter extends RecyclerView.Adapter<GridMenuAdapter.GridViewHolder> {
    private ArrayList<MenuGrid> listGrid;

    private OnItemClickCallback onItemClickCallback;

    public GridMenuAdapter(ArrayList<MenuGrid> listGrid) {
        this.listGrid = listGrid;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_menuhome, viewGroup, false);
        return new GridViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(listGrid.get(position).getPhoto())
                .apply(new RequestOptions().override(150, 150))
                .into(holder.imgPhoto);

        holder.txtGrid.setText(listGrid.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listGrid.get(holder.getAdapterPosition()));
            }
        });
    }

    public interface OnItemClickCallback {
        void onItemClicked(MenuGrid data);
    }




    @Override
    public int getItemCount() {
        return listGrid.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txtGrid;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            txtGrid = itemView.findViewById(R.id.tv_menu_grid);
        }
    }


}
