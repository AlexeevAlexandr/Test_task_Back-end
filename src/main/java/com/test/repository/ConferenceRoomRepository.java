package com.test.repository;

import com.test.entity.ConferenceRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRoomRepository extends MongoRepository<ConferenceRoom, String> {

    List<ConferenceRoom> findByMaxSeatsGreaterThanEqual(long maxSeats);

    void deleteConferenceRoomByRoomName(String name);

    ConferenceRoom findConferenceRoomByRoomName(String name);
}
