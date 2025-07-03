package com.example.kafkademo.properties.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String address;
    private Topic topic;
    private Consumer consumer;
    private Listener listener;

    @Getter
    @Setter
    public static class Topic {
        private String photoUpload;
        private String photoUploadDlt;
    }

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
        private String dltGroupId;
        private boolean enableAutoCommit;
        private String autoOffsetReset;
        private Map<String, Object> properties = new HashMap<>();
        private int concurrency;
    }

    @Getter
    @Setter
    public static class Listener {
        private String ackMode;
    }
}