package com.lspuspcc.tinda.domain;

import java.text.DecimalFormat;

public class DealModel {
    private final int mDealImage;
    private final String mDealProduct;
    private final float mDealPrice;
    private final String mDealDescription;

    public DealModel(int dealImage, String dealProduct, float dealPrice, String dealDescription) {
        this.mDealImage = dealImage;
        this.mDealProduct = dealProduct;
        this.mDealPrice = dealPrice;
        this.mDealDescription = dealDescription;
    }

    public int getDealImage() {
        return mDealImage;
    }

    public String getDealProduct() {
        return mDealProduct;
    }

    public String getDealDescription() {
        return mDealDescription;
    }

    public float getDealPrice() {
        return mDealPrice;
    }

    public String getFormattedDealPrice() {
        return new DecimalFormat("₱###,###,###,##0.00").format(mDealPrice);
    }
}
