package com.test.repository;

import com.test.entity.ConferenceParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConferenceParticipantRepository extends MongoRepository<ConferenceParticipant, String> {

    ConferenceParticipant findConferenceParticipantByFullName(String name);

    void deleteConferenceParticipantByFullName(String name);
}
