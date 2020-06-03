package com.ril.digitalwardrobeAI.Model;

import java.util.ArrayList;

public class WardrobeBean {

    ArrayList<WardrobeItemBean> wardrobeItems;

    public ArrayList<WardrobeItemBean> getWardrobeItems() {
        return wardrobeItems;
    }

    public void setWardrobeItems(ArrayList<WardrobeItemBean> wardrobeItems) {
        this.wardrobeItems = wardrobeItems;

    }

    ArrayList<String> sortedColors;

    public ArrayList<String> getSortedColors() {
        return sortedColors;
    }

    public void setSortedColors(ArrayList<String> sortedColors) {
        this.sortedColors = sortedColors;
    }
}
