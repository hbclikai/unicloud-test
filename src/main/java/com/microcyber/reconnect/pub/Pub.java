package com.microcyber.reconnect.pub;

import com.microcyber.reconnect.MqttConnection;

public class Pub {
    public static void main(String[] args) {
        //创建MqttConnection,并使用它的loopInitPub方法,开启Pub
        MqttConnection mqtt=new MqttConnection();
        mqtt.loopInitPub(new PubCallBack(mqtt));
        //在这个线程中发送消息(需要把mqtt对象传进去)
        SenderThread sender = new SenderThread(mqtt);
        sender.start();
    }
}
