package com.ricardoteixeira.movietvshowsexplorer.app.presentation.register

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ricardoteixeira.movietvshowsexplorer.R
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

/**
Missing test:
- Register success and failure and respective navigation or snackbar
 */

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Test
    fun test_isActivityInView() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.register_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isBackButtonInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.button_go_back_register_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_appNameTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_movies_and_tv_shows_register_activity),
                withText(R.string.app_name)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isEmailTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_email_register_activity),
                withText(R.string.email_address)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isEmailEditTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.edit_email_register_activity),
                withHint(R.string.email)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isPasswordTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_password_register_activity),
                withText(R.string.password)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isPasswordEditTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.edit_password_register_activity),
                withHint(R.string.password)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isInformationImageInView() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.image_information))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isCheckPasswordImageInvisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.check_password))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun test_isConfirmPasswordTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_confirm_password),
                withText(R.string.confirm_password)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isConfirmPasswordEditTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.edit_confirm_password_register_activity),
                withHint(R.string.confirm_password)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCheckConfirmPasswordImageInvisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.check_confirm_password))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun test_isNameTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_name),
                withText(R.string.name)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isNameEditTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.edit_name_register_activity),
                withHint(R.string.name)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCheckNameImageInvisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.check_name))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun test_isRegisterButtonInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.button_register_register_activity),
                withText(R.string.register)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isAlreadyHaveAnAccountTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_do_not_have_account_register_activity),
                withText(R.string.already_have_an_account)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isLoginTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_login_register_activity),
                withText(R.string.login)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isDoNotWantToCreateAnAccountTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_do_not_want_to_register_register_activity),
                withText(R.string.guest)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_isEnterAsGuestTextInViewWithRightText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(
            CoreMatchers.allOf(
                withId(R.id.text_enter_as_guest_register_activity),
                withText(R.string.enter_as_a_guest)
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToWelcomeActivityPressingBackButton() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.button_go_back_register_activity))
            .perform(ViewActions.click())
        onView(withId(R.id.welcome_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_showPasswordDialogPressingInformationButton() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.image_information))
            .perform(ViewActions.click())
        onView(withText(R.string.password_information))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_showSnackbarPressingRegisterButton() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.button_register_register_activity))
            .perform(ViewActions.click())
        onView(withText("Please insert all the fields!"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToLoginActivityPressingRegisterText() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.text_login_register_activity))
            .perform(ViewActions.click())
        onView(withId(R.id.login_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToMainActivityPressingEnterAsAGuestTitle() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.text_enter_as_guest_register_activity))
            .perform(ViewActions.click())
        onView(withId(R.id.main_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isCheckPasswordImageVisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.edit_password_register_activity)).perform(replaceText("asdfghjkl"))
        onView(withId(R.id.check_password)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun test_isCheckConfirmPasswordImageVisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.edit_confirm_password_register_activity)).perform(replaceText("asdfghjkl"))
        onView(withId(R.id.check_confirm_password)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun test_isCheckNameImageVisible() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.edit_name_register_activity)).perform(replaceText("as"))
        onView(withId(R.id.check_name)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun test_showSnackbarPressingRegisterButtonWithPasswordLowerThanEightCharacters() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.edit_email_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.edit_password_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.edit_confirm_password_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.edit_name_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.button_register_register_activity)).perform(ViewActions.click())
        onView(withText("Password must have between 8 and 16 characters."))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_showSnackbarPressingRegisterButtonWhenPasswordsDoNotMatch() {
        ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.edit_email_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.edit_password_register_activity)).perform(replaceText("gfdsgdsfgsfdgsdfg"))
        onView(withId(R.id.edit_confirm_password_register_activity)).perform(replaceText("sdfgdsfgsdfgdsfgds"))
        onView(withId(R.id.edit_name_register_activity)).perform(replaceText("asda"))
        onView(withId(R.id.button_register_register_activity)).perform(ViewActions.click())
        onView(withText("Please make sure that passwords are the same"))
            .check(matches(isDisplayed()))
    }

}