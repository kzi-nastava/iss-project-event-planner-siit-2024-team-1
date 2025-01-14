package com.example.eventplanner.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FilterPanel {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".filter-sidebar input[type='checkbox']")
    private List<WebElement> typeCheckboxes;

    @FindBy(css = "p-calendar input")
    private WebElement eventDateInput;

    @FindBy(css = "[formgroupname='events'] input[formcontrolname='type']")
    private WebElement eventTypeInput;

    @FindBy(css = "[formgroupname='events'] input[formcontrolname='city']")
    private WebElement eventCityInput;

    @FindBy(css = "[formgroupname='services'] input[formcontrolname='priceMin']")
    private WebElement servicePriceMinInput;

    @FindBy(css = "[formgroupname='services'] input[formcontrolname='priceMax']")
    private WebElement servicePriceMaxInput;

    @FindBy(css = "[formgroupname='services'] input[formcontrolname='category']")
    private WebElement serviceCategoryInput;

    @FindBy(css = "p-button[label='Apply filters'] button.p-button")
    private WebElement applyButton;

    @FindBy(css = "button.p-button.p-button-danger")
    private WebElement resetButton;

    public FilterPanel(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void toggleFilterType(String filterLabel) {
        WebElement checkboxLabel = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(), '" + filterLabel + "')]")
        ));
        WebElement checkbox = checkboxLabel.findElement(By.xpath("./preceding-sibling::p-checkbox//input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }

    public void setEventDate(String dateRange) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(eventDateInput));
        input.clear();
        input.sendKeys(dateRange);
        input.sendKeys(Keys.ENTER);
    }

    public void setEventType(String type) {
        wait.until(ExpectedConditions.elementToBeClickable(eventTypeInput)).sendKeys(type);
    }

    public void setEventCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(eventCityInput)).sendKeys(city);
    }



    public void expandAccordionTab(String tabHeader) {
        WebElement accordionTab = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[contains(@class, 'p-accordion-header-text') and contains(text(), '" + tabHeader + "')]")
        ));
        if (!accordionTab.findElement(By.xpath("./ancestor::div[contains(@class, 'p-accordion-tab')]"))
                .getAttribute("class").contains("p-accordion-tab-active")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", accordionTab);
        }
    }



    public void applyFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
    }

    public void resetFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }
}