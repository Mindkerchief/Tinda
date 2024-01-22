package com.lspuspcc.tinda.domain;

import android.content.Context;

import com.lspuspcc.tinda.R;

import java.util.ArrayList;

public class SetupModel {
    private Context mContext;

    public SetupModel(Context context) {
        this.mContext = context;
    }

    public ArrayList<ProductModel> setupProductModel() {
        // Temporary implementation for testing purposes
        ArrayList<ProductModel> productModels = new ArrayList<>();

        int productImage = R.drawable.sample_product_image;
        String productName = mContext.getString(R.string.label_product_name);
        String productStore = mContext.getString(R.string.label_product_store);
        String productPrice = mContext.getString(R.string.label_product_price);

        for (int i = 0; i < 10; i++) {
            productModels.add(new ProductModel(productImage, productName, productStore, productPrice));
        }

        return productModels;
    }

    public ArrayList<StoreModel> setupStoreModel() {
        // Temporary implementation for testing purposes
        ArrayList<StoreModel> storeModels = new ArrayList<>();

        int storeImage = R.drawable.sample_store_image;
        String storeName = mContext.getString(R.string.label_product_store);
        String storeAddress = mContext.getString(R.string.label_store_address);
        String storeCategory = mContext.getString(R.string.label_store_category);

        for (int i = 0; i < 10; i++) {
            storeModels.add(new StoreModel(storeImage, storeName, storeAddress, storeCategory));
        }

        return storeModels;
    }
}
