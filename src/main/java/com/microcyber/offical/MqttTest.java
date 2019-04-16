package com.microcyber.offical;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Scanner;

/**
 * 这个是来自官方的例子:https://www.eclipse.org/paho/clients/java/
 */
public class MqttTest {

    public static void testMqtt() {
        Scanner scanner = new Scanner(System.in);
        //1.topic
        System.out.println("enter topic:");
        String topic = scanner.nextLine();
        //2.content
        System.out.println("enter content:");
        String content = scanner.nextLine();
        //3.content
        System.out.println("enter username:");
        String username = scanner.nextLine();
        //4.content
        System.out.println("enter password:");
        String password = scanner.nextLine();
        //5.ip
        System.out.println("ip:");
        String ip = scanner.nextLine();
        String broker = "tcp://" + ip + ":1883";

        String clientId = "JavaSampleClientId";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            sampleClient.publish(topic, message);
            System.out.println("Message published successfully");
            sampleClient.disconnect();
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason :" + me.getReasonCode());
            System.out.println("msg :" + me.getMessage());
            System.out.println("loc :" + me.getLocalizedMessage());
            System.out.println("cause :" + me.getCause());
            System.out.println("excep :" + me);
            me.printStackTrace();
        }
    }
}