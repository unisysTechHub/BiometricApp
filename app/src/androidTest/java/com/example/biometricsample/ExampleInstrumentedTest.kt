package com.example.biometricsample

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.biometricsample.activity.MainActivityBottomNav
import kotlinx.coroutines.test.TestCoroutineDispatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivityBottomNav::class.java)
    private val testDispatcher = TestCoroutineDispatcher()
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.biometricsample", appContext.packageName)
    }
    @Test()
    fun loadingScreenTest(){
        Espresso.onView(withId(R.id.text_dashboard

        ))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}