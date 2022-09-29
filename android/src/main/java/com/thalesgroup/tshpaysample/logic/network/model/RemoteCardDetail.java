package com.thalesgroup.tshpaysample.logic.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoteCardDetail {

    @SerializedName("data")
    @Expose
    private List<CardInfo> data = null;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public List<CardInfo> getData() {
        return data;
    }

    public void setData(List<CardInfo> data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
