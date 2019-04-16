package com.microcyber.reconnect.sub;

import com.microcyber.reconnect.MqttConnection;

public class Sub {
    public static void main(String[] args){
        /**
         * 这个代码不是阻塞的,也就是说,一旦连接成功,main()方法就结束了
         * 会有后台的订阅线程一直在运行,所以程序不会退出
         */
        MqttConnection mqtt = new MqttConnection();
        mqtt.loopInitSub(new SubCallBack(mqtt));
    }
}
