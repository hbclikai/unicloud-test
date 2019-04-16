package com.microcyber.presure;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;
import java.util.Random;

public class PublishTest {
    private static String topic = "topic/";
    private static String content = "来自paho.client的消息";
    private static int qos = 0;
    private static String broker = "tcp://192.168.2.186:1883";
    private static String userName = "user1";
    private static String password = "123456";
    private static String clientId = "PublishTestClient1";
    private static MemoryPersistence persistence = new MemoryPersistence(); // 内存存储

    public static void main(String[] args) throws MqttException, InterruptedException {
        // 创建客户端
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        // 创建链接参数
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // 在重新启动和重新连接时记住状态
        connOpts.setCleanSession(true);
        connOpts.setUserName(userName);
        connOpts.setPassword(password.toCharArray());
        sampleClient.connect(connOpts);

        Random random = new Random();
        byte[] bytes = new byte[1000];
        // 循环发布
        for (int i = 0; i < 100000000; i++) {
            random.nextBytes(bytes);
            MqttMessage message = new MqttMessage(bytes);
            message.setQos(qos);
            sampleClient.publish(topic+random.nextInt(10000), message);
            System.out.println(i+"已发送");
            Thread.sleep(1);
        }

        sampleClient.disconnect();  // 断开连接
        sampleClient.close();   // 关闭客户端
    }
}
