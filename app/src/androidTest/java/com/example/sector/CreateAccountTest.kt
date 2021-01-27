package com.example.sector

import org.junit.Assert.*
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CreateAccountTest{

    private lateinit var validEmail: String
    private lateinit var validEmailTwo: String
    private lateinit var validEmailThree: String
    private lateinit var invalidEmail: String
    private lateinit var password: String
    private lateinit var securePassword: String
    private lateinit var firstName: String
    private lateinit var surname: String

    @get:Rule
    var activityRule: ActivityScenarioRule<CreateAccount>
            = ActivityScenarioRule(CreateAccount::class.java)

    @Before
    fun initValidString() {
        validEmail = "harryharry@gmail.com"
        validEmailTwo = "Betty@hotmail.com"
        validEmailThree = "Adam@gmail.com"
        invalidEmail = "notAnEmailAddress"
        password = "abc123"
        securePassword = "AbC&123!"
        firstName = "Harry"
        surname = "Winters"

    }

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.sign_up_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_vis_email_pass_firstName_surname_submit() {
        onView(withId(R.id.create_username))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_password))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_first_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.create_surname))
            .check(matches(isDisplayed()))
        onView(withId(R.id.sign_up))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_validSignUp() {
        onView(withId(R.id.create_username))
            .perform(typeText(validEmail), closeSoftKeyboard())
        onView(withId(R.id.create_password))
            .perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.create_first_name))
            .perform(typeText(firstName), closeSoftKeyboard())
        onView(withId(R.id.create_surname))
            .perform(typeText(surname), closeSoftKeyboard())

        onView(withId(R.id.sign_up)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_validEmail_Password_EmptyName() {
        onView(withId(R.id.create_username))
            .perform(typeText(validEmailTwo), closeSoftKeyboard())
        onView(withId(R.id.create_password))
            .perform(typeText(password), closeSoftKeyboard())

        onView(withId(R.id.sign_up)).perform(click())

        //Test if toast is displayed
        onView(withText("Sign up failed please try again in a few minutes"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }

    @Test
    fun test_invalidEmail() {
        onView(withId(R.id.create_username))
            .perform(typeText(invalidEmail), closeSoftKeyboard())
        onView(withId(R.id.create_password))
            .perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.create_first_name))
            .perform(typeText(firstName), closeSoftKeyboard())
        onView(withId(R.id.create_surname))
            .perform(typeText(surname), closeSoftKeyboard())

        onView(withId(R.id.sign_up)).perform(click())
        //Test if toast is displayed
        onView(withText("Sign up failed please try again in a few minutes"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }

    @Test
    fun test_securePassword() {
        onView(withId(R.id.create_username))
            .perform(typeText(validEmailThree), closeSoftKeyboard())
        onView(withId(R.id.create_password))
            .perform(typeText(securePassword), closeSoftKeyboard())
        onView(withId(R.id.create_first_name))
            .perform(typeText(firstName), closeSoftKeyboard())
        onView(withId(R.id.create_surname))
            .perform(typeText(surname), closeSoftKeyboard())

        onView(withId(R.id.sign_up)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_activity))
            .check(matches(isDisplayed()))
    }
}