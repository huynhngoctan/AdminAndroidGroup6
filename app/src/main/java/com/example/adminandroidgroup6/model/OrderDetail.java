package com.example.adminandroidgroup6.model;

public class OrderDetail {
    private String foodName;
    private double quantity;
    private double totalPrice;

    public OrderDetail(String foodName, double quantity, double totalPrice) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
