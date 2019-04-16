package com.microcyber.performancetest;

import com.microcyber.reconnect.MqttConnection;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 什么也不做
 */
public class NothingToDoCallBack implements MqttCallback {
    private MqttConnection mqtt;

    //构造方法中,只是保存对MqttConnection对象的引用
    public NothingToDoCallBack(MqttConnection mqtt){
        this.mqtt=mqtt;
    }

    public void connectionLost(Throwable cause) {
        System.out.println("SubCallBack: connectionLost");
        mqtt.loopInitSub(this);
    }

    //这个方法只在Sub时生效
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    }

    //这个方法只在Pub时生效
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
