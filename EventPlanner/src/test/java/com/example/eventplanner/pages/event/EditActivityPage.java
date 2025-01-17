package com.example.eventplanner.pages.event;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditActivityPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "input[formControlName='title']")
    private WebElement titleInput;

    @FindBy(css = "textarea[formControlName='description']")
    private WebElement descriptionInput;

    @FindBy(css = "input[formControlName='city']")
    private WebElement cityInput;

    @FindBy(css = "input[formControlName='street']")
    private WebElement streetInput;

    @FindBy(css = "input[formControlName='number']")
    private WebElement numberInput;

    @FindBy(css = "input[formControlName='latitude']")
    private WebElement latitudeInput;

    @FindBy(css = "input[formControlName='longitude']")
    private WebElement longitudeInput;

    @FindBy(css = "input[formControlName='start']")
    private WebElement start;

    @FindBy(css = "input[formControlName='end']")
    private WebElement end;

    @FindBy(css = "button.p-button")
    private WebElement createActivityButton;

    public EditActivityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void setTitle(String title) {
        waitAndSendKeys(titleInput, title);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(titleInput));
        return titleInput.getAttribute("value");
    }

    public void setDescription(String description) {
        waitAndSendKeys(descriptionInput, description);
    }

    public String getDescription() {
        wait.until(ExpectedConditions.visibilityOf(descriptionInput));
        return titleInput.getAttribute("value");
    }


    public void setCity(String city) {
        waitAndSendKeys(cityInput, city);
    }

    public void setStreet(String street) {
        waitAndSendKeys(streetInput, street);
    }

    public void setNumber(String number) {
        waitAndSendKeys(numberInput, number);
    }

    public void setLatitude(double latitude) {
        waitAndSendKeys(latitudeInput, String.valueOf(latitude));
    }

    public void setLongitude(double longitude) {
        waitAndSendKeys(longitudeInput, String.valueOf(longitude));
    }


    public void clickCreateActivity() {
        wait.until(ExpectedConditions.elementToBeClickable(createActivityButton)).click();
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(titleInput)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isValidationErrorDisplayed() {
        try {
            // Adjust this selector based on how your validation errors are displayed
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".p-error, .ng-invalid"))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSubmissionSuccessful() {
        return driver.getCurrentUrl().contains("/agenda/1");
    }

    private void waitAndSendKeys(WebElement element, String text) {
        WebElement waitElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        waitElement.clear();
        waitElement.sendKeys(text);
    }
}