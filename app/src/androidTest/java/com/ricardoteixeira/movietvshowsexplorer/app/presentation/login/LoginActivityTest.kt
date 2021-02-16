package com.ricardoteixeira.movietvshowsexplorer.app.presentation.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ricardoteixeira.movietvshowsexplorer.R
import com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome.WelcomeActivity
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

/**
 Missing test:
 - login success and failure and respective navigation or snackbar
 - Missing send reset password email and test snackbar
*/

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Test
    fun test_isActivityInView() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.login_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isBackButtonInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.button_go_back_login_activity))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_appNameTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_movies_and_tv_shows_login_activity),
                ViewMatchers.withText(R.string.app_name)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_welcomeBackTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_welcome_back),
                ViewMatchers.withText(R.string.hello_there_welcome_back)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isEmailTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_email_login_activity),
                ViewMatchers.withText(R.string.email_address)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isEmailEditTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.edit_email_login_activity),
                ViewMatchers.withHint(R.string.email)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isPasswordTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_password_login_activity),
                ViewMatchers.withText(R.string.password)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isPasswordEditTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.edit_password_login_activity),
                ViewMatchers.withHint(R.string.password)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isLoginTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.button_login_login_activity),
                ViewMatchers.withText(R.string.login)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isRegisterButtonInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.button_login_login_activity),
                ViewMatchers.withText(R.string.login)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isResetPasswordTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_reset_password),
                ViewMatchers.withText(R.string.reset_password)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isDoNotHaveAnAccountTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_do_not_have_account_login_Activity),
                ViewMatchers.withText(R.string.do_not_have_an_account)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isRegisterTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_register_login_activity),
                ViewMatchers.withText(R.string.register)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun test_isDoNotWantToCreateAccountTextInViewWithRightText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.text_enter_as_guest_login_activity),
                ViewMatchers.withText(R.string.enter_as_a_guest)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun test_navigateToWelcomeActivityPressingBackButton() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.button_go_back_login_activity))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.welcome_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_showSnackbarPressingLoginButton() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.button_login_login_activity))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Please insert email and password!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_showSnackbarPressingResetPasswordText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.text_reset_password))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Please write email to receive a reset password email!"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_navigateToRegisterActivityPressingRegisterText() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.text_register_login_activity))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.register_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_navigateToMainActivityPressingEnterAsAGuestTitle() {
        ActivityScenario.launch(LoginActivity::class.java)
        Espresso.onView(withId(R.id.text_enter_as_guest_login_activity))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.main_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}