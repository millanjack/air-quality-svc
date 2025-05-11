package com.serjlemast.repository;

import static com.serjlemast.repository.GeneralFields.CREATED_FIELD;
import static com.serjlemast.repository.GeneralFields.SENSOR_ID_FIELD;

import com.serjlemast.repository.entity.SensorDataEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorDataRepository {

  private static final String COLLECTION_NAME = "sensor_data";
  private static final int LIMIT = 200;

  private final MongoTemplate mongoTemplate;

  public List<SensorDataEntity> findLastRecordDataSortByCreatedDesc(String sensorId) {
    var query = new Query();
    query.addCriteria(Criteria.where(SENSOR_ID_FIELD).is(sensorId));
    query.limit(LIMIT);
    query.with(Sort.by(Sort.Direction.DESC, CREATED_FIELD));

    return mongoTemplate.find(query, SensorDataEntity.class);
  }

  public void save(String sensorId, String key, Number val, LocalDateTime timestamp) {
    var document =
        new Document()
            .append(SENSOR_ID_FIELD, sensorId)
            .append("key", key)
            .append("val", val)
            .append(CREATED_FIELD, timestamp);

    mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
  }
}
