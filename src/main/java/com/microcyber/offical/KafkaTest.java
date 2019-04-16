package com.microcyber.offical;

import java.util.Scanner;

public class KafkaTest {

    public static void test() {

        Scanner scanner = new Scanner(System.in);
        //1.dbName
        System.out.println("enter ip address of mysql:");
        String dbIp = scanner.nextLine();

        //2.dbName
        System.out.println("enter dbName:(for example db10)");
        String dbName = scanner.nextLine();

    }
}