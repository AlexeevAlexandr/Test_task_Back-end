package com.test.service;

import com.test.entity.Conference;

import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceService {

    List<Conference> getAll();

    Conference create(Conference conference);

    void delete(String id);

    void deleteByName(String name);

    Conference getById(String id);

    boolean exists(String id);

    Conference update(Conference conference);
}
