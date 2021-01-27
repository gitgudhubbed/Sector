package com.example.sector

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DashboardActivityTest{

    @get:Rule
    var activityRule: ActivityScenarioRule<DashboardActivity>
            = ActivityScenarioRule(DashboardActivity::class.java)

    @Test
    fun test_isActivityInView() {
       onView(withId(R.id.dashboard_activity))
           .check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_eventBtn_profileBtn_createEventBtn() {
        onView(withId(R.id.event_Button))
            .check(matches(isDisplayed()))
        onView(withId(R.id.my_profile_button))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_event_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_nav_EventList_CreateEvent_UserProfile_withReturns() {

        onView(withId(R.id.event_Button)).perform(click())
        onView(withId(R.id.event_dashboard))
            .check(matches(isDisplayed()))
        //Return to Dashboard Activity
        pressBack()
        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))

        onView(withId(R.id.create_event_button)).perform(click())
        onView(withId(R.id.create_event))
            .check(matches(isDisplayed()))
        //Return to Dashboard Activity
        pressBack()
        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))

        onView(withId(R.id.my_profile_button)).perform(click())
        onView(withId(R.id.user_profile_activity))
            .check(matches(isDisplayed()))
        //Return to Dashboard Activity
        pressBack()
        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))

    }

}