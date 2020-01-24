package com.example.choiceproperties.Models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Plots implements Serializable {

    private String plotnumber;
    private String plotarea;
    private String status;
    private String ploteId;
    private Long createdDateTime;


    //empty constructor is neaded
    public Plots() {
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

    public String getPlotnumber() {
        return plotnumber;
    }

    public void setPlotnumber(String plotnumber) {
        this.plotnumber = plotnumber;
    }

    public String getPlotarea() {
        return plotarea;
    }

    public void setPlotarea(String plotarea) {
        this.plotarea = plotarea;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPloteId() {
        return ploteId;
    }

    public void setPloteId(String ploteId) {
        this.ploteId = ploteId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("plotnumber", plotnumber);
        result.put("plotarea", plotarea);
        result.put("status", status);
        result.put("ploteId", ploteId);

        result.put("createdDateTime", getCreatedDateTime());

        return result;
    }

}