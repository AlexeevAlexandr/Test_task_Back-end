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
    private WebElement conferenceStatusActive;

    @FindBy(id = "canceledStatusTest Conference")
    private WebElement conferenceStatusCanceled;

    @FindBy(id = "numberOfRegisteredTest Conference")
    private WebElement numberOfRegistered;

    @FindBy(id = "selectRoomTest Conference")
    private WebElement selectRoom;

    @FindBy(id = "selectedRoomTest Conference")
    private WebElement selectedRoom;

    @FindBy(id = "clickToRegisterButtonTest Conference")
    private WebElement clickToRegisterButton;

    @FindBy(id = "deletingTest Conference")
    private WebElement deleteButton;

    @FindBy(id = "confirmDeletingTest Conference")
    private WebElement deleteConference;

    @FindBy(id = "nameTest Conference")
    private WebElement name;

    @FindBy(id = "birthDateTest Conference")
    private WebElement birthDate;

    @FindBy(id = "submitTest Conference")
    private WebElement submit;

    public void addConference() {
        this.clickToAddConference.click();
    }

    public void deleteConference() {
        this.deleteButton.click();
        this.deleteConference.click();
    }

    public void setStatusCanceled() {
        this.conferenceStatus.click();
        this.conferenceStatusCanceled.click();
    }

    public void setStatusActive() {
        this.conferenceStatus.click();
        this.conferenceStatusActive.click();
    }

    public void showRegistered() {
        this.numberOfRegistered.click();
    }

    public void registerParticipant(String name, String date) {
        this.clickToRegisterButton.click();
        this.name.sendKeys(name);
        this.birthDate.sendKeys(date);
        this.submit.click();
    }

    public void selectRoom() {
        this.selectRoom.click();
    }

    public String checkSelectedRoom() {
        return this.selectedRoom.getAttribute("value");
    }

    public String checkChangedStatus() {
        return this.conferenceStatus.getAttribute("value");
    }
}