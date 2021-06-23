package com.example.adminandroidgroup6.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String foodName;
    private String type;
    private int price;
    private String status;
    private String linkImage;
    private String description;
    private boolean isTopping;

    public Food(String foodName, String type, int price, String status, String linkImage, String description, boolean isTopping) {
        this.foodName = foodName;
        this.type = type;
        this.price = price;
        this.status = status;
        this.linkImage = linkImage;
        this.description = description;
        this.isTopping = isTopping;
    }

    public Food() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public boolean isTopping() {
        return isTopping;
    }
    public void setTopping(boolean topping) {
        isTopping = topping;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
