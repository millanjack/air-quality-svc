package com.serjlemast.repository;

import com.serjlemast.repository.entity.SensorDataEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorDataRepository {

  private final MongoTemplate mongoTemplate;

  public List<SensorDataEntity> findLastDayDataBySensorId(String sensorId) {
    LocalDateTime oneHourAgo = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);
    Query query = new Query();
    query.addCriteria(Criteria.where("created").gte(oneHourAgo).and("sensorId").is(sensorId));
    return mongoTemplate.find(query, SensorDataEntity.class);
  }
}
