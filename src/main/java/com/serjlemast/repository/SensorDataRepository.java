package com.serjlemast.repository;

import com.serjlemast.repository.entity.SensorDataEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorDataRepository {

  private static final String SENSOR_ID = "sensorId";
  private static final String CREATED = "created";

  private static final int LIMIT = 200;

  private final MongoTemplate mongoTemplate;

  public List<SensorDataEntity> findLastRecordDataSortByCreatedDesc(String sensorId) {
    var query = new Query();
    query.addCriteria(Criteria.where(SENSOR_ID).is(sensorId));
    query.limit(LIMIT);
    query.with(Sort.by(Sort.Direction.DESC, CREATED));
    return mongoTemplate.find(query, SensorDataEntity.class);
  }
}
