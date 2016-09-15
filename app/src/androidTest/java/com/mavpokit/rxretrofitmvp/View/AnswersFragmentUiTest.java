package com.mavpokit.rxretrofitmvp.View;

import android.support.test.rule.ActivityTestRule;
import android.text.Html;

import com.mavpokit.rxretrofitmvp.DI.Consts;
import com.mavpokit.rxretrofitmvp.DI.JsonReader;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.DI.UiTestComponent;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mavpokit.rxretrofitmvp.View.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * Created by Alex on 13.09.2016.
 */

public class AnswersFragmentUiTest {
    protected JsonReader jsonReader = new JsonReader();
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
    public void show0answers() {
        apiConfig.setListAnswersResultEmpty();
        Question question=new Question(Consts.LINK,Consts.TITLE,0,Consts.QUESTION_ID);
        question.setBody(Consts.BODY);
        mActivityTestRule.getActivity().openAnswersFragment(question);

        onView(allOf(withId(R.id.textViewQuestionTitle),withText(Consts.TITLE))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionBody),withText(Consts.BODY))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionLink),withText(Consts.LINK))).check(matches(isDisplayed()));
        onView(withId(R.id.answers_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textView0answers)).check(matches(isDisplayed()));
        onView(withId(R.id.answersProgressBar)).check(matches(not((isDisplayed()))));
//
//        for (int i = 0; i < mListQuestion.getItems().size(); i++) {
//            String expectedTitle = mListQuestion.getItems().get(i).getTitle();
//            onView(withId(R.id.questions_recycler_view)).perform(scrollToPosition(i));
//            onView(withRecyclerView(R.id.questions_recycler_view).atPositionOnView(i,R.id.textViewTitle))
//                    .check(matches(withText(containsString(expectedTitle))));
//        }
//
//        onView(withId(R.id.questions_recycler_view)).perform(scrollToPosition(0));
//        onView(withRecyclerView(R.id.questions_recycler_view).atPositionOnView(0,R.id.textViewAnswers))
//                .perform(click());
//        onView(withId(R.id.textViewQuestionTitle)).check(matches(isDisplayed()));

    }

    @Test
    public void show4answersTest() {

        apiConfig.setListAnswersResultOk();

        Question question=new Question(Consts.LINK,Consts.TITLE,4,Consts.QUESTION_ID);
        question.setBody(Consts.BODY);
        mActivityTestRule.getActivity().openAnswersFragment(question);

        onView(allOf(withId(R.id.textViewQuestionTitle),withText(Consts.TITLE))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionBody),withText(Consts.BODY))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionLink),withText(Consts.LINK))).check(matches(isDisplayed()));
        onView(withId(R.id.answers_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.textView0answers)).check(matches(not(isDisplayed())));
        onView(withId(R.id.answersProgressBar)).check(matches(not((isDisplayed()))));

        for (int i = 0; i < mListAnswer.getItems().size(); i++) {
            String expectedBody = Html.fromHtml(mListAnswer.getItems().get(i).getBody()).toString();
            onView(withId(R.id.answers_recycler_view)).perform(scrollToPosition(i));
            onView(withRecyclerView(R.id.answers_recycler_view).atPositionOnView(i,R.id.answerItem_textViewAnswer))
                    .check(matches(withText(containsString(expectedBody))));
        }
    }

    @Test
    public void showErrorTest() {

        apiConfig.setListAnswersResultError();
        Question question=new Question(Consts.LINK,Consts.TITLE,4,Consts.QUESTION_ID);
        question.setBody(Consts.BODY);
        mActivityTestRule.getActivity().openAnswersFragment(question);

        onView(allOf(withId(R.id.textViewQuestionTitle),withText(Consts.TITLE))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionBody),withText(Consts.BODY))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.textViewQuestionLink),withText(Consts.LINK))).check(matches(isDisplayed()));
        onView(withId(R.id.answers_recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.textView0answers)).check(matches(isDisplayed()));
        onView(withId(R.id.answersProgressBar)).check(matches(not((isDisplayed()))));

        onView(withText(Consts.HTTP_404_CLIENT_ERROR))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }





}