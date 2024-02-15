package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardStoreVerticalBinding;

import java.util.ArrayList;

public class StoreVerticalRecyclerViewAdapter extends RecyclerView.Adapter<StoreVerticalRecyclerViewAdapter.StoreVerticalViewHolder> {
    private final Context mContext;
    private ArrayList<StoreVerticalModel> mStoreModels;

    public StoreVerticalRecyclerViewAdapter(Context context, ArrayList<StoreVerticalModel> storeModels) {
        this.mContext = context;
        this.mStoreModels = storeModels;
    }

    @NonNull
    @Override
    public StoreVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        CardStoreVerticalBinding cardStoreVerticalBinding = CardStoreVerticalBinding.inflate(layoutInflater, parent, false);
        return new StoreVerticalViewHolder(cardStoreVerticalBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreVerticalViewHolder holder, int position) {
        StoreVerticalModel storeVerticalModel = mStoreModels.get(position);
        holder.mCardStoreVerticalBinding.setStore(storeVerticalModel);
        holder.mCardStoreVerticalBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mStoreModels.size();
    }

    public void updateRecyclerVStore(ArrayList<StoreVerticalModel> storeResults) {
        this.mStoreModels = storeResults;
    }

    public static class StoreVerticalViewHolder extends RecyclerView.ViewHolder {
        private final CardStoreVerticalBinding mCardStoreVerticalBinding;

        public StoreVerticalViewHolder(@NonNull CardStoreVerticalBinding cardStoreVerticalBinding) {
            super(cardStoreVerticalBinding.getRoot());
            this.mCardStoreVerticalBinding = cardStoreVerticalBinding;
        }
    }
}
