package com.example.choiceproperties_sales.Models;


import androidx.core.app.NotificationCompat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Customer implements Serializable {

    private String name;
    private String address;
    private String mobile;
    private String attendedBy;
    private String dateTime;
    private String discussion;
    private String customerId;
    private String status;
    private Long createdDateTime;


    //empty constructor is neaded
    public Customer() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAttendedBy() {
        return attendedBy;
    }

    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("mobile", mobile);
        result.put("attendedBy", attendedBy);
        result.put("dateTime", dateTime);
        result.put("discussion", discussion);
        result.put("customerId", customerId);
        result.put("status", status);
        result.put("createdDateTime", getCreatedDateTime());

        return result;
    }

}