package com.lspuspcc.tinda.domain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;

import java.util.Locale;

public class StoreFeaturesRecyclerViewAdapter extends RecyclerView.Adapter<StoreFeaturesRecyclerViewAdapter.MyViewHolder> {
    private final int[] mStoreFeatureImages;
    private final float[] mStoreFeaturePrices;

    public StoreFeaturesRecyclerViewAdapter(int[] storeFeatureImages, float[] storeFeaturePrices) {
        this.mStoreFeatureImages = storeFeatureImages;
        this.mStoreFeaturePrices = storeFeaturePrices;
    }

    @NonNull
    @Override
    public StoreFeaturesRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_store_features_small, parent, false);
        return new StoreFeaturesRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreFeaturesRecyclerViewAdapter.MyViewHolder holder, int position) {
        String price = "P" + String.format(Locale.ENGLISH, "%.2f", mStoreFeaturePrices[position]);
        holder.imageVFeatureImage.setImageResource(mStoreFeatureImages[position]);
        holder.textVFeaturePrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return mStoreFeaturePrices.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageVFeatureImage;
        TextView textVFeaturePrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageVFeatureImage = itemView.findViewById(R.id.imageV_featureImage);
            textVFeaturePrice = itemView.findViewById(R.id.textV_featurePrice);
        }
    }
}
