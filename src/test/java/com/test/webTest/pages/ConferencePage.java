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

    @FindBy(id = "conferenceStatusTest Conference")
    private WebElement conferenceStatus;

    @FindBy(id = "activeStatusTest Conference")
    private WebElement activeStatus;

    @FindBy(id = "numberOfRegisteredTest Conference")
    private WebElement numberOfRegistered;

    @FindBy(id = "selectRoomTest Conference")
    private WebElement selectRoom;

    @FindBy(id = "clickToRegisterButtonTest Conference")
    private WebElement clickToRegisterButton;

    @FindBy(id = "deletingTest Conference")
    private WebElement deleteButton;

    @FindBy(id = "confirmDeletingTest Conference")
    private WebElement deleteConference;

    public void addConference() {
        this.clickToAddConference.click();
    }

    public void deleteConference() {
        this.deleteButton.click();
        this.deleteConference.click();
    }

    public void changeStatus() {
        this.conferenceStatus.click();
        this.activeStatus.click();
    }
}