package com.test.service;

import com.test.entity.ConferenceRoom;
import com.test.repository.ConferenceRoomRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;

    public ConferenceRoomServiceImpl(ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
    }

    @Override
    public List<ConferenceRoom> getAll() {
        try {
            log.info("attempt to get Conference room list");
            List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAll();
            log.info("attempt to get Conference room list - success");
            return conferenceRooms;
        } catch (Exception e) {
            log.warning("attempt to get Conference room list - false\n" + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public ConferenceRoom getById(String id) {
        try {
            log.info("attempt to get Conference room");
            ConferenceRoom conferenceRoom = conferenceRoomRepository.findOne(id);
            log.info("attempt to get Conference room - success");
            return conferenceRoom;
        } catch (Exception e) {
            log.warning("attempt to get Conference room - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public ConferenceRoom create(ConferenceRoom conferenceRoom) {
        try {
            log.info("attempt to create Conference room");
            ConferenceRoom savedConferenceRoom = conferenceRoomRepository.save(conferenceRoom);
            log.info("attempt to create Conference room - success");
            return savedConferenceRoom;
        } catch (Exception e) {
            log.warning("attempt to create Conference room - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public ConferenceRoom update(ConferenceRoom conferenceRoom) {
        try {
            log.info("attempt to update Conference room");
            ConferenceRoom updatedConferenceRoom = conferenceRoomRepository.save(conferenceRoom);
            log.info("attempt to update Conference room - success");
            return updatedConferenceRoom;
        } catch (Exception e) {
            log.warning("attempt to update Conference room - false\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String id) {
        try {
            log.info("attempt to delete Conference room");
            conferenceRoomRepository.delete(id);
            log.info("attempt to delete Conference room - success");
        } catch (Exception e) {
            log.warning("attempt to delete Conference room - false\n" + e.getMessage());
        }
    }

    @Override
    public boolean exists(String id) {
        try {
            log.info("attempt to check conference room");
            boolean exist = conferenceRoomRepository.exists(id);
            log.info("attempt to check conference room - success");
            return exist;
        } catch (Exception e) {
            log.warning("attempt to check conference room - false\n" + e.getMessage());
            return false;
        }
    }
}
