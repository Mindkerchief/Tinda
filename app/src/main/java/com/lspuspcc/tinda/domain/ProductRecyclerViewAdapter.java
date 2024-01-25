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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<ProductModel> mProductModels;

    public ProductRecyclerViewAdapter(Context context, ArrayList<ProductModel> productModels) {
        this.mContext = context;
        this.mProductModels = productModels;
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_product, parent, false);
        return new ProductRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVProductImage.setImageResource(mProductModels.get(position).getmProductImage());
        holder.textVProductName.setText(mProductModels.get(position).getmProductName());
        holder.textVProductStore.setText(mProductModels.get(position).getmProductStore());
        holder.textVProductPrice.setText(mProductModels.get(position).getmProductPrice());
    }

    @Override
    public int getItemCount() {
        // Count the number of items to be displayed
        return mProductModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Gets the views from recycler view rows
        ImageView imageVProductImage;
        TextView textVProductName, textVProductStore, textVProductPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVProductImage = itemView.findViewById(R.id.imageV_productImage);
            textVProductName = itemView.findViewById(R.id.textV_productName);
            textVProductStore = itemView.findViewById(R.id.textV_productStore);
            textVProductPrice = itemView.findViewById(R.id.textV_productPrice);
        }
    }

    public void updateRecyclerVProducts(ArrayList<ProductModel> productModels) {
        this.mProductModels = productModels;
    }
}
