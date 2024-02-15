package com.lspuspcc.tinda.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardSubCategoryBinding;

import java.util.ArrayList;

public class SubCategoryRecyclerViewAdapter extends RecyclerView.Adapter<SubCategoryRecyclerViewAdapter.SubCategoryViewHolder> {
    ArrayList<SubCategoryModel> mSubCategoryModels;

    public SubCategoryRecyclerViewAdapter(ArrayList<SubCategoryModel> subCategoryModels) {
        this.mSubCategoryModels = subCategoryModels;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardSubCategoryBinding cardSubCategoryBinding = CardSubCategoryBinding.inflate(layoutInflater, parent, false);
        return new SubCategoryViewHolder(cardSubCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategoryModel subCategoryModel = mSubCategoryModels.get(position);
        holder.cardSubCategoryBinding.setSubCategory(subCategoryModel);
        holder.cardSubCategoryBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSubCategoryModels.size();
    }

    public void updateCategoryModel(ArrayList<SubCategoryModel> subCategoryModels) {
        this.mSubCategoryModels = subCategoryModels;
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private final CardSubCategoryBinding cardSubCategoryBinding;

        public SubCategoryViewHolder(@NonNull CardSubCategoryBinding cardSubCategoryBinding) {
            super(cardSubCategoryBinding.getRoot());
            this.cardSubCategoryBinding = cardSubCategoryBinding;
        }
    }
}
