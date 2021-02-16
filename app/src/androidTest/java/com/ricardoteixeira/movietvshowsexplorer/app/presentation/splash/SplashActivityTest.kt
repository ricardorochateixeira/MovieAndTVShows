package com.ricardoteixeira.movietvshowsexplorer.app.presentation.splash

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ricardoteixeira.movietvshowsexplorer.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
In order to run all the following tests, the animation in the SplashActivity should be disabled (please_wait.blink())
  */

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Test
    fun test_isActivityInView() {
        ActivityScenario.launch(SplashActivity::class.java)

        onView(withId(R.id.splash_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isCoverImageInView() {
        ActivityScenario.launch(SplashActivity::class.java)
        onView(withId(R.id.image_cover_splash_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isAppNameTextInViewWithRightText() {
        ActivityScenario.launch(SplashActivity::class.java)
        onView(allOf(withId(R.id.text_movies_and_tv_shows_splash_activity), withText(R.string.app_name))).check(matches(isDisplayed()))
    }

    @Test
    fun test_isPleaseWaitTextInViewWithRightText() {
        ActivityScenario.launch(SplashActivity::class.java)
        onView(allOf(withId(R.id.please_wait),withText(R.string.wait_message))).check(matches(isDisplayed()))
    }

}