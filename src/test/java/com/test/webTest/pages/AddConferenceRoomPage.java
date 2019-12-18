package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddConferenceRoomPage extends PageObject {

    public AddConferenceRoomPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "name")
    private WebElement name;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "maxSeats")
    private WebElement maxSeats;

    @FindBy(id = "submit")
    private WebElement submit;

    public void setData(String name, String location, String maxSeats) {
        this.name.sendKeys(name);
        this.location.sendKeys(location);
        this.maxSeats.clear();
        this.maxSeats.sendKeys(maxSeats);
    }

    public void submit() {
        this.submit.click();
    }
}
