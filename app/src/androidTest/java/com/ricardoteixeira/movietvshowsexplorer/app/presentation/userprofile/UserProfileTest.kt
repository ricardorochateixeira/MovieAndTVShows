package com.ricardoteixeira.movietvshowsexplorer.app.presentation.userprofile

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.EspressoException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.ricardoteixeira.movietvshowsexplorer.R
import kotlinx.android.synthetic.main.user_profile_activity.*
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserProfileTest {

    @Test
    fun test_isActivityInView() {
        ActivityScenario.launch(UserProfileActivity::class.java)
        Espresso.onView(withId(R.id.user_profile_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isBackButtonInView(mAuth: FirebaseAuth) {
        ActivityScenario.launch(UserProfileActivity::class.java)
        Espresso.onView(withId(R.id.button_back_user_profile_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}