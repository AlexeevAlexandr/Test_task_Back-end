package com.test.webTest;

import com.test.Main;
import com.test.entity.Conference;
import com.test.entity.ConferenceRoom;
import com.test.service.ConferenceParticipantService;
import com.test.service.ConferenceRoomService;
import com.test.service.ConferenceService;
import com.test.webTest.pages.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = Main.class)
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @Autowired
    private ConferenceParticipantService conferenceParticipantService;

    private static WebDriver driver;

    @BeforeClass
    public static void setup(){
        String[] args = new String[0];
        Main.main(args);

        //set driver
        System.setProperty("webdriver.chrome.driver","/home/alexandr/IdeaProjects/Test_task_Back-end/src/test/java/com/test/webTest/chromedriver/chromedriver");
//        System.setProperty("webdriver.chrome.driver","C:\\Users\\IMTOP\\IdeaProjects\\test\\src\\test\\java\\com\\test\\webTest\\chromedriver\\chromedriver.exe");

        //set language
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "en-en,en");
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void cleanUp(){
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }

    @Test
    public void addNewConference() {
        try {
            driver.get("http://localhost:8080/conferences");
            ConferencePage conferencePage = new ConferencePage(driver);
            conferencePage.addConference();

            driver.get("http://localhost:8080/addConference");
            AddConferencePage addConferencePage = new AddConferencePage(driver);
            addConferencePage.enterConferenceName("Test Conference", "12-12-2020", "15:00");
            List actual = addConferencePage.getDataForVerification();
            addConferencePage.clickSubmit();

            List expected = asList("Test Conference", "2020-12-12T15:00");
            assertEquals(expected, actual);
        } finally {
            if (conferenceService.getConferenceByName("Test Conference") != null) {
                conferenceService.deleteByName("Test Conference");
            }
        }
    }

    @Test
    public void changeStatus() {
        conferenceService.create(new Conference("Test Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        try {
            driver.get("http://localhost:8080/conferences");
            ConferencePage conferencePage = new ConferencePage(driver);
            conferencePage.setStatusCanceled();
            String canceledStatus = conferencePage.checkChangedStatus();
            conferencePage.setStatusActive();
            String activeStatus = conferencePage.checkChangedStatus();
            assertEquals(Arrays.asList("canceled", "active"), Arrays.asList(canceledStatus, activeStatus));
        } finally {
            if (conferenceService.getConferenceByName("Test Conference") != null) {
                conferenceService.deleteByName("Test Conference");
            }
        }
    }

    @Test
    public void registerAndDeleteParticipant() {
        conferenceService.create(new Conference("Test Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        try {
            driver.get("http://localhost:8080/conferences");
            ConferencePage conferencePage = new ConferencePage(driver);
            conferencePage.registerParticipant("Peter Nolan", "12-12-2000");

            driver.get("http://localhost:8080/register");
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.conferences();

            driver.get("http://localhost:8080/conferences");
            conferencePage = new ConferencePage(driver);
            conferencePage.showRegistered();

            driver.get("http://localhost:8080/participants");
            ParticipantsPage participantsPage = new ParticipantsPage(driver);
            List name = participantsPage.participantData();
            participantsPage.submitDeleteParticipant();
            participantsPage.clickReturn();

            driver.get("http://localhost:8080/conferences");
            conferencePage = new ConferencePage(driver);
            conferencePage.deleteConference();

            assertEquals(Arrays.asList("Peter Nolan", "2000-12-12"), name);
        }finally {
            if (conferenceService.getConferenceByName("Test Conference") != null) {
                conferenceService.deleteByName("Test Conference");
            }
            if (conferenceParticipantService.findConferenceParticipantByFullName("Peter Nolan") != null) {
                conferenceParticipantService.deleteConferenceParticipantByFullName("Peter Nolan");
            }
        }
    }

    @Test
    public void deleteConference() {
        try {
            conferenceService.create(new Conference("Test Conference",
                    LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

            driver.get("http://localhost:8080/conferences");
            ConferencePage conferencePage = new ConferencePage(driver);
            conferencePage.deleteConference();
        } finally {
            if (conferenceService.getConferenceByName("Test Conference") != null) {
                conferenceService.deleteByName("Test Conference");
            }
        }
    }

    @Test
    public void selectRoom() {
        try {
            conferenceService.create(new Conference("Test Conference",
                    LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            conferenceRoomService.create(new ConferenceRoom("Test Room", "basement", 150));

            driver.get("http://localhost:8080/conferences");
            ConferencePage conferencePage = new ConferencePage(driver);
            conferencePage.selectRoom();

            driver.get("http://localhost:8080/selectRoom");
            SelectRoomPage selectRoomPage = new SelectRoomPage(driver);
            selectRoomPage.submitSelectRoom();

            driver.get("http://localhost:8080/conferences");
            conferencePage = new ConferencePage(driver);
            String actualRoom = conferencePage.checkSelectedRoom();

            conferencePage.deleteConference();

            assertEquals("Test Room", actualRoom);
        } finally {
            if (conferenceRoomService.getConferenceRoomByName("Test Room") != null) {
                conferenceRoomService.deleteByName("Test Room");
            }
            if (conferenceService.getConferenceByName("Test Conference") != null) {
                conferenceService.deleteByName("Test Conference");
            }
        }
    }

    @Test
    public void addConferenceRoom() {
        try {
            driver.get("http://localhost:8080/conferenceRooms");
            ConferenceRoomPage conferenceRoomPage = new ConferenceRoomPage(driver);
            conferenceRoomPage.addConferenceRoom();

            driver.get("http://localhost:8080/addConferenceRoom");
            AddConferenceRoomPage addConferenceRoomPage = new AddConferenceRoomPage(driver);
            addConferenceRoomPage.setData("Test Room", "Test Floor", "150");
            addConferenceRoomPage.submit();

            driver.get("http://localhost:8080/conferenceRooms");
            conferenceRoomPage = new ConferenceRoomPage(driver);
            List actualRoom = conferenceRoomPage.checkData();
            assertEquals(Arrays.asList("Test Room", "Test Floor", "150"), actualRoom);
        } finally {
            if (conferenceRoomService.getConferenceRoomByName("Test Room") != null) {
                conferenceRoomService.deleteByName("Test Room");
            }
        }

    }
}