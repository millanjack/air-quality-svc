package com.serjlemast.repository;

import static com.serjlemast.configuration.TimeZoneConfiguration.UTC_TIME_ZONE;
import static com.serjlemast.repository.GeneralFields.CREATED_FIELD;
import static com.serjlemast.repository.GeneralFields.DEVICE_ID_FIELD;

import com.serjlemast.model.raspberry.RaspberryInfo;
import com.serjlemast.repository.entity.RaspberryEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RaspberryRepository {

  private final MongoTemplate mongoTemplate;

  /*
   * Updates an existing Raspberry Pi entity by device ID, or inserts a new one if not present.
   *
   * If the entity exists, its runtime data (e.g., memory, temperature, updated time) is updated.
   * If not, a new entity is inserted with system-level details (e.g., board model, OS, Java versions).
   *
   * Atomicity:
   *  This operation is atomic using MongoDB's {@code findAndModify}.
   *
   * @param info - Domain model with Raspberry Pi runtime and system details
   * @param timestamp - Timestamp to record as the latest update time
   * @throws RepositoryException if the entity cannot be created or updated
   */
  public void findAndModify(RaspberryInfo info, LocalDateTime timestamp) {
    var query = new Query(Criteria.where(DEVICE_ID_FIELD).is(info.deviceId()));

    var update =
        new Update()
            .set("jvMemoryMb", info.jvMemoryMb())
            .set("boardTemperature", info.boardTemperature())
            .set("updated", timestamp)
            .setOnInsert("boardModel", info.boardModel())
            .setOnInsert("operatingSystem", info.operatingSystem())
            .setOnInsert("javaVersions", info.javaVersions())
            .setOnInsert(CREATED_FIELD, LocalDateTime.now(ZoneId.of(UTC_TIME_ZONE)));

    Optional.ofNullable(
            /*
             * When modifying a single document, both db.collection.findAndModify()
             * and the updateOne() method atomically update the document.
             *
             * See Atomicity and Transactions:
             * link: https://www.mongodb.com/docs/manual/reference/method/db.collection.findAndModify/#atomicity
             */
            mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                RaspberryEntity.class))
        .map(RaspberryEntity::getId)
        .orElseThrow(
            () ->
                new RepositoryException(
                    "Creating or updating raspberry entity failed for %s".formatted(info)));
  }
}
