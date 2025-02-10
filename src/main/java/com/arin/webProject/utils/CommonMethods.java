package com.arin.webProject.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.arin.webProject.base.PageInitializer;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;




public class CommonMethods extends PageInitializer {


    public static void sendText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }


    public static void clickRadioOrCheckbox(List<WebElement> elementList, String value) {

        for (WebElement el : elementList) {
            String actualValue = el.getAttribute("value").trim();

            if (actualValue.equalsIgnoreCase(value) && el.isEnabled()) {
                el.click();
                break;
            }
        }

    }


    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void selectDropDown(WebElement element, String visibleText) {
        try {
            Select sel = new Select(element);
            sel.selectByVisibleText(visibleText);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void selectDropDown(WebElement element, int index) {
        try {
            Select sel = new Select(element);

            int size = sel.getOptions().size();

            if (size > index) {
                sel.selectByIndex(index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }

    }


    public static void dismissAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }


    public static String getAlertText() {
        String alertText = null;

        try {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
        return alertText;
    }


    public static void sendAlertText(String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }


    public static void switchToFrame(String nameOrId) {
        try {
            driver.switchTo().frame(nameOrId);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }


    public static void switchToFrame(int index) {
        try {
            driver.switchTo().frame(index);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }


    public static void switchToFrame(WebElement element) {
        try {
            driver.switchTo().frame(element);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }

    }


    public static void switchToChildWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            if (!mainWindow.equals(handle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }


    public static WebDriverWait getWaitObject() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));

        return wait;

    }


    public static WebElement waitForClickability(WebElement element) {

        return getWaitObject().until(ExpectedConditions.elementToBeClickable(element));

    }


    public static WebElement waitForVisibility(WebElement element) {

        return getWaitObject().until(ExpectedConditions.visibilityOf(element));

    }


    public static void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }


    public static JavascriptExecutor getJSObject() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;

    }


    public static void jsClick(WebElement element) {
        getJSObject().executeScript("arguments[0].click()", element);

    }


    public static void scrollToElement(WebElement element) {

        getJSObject().executeScript("arguments[0].scrollIntoView(true)", element);

    }


    public static void scrollDown(int pixel) {
        getJSObject().executeScript("window.scrollBy(0," + pixel + ")");
    }


    public static void scrollUp(int pixel) {
        getJSObject().executeScript("window.scrollBy(0,-" + pixel + ")");
    }


    public static void selectCalendarDate(List<WebElement> elements, String date) {
        for (WebElement day : elements) {
            if (day.isEnabled()) {
                if (day.getText().equals(date)) {
                    day.click();
                    break;
                }
            } else {
                System.out.println("This date is not enabled!");
                break;
            }
        }

    }


    public static byte[] takeScreenshot(String fileName) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String destination = Constants.SCREENSHOT_FILEPATH + fileName + getTimeStamp() + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get the screenshot as a byte[]
        byte[] picBytes = ts.getScreenshotAs(OutputType.BYTES);

        return picBytes;
    }


    public static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        return sdf.format(date);
    }

}