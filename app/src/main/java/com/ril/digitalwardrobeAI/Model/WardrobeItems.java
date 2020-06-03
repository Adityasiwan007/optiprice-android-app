package com.ril.digitalwardrobeAI.Model;

public class WardrobeItems {
    String url,type;

    public String getUrl() {
        return url;
    }

    public WardrobeItems(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
