package com.example.sector

import android.widget.Spinner
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class CreateEventTest{

    private lateinit var venueName : String
    private lateinit var startDate : String
    private lateinit var startTime : String
    private lateinit var specialVenueName : String

    var jobTypeIndex = 2



    @Before
    fun initValidString() {
        venueName = "The Scene"
        specialVenueName = "@ Bristol"
        startDate = "Tue, Jane 26, 2021"
        startTime = "14:00"

    }

    @get:Rule
    var activityRule: ActivityScenarioRule<CreateEvent>
            = ActivityScenarioRule(CreateEvent::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.create_event))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_vis_dataInputFields_submitBtn() {
        onView(withId(R.id.job_type))
            .check(matches(isDisplayed()))
        onView(withId(R.id.venue_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.start_date))
            .check(matches(isDisplayed()))
        onView(withId(R.id.start_time))
            .check(matches(isDisplayed()))
        onView(withId(R.id.end_date))
            .check(matches(isDisplayed()))
        onView(withId(R.id.end_time))
            .check(matches(isDisplayed()))
        onView(withId(R.id.number_of_staff))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_event_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_validEventCreation() {

        onView(withId(R.id.venue_name))
            .perform(typeText(venueName), closeSoftKeyboard())

        //Date time picker data input
        onView(withId(R.id.start_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())


        onView(withId(R.id.start_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.end_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.end_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.number_of_staff))
            .perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.create_event_button)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_specialVenueName() {

        onView(withId(R.id.venue_name))
            .perform(typeText(specialVenueName), closeSoftKeyboard())

        //Date time picker data input
        onView(withId(R.id.start_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.start_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())


        onView(withId(R.id.end_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.end_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.number_of_staff))
            .perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.create_event_button)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_TextInStaffNumberField() {

        onView(withId(R.id.venue_name))
            .perform(typeText(specialVenueName), closeSoftKeyboard())

        //Date time picker data input
        onView(withId(R.id.start_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.start_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())


        onView(withId(R.id.end_date))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.end_time))
            .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.number_of_staff))
            .perform(typeText("one"), closeSoftKeyboard())
        onView(withId(R.id.create_event_button)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))
    }
}

