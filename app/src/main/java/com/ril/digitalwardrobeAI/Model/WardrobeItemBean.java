package com.ril.digitalwardrobeAI.Model;

import com.ril.digitalwardrobeAI.DateFormat;

import java.util.ArrayList;
import java.util.Date;

public class WardrobeItemBean {

    ArrayList<String> rawImages;
    Tags tags;



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



}
