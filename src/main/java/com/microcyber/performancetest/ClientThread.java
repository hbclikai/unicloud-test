package com.microcyber.performancetest;

import com.alibaba.fastjson.JSONObject;
import com.microcyber.reconnect.MqttConnection;
import com.microcyber.performancetest.bean.McMessage;
import com.microcyber.performancetest.bean.MessageItem;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientThread extends Thread {
    private MqttConnection mqtt;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d H:m:s");

    //构造方法中,只是保存对MqttConnection对象的引用
    public ClientThread(MqttConnection mqtt) {
        this.mqtt = mqtt;
    }

    @Override
    public void run() {
        McMessage message = getInitMessage();

        for (int i = 1; i < 100000000; i++) {
            sendOneMessage("Topic/mc/mqttclient/300218010516/system/MonitorData", JSONObject.toJSONString(message));
//            System.out.println(JSONObject.toJSONString(message, false));
//            try {
                changeData(message);
//                Thread.sleep(1);
                if (i % 1000 == 0) {
                    System.out.println("已发送" + i);
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 每次发送之前,都修改一下消息的数据
     */
    private void changeData(McMessage message) {
        List<MessageItem> dataList = message.getData();
        //step1.更新时间
        message.setTime(formatter.format(new Date()));

        //step2.更新所有item
        for (MessageItem item : dataList) {
            int dataType = item.getDataType();
            if (dataType == 1) {    //1整数
                int i = Integer.parseInt(item.getValue().toString()); //当前值
                double newValue = i * (Math.random() * 0.2 + 0.9);   //0.9倍到1.1倍之间
                int newIntValue = (int) Math.round(newValue);

                //如果超过上下或低于下限,就拉回来
                if (newIntValue > item.getMaxValue()) {
                    newIntValue = (int) Math.round(item.getMaxValue());
                }
                if (newIntValue < item.getMinValue()) {
                    newIntValue = (int) Math.round(item.getMinValue());
                }
                //为item赋值
                item.setValue(newIntValue);
            }

            if (dataType == 2) {    //2浮点数
                double i = Double.parseDouble(item.getValue().toString());
                double newValue = i * (Math.random() * 0.2 + 0.9);   //0.9倍到1.1倍之间

                //如果超过上下或低于下限,就拉回来
                if (newValue > item.getMaxValue()) {
                    newValue = item.getMaxValue();
                }
                if (newValue < item.getMinValue()) {
                    newValue = item.getMinValue();
                }
                //为item赋值
                item.setValue(String.format("%.2f", newValue));
            }

            if (dataType == 4) {  //4布尔值
                if (Math.random() > 0.9) {  //10%的几率产生反转
                    boolean oldValue = Boolean.parseBoolean(item.getValue().toString());
                    item.setValue(!oldValue);
                }
            }
        }
    }

    /**
     * 获取初始消息
     */
    private McMessage getInitMessage() {
        //创建McMessage对象
        String initTime = formatter.format(new Date());
        List<MessageItem> dataList = new ArrayList<>();
        McMessage mcMessage = new McMessage(initTime, dataList);
        mcMessage.setData(dataList);

        //初始值(整数不要太小,否则每次都不能变化)
        dataList.add(new MessageItem(2, "球磨机1.温度1", 11.1));
        dataList.add(new MessageItem(2, "球磨机1.温度2", 12.2));
        dataList.add(new MessageItem(2, "破碎机1.温度1", 13.3));
        dataList.add(new MessageItem(2, "破碎机1.温度2", 14.4));
        dataList.add(new MessageItem(1, "破碎机1.左流量", 101));
        dataList.add(new MessageItem(1, "破碎机1.右流量", 102));
        dataList.add(new MessageItem(1, "破碎机2.左流量", 113));
        dataList.add(new MessageItem(1, "破碎机2.右流量", 114));
        dataList.add(new MessageItem(1, "破碎机2.1#流量", 115));
        dataList.add(new MessageItem(1, "破碎机2.2#流量", 116));
        dataList.add(new MessageItem(1, "破碎机2.3#流量", 117));
        dataList.add(new MessageItem(1, "破碎机2.4#流量", 118));
        dataList.add(new MessageItem(4, "破碎机2.开关量1", true));
        dataList.add(new MessageItem(4, "破碎机2.开关量2", false));
        dataList.add(new MessageItem(2, "破碎机2.1#电流", 20.5));
        dataList.add(new MessageItem(2, "破碎机2.2#电流", 20.6));

        //给上下限赋值
        for (MessageItem item : dataList) {
            if (item.getDataType() == 1) {  //整数
                int i = Integer.parseInt(item.getValue().toString());
                item.setMaxValue(i * 1.5);
                item.setMinValue(i * 0.6);
            }
            if (item.getDataType() == 2) {  //小数
                double i = Double.parseDouble(item.getValue().toString());
                item.setMaxValue(i * 1.5);
                item.setMinValue(i * 0.6);
            }
        }
        return new McMessage(initTime, dataList);
    }

//    //保留两位小数
//    private double toDouble2(double oldValue){
//        return
//    }

    private boolean sendOneMessage(String topic, String message) {
        byte[] bytes = message.getBytes(Charset.forName("GBK"));
        MqttMessage mqttMsg = new MqttMessage(bytes);
        mqttMsg.setQos(mqtt.QOS_PUB[0]);
        try {
            mqtt.client.publish(topic, mqttMsg);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }
}
