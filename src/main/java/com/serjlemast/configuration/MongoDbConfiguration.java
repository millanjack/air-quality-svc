package com.serjlemast.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCompressor;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoDbConfiguration extends AbstractMongoClientConfiguration {
  private final MongoProperties mongoProperties;

  @Bean
  public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    log.info("MongoDb configuration MongoTransactionManager");
    return new MongoTransactionManager(dbFactory);
  }

  @Override
  protected String getDatabaseName() {
    var databaseName =
        Objects.requireNonNull(mongoProperties.getDatabase(), "Database name is required");
    log.info("MongoDb configuration database properties: {}", databaseName);
    return mongoProperties.getDatabase();
  }

  @Override
  public MongoClient mongoClient() {
    var uri = Objects.requireNonNull(mongoProperties.getUri(), "Database URI is required");
    log.info("MongoDb configuration URI property: {}", uri);
    var connectionString = new ConnectionString(uri);
    var settings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToConnectionPoolSettings(this::buildConnectionPoolSettings)
            .applyToSocketSettings(this::buildMongoSocketSettings)
            // Use Write Concern
            .writeConcern(WriteConcern.MAJORITY)
            // and Read Preference
            .readPreference(ReadPreference.primary())
            // Enable Snappy Compression to reduces network load
            .compressorList(List.of(MongoCompressor.createSnappyCompressor()))
            .build();
    return MongoClients.create(settings);
  }

  private ConnectionPoolSettings.Builder buildConnectionPoolSettings(
      ConnectionPoolSettings.Builder builder) {
    return builder.minSize(10).maxSize(100);
  }

  private SocketSettings.Builder buildMongoSocketSettings(SocketSettings.Builder builder) {
    return builder //
        .connectTimeout(3, TimeUnit.SECONDS)
        .readTimeout(3, TimeUnit.SECONDS);
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }
}