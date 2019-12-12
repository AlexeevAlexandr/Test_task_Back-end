package com.test.validator;

import com.test.entity.Conference;
import com.test.entity.ConferenceRoom;
import com.test.exception.ConferenceException;
import com.test.service.ConferenceRoomService;
import com.test.service.ConferenceService;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements Validator {


    @Override
    public void isEmptyConferenceRoom(ConferenceRoom conferenceRoom) {
        if (conferenceRoom.getRoomName() == null || conferenceRoom.getRoomName().isEmpty()){
            throw new ConferenceException("Name can not be empty");
        }

        if (conferenceRoom.getLocation() == null || conferenceRoom.getLocation().isEmpty()){
            throw new ConferenceException("Location can not be empty");
        }

        if (conferenceRoom.getMaxSeats() < 0) {
            throw new ConferenceException("Number of seats can not be negative");
        }
    }

    @Override
    public void isEmptyConference(Conference conference){
        if (conference.getConferenceName() == null || conference.getConferenceName().isEmpty()){
            throw new ConferenceException("Name can not be empty");
        }

        if (conference.getConferenceDateTime() == null){
            throw new ConferenceException("Date can not be empty");
        }
    }

    @Override
    public void isEmptyId(String id) {
        if (id == null){
            throw new ConferenceException("Id can not be null");
        }
    }

    @Override
    public void isConferenceRoomExist(ConferenceRoomService conferenceRoomService, String id) {
        if (! conferenceRoomService.exists(id)) {
            throw new ConferenceException(String.format("ID: %s not found", id));
        }
    }

    @Override
    public void isConferenceExist(ConferenceService conferenceService, String id) {
        if (! conferenceService.exists(id)) {
            throw new ConferenceException(String.format("ID: %s not found", id));
        }
    }

    @Override
    public void isConferenceRoomNull(ConferenceRoom conferenceRoom) {
        if (conferenceRoom == null) {
            throw new ConferenceException("Conference room not found");
        }
    }

    @Override
    public void isConferenceNull(Conference conference) {
        if (conference == null) {
            throw new ConferenceException("Conference not found");
        }
    }
}
