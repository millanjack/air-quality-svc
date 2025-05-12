package com.serjlemast.repository;

import static com.serjlemast.repository.GeneralFields.CREATED_FIELD;
import static com.serjlemast.repository.GeneralFields.KEY_FIELD;
import static com.serjlemast.repository.GeneralFields.SENSOR_ID_FIELD;
import static com.serjlemast.repository.GeneralFields.VAL_FIELD;

import com.serjlemast.repository.entity.SensorDataEntity;
import java.time.LocalDateTime;
import java.util.Comparator;
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

  /*
   * Retrieves the latest 200 sensor data records for a given sensor ID, sorted in ascending order.
   *
   * Behavior:
   *   - Filters out records where the key field is not null
   *   - Fetches up to 200 records, sorted by created in descending order (latest first)
   *   - Sorts the result back to ascending order before returning
   */
  public List<SensorDataEntity> findLast200Record(String sensorId) {
    var query = new Query();
    query.addCriteria(Criteria.where(SENSOR_ID_FIELD).is(sensorId).and(KEY_FIELD).ne(null));
    query.limit(LIMIT);
    query.with(Sort.by(Sort.Direction.DESC, CREATED_FIELD));

    return mongoTemplate.find(query, SensorDataEntity.class).stream()
        // revert to DESC mode
        .sorted(Comparator.comparing(SensorDataEntity::getCreated))
        .toList();
  }

  /*
   * Saves a single sensor data record directly into the MongoDB collection.
   *
   * This method performs a low-level insert using a raw {@link org.bson.Document}
   * rather than a mapped entity.
   */
  public void save(String sensorId, String key, Number val, LocalDateTime timestamp) {
    var document =
        new Document()
            .append(SENSOR_ID_FIELD, sensorId)
            .append(KEY_FIELD, key)
            .append(VAL_FIELD, val)
            .append(CREATED_FIELD, timestamp);

    mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
  }
}
