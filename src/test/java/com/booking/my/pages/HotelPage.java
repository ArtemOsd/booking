package com.booking.my.pages;

import com.booking.my.config.ChromeConfigs;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class HotelPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "button[data-title*=prices]")
    private WebElement reserveButton;
    @FindBy(css = "span.hprt-price-price-standard,span.hprt-price-price-actual")
    private List<WebElement> pricesFromTable;
    @FindBy(css = "div.hprt-reservation-total-price")
    private WebElement totalPrice;
    @FindBy(css = "div.hprt-reservation-cta button[type='Submit']")
    private WebElement finalReserveButton;
    @FindBy(css = "h2.hp__hotel-name")
    private WebElement hotelNameHeader;
    @FindBy(css = "span.hp__hotel-type-badge")
    private WebElement hotelTypeBadge;


    public HotelPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 15);
    }

    public void goToPrice() {
        reserveButton.click();
    }

    public String getTotalPrice() {
        return totalPrice.getText().substring(totalPrice.getText().indexOf(" ") + 1).trim();
    }

    public void selectCheapestRoom() {
        By roomSelect = By.xpath("//div/span[contains(text(),'" + getCheapestPrice() + "')]/ancestor::tr//select");
        WebElement cheapestRoomSelect = driver.findElement(roomSelect);
        if (!isPresentElement(totalPrice)) cheapestRoomSelect.sendKeys(Keys.ARROW_DOWN);
    }

    private boolean isPresentElement(WebElement element) {
        boolean exist;
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            exist = element.isDisplayed();
        } catch (NoSuchElementException e) {
            exist = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(ChromeConfigs.getDefaultImplicitlyWaitTime(), TimeUnit.SECONDS);
        }
        return exist;
    }

    public DetailsPage reserveRoom() {
        finalReserveButton.click();
        return new DetailsPage(driver);
    }

    public String getCheapestPrice() {
        TreeSet<String> set = new TreeSet<>();

        for (WebElement price : pricesFromTable) {
            String pr = price.getText().substring(price.getText().indexOf(" ") + 1).trim();
            set.add(pr);
        }
        return set.first();
    }

    public String getHotelName() {
        String hotelname;
        if (isPresentElement(hotelTypeBadge)) {
            int badgeLength = hotelTypeBadge.getText().length();
            hotelname = hotelNameHeader.getText().substring(badgeLength + 1).toLowerCase().trim();
        } else hotelname = hotelNameHeader.getText().toLowerCase().trim();
        return hotelname;
    }

}
