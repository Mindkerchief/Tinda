package com.lspuspcc.tinda.ui.basket;

public interface BasketCallBack {
    void deleteOnBasket();
    void productSelected(float amountToAdd);
    void productUnselected(float amountToSubtract);
}
