package com.lspuspcc.tinda.domain;

public class ProductModel {
    private int mProductImage;
    private String mProductName;
    private String mProductStore;
    private String mProductPrice;

    public ProductModel(int productImage, String productName, String productStore, String productPrice) {
        this.mProductImage = productImage;
        this.mProductName = productName;
        this.mProductStore = productStore;
        this.mProductPrice = productPrice;
    }

    /**
     * Temporary implementation of model for testing purposes
    */

    public int getmProductImage() {
        return mProductImage;
    }

    public String getmProductName() {
        return mProductName;
    }

    public String getmProductStore() {
        return mProductStore;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }
}
