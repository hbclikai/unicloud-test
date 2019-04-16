package com.microcyber.offical;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class KafkaProducerTest {

    public static void test() throws InterruptedException {
        String boostrapServers = "192.168.2.4:9092,192.168.2.5:9092,192.168.2.6:9092";

        // 读取topic
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter topic:(for example topic1)");
        String topic = scanner.nextLine();

        System.out.println("boostrapServers: "+boostrapServers);
        System.out.println("即将向主题'topic1'发送消息....");

        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put("bootstrap.servers", boostrapServers);
        // 等待所有副本节点的应答才算写入成功.
        props.put("acks", "all");
        // 消息发送最大尝试次数
        props.put("retries", 0);
        // 发送消息的缓冲区大小(与linger.ms配合使用)
        props.put("batch.size", 16384);
        // 发送消息延迟 (默认为0)
        props.put("linger.ms", 5);
        // 内存缓存区内存大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>(topic
                    , Integer.toString(i), "test-" + i));
            System.out.println("发送消息测试成功test-" + i);
            Thread.sleep(1000);
        }
        producer.close();
    }
}