package com.test.webTest.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AddConferencePage extends PageObject {

    public AddConferencePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "conferenceName")
    private WebElement conferenceName;

    @FindBy(id = "date")
    private WebElement date;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public void enterConferenceName(String name, String date, String time) {
        this.conferenceName.sendKeys(name);
        this.date.sendKeys(date);
        this.date.sendKeys(Keys.TAB);
        this.date.sendKeys(time);
    }

    public List getDataForVerification() {
        List<String> list = new ArrayList<>();
        list.add(conferenceName.getAttribute("value"));
        list.add(date.getAttribute("value"));
        return list;
    }

    public void clickSubmit() {
        this.submitButton.click();
    }
}