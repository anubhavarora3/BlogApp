package com.example.anubhav.blogapp.model;

/**
 * Created by anubhav on 31/07/17.
 */

public class Blog {

    public String title, description, timeStamp, userId, image;

    public Blog() {
    }

    public Blog(String title, String description, String timeStamp, String userId, String image) {
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.image = image;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
