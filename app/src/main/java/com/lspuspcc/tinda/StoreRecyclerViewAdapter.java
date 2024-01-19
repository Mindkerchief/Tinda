package com.lspuspcc.tinda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<StoreModel> storeModels;

    public StoreRecyclerViewAdapter(Context context, ArrayList<StoreModel> storeModels) {
        this.context = context;
        this.storeModels = storeModels;
    }

    @NonNull
    @Override
    public StoreRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_store, parent, false);
        return new StoreRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVStoreImage.setImageResource(storeModels.get(position).getStoreImage());
        holder.textVStoreName.setText(storeModels.get(position).getStoreName());
        holder.textVStoreAddress.setText(storeModels.get(position).getStoreAddress());
        holder.textVStoreCategory.setText(storeModels.get(position).getStoreCategory());
    }

    @Override
    public int getItemCount() {
        // counts number of items to be displayed
        return storeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // gets the views from recycler view rows
        ImageView imageVStoreImage;
        TextView textVStoreName, textVStoreAddress, textVStoreCategory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVStoreImage = itemView.findViewById(R.id.imageVStoreImage);
            textVStoreName = itemView.findViewById(R.id.textVStoreName);
            textVStoreAddress = itemView.findViewById(R.id.textVStoreAddress);
            textVStoreCategory = itemView.findViewById(R.id.textVStoreCategory);
        }
    }
}
