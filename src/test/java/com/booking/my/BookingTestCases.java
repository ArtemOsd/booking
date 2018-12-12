package com.booking.my;

import com.booking.my.config.ChromeConfigs;
import com.booking.my.pages.DetailsPage;
import com.booking.my.pages.HomePage;
import com.booking.my.pages.HotelPage;
import com.booking.my.pages.SearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookingTestCases {
    private WebDriver driver;
    private SearchPage searchPage;
    private HomePage homePage;
    private HotelPage hotelPage;
    private DetailsPage detailsPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(ChromeConfigs.createChromeOptions());
    }

    @Test
    public void reserveRoomInLAHotel() {
        driver.get("https://booking.com");
        homePage = new HomePage(driver);
        searchPage = homePage.fillSearchFieldsAndSubmit("Los Angeles");
        Assert.assertTrue(searchPage.getSearchHeader().contains("los angeles"));
        searchPage.filterByAvailableHotel();
        hotelPage = searchPage.selectOneOfTopThreeHotels();
        Assert.assertEquals(searchPage.getHotelName(), hotelPage.getHotelName(), "Wrong selected hotel");
        hotelPage.goToPrice();
        hotelPage.selectCheapestRoom();
        Assert.assertEquals(hotelPage.getCheapestPrice(), hotelPage.getTotalPrice(), "The prices are different");
        detailsPage = hotelPage.reserveRoom();
        Assert.assertEquals(detailsPage.getFormName(), "enter your details");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
