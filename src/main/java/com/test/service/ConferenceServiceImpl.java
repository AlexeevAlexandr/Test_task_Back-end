package com.test.service;

import com.test.entity.Conference;
import com.test.repository.ConferenceRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Log
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> getAll() {
        try {
            log.info("attempt to get all conferences");
            List<Conference> conferences = conferenceRepository.findAll();
            conferences.sort(Comparator.comparing(Conference::getConferenceDateTime));
            log.info("attempt to get all conferences - success");
            return conferences;
        } catch (Exception e) {
            log.warning("attempt to get all conferences - false\n" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Conference create(Conference conference) {
        try {
            log.info("attempt to create conference");
            Conference savedConference = conferenceRepository.save(conference);
            log.info("attempt to create conference - success");
            return savedConference;
        } catch (Exception e) {
            log.warning("attempt to create conference - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String id) {
        try {
            log.info("attempt to delete conference");
            conferenceRepository.delete(id);
            log.info("attempt to delete conference - success");
        } catch (Exception e) {
            log.warning("attempt to delete conference - false\n" + e.getMessage());
        }
    }

    @Override
    public Conference getById(String id) {
        try {
            log.info("attempt to get conference by id");
            Conference conference = conferenceRepository.findOne(id);
            log.info("attempt to get conference by id - success");
            return conference;
        } catch (Exception e) {
            log.warning("attempt to get conference by id - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean exists(String id) {
        try {
            log.info("attempt to check conference");
            boolean exist = conferenceRepository.exists(id);
            log.info("attempt to check conference - success");
            return exist;
        } catch (Exception e) {
            log.warning("attempt to check conference - false\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public Conference update(Conference conference) {
        try {
            log.info("attempt to update conference");
            Conference updatedConference = conferenceRepository.save(conference);
            log.info("attempt to update conference - success");
            return updatedConference;
        } catch (Exception e) {
            log.warning("attempt to update conference - false\n" + e.getMessage());
            return null;
        }
    }
}
