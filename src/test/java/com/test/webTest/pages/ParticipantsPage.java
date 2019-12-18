package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class ParticipantsPage extends PageObject {

    public ParticipantsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "returnLink")
    private WebElement returnLink;

    @FindBy(id = "deletePeter Nolan")
    private WebElement delete;

    @FindBy(id = "submitPeter Nolan")
    private WebElement submit;

    @FindBy(id = "participantNamePeter Nolan")
    private WebElement participantName;

    @FindBy(id = "participantBirthDate2000-12-12")
    private WebElement participantBirthDate;

    public void clickReturn() {
        returnLink.click();
    }

    public void submitDeleteParticipant() {
        delete.click();
        submit.click();
    }

    public List participantData() {
        return Arrays.asList(this.participantName.getAttribute("value"), this.participantBirthDate.getAttribute("value"));
    }
}
