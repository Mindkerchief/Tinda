package com.lspuspcc.tinda.domain;

public class SearchCategoryModel {
    private int mCategoryIcon;
    private String mCategoryName;
    private byte mCategoryIndex;

    public SearchCategoryModel(int categoryIcon, String categoryName, byte categoryIndex) {
        this.mCategoryIcon = categoryIcon;
        this.mCategoryName = categoryName;
        this.mCategoryIndex = categoryIndex;
    }

    public int getCategoryIcon() {
        return mCategoryIcon;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public byte getCategoryIndex() {
        return mCategoryIndex;
    }
}
