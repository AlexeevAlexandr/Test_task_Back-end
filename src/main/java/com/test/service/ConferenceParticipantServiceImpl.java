package com.test.service;

import com.test.entity.ConferenceParticipant;
import com.test.repository.ConferenceParticipantRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class ConferenceParticipantServiceImpl implements ConferenceParticipantService {

    private final ConferenceParticipantRepository conferenceParticipantRepository;

    public ConferenceParticipantServiceImpl(ConferenceParticipantRepository conferenceParticipantRepository) {
        this.conferenceParticipantRepository = conferenceParticipantRepository;
    }

    @Override
    public ConferenceParticipant findConferenceParticipantByFullName(String name) {
        try {
            log.info("attempt to find conference participant by fullName");
            ConferenceParticipant conferenceParticipant = conferenceParticipantRepository.findConferenceParticipantByFullName(name);
            log.info("attempt to find conference participant by fullName - success");
            return conferenceParticipant;
        } catch (Exception e) {
            log.warning("attempt to find conference participant by fullName - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteConferenceParticipantByFullName(String name) {
        try {
            log.info("attempt to delete conference participant by fullName");
            conferenceParticipantRepository.deleteConferenceParticipantByFullName(name);
            log.info("attempt to delete conference participant by fullName - success");
        } catch (Exception e) {
            log.warning("attempt to delete conference participant by fullName - false\n" + e.getMessage());
        }
    }
}
