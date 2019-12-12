package com.test;

import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceRoomService;
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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Main.class)
@SpringBootTest
public class ConferenceRoomControllerTest {

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
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");

        String id =
                given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                        when().post("/conferenceRoom").
                        then().statusCode(SC_OK).extract().path("id");

        conferenceRoomService.delete(id);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Empty_RoomName_Data() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");
        jsonObject.put("roomName", "");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conferenceRoom").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Empty_Location_Data() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");
        jsonObject.put("location", "");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conferenceRoom").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Null_RoomName_Data() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");
        jsonObject.put("roomName", nullValue());

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conferenceRoom").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Null_Location_Data() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");
        jsonObject.put("location", nullValue());

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conferenceRoom").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_Negative_MaxSeats_Data() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");
        jsonObject.put("maxSeats", -1);

        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(jsonObject.toString()).
                when().post("/conferenceRoom").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getAll() {
        when().get("/conferenceRoom").
                then().statusCode(SC_OK);
    }

    @Test
    public void getById() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            // get by id
            when().get("/conferenceRoom/" + id).
                    then().statusCode(SC_OK).body("id", equalTo(id));
        } finally {
            // delete
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void getById_NotFound() {
        when().get("/conferenceRoom/0123456789").
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void getBy_Null_Id() {
        when().get("/conferenceRoom/" + nullValue()).
                then().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void update() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            // change
            ConferenceRoom changedConferenceRoom = conferenceRoomService.getById(id);
            changedConferenceRoom.setMaxSeats(200);

            // update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(changedConferenceRoom).
                    when().
                    put("/conferenceRoom").
                    then().statusCode(SC_OK);

            // check changed
            ConferenceRoom checkConferenceRoom = conferenceRoomService.getById(id);
            Assert.assertEquals(changedConferenceRoom.toString(), checkConferenceRoom.toString());
        } finally {
            // delete
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void update_With_Null_Id() throws Exception {
        JSONObject jsonObject = testHelper.getJsonObjectFromFile("json/conferenceRoom.json");

        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                body(jsonObject.toString()).
                when().
                put("/conferenceRoom").
                then().
                statusCode(SC_NOT_FOUND);
    }

    @Test
    public void update_With_Null_RoomName() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            // change
            conferenceRoom.setRoomName(null);

            //update
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(conferenceRoom).
                    when().
                    put("/conferenceRoom").
                    then().statusCode(SC_NOT_FOUND);
        } finally {
            // delete
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void update_With_Null_Location() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            // change
            conferenceRoom.setLocation(null);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(conferenceRoom).
                    when().
                    put("/conferenceRoom").
                    then().statusCode(SC_NOT_FOUND);
        } finally {
            // delete
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void update_With_Negative_MaxSeats() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            // change
            conferenceRoom.setMaxSeats(-1);

            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    body(conferenceRoom).
                    when().
                    put("/conferenceRoom").
                    then().statusCode(SC_NOT_FOUND);
        } finally {
            // delete
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void delete() {
        // create
        ConferenceRoom conferenceRoom = conferenceRoomService.create(
                new ConferenceRoom("New room name", "First floor", 150));
        String id = conferenceRoom.getId();

        try {
            //delete
            given().contentType(MediaType.APPLICATION_JSON_VALUE).
                    when().
                    delete("/conferenceRoom/{id}",id).
                    then().statusCode(SC_OK);
        } finally {
            if (conferenceRoomService.exists(id)) {
                conferenceRoomService.delete(id);
            }
        }
    }

    @Test
    public void delete_Id_NotFound() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                delete("/conferenceRoom/wrongId").
                then().
                statusCode(SC_NOT_FOUND);
    }

    @Test
    public void delete_With_Null_Id() {
        given().contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                delete("/conferenceRoom/" + nullValue()).
                then().
                statusCode(SC_NOT_FOUND);
    }
}
