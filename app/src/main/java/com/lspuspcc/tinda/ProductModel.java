package com.lspuspcc.tinda;

public class ProductModel {
    private int productImage;
    private String productName;
    private String productStore;
    private String productPrice;

    public ProductModel(int productImage, String productName, String productStore, String productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productStore = productStore;
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductStore() {
        return productStore;
    }

    public void setProductStore(String productStore) {
        this.productStore = productStore;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
