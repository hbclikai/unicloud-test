package com.microcyber.performancetest.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class McMessage {
    private String time;

    @JSONField(name = "Data")
    private List<MessageItem> Data=new ArrayList<>();

    public McMessage(){

    }

    public McMessage(String time, List<MessageItem> data) {
        this.time = time;
        Data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<MessageItem> getData() {
        return Data;
    }

    public void setData(List<MessageItem> data) {
        Data = data;
    }
}
