package com.cs501.flashcard

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FlashcardActivityTest {

    private lateinit var activity: FlashcardActivity

    @Before
    fun setUp() {
        activity = FlashcardActivity()
    }

    @Test
    fun testIsAnswerCorrect() {
        activity.currentAnswer = 5

        assertTrue(activity.isAnswerCorrect(5))
        assertFalse(activity.isAnswerCorrect(10))
    }
}