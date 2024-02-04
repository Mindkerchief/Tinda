package com.lspuspcc.tinda.domain;

public class DealModel {
    private final int mDealImage;
    private final String mDealProduct;
    private final String mDealAddress;
    private final float mDealPrice;

    public DealModel(int dealImage, String dealProduct, String dealAddress, float dealPrice) {
        this.mDealImage = dealImage;
        this.mDealProduct = dealProduct;
        this.mDealAddress = dealAddress;
        this.mDealPrice = dealPrice;
    }

    public int getDealImage() {
        return mDealImage;
    }

    public String getDealProduct() {
        return mDealProduct;
    }

    public String getDealAddress() {
        return mDealAddress;
    }

    public float getDealPrice() {
        return mDealPrice;
    }
}
