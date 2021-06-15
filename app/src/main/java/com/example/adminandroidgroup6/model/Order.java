package com.example.adminandroidgroup6.model;

public class Order {
    private String customerName;
    private String date;
    private String address;

    public Order(String customerName, String date, String address) {
        this.customerName = customerName;
        this.date = date;
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
