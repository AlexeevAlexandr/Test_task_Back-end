package com.test.repository;

import com.test.entity.ConferenceRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends MongoRepository<ConferenceRoom, String> {
}
