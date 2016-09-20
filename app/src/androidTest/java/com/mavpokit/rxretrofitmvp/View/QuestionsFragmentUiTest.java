package com.mavpokit.rxretrofitmvp.View;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mavpokit.rxretrofitmvp.DI.Consts;
import com.mavpokit.rxretrofitmvp.DI.JsonReader;
import com.mavpokit.rxretrofitmvp.DI.UiTestComponent;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.mavpokit.rxretrofitmvp.View.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;



@LargeTest
@RunWith(AndroidJUnit4.class)
public class QuestionsFragmentUiTest {
    protected JsonReader jsonReader = new JsonReader();
    ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS_FILE);
    ListAnswer mListAnswer = jsonReader.getListAnswer(Consts.JSONANSWERS_FILE);

    @Inject
    ApiConfig apiConfig;

    @Inject
    StackoverflowApiInterface apiInterface;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        ((UiTestComponent) MyApplication.getAppComponent()).inject(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void showQuestionListAndOpenAnswersTest() {

        apiConfig.setListQuestionsResultOk();

        typeQueryText();

        onView(withId(R.id.questions_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.progressBar)).check(matches(not((isDisplayed()))));

        for (int i = 0; i < mListQuestion.getItems().size(); i++) {
            String expectedTitle = mListQuestion.getItems().get(i).getTitle();
            onView(withId(R.id.questions_recycler_view)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.questions_recycler_view).atPositionOnView(i,R.id.textViewTitle))
                    .check(matches(withText(containsString(expectedTitle))));
        }

        onView(withId(R.id.questions_recycler_view)).perform(scrollToPosition(0));
        onView(withRecyclerView(R.id.questions_recycler_view).atPositionOnView(0,R.id.textViewAnswers))
                .perform(click());
        onView(withId(R.id.textViewQuestionTitle)).check(matches(isDisplayed()));

    }

    @Test
    public void showNothingTest() {

        apiConfig.setListQuestionsResultEmpty();

        typeQueryText();

        onView(withId(R.id.questions_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.emptyTextView)).check(matches((isDisplayed())));
        onView(withId(R.id.progressBar)).check(matches(not((isDisplayed()))));

        onView(withText(R.string.no_results_toast))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        //delay for wait until toast dissapears, because it overlaps toast in next test
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    @Test
    public void showErrorTest() {

        apiConfig.setListQuestionsResultError();

        typeQueryText();

        onView(withId(R.id.questions_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.emptyTextView)).check(matches((isDisplayed())));
        onView(withId(R.id.progressBar)).check(matches(not((isDisplayed()))));

        onView(withText(Consts.HTTP_404_CLIENT_ERROR))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

        //delay for wait until toast dissapears, because it overlaps toast in next test
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void typeQueryText() {
        onView(withId(R.id.action_search)).perform(click());
        onView(allOf(withId(R.id.search_src_text),
                withParent(withId(R.id.search_plate))))
                .perform(replaceText("java"))
                .perform(pressImeActionButton(), closeSoftKeyboard());
    }




}