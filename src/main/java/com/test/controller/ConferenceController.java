package com.test.controller;

import com.test.entity.Conference;
import com.test.service.ConferenceService;
import com.test.validator.Validator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final Validator validator;

    public ConferenceController(ConferenceService conferenceService, Validator validator) {
        this.conferenceService = conferenceService;
        this.validator = validator;
    }

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Conference> getAll() {
        return conferenceService.getAll();
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Conference getById(@PathVariable String id) {
        validator.isEmptyId(id);
        Conference conference = conferenceService.getById(id);
        validator.isConferenceNull(conference);
        return conference;
    }

    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Conference create(@RequestBody Conference conference) {
        validator.isEmptyConference(conference);
        return conferenceService.create(conference);
    }

    @PutMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Conference update(@RequestBody Conference conference) {
        String id = conference.getId();
        validator.isEmptyId(id);
        validator.isEmptyConference(conference);
        validator.isConferenceExist(conferenceService, id);
        return conferenceService.update(conference);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void delete(@PathVariable String id) {
        validator.isEmptyId(id);
        validator.isConferenceExist(conferenceService, id);
        conferenceService.delete(id);
    }
}
