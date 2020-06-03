package com.ril.digitalwardrobeAI.Model;

public class AddToWardrobeBean {

    String staticAssetId;

    public AddToWardrobeBean(String staticAssetId) {
        this.staticAssetId = staticAssetId;
    }

    public String getStaticAssetId() {
        return staticAssetId;
    }

    public void setStaticAssetId(String staticAssetId) {
        this.staticAssetId = staticAssetId;
    }
}
