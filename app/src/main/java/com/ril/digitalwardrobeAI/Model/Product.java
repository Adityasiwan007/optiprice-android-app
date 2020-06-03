package com.ril.digitalwardrobeAI.Model;

import java.util.ArrayList;

public class Product {


    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags msdTags) {
        this.tags = tags;
    }

    ArrayList<String> rawImages;
    Tags tags;
    
    public ArrayList<String> getRawImages() {
        return rawImages;
    }

    public void setRawImages(ArrayList<String> rawImages) {
        this.rawImages = rawImages;
    }
}
