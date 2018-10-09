package com.enggmartservices.enggmart.models;

import java.io.Serializable;

public class ModelOrders implements Serializable {

    private String orderName = "";
    private String orderStatus = "";
    private String orderImage = "";
    private String orderID = "";

    public ModelOrders(String orderID, String orderName, String orderStatus, String orderImage) {
        this.orderID = orderID;
        this.orderImage = orderImage;
        this.orderName = orderName;
        this.orderStatus = orderStatus;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(String orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
