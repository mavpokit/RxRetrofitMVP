package com.mavpokit.rxretrofitmvp.View;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mavpokit.rxretrofitmvp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
//        ViewInteraction searchAutoComplete = onView(
//                allOf(withId(R.id.search_src_text),
//                        withParent(allOf(withId(R.id.search_plate),
//                                withParent(withId(R.id.search_edit_frame)))),
//                        isDisplayed()));
//        searchAutoComplete.perform(replaceText("java"), closeSoftKeyboard());

        onView(withId(R.id.search_src_text))
                .perform(replaceText("java"))
                .perform(pressImeActionButton());

//        ViewInteraction searchAutoComplete2 = onView(
//                allOf(withId(R.id.search_src_text), withText("java"),
//                        withParent(allOf(withId(R.id.search_plate),
//                                withParent(withId(R.id.search_edit_frame)))),
//                        isDisplayed()));
//        searchAutoComplete2.perform(pressImeActionButton());
//
        ViewInteraction textView = onView(
                allOf(withId(R.id.textViewTitle), withText("1. Problems with Changing JTextArea font size"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_view),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("1. Problems with Changing JTextArea font size")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
