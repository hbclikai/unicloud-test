package com.microcyber.offical;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaTest {

    public static void test() {
        String boostrapServers = "192.168.2.4:9092,192.168.2.5:9092,192.168.2.6:9092";

        System.out.println("boostrapServers: "+boostrapServers);
        System.out.println("即将向主题'test'发送消息....");

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
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化(老师说绝大多数都是用StringSerializer,因为内容是json)
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<String, String>("test"
                    , Integer.toString(i), "hello world-" + i));
        }

        producer.close();
        System.out.println("发送消息测试成功");
    }
}