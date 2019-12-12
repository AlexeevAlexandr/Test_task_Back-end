package com.test.controller;

import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
import com.test.validator.Validator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/conferenceRoom")
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;
    private final Validator validator;

    public ConferenceRoomController(ConferenceRoomService conferenceRoomService, Validator validator) {
        this.conferenceRoomService = conferenceRoomService;
        this.validator = validator;
    }

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<ConferenceRoom> getAll(){
        return conferenceRoomService.getAll();
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ConferenceRoom getById(@PathVariable String id) {
        validator.isEmptyId(id);
        ConferenceRoom conferenceRoom = conferenceRoomService.getById(id);
        validator.isConferenceRoomNull(conferenceRoom);
        return conferenceRoom;
    }

    @PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ConferenceRoom create(@RequestBody ConferenceRoom conferenceRoom) {
        validator.isEmptyConferenceRoom(conferenceRoom);
        return conferenceRoomService.create(conferenceRoom);
    }

    @PutMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ConferenceRoom update(@RequestBody ConferenceRoom conferenceRoom) {
        String id = conferenceRoom.getId();
        validator.isEmptyId(id);
        validator.isEmptyConferenceRoom(conferenceRoom);
        validator.isConferenceRoomExist(conferenceRoomService, id);
        return conferenceRoomService.update(conferenceRoom);
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void delete(@PathVariable String id) {
        validator.isEmptyId(id);
        validator.isConferenceRoomExist(conferenceRoomService, id);
        conferenceRoomService.delete(id);
    }
}
