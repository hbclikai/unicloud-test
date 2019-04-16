package com.microcyber.reconnect.pub;

import com.microcyber.reconnect.MqttConnection;
import com.microcyber.util.FileUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class SenderThread extends Thread {
    private MqttConnection mqtt;

    //构造方法中,只是保存对MqttConnection对象的引用
    public SenderThread(MqttConnection mqtt){
        this.mqtt=mqtt;
    }

    @Override
    public void run(){
        for (int i = 1; i < 10000000; i++) {
            String message = FileUtils.getResourceAsString("raw.json");
            sendOneMessage("Topic/test/b/300218010516/system/taglist ", message);
            try {
                Thread.sleep(1000);
                if (i % 100 == 0) {
                    System.out.println("已发送"+i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean sendOneMessage(String topic,String message){
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
