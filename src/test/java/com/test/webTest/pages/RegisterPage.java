package com.test.webTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends PageObject {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "conferences")
    private WebElement conferences;

    public void conferences() {
        this.conferences.click();
    }
}
