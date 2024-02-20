package com.lspuspcc.tinda.domain;

import java.text.DecimalFormat;

public class ProductModel {
    private final int mProductImage;
    private final String mProductName;
    private final String mProductStore;
    private final float mProductPrice;

    public ProductModel(int productImage, String productName, String productStore, float productPrice) {
        this.mProductImage = productImage;
        this.mProductName = productName;
        this.mProductStore = productStore;
        this.mProductPrice = productPrice;
    }

    public int getProductImage() {
        return mProductImage;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getProductStore() {
        return mProductStore;
    }

    public float getProductPrice() {
        return mProductPrice;
    }

    public String getFormattedProductPrice() {
        return new DecimalFormat("₱###,###,###,##0.00").format(mProductPrice);
    }
}
