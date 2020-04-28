package com.example.jhump;

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload () {

    }

    public Upload(String name, String imageurl) {
        mName = name;
        mImageUrl = imageurl;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }


}
