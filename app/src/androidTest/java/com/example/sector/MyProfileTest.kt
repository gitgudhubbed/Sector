package com.example.sector

import org.junit.Assert.*
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MyProfileTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MyProfile> = ActivityScenarioRule(MyProfile::class.java)


    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.user_profile_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_vis_userInfo_userEvents() {
        onView(withId(R.id.personal_detail_linearLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.user_event_linearLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_isTitleTextDisplayed() {
        onView(withId(R.id.user_profile))
            .check(matches(withText(R.string.my_profile)))

        onView(withId(R.id.user_event))
            .check(matches(withText(R.string.my_jobs)))
    }

    @Test
    fun test_expandAndRetractUserEvents() {
        onView(withId(R.id.user_event_linearLayout))
            .perform(click())
        onView((withId(R.id.user_event_list)))
            .check(matches(isDisplayed()))
        /*onView(withId(R.id.user_event_linearLayout))
            .perform(click())
        onView((withId(R.id.user_event_list)))
            .check(matches(isDisplayed()))*/
    }
}

