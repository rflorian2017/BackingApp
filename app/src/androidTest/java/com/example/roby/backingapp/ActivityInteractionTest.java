package com.example.roby.backingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.allOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInteractionTest {
    public static final String RECIPE_NAME = "Cheesecake";
    public static final String RECIPE_STEP = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testContainsRecipe() {
        onView(withId(R.id.rv_recipes))
                .check(matches(hasDescendant(withText(RECIPE_NAME))));
    }

    @Test
    public void testCheescakeRecipe() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RECIPE_NAME)), click()));

        onView(allOf(withId(R.id.step_description_tv), withText(RECIPE_STEP)));
    }


}
