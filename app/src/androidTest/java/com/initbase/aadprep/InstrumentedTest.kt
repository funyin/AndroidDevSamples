package com.initbase.aadprep

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import org.hamcrest.Matchers.*
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions


@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Rule
    @JvmField
    var testActivityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        onView(withId(0)).perform(DrawerActions.open())
        NavigationViewActions.navigateTo(0)
        onView(withId(0)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            0, click()
        ))
        val (empty,rick,) = 1 to 2

    }


    @Test
    fun carryOutTest() {
        onData(
            allOf(
                equalTo(MainActivity.testItem),
                instanceOf(String::class.java),
//                withParentIndex(1)
            )
        ).perform(click())
        val editText = withId(R.id.edit_text)
        val testString = "This is an esspresso test"
        onView(editText).perform(
            typeText(testString),
            closeSoftKeyboard()
        )
        onView(editText).check(matches(withText(containsString(testString))))
        onView(withId(R.id.btn_submit)).perform(click())
        pressBack()
    }
}