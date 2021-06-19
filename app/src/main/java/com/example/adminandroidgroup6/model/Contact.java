package com.example.adminandroidgroup6.model;

import java.io.Serializable;

public class Contact implements Serializable {
    String idUser;
    String idContact;
    String content;
    String type;
    String status;
    String dateCreate;

    public Contact() {
    }

    public Contact(String idUser, String idContact, String content, String type, String status, String dateCreate) {
        this.idUser = idUser;
        this.idContact = idContact;
        this.content = content;
        this.type = type;
        this.status = status;
        this.dateCreate = dateCreate;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdContact() {
        return idContact;
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
