package com.example.myapplication

import android.content.pm.ActivityInfo
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAbout() {
        launchActivity<MainActivity>()
        openAbout()
        Espresso.onView(ViewMatchers.withId(R.id.activity_about))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    private fun isFirstFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.fragment1))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun isSecondFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.fragment2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun isThirdFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.fragment3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun inFragment(i: Int) { //проверка, что внутри фрагмента
        Espresso.onView(ViewMatchers.withId(R.id.activity_main))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        if (i == 1) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToFirst))
                .check(ViewAssertions.doesNotExist())
            Espresso.onView(ViewMatchers.withId(R.id.bnToSecond))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.bnToThird))
                .check(ViewAssertions.doesNotExist())
        }
        else if (i == 2) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToFirst))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.bnToSecond))
                .check(ViewAssertions.doesNotExist())
            Espresso.onView(ViewMatchers.withId(R.id.bnToThird))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        else if (i == 3) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToFirst))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.bnToSecond))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.bnToThird))
                .check(ViewAssertions.doesNotExist())
        }
    }

    private fun isAbout() {
        Espresso.onView(ViewMatchers.withId(R.id.activity_about))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tvAbout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.activity_main))
            .check(ViewAssertions.doesNotExist())
    }

    private fun selFragment(i: Int) { //проверка составляющих фрагментов
        if (i == 1) {
            isFirstFragment()
            inFragment(i)
        }
        else if (i == 2) {
            isSecondFragment()
            inFragment(i)
        }
        else if (i == 3) {
            isThirdFragment()
            inFragment(i)
        }
        else if (i == 4) {
            isAbout()
        }
    }

    private fun clickToFragment(i: Int) { //переход по клику на кнопку к следующему фрагменту
        if (i == 1) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToFirst)).perform(ViewActions.click())
        }
        else if (i == 2) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToSecond)).perform(ViewActions.click())
        }
        else if (i == 3) {
            Espresso.onView(ViewMatchers.withId(R.id.bnToThird)).perform(ViewActions.click())
        }
    }

    @Test
    fun fromToTest() {
        launchActivity<MainActivity>()
        //first
        selFragment(1)
        clickToFragment(1)
        selFragment(2)
        ViewActions.pressBack()
        selFragment(1)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(1)

        //second
        clickToFragment(2)
        selFragment(2)
        clickToFragment(1)
        selFragment(1)
        clickToFragment(2)
        selFragment(2)
        clickToFragment(3)
        selFragment(3)
        ViewActions.pressBack()
        selFragment(2)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(2)

        //third
        clickToFragment(3)
        selFragment(3)
        clickToFragment(1)
        selFragment(1)
        clickToFragment(2)
        selFragment(2)
        clickToFragment(3)
        selFragment(3)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(3)
    }

    @Test
    fun navigateTest() {
        launchActivity<MainActivity>()
        selFragment(1)
        clickToFragment(2)
        clickToFragment(3)
        clickToFragment(1)
        clickToFragment(2)
        clickToFragment(1)
        clickToFragment(2)
        clickToFragment(3)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(3)
        ViewActions.pressBack()
        selFragment(2)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(2)
        ViewActions.pressBack()
        selFragment(1)
        openAbout()
        selFragment(4)
        ViewActions.pressBack()
        selFragment(1)
        try {
            ViewActions.pressBack()
            assert(value = false)
        } catch (NoActivityResumedException: Exception) {
            assert(value = true)
        }
    }

    @Test
    fun upNavigate() {
        launchActivity<MainActivity>()
        selFragment(1)
        openAbout()
        selFragment(4)
        Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(ViewActions.click())
        selFragment(1)
        clickToFragment(2)
        selFragment(2)
        openAbout()
        selFragment(4)
        Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(ViewActions.click())
        selFragment(2)
        clickToFragment(3)
        selFragment(3)
        openAbout()
        selFragment(4)
        Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(ViewActions.click())
        selFragment(3)
        Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(ViewActions.click())
        selFragment(2)
        Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(ViewActions.click())
        selFragment(1)
        try {
            Espresso.onView(ViewMatchers.withContentDescription(R.string.nav_app_bar_navigate_up_description))
                .perform(ViewActions.click())
            assert(value = false)
        } catch (NoActivityResumedException: Exception) {
            assert(value = true)
        }
    }

    private fun checkOrientationOnFragment(i: Int) {
        selFragment(i)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        Thread.sleep(500)
        selFragment(i)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        Thread.sleep(500)
        selFragment(i)
    }

    @Test
    fun rotateScreen() {
        launchActivity<MainActivity>()
        checkOrientationOnFragment(1)
        clickToFragment(2)
        checkOrientationOnFragment(2)
        clickToFragment(3)
        checkOrientationOnFragment(3)
        openAbout()
        checkOrientationOnFragment(4)
        clickToFragment(1)
        checkOrientationOnFragment(1)
        openAbout()
        checkOrientationOnFragment(4)
        ViewActions.pressBack()
        checkOrientationOnFragment(1)
    }
}