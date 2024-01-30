package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        holder.imageVStoreImage.setImageResource(mStoreModels.get(position).getStoreImage());
        holder.textVStoreName.setText(mStoreModels.get(position).getStoreName());
        holder.textVStoreAddress.setText(mStoreModels.get(position).getStoreAddress());
        holder.textVStoreCategory.setText(mStoreModels.get(position).getStoreCategory());

        // Setup Store Features if exists
        if (mStoreLayout == R.layout.card_store) {
            StoreFeaturesRecyclerViewAdapter storeFeaturesRVAdapter = new StoreFeaturesRecyclerViewAdapter(mContext,
                    mStoreModels.get(position).getStoreFeatureImages(), mStoreModels.get(position).getStoreFeaturePrices());
            holder.recyclerVStoreFeatures.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerVStoreFeatures.setAdapter(storeFeaturesRVAdapter);
        }
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
        RecyclerView recyclerVStoreFeatures;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVStoreImage = itemView.findViewById(R.id.imageV_storeImage);
            textVStoreName = itemView.findViewById(R.id.textV_storeName);
            textVStoreAddress = itemView.findViewById(R.id.textV_storeAddress);
            textVStoreCategory = itemView.findViewById(R.id.textV_storeCategory);
            recyclerVStoreFeatures = itemView.findViewById(R.id.recyclerV_storeFeatures);
        }
    }

    public void updateRecyclerVStore(ArrayList<StoreModel> storeResults) {
        this.mStoreModels = storeResults;
    }
}
