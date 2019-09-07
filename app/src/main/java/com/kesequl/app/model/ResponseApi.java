package com.kesequl.app.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApi<T> {
    @SerializedName("pesan")
    private String pesan;

    @SerializedName("data")
    private T data;

    @SerializedName("status")
    private int status;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
