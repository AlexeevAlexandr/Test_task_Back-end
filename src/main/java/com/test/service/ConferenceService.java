package com.test.service;

import com.test.entity.Conference;

import java.util.List;

public interface ConferenceService {

    List<Conference> getAll();

    Conference create(Conference conference);

    void delete(String id);

    Conference getById(String id);

    boolean exists(String id);

    Conference update(Conference conference);
}
