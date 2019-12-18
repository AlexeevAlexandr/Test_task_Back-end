package com.test.repository;

import com.test.entity.Conference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConferenceRepository extends MongoRepository<Conference, String> {

    void deleteConferenceByConferenceName(String name);

    Conference getConferenceByConferenceName(String name);
}
