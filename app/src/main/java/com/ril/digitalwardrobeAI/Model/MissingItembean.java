package com.ril.digitalwardrobeAI.Model;

import com.ril.digitalwardrobeAI.DateFormat;

import java.util.ArrayList;
import java.util.Date;

public class MissingItembean {
    ArrayList<String> rawImages;
    Tags tags;
    Double price;
    Double rating;
    String occasion;
    String size;
    String manual_desc;
    String review;
    String seller;
    String grade;
    String f_scale;
    String condition;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getF_scale() {
        return f_scale;
    }

    public void setF_scale(String f_scale) {
        this.f_scale = f_scale;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getAddedOn() {
        if(addedOn==null)
        {
            addedOn=new Date(1557772200000L);
        }
        else{

            addedOn= DateFormat.setTime(addedOn,0,0,0,0);
        }
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    Date addedOn;

    public ArrayList<String> getRawImages() {
        return rawImages;
    }

    public void setRawImages(ArrayList<String> rawImages) {
        this.rawImages = rawImages;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getManual_desc() {
        return manual_desc;
    }

    public void setManual_desc(String manual_desc) {
        this.manual_desc = manual_desc;
    }
}
