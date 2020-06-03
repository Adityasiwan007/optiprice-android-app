package com.ril.digitalwardrobeAI.Model;

public class Task {

    private String imagePath;
    private String condition;
    private String hot;
    private Double marketPrice;
    private int fs;

    public Task(String imagePath, String condition,Double marketPrice,int fs,String hot) {
        this.imagePath = imagePath;
        this.condition = condition;
        this.marketPrice=marketPrice;
        this.fs=fs;
        this.hot=hot;
    }
}
