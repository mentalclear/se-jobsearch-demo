package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitManager;

public class SearchResultsPageOnIndeed {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By remoteJobsSelector = By.id("filter-remotejob");
    private final By remoteJobsMenuItem = By.xpath("//ul[@id='filter-remotejob-menu']/li");
    private final By postedBySelector = By.id("filter-srctype");
    private final By postedByMenuItem = By.xpath("//ul[@id='filter-srctype-menu']/li");
    private final By jobTypeSelector = By.id("filter-jobtype");
    private final By jobTypeMenuItem = By.xpath("//ul[@id='filter-jobtype-menu']/li");
    private final By datePostedSelector = By.id("filter-dateposted");
    private final By datePostedMenuItem14days = By.xpath("//ul[@id='filter-dateposted-menu']/li[4]");
    private final By resultingAmountOfJobs = By.xpath("//div[@class='jobsearch-JobCountAndSortPane-jobCount']/span[1]");
    private final By searchResultsItems = By.xpath("//ul[@class='jobsearch-ResultsList css-0']/li");
    private final By companyNameLink = By.xpath("//div[@data-company-name]/a");
    private final By paginationNext = By.xpath("//a[@data-testid='pagination-page-next']");
    private final By annoyingElement = By.xpath("//div[contains(@class, 'jobsearch-JapanSnackBarContainer-toastWrapper')]");
    private final By footerContainer = By.id("gnav-footer-container");

    public SearchResultsPageOnIndeed(WebDriver driver) {
        this.driver = driver;
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    public void setRemoteJobs() {
        setSearchOption(remoteJobsSelector, remoteJobsMenuItem);
    }
    public void setPostedByEmployer() {
        setSearchOption(postedBySelector, postedByMenuItem);
    }
    public void setJobTypeFullTime() {
        setSearchOption(jobTypeSelector, jobTypeMenuItem);
    }
    public void setDatePosted14Days() {
        setSearchOption(datePostedSelector, datePostedMenuItem14days);
    }
    private void setSearchOption(By searchPillSelector, By searchPillMenuItem) {
        wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchPillSelector));
            driver.findElement(searchPillSelector).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(searchPillMenuItem));
            driver.findElement(searchPillMenuItem).click();
        } catch (NoSuchElementException e) {
            System.out.printf("Selector %s isn't visible... but we can proceed", searchPillSelector);
        }
    }
    public String getRemoteJobsPillStyle() {
        return driver.findElement(remoteJobsSelector).getCssValue("background-color");
    }
    public String postedByPillStyle() {
        return driver.findElement(postedBySelector).getCssValue("background-color");
    }
    public String jobTypePillStyle() {
        return driver.findElement(jobTypeSelector).getCssValue("background-color");
    }
    public String datePostedPillStyle() {
        return driver.findElement(datePostedSelector).getCssValue("background-color");
    }
    public String getResultingNumberOfJobs() {
        return driver.findElement(resultingAmountOfJobs).getText();
    }
    public int getResultsSize() {
        return driver.findElements(searchResultsItems).size();
    }
    public Boolean isPaginationNextVisible() {
        return driver.findElements(paginationNext).size() > 0;
    }
    public CompanyPageOnIndeed clickListItemCompanyLink(int index) {
        wait = new WebDriverWait(driver, 5);
        clickSearchResultCard(index);
        try {
            clickCompanyLink();
        } catch (TimeoutException e) {
            return null;
        }
        return new CompanyPageOnIndeed(driver);
    }
    private void clickCompanyLink() {
        wait.until(ExpectedConditions.presenceOfElementLocated(companyNameLink));
        WebElement companyLinkElement = driver.findElement(companyNameLink);
        wait.until(ExpectedConditions.elementToBeClickable(companyLinkElement));
        companyLinkElement.click();
    }
    private void clickSearchResultCard(int index) {
        WebElement searchResultsElement = driver.findElements(searchResultsItems).get(index);
        wait.until(ExpectedConditions.elementToBeClickable(searchResultsElement));
        searchResultsElement.click();
    }
    public void getToNextResultsPage() {
        driver.findElement(paginationNext).click();
    }
    public void scrollThePage() {
        WaitManager waitManager = new WaitManager(driver, 2);
        var bottomPageElement = driver.findElement(footerContainer);
        String script = "arguments[0].scrollIntoView({behavior: 'smooth'})";
        ((JavascriptExecutor)driver).executeScript(script, bottomPageElement);
        waitManager.dummyWait();
    }
    public void setAnnoyingElementToHidden(){
        WebElement element = driver.findElement(annoyingElement);
        ((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('hidden','')", element);
    }
    public boolean isAnnoyingElementVisible(){
        return driver.findElement(annoyingElement).isDisplayed();
    }
}
