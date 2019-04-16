package com.microcyber.performancetest;

import com.microcyber.reconnect.MqttConnection;

public class SimulatorApp {
    public static void main(String[] args) {
        //创建MqttConnection,并使用它的loopInitPub方法,开启Pub
        MqttConnection mqtt=new MqttConnection();
        mqtt.loopInitPub(new NothingToDoCallBack(mqtt));
        //在这个线程中发送消息(需要把mqtt对象传进去)
        ClientThread sender = new ClientThread(mqtt);
        sender.start();
    }
}
