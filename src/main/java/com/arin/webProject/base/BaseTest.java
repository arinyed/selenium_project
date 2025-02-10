package com.arin.webProject.base;

import com.arin.webProject.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver("chrome"); // Pass browser type as needed
        driver.manage().window().maximize();
        driver.get("https://yourwebsite.com");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
