package com.example.choiceproperties.Models;


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
    private String note;
    private String dateTime;
    private String discussion;
    private String customerId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("mobile", mobile);
        result.put("note", note);
        result.put("dateTime", dateTime);
        result.put("discussion", discussion);
        result.put("customerId", customerId);
        result.put("createdDateTime", getCreatedDateTime());

        return result;
    }

}