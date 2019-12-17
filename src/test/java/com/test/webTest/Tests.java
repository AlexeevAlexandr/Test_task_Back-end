package com.test.webTest;

import com.test.Main;
import com.test.entity.Conference;
import com.test.service.ConferenceService;
import com.test.webTest.pages.AddConferencePage;
import com.test.webTest.pages.ConferencePage;
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
    ConferenceService conferenceService;

    private static WebDriver driver;

    @BeforeClass
    public static void setup(){
        //set driver
//        System.setProperty("webdriver.chrome.driver","/home/alexandr/IdeaProjects/Test_task_Back-end/src/test/java/com/test/webTest/chromedriver/chromedriver");
        System.setProperty("webdriver.chrome.driver","C:\\Users\\IMTOP\\IdeaProjects\\test\\src\\test\\java\\com\\test\\webTest\\chromedriver\\chromedriver.exe");

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
        driver.get("http://localhost:8080/conferences");
        ConferencePage conferencePage = new ConferencePage(driver);

        //set data
        conferencePage.addConference();
        driver.get("http://localhost:8080/addConference");
        AddConferencePage addConferencePage = new AddConferencePage(driver);
        addConferencePage.enterConferenceName("Test Conference", "12-12-2020", "15:00");
        List actual = addConferencePage.getDataForVerification();
        addConferencePage.clickSubmit();

        //validate data
        List expected =  asList("Test Conference", "2020-12-12T15:00");
        assertEquals(expected, actual);

        //delete test conference
        conferenceService.deleteByName("Test Conference");
    }

    @Test
    public void changeStatus() {
        driver.get("http://localhost:8080/conferences");
        ConferencePage conferencePage = new ConferencePage(driver);
        conferencePage.changeStatus();
        conferenceService.deleteByName("Test Conference");
    }

    @Test
    public void deleteConference() {
        conferenceService.create(new Conference("Test Conference",
                LocalDateTime.parse("2020-12-12 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        driver.get("http://localhost:8080/conferences");
        ConferencePage conferencePage = new ConferencePage(driver);
        conferencePage.deleteConference();
    }
}