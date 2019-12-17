package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelectRoomPage extends PageObject {

    public SelectRoomPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "submitSelectedRoomTest Room")
    private WebElement submitSelectedRoom;

    public void submitSelectRoom() {
        this.submitSelectedRoom.click();
    }
}
