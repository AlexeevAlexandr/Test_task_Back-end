package com.test.service;

import com.test.entity.ConferenceParticipant;

public interface ConferenceParticipantService {

    ConferenceParticipant findConferenceParticipantByFullName(String name);

    void deleteConferenceParticipantByFullName(String name);
}
