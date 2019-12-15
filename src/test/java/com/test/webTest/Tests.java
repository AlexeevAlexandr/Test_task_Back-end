package com.test.webTest;

import com.test.webTest.pages.AddConferencePage;
import com.test.webTest.pages.ConferencePage;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    private static WebDriver driver;

    @BeforeClass
    public static void setup(){
        //set driver
        System.setProperty("webdriver.chrome.driver","/home/alexandr/IdeaProjects/Test_task_Back-end/src/test/java/com/test/webTest/chromedriver/chromedriver");

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
    public void test1() {
        driver.get("http://localhost:8080/conferences");
        ConferencePage conferencePage = new ConferencePage(driver);

        //set data
        conferencePage.addConference();
        driver.get("http://localhost:8080/addConference");
        AddConferencePage addConferencePage = new AddConferencePage(driver);
        addConferencePage.enterConferenceName("New Conference", "12-12-2020", "15:00");
        List actual = addConferencePage.getDataForVerification();
        addConferencePage.clickSubmit();

        //validate data
        List expected =  asList("New Conference", "2020-12-12T15:00");
        assertEquals(expected, actual);
    }

    @Test
    public void test2() {

    }
}