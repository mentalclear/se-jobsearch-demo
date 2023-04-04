package step.definitions;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import pages.HomePageOnIndeed;
import pages.SearchResultsPageOnIndeed;

import java.util.concurrent.TimeUnit;

import static base.BaseTests.selectWebDriverForOS;


public class HomePageOnIndeedSteps {
    private static WebDriver driver;
    private HomePageOnIndeed homepage;
    private SearchResultsPageOnIndeed searchResultsPage;

    SoftAssert softAssert = new SoftAssert();

    @BeforeAll
    public static void setUp() {
        selectWebDriverForOS();
    }

    @Given("User has started Chrome browser")
    public void user_has_started_chrome_browser() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @When("User navigates to {string}")
    public void user_navigates_to(String homepageUrl) {
        homepage = new HomePageOnIndeed(driver);
        driver.get(homepageUrl);
    }

    @Then("Page title should be {string}")
    public void page_title_should_be(String homepageTitle) {
        softAssert.assertEquals(homepage.getPageTitle(),homepageTitle);
        softAssert.assertAll();
    }

    @Then("{string} input field should be present")
    public void input_field_should_be_present(String fieldName) {
        if (fieldName.contains("What"))
            softAssert.assertTrue(homepage.isWhatFieldPresent());
        if (fieldName.contains("Where"))
            softAssert.assertTrue(homepage.isWhereFieldPresent());
        softAssert.assertAll();
    }
    @Then("Find jobs button should be present")
    public void button_should_be_present() {
      softAssert.assertTrue(homepage.isSubmitButtonPresent());
      softAssert.assertAll();
    }

    @When("^User inputs (.*?) and (.*?)$")
    public void user_inputs_what_and_where(String jobName, String jobLocation) {
        homepage.populateWhatField(jobName);
        homepage.populateWhereField(jobLocation);
    }

    @When("User clicks Search Jobs button")
    public void user_clicks_search_jobs_button() {
       searchResultsPage = homepage.clickFindJobs();
    }

    @Then("User is presented with search results")
    public void user_is_presented_with_search_results() {
        softAssert.assertTrue(searchResultsPage.getResultsSize() > 0);
    }

    @After()
    public static void cleanUp() {
       driver.quit();
    }
}
