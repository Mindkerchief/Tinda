package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;

import java.util.ArrayList;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<StoreModel> mStoreModels;

    public StoreRecyclerViewAdapter(Context context, ArrayList<StoreModel> storeModels) {
        this.mContext = context;
        this.mStoreModels = storeModels;
    }

    @NonNull
    @Override
    public StoreRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_store_vertical, parent, false);
        return new StoreRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVStoreImage.setImageResource(mStoreModels.get(position).getmStoreImage());
        holder.textVStoreName.setText(mStoreModels.get(position).getmStoreName());
        holder.textVStoreAddress.setText(mStoreModels.get(position).getmStoreAddress());
        holder.textVStoreCategory.setText(mStoreModels.get(position).getmStoreCategory());
    }

    @Override
    public int getItemCount() {
        // Counts the number of items to be displayed
        return mStoreModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Gets the views from recycler view rows
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
