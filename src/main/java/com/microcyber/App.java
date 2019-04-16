package com.microcyber;

import com.microcyber.offical.MqttTest;
import com.microcyber.offical.MysqlTest;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //选择测试种类
        System.out.println("enter test type:");
        System.out.println("mqtt: 1");
        System.out.println("mysql: 2");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                MqttTest.testMqtt();
                break;
            case "2":
                MysqlTest.test();
                break;
        }

    }
}
