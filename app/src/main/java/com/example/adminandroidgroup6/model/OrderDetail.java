package com.example.adminandroidgroup6.model;

public class OrderDetail {
    String idOrderDetail;
    String idOrder;
    String idFood;
    int quantity;

    public OrderDetail(String idOrderDetail, String idOrder, String idFood, int quantity) {
        this.idOrderDetail = idOrderDetail;
        this.idOrder = idOrder;
        this.idFood = idFood;
        this.quantity = quantity;
    }

    public OrderDetail() {
    }

    public String getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(String idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
