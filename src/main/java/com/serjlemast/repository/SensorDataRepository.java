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

  private final MongoTemplate mongoTemplate;

  public List<SensorDataEntity> findLastRecordDataSortByCreatedDesc(String sensorId) {
    var query = new Query();
    query.addCriteria(Criteria.where("sensorId").is(sensorId));
    query.limit(200);
    query.with(Sort.by(Sort.Direction.DESC, "created"));
    return mongoTemplate.find(query, SensorDataEntity.class);
  }
}
