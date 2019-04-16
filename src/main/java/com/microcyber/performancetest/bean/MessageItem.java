package com.microcyber.performancetest.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class MessageItem {
    private String name;
    private Object value;

    @JSONField(serialize=false)
    private int dataType;       //数据类型:1整数(网关给的没有long,全是int);2浮点数(全是double);3字符串;4布尔值

    @JSONField(serialize=false)
    private double maxValue;    //上限

    @JSONField(serialize=false)
    private double minValue;    //下限


    public MessageItem(int dataType, String name, Object value) {
        this.dataType = dataType;
        this.name = name;
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }
}
