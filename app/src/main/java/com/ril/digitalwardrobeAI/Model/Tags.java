package com.ril.digitalwardrobeAI.Model;

public class Tags {

    String title;
    String colorHex;

    public String getBaseColorHex() {
        return baseColorHex;
    }

    public void setBaseColorHex(String baseColorHex) {
        this.baseColorHex = baseColorHex;
    }

    String baseColorHex;
    String description;



    String category;

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Tags(String title, String colorHex, String description) {
        this.title = title;
        this.colorHex = colorHex;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
