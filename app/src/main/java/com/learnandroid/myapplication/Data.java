package com.learnandroid.myapplication;

public class Data {

    private String title;
    private String description;
    private String source;
    private String imageUrl;
    public static final String IMG = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.OTHx6upJeSBgJT-smbgXUwHaHa%26pid%3DApi&f=1";


    public Data(String title,String description,String source){
        new Data(title,description,source,IMG);
    }

    public Data(String title,String description,String source,String imageUrl){
        this.setTitle(title);
        this.setDescription(description);
        this.setSource(source);
        this.setImageUrl(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
