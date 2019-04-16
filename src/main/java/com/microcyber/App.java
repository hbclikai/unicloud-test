package com.microcyber;

import com.microcyber.offical.KafkaTest;
import com.microcyber.offical.MqttTest;
import com.microcyber.offical.MysqlTest;
import com.microcyber.offical.RedisTest;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //选择测试种类
        System.out.println("enter test type:");
        System.out.println("mqtt: 1");
        System.out.println("mysql: 2");
        System.out.println("redis: 3");
        System.out.println("influxdb: 4");
        System.out.println("kafka: 5");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                MqttTest.test();
                break;
            case "2":
                MysqlTest.test();
                break;
            case "3":
                RedisTest.test();
                break;
            case "4":
                System.out.println("run: 'curl 192.168.2.13:8086' yourself");
                break;
            case "5":
                KafkaTest.test();
                break;
        }

    }
}
