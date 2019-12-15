package com.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "conference")
public class Conference {

    @Id
    private String id;
    private String conferenceName;
    private String status = "active";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime conferenceDateTime;
    private List<ConferenceParticipant> participants = new ArrayList<>();
    private ConferenceRoom conferenceRoom = new ConferenceRoom();

    public Conference(String conferenceName, LocalDateTime conferenceDateTime) {
        this.conferenceName = conferenceName;
        this.conferenceDateTime = conferenceDateTime;
    }

    @Override
    public String toString() {
        return "{" +
                    "Conference name: " + conferenceName +
                    "Conference date: " + conferenceDateTime +
                "}";
    }
}
