package com.example.choiceproperties.Models;


import androidx.core.app.NotificationCompat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Requests implements Serializable {

    private String address;
    private String message;
    private String mobile;
    private String name;
    private String status;
    private String requestId;
    private Long createdDateTime;


    //empty constructor is neaded
    public Requests() {
    }

    @Exclude
    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = (Long) createdDateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Exclude
    public Map getLeedStatusMap1() {
        Map<String, Object> leedMap = new HashMap();
        leedMap.put(NotificationCompat.CATEGORY_STATUS, getStatus());
        leedMap.put("createdDateTime", getCreatedDateTime());
        return leedMap;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("message", message);
        result.put("mobile", mobile);
        result.put("name", name);
        result.put("status", status);
        result.put("requestId", requestId);
        result.put("createdDateTime", getCreatedDateTime());

        return result;
    }

}