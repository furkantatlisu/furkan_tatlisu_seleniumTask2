package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CareersTest extends BaseTest {

    HomePage homePage;
    QualityAssurancePage qualityAssurancePage;
    OpenPositionsPage openPositionsPage;
    ApplicationFormPage applicationFormPage;

    @Test
    public void testInsiderCareersFunctionality() throws InterruptedException {
        try {
            // Step 1: Check Insider home page is opened
            homePage = new HomePage(driver);
            Assert.assertTrue(homePage.isHomePageOpened(), "Home page is not opened");
            test.log(com.aventstack.extentreports.Status.PASS, "Home page opened successfully");

            // Step 2: Navigate to Careers page and check blocks
            homePage.navigateToCareersPage();
            CareersPage careersPage = new CareersPage(driver);

            Assert.assertTrue(careersPage.isCareersPageOpened(), "Careers page is not opened");
            Assert.assertTrue(careersPage.areAllBlocksDisplayed(), "Not all blocks are displayed on Careers page");
            test.log(com.aventstack.extentreports.Status.PASS, "Careers page opened with all blocks displayed");

            // Step 3: Go to QA jobs page, filter jobs
            qualityAssurancePage = new QualityAssurancePage(driver);

            qualityAssurancePage.goToQAPageUrl();
            Assert.assertTrue(qualityAssurancePage.isQualityAssurancePageOpened(), "Quality Assurance page is not opened");
            qualityAssurancePage.clickSeeAllQAJobs();

            openPositionsPage = new OpenPositionsPage(driver);

            Assert.assertTrue(openPositionsPage.isOpenPositionsPageOpened(), "Open Positions page is not opened");

            openPositionsPage.filterJobsByLocationAndDepartment("Istanbul, Turkiye", "Quality Assurance");
            Assert.assertTrue(openPositionsPage.isJobsListPresent(), "Jobs list is not present after filtering");
            test.log(com.aventstack.extentreports.Status.PASS, "QA jobs filtered successfully");

            // Step 4: Verify job details
            Assert.assertTrue(openPositionsPage.verifyJobDetails("Quality Assurance", "Istanbul, Turkiye"),
                    "Job details verification failed");
            test.log(com.aventstack.extentreports.Status.PASS, "All job details verified successfully");

            // Step 5: Check Lever Application form page
            applicationFormPage = new ApplicationFormPage(driver);
            openPositionsPage.clickViewRoleForFirstJob();
            Assert.assertTrue(applicationFormPage.isLeverApplicationPageOpened(),
                    "Lever Application form page is not opened");
            test.log(com.aventstack.extentreports.Status.PASS, "Lever Application form page opened successfully");

        } catch (Exception e) {
            test.log(com.aventstack.extentreports.Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }
}
