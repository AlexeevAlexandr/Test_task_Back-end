package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConferencePage extends PageObject{

    public ConferencePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "clickToAddConference")
    private WebElement clickToAddConference;

    @FindBy(id = "conferenceStatus")
    private WebElement conferenceStatus;

    @FindBy(id = "numberOfRegistered")
    private WebElement numberOfRegistered;

    @FindBy(id = "selectRoom")
    private WebElement selectRoom;

    @FindBy(id = "clickToRegisterButton")
    private WebElement clickToRegisterButton;

    @FindBy(id = "deleteButton")
    private WebElement deleteButton;

    public void addConference() {
        this.clickToAddConference.click();
    }
}