package com.example.pt_signup;

import android.graphics.drawable.Drawable;

public class KeywordItem {
    private String CID;
    private String imageUrl;
    private String name;
    private String addr;

    public void setCID(String CID) {
        this.CID = CID;
    }
    public void setImageUrl(String  imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String  getCID() {
        return this.CID;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public String getName() {
        return this.name;
    }
    public String getAddr() {
        return this.addr;
    }
}
