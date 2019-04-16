package com.microcyber.reconnect;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 这个类负责Mqtt的连接
 * 引入了断线重连的机制,如论是pub还是sub,都能断线重连
 * 注:要么是pub,要么是sub,不可以同时sub和pub
 */
public class MqttConnection {
    public String HOST = "tcp://192.168.2.186:1883";        //Mqtt服务器地址
    public String[] SUB_TOPIC = {"topic1/+/+/+/+/+","topic2/+/+","topic3/+/+"};        //要订阅的主题
    public int[] QOS_SUB = {2,2,2};     //pub和sub的qos是不同的
    public int[] QOS_PUB = {2};
    public String CLIENT_ID = "pahoSubClient"+Math.random();    //相同clientId不能同时在线,否则被踢掉
    public String USERNAME = "test"+Math.random();;
    public String PASSWORD = "123456";

    //这个对象才是核心,每次重连,都重新创建一个MqttClient对象
    public MqttClient client;

    /**
     * 本方法会重新创建一个client,而不是重连
     * 如果重新创建的这个client能连接到emqtt服务器上,就返回true,否则返回false
     */
    public boolean init() {
        try {
            MqttClient tempClient = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
            tempClient.connect(getOptions());
            client = tempClient;    //连接成功后,把这个MqttClient对象赋值到全局变量上去
            System.out.println("init()执行成功(Mqtt连接成功)");
            return true;
        } catch (MqttException e) {
            System.out.println("init()执行失败");
        }
        return false;
    }

    /**
     * 订阅消息时使用,只是反复的调用上面的init()方法
     * 直到连接成功为止,否则本方法就一直循环尝试连接(会阻塞),连接成功就不阻塞了
     * 本方法在两个地方被使用:1.程序开始运行时; 2.connectionLost事件发生时
     */
    public void loopInitSub(MqttCallback callBack) {
        while (true) {
            boolean initResult = init();
            if (initResult) {   //如果连接成功,就给client设置callback,并让它订阅
                try {
                    client.subscribe(SUB_TOPIC, QOS_SUB);
                    client.setCallback(callBack);
                } catch (MqttException e) {
                    System.out.println("订阅到topic时出错");
                    sleepSafe(1000);
                    continue;   //按理说这个订阅不会出错,但是如果真的出错了,就继续重试
                }
                break;
            }
            sleepSafe(1000);
        }
    }

    /**
     * 发布消息时使用,只是反复的调用上面的init()方法
     */
    public void loopInitPub(MqttCallback callBack) {
        while (true) {
            boolean initResult = init();
            if (initResult) {
                client.setCallback(callBack);
                break;
            }
            sleepSafe(1000);
        }
    }

    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        return options;
    }

    public void sleepSafe(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
