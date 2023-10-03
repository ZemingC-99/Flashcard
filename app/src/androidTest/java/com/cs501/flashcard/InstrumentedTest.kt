/*
References:
1.https://stackoverflow.com/questions/69616702/activitytestrule-is-deprecated-on-android-test
2.https://developer.android.com/reference/androidx/test/rule/ActivityTestRule
 */
package com.cs501.flashcard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.*
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FlashcardActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(FlashcardActivity::class.java)

    //gradle build problem
    @Test
    fun testSubmitAnswer() {
        val currentAnswer = 42

        onView(withId(R.id.answerEditText))
            .perform(typeText(currentAnswer.toString()), closeSoftKeyboard())

        onView(withId(R.id.submitAnswerButton)).perform(click())
        onView(withText("Correct!"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }
}