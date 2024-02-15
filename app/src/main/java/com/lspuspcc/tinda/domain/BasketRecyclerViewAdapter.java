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
import com.lspuspcc.tinda.ui.basket.BasketFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder> {
    private final LifecycleOwner mBasketLifecycle;
    private final ArrayList<BasketModel> mBasketModels;
    private final MutableLiveData<HashSet<BasketModel>> mSelectedItems;
    private boolean mFirstLoad;

    public BasketRecyclerViewAdapter(BasketFragment basketFragment, ArrayList<BasketModel> basketModels,
                                     MutableLiveData<HashSet<BasketModel>> selectedItems) {
        this.mBasketLifecycle = basketFragment;
        this.mBasketModels = basketModels;
        this.mSelectedItems = selectedItems;
        this.mFirstLoad = true;
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
        holder.mCardBasketProductBinding.setItem(basketModel);
        holder.mCardBasketProductBinding.executePendingBindings();

        // Handle views events
        basketModel.getProductCount().observe(mBasketLifecycle, aByte -> {
            // Changes in productCount happens inside the model
            if (!mFirstLoad) {
                holder.updateProductCount(Objects.requireNonNull(basketModel.getProductCount().getValue()));

                if (basketModel.getIsProductSelected())
                    mSelectedItems.setValue(mSelectedItems.getValue());
            }
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

        if (mFirstLoad) {
            if (basketModel.getIsProductSelected())
                Objects.requireNonNull(mSelectedItems.getValue()).add(basketModel);

            // Update product count and total price if this is the last position
            if (position == mBasketModels.size() - 1) {
                mFirstLoad = false;
                mSelectedItems.setValue(mSelectedItems.getValue());
            }
        }
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
