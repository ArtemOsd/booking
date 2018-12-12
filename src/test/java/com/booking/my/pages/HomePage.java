package com.booking.my.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(css = "input[type='search']")
    private WebElement searchTextBox;
    @FindBy(css = "div[data-mode='checkin'] button[aria-label='Open calendar']")
    private WebElement checkinDateButton;
    @FindBy(xpath = "//div[text()='January 2019']/following::table[@class]/tbody/tr[3]/td[4]")
    private WebElement dateInCalendar;
    @FindBy(css = "button[class*=searchbox]")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchPage fillSearchFieldsAndSubmit(String text) {
        searchTextBox.sendKeys(text);
        checkinDateButton.click();
        dateInCalendar.click();
        checkinDateButton.click();
        searchButton.submit();
        return new SearchPage(driver);
    }
}
