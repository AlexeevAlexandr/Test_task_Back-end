package com.test;

import com.test.entity.Conference;
import com.test.entity.ConferenceParticipant;
import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
import com.test.service.ConferenceService;
import com.test.testHelper.TestHelper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
@SpringBootTest
public class ConferenceControllerTest {

    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private TestHelper testHelper;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public final void setup() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void create() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");

        String id =
                given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                        when().post("/conference").
                        then().statusCode(SC_OK).extract().path("id");

        conferenceService.delete(id);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Empty_conferenceName() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");
        jsonObject.put("conferenceName", "");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conference").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Empty_conferenceDateTime() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");
        jsonObject.put("conferenceDateTime", "");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conference").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Null_conferenceName() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");
        jsonObject.put("conferenceName", nullValue());

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conference").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Null_conferenceDateTime() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");
        jsonObject.put("conferenceDateTime", nullValue());

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conference").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getAll() {
        when().get("/conference").
                then().statusCode(SC_OK);
    }

    @Test
    public void getById() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                        LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // get by id
            when().get("/conference/" + id).
                    then().statusCode(SC_OK).body("id", equalTo(id));
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void getById_NotFound() {
        when().get("/conference/0123456789").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getBy_Null_Id() {
        when().get("/conference/" + nullValue()).
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void update() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                        LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // change
            Conference changedConference = conferenceService.getById(id);
            changedConference.setConferenceDateTime(
                    LocalDateTime.parse("2020-12-13 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            // update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(changedConference).
                    when().
                    put("/conference").
                    then().statusCode(SC_OK);

            // check changed
            Conference checkConference = conferenceService.getById(id);
            Assert.assertEquals(changedConference.toString(), checkConference.toString());
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void add_Participant() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // change
            Conference changedConference = conferenceService.getById(id);
            List<ConferenceParticipant> conferenceParticipants = new ArrayList<>();
            conferenceParticipants.add(new ConferenceParticipant("Jason",
                    LocalDate.parse("1986-04-26", DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            changedConference.setParticipants(conferenceParticipants);
            // update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(changedConference).
                    when().
                    put("/conference").
                    then().statusCode(SC_OK);

            // check changed
            Conference checkConference = conferenceService.getById(id);
            Assert.assertEquals(
                    changedConference.getParticipants().get(0).toString(),
                    checkConference.getParticipants().get(0).toString());
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void add_ConferenceRoom() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // change
            Conference changedConference = conferenceService.getById(id);
            ConferenceRoom conferenceRoom = conferenceRoomService.create(
                    new ConferenceRoom("New room name", "First floor", 150));
            changedConference.setConferenceRoom(conferenceRoom);
            // update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(changedConference).
                    when().
                    put("/conference").
                    then().statusCode(SC_OK);

            // check changed
            Conference checkConference = conferenceService.getById(id);
            Assert.assertEquals(
                    changedConference.getConferenceRoom().toString(),
                    checkConference.getConferenceRoom().toString());
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void update_With_Null_Id() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conference.json");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                body(jsonObject.toString()).
                when().
                put("/conference").
                then().
                statusCode(SC_NOT_FOUND);
    }

    @Test
    public void update_With_ConferenceName_Null() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                        LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // change
            conference.setConferenceName(null);

            //update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(conference).
                    when().
                    put("/conference").
                    then().statusCode(SC_NOT_FOUND);
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void update_With_City_Null() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            // change
            conference.setConferenceDateTime(null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(conference).
                    when().
                    put("/conference").
                    then().statusCode(SC_NOT_FOUND);
        } finally {
            // delete
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void delete() {
        // create
        Conference conference = conferenceService.create(new Conference("New Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        String id = conference.getId();

        try {
            //delete
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    when().
                    delete("/conference/{id}",id).
                    then().statusCode(SC_OK);
        } finally {
            if (conferenceService.exists(id)) {
                conferenceService.delete(id);
            }
        }
    }

    @Test
    public void delete_Id_NotFound() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                delete("/conference/wrongId").
                then().
                statusCode(SC_NOT_FOUND);
    }

    @Test
    public void delete_With_Null_Id() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                delete("/conference/" + nullValue()).
                then().
                statusCode(SC_NOT_FOUND);
    }
}
