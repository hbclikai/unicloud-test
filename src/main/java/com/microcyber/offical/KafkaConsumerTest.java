package com.microcyber.offical;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void test() {
        String boostrapServers = "192.168.2.4:9092,192.168.2.5:9092,192.168.2.6:9092";
//        String boostrapServers = "192.168.2.186:9092";

        System.out.println("boostrapServers: " + boostrapServers);
        System.out.println("即将从主题'test'消费消息....");

        Properties props = new Properties();
        // 定义kakfa 服务的地址，不是必须要把所有broker都写出来,写一个也行
        props.put("bootstrap.servers", boostrapServers);
        props.put("group.id", "unicloud-test-group-id-1");
        // 是否自动确认offset(默认为true),如果改成false,则需要手动调用consumer.commitAsync()来修改offse
        props.put("enable.auto.commit", "true");
        // 自动确认offset的时间间隔,这里是每1秒自动确认一次(默认5000)
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("topic1"));

        System.out.println("开始执行poll");

        // 读取超时时间为1000ms
        ConsumerRecords<String, String> records = consumer.poll(1000);
        System.out.println("获取消息:"+records.count()+"条");
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, partition = %s, value = %s%n"
                    , record.offset(), record.key(), record.partition(), record.value());
        }

        System.out.println("消费结束");
    }
}