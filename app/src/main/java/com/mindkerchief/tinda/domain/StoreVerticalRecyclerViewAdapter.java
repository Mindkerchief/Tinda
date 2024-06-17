package com.mindkerchief.tinda.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindkerchief.tinda.databinding.CardStoreVerticalBinding;

import java.util.ArrayList;

public class StoreVerticalRecyclerViewAdapter extends RecyclerView.Adapter<StoreVerticalRecyclerViewAdapter.StoreVerticalViewHolder> {
    private ArrayList<StoreVerticalModel> mStoreVerticalResults = new ArrayList<>();

    @NonNull
    @Override
    public StoreVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardStoreVerticalBinding cardStoreVerticalBinding = CardStoreVerticalBinding.inflate(layoutInflater, parent, false);
        return new StoreVerticalViewHolder(cardStoreVerticalBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreVerticalViewHolder holder, int position) {
        StoreVerticalModel storeVerticalModel = mStoreVerticalResults.get(position);
        holder.mCardStoreVerticalBinding.setStore(storeVerticalModel);
        holder.mCardStoreVerticalBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mStoreVerticalResults.size();
    }

    public void updateRecyclerVStore(ArrayList<StoreVerticalModel> storeVerticalResults) {
        notifyItemRangeRemoved(0, mStoreVerticalResults.size());
        this.mStoreVerticalResults = storeVerticalResults;
        notifyItemRangeInserted(0, mStoreVerticalResults.size());
    }

    public static class StoreVerticalViewHolder extends RecyclerView.ViewHolder {
        private final CardStoreVerticalBinding mCardStoreVerticalBinding;

        public StoreVerticalViewHolder(@NonNull CardStoreVerticalBinding cardStoreVerticalBinding) {
            super(cardStoreVerticalBinding.getRoot());
            this.mCardStoreVerticalBinding = cardStoreVerticalBinding;
        }
    }
}
