package com.matthewfraser.cp470_losty;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ViewHolder> {

    private List<ItemModel> itemList;

    public ProfileItemAdapter(List<ItemModel> itemList) {this.itemList = itemList;}
    PostDatabaseHelper db;
    private Context context;


    @NonNull
    @Override
    public ProfileItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, parent, false);
        context = parent.getContext();
        db = new PostDatabaseHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileItemAdapter.ViewHolder holder, int position) {
//        Bitmap image = itemList.get(position).getImage();
        String name = itemList.get(position).getName();
        int id = itemList.get(position).getId();
//        String brand = itemList.get(position).getBrand();
//        String color = itemList.get(position).getColor();
//        String desc = itemList.get(position).getDesc();
        holder.setData(name, id);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView nameView;
        private Button buttonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.postTitleTextView);
            buttonView = itemView.findViewById(R.id.deleteButton);
        }

        public void setData(String name, int id) {
            nameView.setText(name);

            // Write listener here for button
            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open custom dialog
                    CustomDeleteDialog deleteDialog = new CustomDeleteDialog((Activity)context, id);
                    deleteDialog.show();
                }
            });


        }
    }
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        context = recyclerView.getContext();
//    }
}
