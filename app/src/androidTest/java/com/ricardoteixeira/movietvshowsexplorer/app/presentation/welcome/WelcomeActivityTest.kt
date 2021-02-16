package com.ricardoteixeira.movietvshowsexplorer.app.presentation.welcome

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ricardoteixeira.movietvshowsexplorer.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {

    @Test
    fun test_isActivityInView() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(withId(R.id.welcome_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCoverImageInView() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(withId(R.id.image_cover_welcome_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isAppNameTextInViewWithRightText() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(allOf(withId(R.id.text_movies_and_tv_shows_welcome_activity), withText(R.string.app_name))).check(matches(isDisplayed()))
    }

    @Test
    fun test_isLoginButtonInViewWithRightText() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(allOf(withId(R.id.button_login_welcome_activity), withText(R.string.login))).check(matches(isDisplayed()))
    }

    @Test
    fun test_isRegisterButtonInViewWithRightText() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(allOf(withId(R.id.button_register_welcome_activity), withText(R.string.register))).check(matches(isDisplayed()))
    }

    @Test
    fun test_isDoYouWantToCreateAccountTextInViewWithRightText() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(allOf(withId(R.id.text_do_not_want_to_register_welcome_Activity), withText(R.string.guest))).check(matches(isDisplayed()))
    }

    @Test
    fun test_isEnterAsGuestTextInViewWithRightText() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(allOf(withId(R.id.text_enter_as_guest_welcome_activity), withText(R.string.enter_as_a_guest))).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToLoginActivity() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(withId(R.id.button_login_welcome_activity)).perform(click())
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToRegisterActivity() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(withId(R.id.button_register_welcome_activity)).perform(click())
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigateToMainActivityPressingEnterAsAGuest() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        onView(withId(R.id.text_enter_as_guest_welcome_activity)).perform(click())
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()))
    }
}