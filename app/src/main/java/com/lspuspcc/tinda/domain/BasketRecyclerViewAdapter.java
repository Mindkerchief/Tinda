package com.lspuspcc.tinda.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lspuspcc.tinda.R;
import com.lspuspcc.tinda.ui.basket.BasketCallBack;
import com.lspuspcc.tinda.ui.basket.BasketFragment;

import java.util.ArrayList;
import java.util.Locale;

public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.MyViewModel> {
    private final Context mContext;
    private final BasketCallBack mBasketCallBack;
    private final ArrayList<BasketModel> mBasketModels;

    public BasketRecyclerViewAdapter(BasketFragment basketFragment, ArrayList<BasketModel> basketModels) {
        this.mContext = basketFragment.getContext();
        this.mBasketCallBack = basketFragment;
        this.mBasketModels = basketModels;
    }

    @NonNull
    @Override
    public BasketRecyclerViewAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.card_basket_product, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketRecyclerViewAdapter.MyViewModel holder, int position) {
        holder.imageVProductImage.setImageResource(mBasketModels.get(position).getProductImage());
        holder.textVProductName.setText(mBasketModels.get(position).getProductName());
        holder.textVProductStore.setText(mBasketModels.get(position).getProductStore());
        holder.checkBoxProductSelection.setChecked(mBasketModels.get(position).getIsProductSelected());

        String productPrice = "₱" + String.format(Locale.ENGLISH, "%.2f", mBasketModels.get(position).getProductPrice());
        holder.textVProductPrice.setText(productPrice);

        if (mBasketModels.get(position).getProductCount() < 2)
            holder.btnSubtractItem.setActivated(false);
        else if (mBasketModels.get(position).getProductCount() > 99)
            holder.btnAddItem.setActivated(false);

        // Handle views events
        holder.checkBoxProductSelection.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBasketCallBack.setCountAndSubTotal(isChecked, mBasketModels.get(position).getProductTotalPrice(),
                    mBasketModels.get(position).getProductCount());
            mBasketModels.get(position).setIsProductSelected(isChecked);
        });

        holder.btnDeleteOnBasket.setOnClickListener(v -> {
            // Trigger OnCheckedChange event to remove product count and subtotal
            holder.checkBoxProductSelection.setChecked(false);
            mBasketModels.remove(position);

            notifyItemRemoved(position);
            // TODO: For some reason, this produce an array out of bounds even though the index is correct
            notifyItemRangeChanged(position, mBasketModels.size() - position);
        });

        holder.btnAddItem.setOnClickListener(v -> {
            updateItemCount(holder.textVProductCount, holder.btnAddItem, holder.btnSubtractItem, position, true);
        });

        holder.btnSubtractItem.setOnClickListener(v -> {
            updateItemCount(holder.textVProductCount, holder.btnAddItem, holder.btnSubtractItem, position, false);
        });
    }

    @Override
    public int getItemCount() {
        return mBasketModels.size();
    }

    public static class MyViewModel extends RecyclerView.ViewHolder {
        ImageView imageVProductImage;
        TextView textVProductName, textVProductStore, textVProductPrice, textVProductCount;
        CheckBox checkBoxProductSelection;
        ImageButton btnDeleteOnBasket, btnAddItem, btnSubtractItem;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            imageVProductImage = itemView.findViewById(R.id.imageV_basketProductImage);
            textVProductName = itemView.findViewById(R.id.textV_basketProductName);
            textVProductStore = itemView.findViewById(R.id.textV_basketProductStore);
            textVProductPrice = itemView.findViewById(R.id.textV_basketProductPrice);
            textVProductCount = itemView.findViewById(R.id.textV_productCount);
            checkBoxProductSelection = itemView.findViewById(R.id.checkBox_productSelection);
            btnDeleteOnBasket =itemView.findViewById(R.id.btn_deleteOnBasket);
            btnAddItem = itemView.findViewById(R.id.btn_addItem);
            btnSubtractItem = itemView.findViewById(R.id.btn_subtractItem);
        }
    }

    private void updateItemCount(TextView textVProductCount, ImageButton btnAddItem, ImageButton btnSubtractItem,
                                 int position, boolean isAddItem) {
        int itemToAdd = (isAddItem) ? 1 : -1;
        float itemPrice = mBasketModels.get(position).getProductPrice() * itemToAdd;
        String newProductCount = String.valueOf(Integer.parseInt(textVProductCount.getText().toString()) + itemToAdd);

        // Limit the item count from 1 - 100
        if (newProductCount.equals("1") | newProductCount.equals("0"))
            btnSubtractItem.setClickable(false);
        else if (newProductCount.equals("2"))
            btnSubtractItem.setClickable(true);
        else if (newProductCount.equals("99"))
            btnAddItem.setClickable(true);
        else if (newProductCount.equals("100"))
            btnAddItem.setClickable(false);

        mBasketModels.get(position).setProductCount(Short.parseShort(newProductCount));
        textVProductCount.setText(newProductCount);

        if (mBasketModels.get(position).getIsProductSelected())
            mBasketCallBack.setCountAndSubTotal(true, itemPrice, (short) itemToAdd);
    }
}
