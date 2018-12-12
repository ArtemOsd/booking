package com.booking.my.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;
    @FindBy(xpath = "//div[@id='filter_hoteltype']//span[contains(text(),'Hotels')]/ancestor::a")
    private WebElement hotelCheckbox;
    @FindBy(css = "div#filter_out_of_stock a")
    private WebElement availablePropertiesCheckbox;
    @FindBy(css = "p.sr-filter-heading")
    private WebElement fillterNotification;
    @FindBy(css = "div[data-block-id='heading'] h1")
    private WebElement headerOfSearch;
    @FindBy(css = "div.sr-usp-overlay__loading")
    private WebElement loadingLabel;
    @FindBy(css = "a.hotel_name_link span.sr-hotel__name")
    private List<WebElement> hotelsList;
    private String hotelName;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 15);
    }

    public void filterByAvailableHotel() {
        if (!hotelCheckbox.isSelected()) hotelCheckbox.click();
        if (!availablePropertiesCheckbox.isSelected()) availablePropertiesCheckbox.click();
        //sometimes notification doesn't appears
        String notificationText = "Filters have been applied";
        wait.until(ExpectedConditions.textToBePresentInElement(fillterNotification, notificationText));

//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public String getSearchHeader() {
        return headerOfSearch.getText().toLowerCase();
    }

    public String getHotelName() {
        return hotelName;
    }

    public HotelPage selectOneOfTopThreeHotels() {
        int randomNumberHotelOfTopThree = (int) (Math.random() * 3);
        WebElement currentHotel = wait.until(ExpectedConditions.elementToBeClickable(hotelsList.get(randomNumberHotelOfTopThree)));
        hotelName = currentHotel.getText().toLowerCase().trim();
        currentHotel.click();
        switchTab(1);
        return new HotelPage(driver);
    }

    private void switchTab(int tab) {
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() < 2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.switchTo().window(tabs2.get(tab));
    }
}
