package com.lspuspcc.tinda.domain;

public class BasketModel {
    private boolean mIsProductSelected;
    private final int mProductImage;
    private final String mProductName;
    private final String mProductStore;
    private final float mProductPrice;
    private double mProductTotalPrice;
    private short mProductCount;

    public BasketModel(boolean isProductSelected, int productImage, String productName, String productStore,
                       float productPrice, short productCount) {
        this.mIsProductSelected = isProductSelected;
        this.mProductImage = productImage;
        this.mProductName = productName;
        this.mProductStore = productStore;
        this.mProductPrice = productPrice;
        this.mProductTotalPrice = productPrice;
        this.mProductCount = productCount;
    }

    public boolean getIsProductSelected() {
        return mIsProductSelected;
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

    public double getProductTotalPrice() {
        return mProductTotalPrice;
    }

    public short getProductCount() {
        return mProductCount;
    }

    public void setIsProductSelected(boolean isProductSelected) {
        this.mIsProductSelected = isProductSelected;
    }

    public void setProductCount(short productCount) {
        this.mProductTotalPrice = mProductPrice * productCount;
        this.mProductCount = productCount;
    }
}
