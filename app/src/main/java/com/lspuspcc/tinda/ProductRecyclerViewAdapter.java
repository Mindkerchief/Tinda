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

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductModel> productModels;

    public ProductRecyclerViewAdapter(Context context, ArrayList<ProductModel> productModels) {
        this.context = context;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public ProductRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_product, parent, false);
        return new ProductRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assign values in the views
        holder.imageVProductImage.setImageResource(productModels.get(position).getProductImage());
        holder.textVProductName.setText(productModels.get(position).getProductName());
        holder.textVProductStore.setText(productModels.get(position).getProductStore());
        holder.textVProductPrice.setText(productModels.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        // counts number of items to be displayed
        return productModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // gets the views from recycler view rows
        ImageView imageVProductImage;
        TextView textVProductName, textVProductStore, textVProductPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageVProductImage = itemView.findViewById(R.id.imageVProductImage);
            textVProductName = itemView.findViewById(R.id.textVProductName);
            textVProductStore = itemView.findViewById(R.id.textVProductStore);
            textVProductPrice = itemView.findViewById(R.id.textVProductPrice);
        }
    }
}