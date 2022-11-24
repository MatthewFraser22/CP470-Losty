package com.matthewfraser.cp470_losty;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ItemModel> itemList;

    public ItemAdapter (List<ItemModel> itemList) {this.itemList = itemList;}

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Bitmap image = itemList.get(position).getImage();
        String name = itemList.get(position).getName();
        String brand = itemList.get(position).getBrand();
        String color = itemList.get(position).getColor();
        String desc = itemList.get(position).getDesc();
        holder.setData(image, name, brand, color, desc);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nameView;
        private TextView brandView;
        private TextView colorView;
        private TextView descView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ItemPicture);
            nameView = itemView.findViewById(R.id.ItemName);
            brandView = itemView.findViewById(R.id.ItemBrand);
            colorView = itemView.findViewById(R.id.ItemColor);
            descView = itemView.findViewById(R.id.ItemDescription);
        }

        public void setData(Bitmap image, String name, String brand, String color, String desc) {
            imageView.setImageBitmap(image);
            nameView.setText(name);
            brandView.setText(brand);
            colorView.setText(color);
            descView.setText(desc);
        }

        public void setData(int image, String name, String brand, String color, String desc) {
            imageView.setImageResource(image);
            nameView.setText(name);
            brandView.setText(brand);
            colorView.setText(color);
            descView.setText(desc);
        }
    }
}
