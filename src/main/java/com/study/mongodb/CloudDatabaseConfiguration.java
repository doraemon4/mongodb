package com.study.mongodb;

import com.google.common.base.Splitter;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CloudDatabaseConfiguration {
    private final Logger log = LoggerFactory.getLogger(CloudDatabaseConfiguration.class);

    @Inject
    private MongoProperties mongoProperties;
    @Inject
    private ClusterProperties clusterProperties;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        List<String> hostAndPorts = clusterProperties.getUrls();

        List<ServerAddress> addresses = new ArrayList<>();
        hostAndPorts.stream().forEach(hostAndPort -> {
            List<String> address = Splitter.on(":").splitToList(hostAndPort);
            addresses.add(new ServerAddress(address.get(0), Integer.parseInt(address.get(1))));
        });
        //此处注意认证方式(createMongoCRCredential和createScramSha1Credential的区别)
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(
                mongoProperties.getUsername(), mongoProperties.getDatabase(),
                mongoProperties.getPassword());

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder().
                socketTimeout(1000).
                readPreference(ReadPreference.secondaryPreferred()).
                build();

        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClient(addresses, mongoCredential, mongoClientOptions),
                mongoProperties.getDatabase());
        return mongoDbFactory;


    }
}
