package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardStoreBinding;

import java.util.ArrayList;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.StoreViewHolder> {
    private ArrayList<StoreModel> mStoreModels = new ArrayList<>();

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardStoreBinding cardStoreBinding = CardStoreBinding.inflate(layoutInflater, parent, false);
        return new StoreViewHolder(cardStoreBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreModel storeModel = mStoreModels.get(position);
        holder.mCardStoreBinding.setStore(storeModel);
        holder.mCardStoreBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mStoreModels.size();
    }

    public void updateRecyclerVStore(ArrayList<StoreModel> storeResults) {
        notifyItemRangeRemoved(0, mStoreModels.size());
        this.mStoreModels = storeResults;
        notifyItemRangeChanged(0, mStoreModels.size());
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        private final CardStoreBinding mCardStoreBinding;

        public StoreViewHolder(@NonNull CardStoreBinding cardStoreBinding) {
            super(cardStoreBinding.getRoot());
            this.mCardStoreBinding = cardStoreBinding;
        }
    }
}
