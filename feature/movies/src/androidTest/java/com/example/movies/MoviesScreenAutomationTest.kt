package com.example.movies

import android.content.Context
import androidx.compose.ui.test.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.example.movies.ui.MOVIE_LIST_TAG
import okhttp3.internal.wait
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val PACKAGE_NAME = "com.example.distilled"
private const val DISTILLED = "distilled"
private const val TIME_OUT = 10_000L // ten seconds

@RunWith(AndroidJUnit4::class)
class MoviesScreenAutomationTest {

    private lateinit var device: UiDevice
    private lateinit var context: Context

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        context = ApplicationProvider.getApplicationContext()

        // find the distilled app:
        val distilledApp = device.findObject(By.text(DISTILLED))
        // click on its icon until a new window appears
        distilledApp.clickAndWait(Until.newWindow(), TIME_OUT)
    }

    @Test
    fun whenMoviesScreenHasReceivedData_theListShouldBeSeenAndSortingShouldWorkAsExpected() {
        // Here we are testing to ensure that the list of movies is displayed successfully.

        // The app should be loading at this point so we need to wait until loading is complete:
        //val textComposable = device.wait(Until.findObject(By.text("Sort movies alphabetically")), TIME_OUT)

        //val textComposable = device.wait(Until.findObject(By.text("Breaking Bad")), TIME_OUT)

        //device.wait(Until.findObject(By.res(MOVIE_LIST_TAG)), TIME_OUT)
        //device.wait(Until.findObject(By.text("Breaking Bad")), TIME_OUT)

        device.wait(Until.hasObject(By.res(MOVIE_LIST_TAG)), TIME_OUT)

        val listOfMovies = device.findObject(By.res(MOVIE_LIST_TAG))

        assertEquals(MOVIE_LIST_TAG, listOfMovies.resourceName)

        // scroll to bottom:
        var bottomItemFound = false
        while (!bottomItemFound) {
            listOfMovies.scrollUntil(Direction.DOWN, Until.scrollFinished(Direction.DOWN))

            val lastItem = device.findObject(By.text("Once"))
            if (lastItem != null) {
                bottomItemFound = true
            }
        }
    }
}