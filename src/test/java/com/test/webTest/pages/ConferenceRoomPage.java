package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class ConferenceRoomPage extends PageObject {

    public ConferenceRoomPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "addConferenceRoom")
    private WebElement addConferenceRoom;

    @FindBy(id = "roomNameTest Room")
    private WebElement roomName;

    @FindBy(id = "locationTest Room")
    private WebElement location;

    @FindBy(id = "maxSeatsTest Room")
    private WebElement maxSeats;

    public void addConferenceRoom() {
        this.addConferenceRoom.click();
    }

    public List checkData() {
        return Arrays.asList(
                this.roomName.getAttribute("value"),
                this.location.getAttribute("value"),
                this.maxSeats.getAttribute("value")
        );
    }
}
