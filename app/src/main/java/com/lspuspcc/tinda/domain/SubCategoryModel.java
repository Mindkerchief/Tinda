package com.lspuspcc.tinda.domain;

public class SubCategoryModel {
    private final int mSubCategoryImage;
    private final String mSubCategoryName;

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
