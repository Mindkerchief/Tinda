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
    private Context mContext;
    private ArrayList<StoreModel> mStoreModels;
    private int mStoreLayout;

    public StoreRecyclerViewAdapter(Context context, ArrayList<StoreModel> storeModels, int storeLayout) {
        this.mContext = context;
        this.mStoreModels = storeModels;
        this.mStoreLayout = storeLayout;
    }

    @NonNull
    @Override
    public StoreRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mStoreLayout, parent, false);
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

            imageVStoreImage = itemView.findViewById(R.id.imageV_storeImage);
            textVStoreName = itemView.findViewById(R.id.textV_storeName);
            textVStoreAddress = itemView.findViewById(R.id.textV_storeAddress);
            textVStoreCategory = itemView.findViewById(R.id.textV_storeCategory);
        }
    }

    public void updateRecyclerVStore(ArrayList<StoreModel> storeResults) {
        this.mStoreModels = storeResults;
    }
}
