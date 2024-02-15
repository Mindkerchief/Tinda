package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.databinding.CardStoreBinding;
import com.lspuspcc.tinda.databinding.CardStoreVerticalBinding;

import java.util.ArrayList;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.StoreViewHolder> {
    private final Context mContext;
    private ArrayList<StoreModel> mStoreModels;
    private final int mStoreLayout;

    public StoreRecyclerViewAdapter(Context context, ArrayList<StoreModel> storeModels, int storeLayout) {
        this.mContext = context;
        this.mStoreModels = storeModels;
        this.mStoreLayout = storeLayout;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (mStoreLayout == R.layout.card_store) {
            CardStoreBinding cardStoreBinding = CardStoreBinding.inflate(layoutInflater, parent, false);
            return new StoreViewHolder(cardStoreBinding);
        }
        else {
            CardStoreVerticalBinding cardStoreVerticalBinding = CardStoreVerticalBinding.inflate(layoutInflater, parent, false);
            return new StoreViewHolder(cardStoreVerticalBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreModel storeModel = mStoreModels.get(position);

        if (mStoreLayout == R.layout.card_store) {
            holder.mCardStoreBinding.setStore(storeModel);
            holder.mCardStoreBinding.executePendingBindings();

            StoreFeaturesRecyclerViewAdapter storeFeaturesRVAdapter = new StoreFeaturesRecyclerViewAdapter(
                    mStoreModels.get(position).getStoreFeatureImages(), mStoreModels.get(position).getStoreFeaturePrices());
            holder.mRecyclerVStoreFeatures.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.mRecyclerVStoreFeatures.setAdapter(storeFeaturesRVAdapter);
        }
        else {
            holder.mCardStoreVerticalBinding.setStore(storeModel);
            holder.mCardStoreVerticalBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mStoreModels.size();
    }

    public void updateRecyclerVStore(ArrayList<StoreModel> storeResults) {
        this.mStoreModels = storeResults;
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        private CardStoreBinding mCardStoreBinding;
        private CardStoreVerticalBinding mCardStoreVerticalBinding;
        private RecyclerView mRecyclerVStoreFeatures;

        public StoreViewHolder(@NonNull CardStoreBinding cardStoreBinding) {
            super(cardStoreBinding.getRoot());
            this.mCardStoreBinding = cardStoreBinding;
            this.mRecyclerVStoreFeatures = cardStoreBinding.recyclerVStoreFeatures;
        }

        public StoreViewHolder(@NonNull CardStoreVerticalBinding cardStoreVerticalBinding) {
            super(cardStoreVerticalBinding.getRoot());
            this.mCardStoreVerticalBinding = cardStoreVerticalBinding;
        }
    }
}
