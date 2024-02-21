package com.lspuspcc.tinda.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardDealBinding;

import java.util.ArrayList;

public class DealRecyclerViewAdapter extends RecyclerView.Adapter<DealRecyclerViewAdapter.DealViewHolder> {
    private ArrayList<DealModel> mDealModels;

    public DealRecyclerViewAdapter(ArrayList<DealModel> dealModels) {
        this.mDealModels = dealModels;
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardDealBinding cardDealBinding = CardDealBinding.inflate(layoutInflater, parent, false);
        return new DealViewHolder(cardDealBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        DealModel dealModel = mDealModels.get(position);

        holder.mBtnDealMessage.setOnClickListener(v -> {
            // TODO: Provide Message Button Functionality
        });

        holder.mCardDealBinding.setDeal(dealModel);
        holder.mCardDealBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDealModels.size();
    }

    public void updateDealModel(ArrayList<DealModel> dealModels) {
        notifyItemRangeRemoved(0, mDealModels.size());
        this.mDealModels = dealModels;
        notifyItemRangeChanged(0, mDealModels.size());
    }

    public static class DealViewHolder extends RecyclerView.ViewHolder {
        private final CardDealBinding mCardDealBinding;
        private final ImageButton mBtnDealMessage;

        public DealViewHolder(@NonNull CardDealBinding cardDealBinding) {
            super(cardDealBinding.getRoot());
            this.mCardDealBinding = cardDealBinding;
            this.mBtnDealMessage = cardDealBinding.btnDealMessage;
        }
    }
}
