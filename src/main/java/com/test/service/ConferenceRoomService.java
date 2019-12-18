package com.test.service;

import com.test.entity.ConferenceRoom;

import java.util.List;

public interface ConferenceRoomService {

    List<ConferenceRoom> getAll();

    ConferenceRoom getById(String id);

    ConferenceRoom create(ConferenceRoom conferenceRoom);

    ConferenceRoom update(ConferenceRoom conferenceRoom);

    void delete(String id);

    void deleteByName(String name);

    boolean exists(String id);

    List<ConferenceRoom> findByIdGreaterThanEqual(int seats);

    ConferenceRoom getConferenceRoomByName(String name);
}
