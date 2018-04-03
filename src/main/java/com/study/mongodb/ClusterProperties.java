package com.study.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2018-4-3 21:27:01
 */
@Component
@ConfigurationProperties(prefix = "mongodb")
@PropertySource(value = "classpath:config/mongo.properties")
public class ClusterProperties {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
