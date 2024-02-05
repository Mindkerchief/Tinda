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
    private ArrayList<BasketModel> mBasketModels;

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

        String productPrice = "P" + String.format(Locale.ENGLISH, "%.2f", mBasketModels.get(position).getProductPrice());
        holder.textVProductPrice.setText(productPrice);

        holder.btnDeleteOnBasket.setOnClickListener(v -> mBasketCallBack.deleteOnBasket());
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

            btnAddItem.setOnClickListener(v -> {
                String newProductCount = String.valueOf(Integer.parseInt(textVProductCount.getText().toString()) + 1);

                if (newProductCount.equals("100"))
                    v.setClickable(false);
                else if (newProductCount.equals("1"))
                    btnSubtractItem.setClickable(true);

                textVProductCount.setText(newProductCount);
            });

            btnSubtractItem.setOnClickListener(v -> {
                String newProductCount = String.valueOf(Integer.parseInt(textVProductCount.getText().toString()) - 1);

                if (newProductCount.equals("0"))
                    v.setClickable(false);
                else if (newProductCount.equals("99"))
                    btnAddItem.setClickable(true);

                textVProductCount.setText(newProductCount);
            });
        }
    }

    public void updateBasketModel(ArrayList<BasketModel> basketModels) {
        this.mBasketModels = basketModels;
    }
}
