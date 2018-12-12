package com.booking.my.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DetailsPage {
    private WebDriver driver;

    @FindBy(css = "li.bui-nav-progress__item--active strong.bui-nav-progress__title")
    private WebElement formHeader;


    public DetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getFormName(){
        return formHeader.getText().toLowerCase().trim();
    }


}
