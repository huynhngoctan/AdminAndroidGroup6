package com.example.adminandroidgroup6.model;

public class Food {
    private String id;
    private String foodName;
    private String type;
    private double price;
    private String status;
    private String linkImage;
    private boolean isTopping;

    public Food(String foodName, String type, double price, String status, String linkImage,boolean isTopping) {
        this.foodName = foodName;
        this.type = type;
        this.price = price;
        this.status = status;
        this.linkImage = linkImage;
        this.isTopping =isTopping;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
}
