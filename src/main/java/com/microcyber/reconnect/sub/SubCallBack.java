package com.microcyber.reconnect.sub;

import com.microcyber.reconnect.MqttConnection;
import org.eclipse.paho.client.mqttv3.*;

public class SubCallBack implements MqttCallback {
    private MqttConnection mqtt;

    //构造方法中,只是保存对MqttConnection对象的引用
    public SubCallBack(MqttConnection mqtt){
        this.mqtt=mqtt;
    }

    public void connectionLost(Throwable cause) {
        System.out.println("SubCallBack: connectionLost");
        mqtt.loopInitSub(this);
    }

    //这个方法只在Sub时生效
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("topic:"+topic);
        System.out.println("Qos:"+message.getQos());
        System.out.println("message content:"+new String(message.getPayload(),"GBK"));

    }

    //这个方法只在Pub时生效
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
