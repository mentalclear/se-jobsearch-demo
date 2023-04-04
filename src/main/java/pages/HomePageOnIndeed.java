package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageOnIndeed {
    private WebDriver driver;
    private By whatSearchField = By.id("text-input-what");
    private By whereSearchField = By.id("text-input-where");
    private By whereFieldButton = By.xpath("//input[@id='text-input-where']/following-sibling::span");
    private By findJobsButton = By.cssSelector("button.yosegi-InlineWhatWhere-primaryButton");

    public HomePageOnIndeed(WebDriver driver) {
        this.driver = driver;
    }
    public void populateWhatField(String searchTerm) {
        driver.findElement(whatSearchField).sendKeys(searchTerm);
    }
    public void populateWhereField(String searchTerm) {
        WebElement fieldElement = driver.findElement(whereSearchField);
        fieldElement.click();
        driver.findElement(whereFieldButton).click();
        fieldElement.sendKeys(searchTerm);
    }
    public SearchResultsPageOnIndeed clickFindJobs() {
        driver.findElement(findJobsButton).click();
        return new SearchResultsPageOnIndeed(driver);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isWhatFieldPresent() {
        return driver.findElement(whatSearchField).isDisplayed();
    }

    public boolean isWhereFieldPresent() {
        return driver.findElement(whereSearchField).isDisplayed();
    }

    public boolean isSubmitButtonPresent() {
        return driver.findElement(findJobsButton).isDisplayed();
    }

}
