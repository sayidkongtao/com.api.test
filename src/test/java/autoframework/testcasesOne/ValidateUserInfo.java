package autoframework.testcasesOne;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Epic 1")
@Feature("Feature: ValidateUserInfo 1")
public class ValidateUserInfo {

    @Story("Story: validate user email and phone in DB phase 1")
    @Test(description = "[Case number]: validate user email and phone in DB")
    @Description("Case Steps: " +
            "1. Login in to oneapp " +
            "2. Create account " +
            "3. Get the email and phone number from Database " +
            "4. Validate the email and phone number" +
            "Expected Result: email and phone number should be the same as user input")
    public void validateUserInfo() {
        UserSteps userSteps = new UserSteps();
        userSteps.login();
        userSteps.createAccount();
        userSteps.getEmailAndPhoneFromDB();
        userSteps.validateEmail("test@demo.com");
        userSteps.validatePhone("15827521823");
    }

    @Story("Story: validate user email and phone in DB phase 1")
    @Test(description = "[Case number]: validate user email and phone in DB")
    @Description("Case Steps: " +
            "1. Login in to oneapp " +
            "2. Create account " +
            "3. Get the email and phone number from Database " +
            "4. Validate the email and phone number" +
            "Expected Result: email and phone number should be the same as user input")
    public void validateUserInfo1() {
        UserSteps userSteps = new UserSteps();
        userSteps.login();
        userSteps.createAccount();
        userSteps.getEmailAndPhoneFromDB();
        userSteps.validateEmail("test1@demo.com");
        userSteps.validatePhone("15827521823");
    }
}
