package com.example.colorsmash;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import android.view.View;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch()
    {
        View view = mainActivity.findViewById(R.id.ButtonStartGame);

        assertNotNull(view);

    }

    @Test
    public void testLaunchOfLoginActivityOnButtonClick()
    {
        assertNotNull(mainActivity.findViewById(R.id.ButtonLogin));

        onView(withId(R.id.ButtonLogin)).perform(click()); // mock a click on a button

        Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(LoginActivity);

        LoginActivity.finish();

    }

    @After
    public void tearDown() throws Exception {

        mainActivity = null;
    }
}