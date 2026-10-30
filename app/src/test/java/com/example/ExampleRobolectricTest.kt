package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.FunoraData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Funora", appName)
    }

    @Test
    fun `verify quizzes and personality tests data integrity`() {
        assertTrue("Quizzes should not be empty", FunoraData.quizzes.isNotEmpty())
        assertTrue("Personality tests should not be empty", FunoraData.personalityTests.isNotEmpty())
        assertTrue("Facts should not be empty", FunoraData.facts.isNotEmpty())
        assertTrue("Daily questions pool should not be empty", FunoraData.dailyQuestionsPool.isNotEmpty())
    }
}
