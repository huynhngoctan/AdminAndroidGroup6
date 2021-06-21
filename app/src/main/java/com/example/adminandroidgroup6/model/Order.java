package com.example.adminandroidgroup6.model;

import java.io.Serializable;

public class Order implements Serializable {

    String idOrder;
    String idUser;
    String dateCreate;
    String dateDelivery;
    String status;
    String dateUpdate;
    int totalPrice;
    String paymentOption;
    String address;
    String phone;

    public Order(String idOrder, String idUser, String dateCreate, String dateDelivery, String status, String dateUpdate, int totalPrice, String paymentOption, String address, String phone) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.dateCreate = dateCreate;
        this.dateDelivery = dateDelivery;
        this.status = status;
        this.dateUpdate = dateUpdate;
        this.totalPrice = totalPrice;
        this.paymentOption = paymentOption;
        this.address = address;
        this.phone = phone;
    }

    public Order() {
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
