package autoframework.testcasesOne;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;


@Epic("Epic 2")
@Feature("Feature: ValidateUserInfo 2")
public class ValidateUserInfo1 {


    @Story("Story: Validate user email and phone in DB phase 2")
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
        userSteps.validatePhone("15827521824");
    }

    @Story("Story: Validate user email and phone in DB phase 3")
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
        userSteps.validateEmail("test@demo.com");
        userSteps.validatePhone("15827521823");
    }
}
