package com.test.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "conferenceRoom")
public class ConferenceRoom {

    @Id
    private String id;
    private String roomName;
    private String location;
    private long maxSeats;

    public ConferenceRoom(String roomName, String location, long maxSeats) {
        this.roomName = roomName;
        this.location = location;
        this.maxSeats = maxSeats;
    }

    @Override
    public String toString() {
        return "{" +
                    "RoomName: " + roomName +
                    "Location: " + location +
                    "MaxSeats: " + maxSeats +
                "}";
    }
}
