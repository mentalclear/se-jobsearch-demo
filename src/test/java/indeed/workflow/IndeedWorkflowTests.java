package indeed.workflow;

import base.BaseTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CompanyPageOnIndeed;
import pages.SearchResultsPageOnIndeed;
import utils.CsvFileWriter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class IndeedWorkflowTests extends BaseTests {
    private SearchResultsPageOnIndeed searchResultsPage;
    @DataProvider(name = "SearchTermsQE")
    public Object[][] createQEData(){
        return new Object[][] {
                {"Quality Engineer", "Virginia, United States"},
                {"Quality Engineer", "Maryland, United States"},
                {"Quality Engineer", "Washington DC, United States"},
                {"Software Quality Engineer", "Virginia, United States"},
                {"Software Quality Engineer", "Maryland, United States"},
                {"Software Quality Engineer", "Washington DC, United States"},
                {"Software Tester", "Virginia, United States"},
                {"Software Tester", "Maryland, United States"},
                {"Software Tester", "Washington DC, United States"}
        };
    }
    // @Test(enabled = false)
    @Test(dataProvider = "SearchTermsQE", priority = 1)
    public void searchIndeedJobsQETest(String jTitle, String jLocation) {
        String jobTitle = jTitle;
        String jobLocation = jLocation;
        CsvFileWriter outputFile = new CsvFileWriter(
                getClearedTitle(jobTitle) + "_" + getClearedTitle(jobLocation));

        startHomePageSearch(jobTitle, jobLocation);
        setSearchToPostedByEmployer();
        setSearchToJobTypeFullTime();
        setSearchToDatePosted14Days();

        disableObstructingElement();
        extractCompaniesData(jobTitle, jobLocation, outputFile);
    }

    @DataProvider(name = "SearchTermsDev")
    public Object[][] createDevData(){
        return new Object[][] {
                {"vue", "United States"},
                {"vue.js", "United States"},
                {"vuejs", "United States"},
                {"nuxt", "United States"},
                {"react", "United States"},
                {"react.js", "United States"},
                {"next.js", "United States"},
                {"nextjs", "United States"}
        };
    }

    @Test(dataProvider = "SearchTermsDev", priority = 2)
    public void searchForDevJobsRemoteUSTest(String jTitle, String jLocation) {
        String jobTitle = jTitle;
        String jobLocation = jLocation;
        CsvFileWriter outputFile = new CsvFileWriter(
                getClearedTitle(jobTitle) + "_" + getClearedTitle(jobLocation));

        startHomePageSearch(jobTitle, jobLocation);
        setSearchToPostedByEmployer();
        setSearchToJobTypeFullTime();
        setSearchToRemoteJobs();
        setSearchToDatePosted14Days();

        disableObstructingElement();
        extractCompaniesData(jobTitle, jobLocation, outputFile);
    }
    private void extractCompaniesData(String jobTitle, String jobLocation, CsvFileWriter file) {
        int linksToProcess = searchResultsPage.getResultsSize();
        while(linksToProcess > 1) {
            for (int i = 0; i < searchResultsPage.getResultsSize(); i++) {
                if (i == 5 || i == 11 || i == 17) continue;
                searchResultsPage.scrollThePage();
                CompanyPageOnIndeed companyPage = searchResultsPage.clickListItemCompanyLink(i);
                if (companyPage == null) continue;
                getWindowManager().switchToNewTab();
                companyPage.storeCompanyInfo(file);
                companyPage.closePage();
                getWindowManager().switchToTab(jobTitle + " Jobs, Employment in "+ jobLocation +" | Indeed.com");
                linksToProcess--;
            }
            if (searchResultsPage.isPaginationNextVisible()) {
                searchResultsPage.getToNextResultsPage();
                linksToProcess = searchResultsPage.getResultsSize();
            }
        }
    }
    private void startHomePageSearch(String jobTitle, String jobLocation) {
        homePageOnIndeed.populateWhatField(jobTitle);
        homePageOnIndeed.populateWhereField(jobLocation);
        searchResultsPage = homePageOnIndeed.clickFindJobs();
        assertTrue(searchResultsPage.getCurrentUrl().contains("https://www.indeed.com/jobs"));
    }
    private void setSearchToRemoteJobs() {
        searchResultsPage.setRemoteJobs();
        assertEquals(searchResultsPage.getRemoteJobsPillStyle(), "rgba(89, 89, 89, 1)");
        assertTrue(searchResultsPage.getResultingNumberOfJobs().contains("jobs"));
    }
    private void setSearchToPostedByEmployer() {
        searchResultsPage.setPostedByEmployer();
        assertEquals(searchResultsPage.postedByPillStyle(), "rgba(89, 89, 89, 1)");
        assertTrue(searchResultsPage.getResultingNumberOfJobs().contains("jobs"));
    }

    private void setSearchToJobTypeFullTime() {
        searchResultsPage.setJobTypeFullTime();
        assertEquals(searchResultsPage.jobTypePillStyle(), "rgba(89, 89, 89, 1)");
        assertTrue(searchResultsPage.getResultingNumberOfJobs().contains("jobs"));
    }

    private void setSearchToDatePosted14Days() {
        searchResultsPage.setDatePosted14Days();
        assertEquals(searchResultsPage.datePostedPillStyle(), "rgba(89, 89, 89, 1)");
        assertTrue(searchResultsPage.getResultingNumberOfJobs().contains("jobs"));
    }
    private void disableObstructingElement(){
        searchResultsPage.setAnnoyingElementToHidden();
        assertTrue(!searchResultsPage.isAnnoyingElementVisible());
    }
    private String getClearedTitle(String title) {
        return title.trim().replaceAll("[,.\\s]", "_");
    }
}
