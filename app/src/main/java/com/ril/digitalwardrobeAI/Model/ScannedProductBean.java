package com.ril.digitalwardrobeAI.Model;

public class ScannedProductBean {
    String baseHexColorOfUploadedItem;
    public String getBaseHexColorOfUploadedItem() {
        return baseHexColorOfUploadedItem;
    }

    public void setBaseHexColorOfUploadedItem(String baseHexColorOfUploadedItem) {
        this.baseHexColorOfUploadedItem = baseHexColorOfUploadedItem;
    }

    public String getDescription() {
        return description;
    }

    public ScannedProductBean() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    String description;
    String title;
    String category;
    String styleOccasion;
    String sleeveLength,pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getStyleOccasion() {
        return styleOccasion;
    }

    public void setStyleOccasion(String styleOccasion) {
        this.styleOccasion = styleOccasion;
    }

    public String getSleeveLength() {
        return sleeveLength;
    }

    public void setSleeveLength(String sleeveLength) {
        this.sleeveLength = sleeveLength;
    }

    public String getNeckline() {
        return neckline;
    }

    public void setNeckline(String neckline) {
        this.neckline = neckline;
    }

    String neckline;


    String colorHex;
}
