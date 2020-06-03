package com.ril.digitalwardrobeAI.Model;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
public class MissingItems {
    @Expose
    private ArrayList<MissingItembean> missingItems;

    public ArrayList<MissingItembean> getMissingDetails() {
        return missingItems;
    }

    public void setMissingDetails(ArrayList<MissingItembean> missingDetails) {
        this.missingItems = missingDetails;
    }
}
