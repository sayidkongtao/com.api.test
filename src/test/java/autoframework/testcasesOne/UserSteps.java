package autoframework.testcasesOne;

import autoframework.utils.Utils;
import io.qameta.allure.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserSteps {

    @Step("Login in to oneapp")
    public void login() {
        Utils.sleepBySecond(1);
    }

    @Step("Create account")
    public void createAccount() {
        Utils.sleepBySecond(1);
    }

    @Step("Get the email and phone number from Database")
    public void getEmailAndPhoneFromDB() {
        Utils.sleepBySecond(1);
    }

    @Step("Validate the email")
    public void validateEmail(String email) {
        assertThat("The email should be: test@demo.com", email, is("test@demo.com"));
    }

    @Step("Validate the phone number")
    public void validatePhone(String phoneNumber) {
        assertThat("The email should be: 1582752823", phoneNumber, is("15827521823"));
    }
}
