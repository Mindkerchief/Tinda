package com.lspuspcc.tinda.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardProductBinding;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    public ArrayList<ProductModel> mProductModels;

    public ProductRecyclerViewAdapter(ArrayList<ProductModel> productModels) {
        this.mProductModels = productModels;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardProductBinding cardProductBinding = CardProductBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(cardProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel productModel = mProductModels.get(position);
        holder.mProductBinding.setProduct(productModel);
        holder.mProductBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductModels.size();
    }

    public void updateRecyclerVProducts(ArrayList<ProductModel> productModels) {
        notifyItemRangeRemoved(0, mProductModels.size());
        this.mProductModels = productModels;
        notifyItemRangeInserted(0, mProductModels.size());

    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final CardProductBinding mProductBinding;
        public ProductViewHolder(@NonNull CardProductBinding cardProductBinding) {
            super(cardProductBinding.getRoot());
            this.mProductBinding = cardProductBinding;
        }
    }
}
