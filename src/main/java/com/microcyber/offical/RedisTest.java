package com.microcyber.offical;

import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class RedisTest {

    public static void test() {

        Scanner scanner = new Scanner(System.in);
        //1.dbName
        System.out.println("enter ip address of redis:");
        String dbIp = scanner.nextLine();

        //2.password
        System.out.println("enter password:");
        String password = scanner.nextLine();

        Jedis jedis=new Jedis(dbIp,6379);
        jedis.auth(password);
        jedis.set("temp:unicloud-test","value1");
        jedis.expire("temp:unicloud-test", 5);
        if("value1".equals(jedis.get("temp:unicloud-test"))){
            System.out.println("设置key---获取key---成功");
        }

    }
}