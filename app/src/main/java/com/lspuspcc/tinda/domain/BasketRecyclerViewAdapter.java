package com.lspuspcc.tinda.domain;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.databinding.CardBasketItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder> {
    private final LifecycleOwner mBasketLifecycle;
    private final ArrayList<BasketModel> mBasketModels;
    private final MutableLiveData<HashSet<BasketModel>> mSelectedItems;

    public BasketRecyclerViewAdapter(LifecycleOwner basketItemsLifecycle, ArrayList<BasketModel> basketModels,
                                     MutableLiveData<HashSet<BasketModel>> selectedItems) {
        this.mBasketLifecycle = basketItemsLifecycle;
        this.mBasketModels = basketModels;
        // 5
        this.mSelectedItems = selectedItems;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardBasketItemBinding cardBasketProductBinding = CardBasketItemBinding.inflate(layoutInflater, parent, false);
        return new BasketViewHolder(cardBasketProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        BasketModel basketModel = mBasketModels.get(position);

        // Handle views events
        basketModel.getProductCount().observe(mBasketLifecycle, aByte -> {
            // Changes in productCount happens inside the model
            holder.updateProductCount(Objects.requireNonNull(basketModel.getProductCount().getValue()));

            if (basketModel.getIsProductSelected())
                mSelectedItems.setValue(mSelectedItems.getValue());
        });

        holder.mCheckBoxProductSelection.setOnCheckedChangeListener((buttonView, isChecked) -> {
            basketModel.setIsProductSelected(isChecked);
            HashSet<BasketModel> newSelectedItems = Objects.requireNonNull(mSelectedItems.getValue());
            // Add or remove model
            if (isChecked)
                newSelectedItems.add(basketModel);
            else
                newSelectedItems.remove(basketModel);

            mSelectedItems.setValue(newSelectedItems);
        });

        holder.mBtnDeleteOnBasket.setOnClickListener(v -> {
            // Trigger OnCheckedChange event to remove product count and subtotal
            holder.mCheckBoxProductSelection.setChecked(false);
            mBasketModels.remove(position);

            // Not sure what payload do but it prevents ouf of index error
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mBasketModels.size() - position, 1);
        });

        // Let all event listener and observer to initialized before setting the model
        holder.mCardBasketProductBinding.setItem(basketModel);
        holder.mCardBasketProductBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBasketModels.size();
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder {
        private final CardBasketItemBinding mCardBasketProductBinding;
        private final CheckBox mCheckBoxProductSelection;
        private final ImageButton mBtnDeleteOnBasket;

        public BasketViewHolder(@NonNull CardBasketItemBinding cardBasketProductBinding) {
            super(cardBasketProductBinding.getRoot());
            mCardBasketProductBinding = cardBasketProductBinding;
            mCheckBoxProductSelection = cardBasketProductBinding.checkBoxProductSelection;
            mBtnDeleteOnBasket = cardBasketProductBinding.btnDeleteOnBasket;
        }

        public void updateProductCount(byte newProductCount) {
            // Limit product count to 1-99
            mCardBasketProductBinding.textVProductCount.setText(String.valueOf(newProductCount));
            mCardBasketProductBinding.btnAddItem.setEnabled(newProductCount < 99);
            mCardBasketProductBinding.btnSubtractItem.setEnabled(newProductCount > 1);
        }
    }
}
