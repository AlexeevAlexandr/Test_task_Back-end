package com.test.validator;

import com.test.entity.Conference;
import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
import com.test.service.ConferenceService;

public interface Validator {

    void isEmptyConferenceRoom(ConferenceRoom conferenceRoom);

    void isEmptyConference(Conference conference);

    void isEmptyId(String id);

    void isConferenceRoomExist(ConferenceRoomService conferenceRoomService, String id);

    void isConferenceExist(ConferenceService conferenceService, String id);

    void isConferenceRoomNull(ConferenceRoom conferenceRoom);

    void isConferenceNull(Conference conference);
}
