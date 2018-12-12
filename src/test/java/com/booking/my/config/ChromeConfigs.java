package com.booking.my.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;


public class ChromeConfigs {
    private final static int DEFAULT_IMPLICITLY_WAIT_TIME = 8;

    public static int getDefaultImplicitlyWaitTime() {
        return DEFAULT_IMPLICITLY_WAIT_TIME;
    }

    public static ChromeOptions createChromeOptions() {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
//        String STRING_KEY = "webdriver.chrome.driver";
//        String STRING_PATH = "bin/chromedriver";
//        System.setProperty(STRING_KEY, STRING_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-gpu ");
        options.addArguments("--no-sandbox");
        options.addArguments("--lang=en");
        options.addArguments("pageLoadStrategy", "eager");
        return options;
    }

    public static WebDriver setDriverConfigs(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICITLY_WAIT_TIME, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);

        // below code is for window miximizing of chrome browser on mac mini
        Point targetPosition = new Point(0, 0);
        driver.manage().window().setPosition(targetPosition);

        Dimension targetSize = new Dimension(1920, 1080); //your screen resolution here
        driver.manage().window().setSize(targetSize);

        return driver;
    }

}
