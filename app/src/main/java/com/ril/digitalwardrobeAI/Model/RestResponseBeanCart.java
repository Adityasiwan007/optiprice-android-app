package com.ril.digitalwardrobeAI.Model;

public class RestResponseBeanCart {


    boolean success = false;
    String[] cart;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String[] getCart() {
        return cart;
    }

    public void setCart(String[] cart) {
        this.cart = cart;
    }
}
