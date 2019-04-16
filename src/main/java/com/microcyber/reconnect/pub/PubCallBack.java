package com.microcyber.reconnect.pub;

import com.microcyber.reconnect.MqttConnection;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Pub和Sub的时候,用的都是这个MqttCallback接口的实现类
 * 只不过messageArrived()方法只在Sub的时候生效
 * 而deliveryComplete()方法只在Pub的时候生效
 */
public class PubCallBack implements MqttCallback {
    private MqttConnection mqtt;

    //构造方法中,只是保存对MqttConnection对象的引用
    public PubCallBack(MqttConnection mqtt){
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
//        System.out.println("SubCallBack: deliveryComplete:"+ token.isComplete());
    }
}
