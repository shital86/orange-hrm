package com.orangehrmlive.demo.testsuite;

import com.orangehrmlive.demo.pages.*;
import com.orangehrmlive.demo.testbase.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UserTest extends TestBase {
    Homepage homePage;
    LoginPage loginPage;
    AdminPage adminPage;
    AddUserPage addUserPage;
    ViewSystemUserPage viewSystemUserPage;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        homePage = new Homepage();
        loginPage = new LoginPage();
        adminPage = new AdminPage();
        addUserPage = new AddUserPage();
        viewSystemUserPage = new ViewSystemUserPage();
    }

    @Test(groups = {"sanity","regression"})
    public void adminShouldAddUserSuccessFully() throws InterruptedException {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.loginBtn();
        homePage.clickAdminLink();
        Thread.sleep(1000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(adminPage.getAdminHeader(), "System Users", "Failed to navigate Admin Page");
        Thread.sleep(1000);
        adminPage.clickAddButton();
        addUserPage.setUserRoleDropDown("Admin");
        addUserPage.setEmployeeName("Kim Marsh");
        addUserPage.setUserName("admin");
        addUserPage.setStatusDropDown("Disabled");
        addUserPage.setPassword("123456");
        addUserPage.setConfirmPassword("123456");
        addUserPage.setSaveButton();
        Thread.sleep(1000);
        softAssert.assertEquals(addUserPage.getConfirmMessage(), "Successfully Saved", "Failed to save");
        softAssert.assertAll();
    }

    @Test(groups = {"sanity","smoke","regression"})
    public void searchTheUserCreatedAndVerifyIt() {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.loginBtn();
        homePage.clickAdminLink();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(adminPage.getAdminHeader(), "System Users", "Failed to navigate Admin Page");
        viewSystemUserPage.setUserName("admin123");
        viewSystemUserPage.setUserRoleDropDown("Admin");
        viewSystemUserPage.setEmployeeName("Kim Marsh");
        viewSystemUserPage.setStatus("Disabled");
        viewSystemUserPage.setSearchButton();
        softAssert.assertEquals(viewSystemUserPage.getSearchResult(), "admin123", "Failed to search");
        viewSystemUserPage.setSearchResultCheckbox();
        viewSystemUserPage.setDeleteButton();
        softAssert.assertAll();
    }

    @Test(groups = {"regression"})
    public void verifyThatAdminShouldDeleteTheUserSuccessFully() throws InterruptedException {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.loginBtn();
        Thread.sleep(2000);
        homePage.clickAdminLink();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(adminPage.getAdminHeader(), "System Users", "Failed to navigate Admin Page");
        viewSystemUserPage.setUserName("Orange");
        viewSystemUserPage.setUserRoleDropDown("Admin");
        viewSystemUserPage.setEmployeeName("Kim Marsh");
        viewSystemUserPage.setStatus("Disabled");
        viewSystemUserPage.setSearchButton();
        Thread.sleep(2000);
        viewSystemUserPage.setSearchResultCheckbox();
        viewSystemUserPage.setDeleteButton();
        viewSystemUserPage.setDeleteRecordDialogueBoxOK();
        softAssert.assertEquals(viewSystemUserPage.getDeletionConfirmation(), "Successfully Deleted", "Failed to save");
        softAssert.assertAll();
    }

    @Test(groups = {"regression"})
    public void searchTheDeletedUserAndVerifyTheMessageNoRecordFound() {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.loginBtn();
        homePage.clickAdminLink();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(adminPage.getAdminHeader(), "System Users", "Failed to navigate Admin Page");
        viewSystemUserPage.setUserName("Orange");
        viewSystemUserPage.setUserRoleDropDown("Admin");
        viewSystemUserPage.setEmployeeName("Kim Marsh");
        viewSystemUserPage.setStatus("Disabled");
        viewSystemUserPage.setSearchButton();
        softAssert.assertEquals(viewSystemUserPage.getNoRecordFound(), "No Records Found", "Failed to save");
        softAssert.assertAll();
    }


}
