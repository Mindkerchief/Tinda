package com.lspuspcc.tinda.domain;

public class SubCategoryModel {
    private int mSubCategoryImage;
    private String mSubCategoryName;

    public SubCategoryModel(int mSubCategoryImage, String mSubCategory) {
        this.mSubCategoryImage = mSubCategoryImage;
        this.mSubCategoryName = mSubCategory;
    }

    public int getSubCategoryImage() {
        return mSubCategoryImage;
    }

    public String getSubCategoryName() {
        return mSubCategoryName;
    }
}
