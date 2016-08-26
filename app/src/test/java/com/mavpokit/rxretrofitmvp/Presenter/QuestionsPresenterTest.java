package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.DI.AppTestComponent;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.DI.MyTestApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

/**
 * Created by Alex on 18.08.2016.
 */
public class QuestionsPresenterTest extends BaseTest{

    private static final String LINK="https://github.com";
    Question question;
    ArrayList<Question> questionsList=new ArrayList<>();
    private ListQuestion listQuestion=new ListQuestion();

    @Mock
    private IQuestionsView view;

    @Inject
    IQuestionsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);

        question=new Question(LINK,"title",1,0);
        questionsList.add(question);
        listQuestion.setItems(questionsList);

        //presenter=new QuestionsPresenter();
        presenter.onCreate(view,null);
        presenter.setListQuestion(listQuestion);
    }

    @Test
    public void testShowAnswers() throws Exception {
        final int INDEX=0;
        presenter.showAnswers(INDEX);
        verify(view).openAnswers(listQuestion.getItems().get(INDEX));
    }

    @Test
    public void testOpenLink() throws Exception {
        final int INDEX=0;
        presenter.openLink(INDEX);

        Uri uri=Uri.parse(LINK);
        verify(view).openLink(uri);
    }

    @Test
    public void testOnCreateView() throws Exception{
        presenter.onCreateView();
        if (isListNotEmpty(listQuestion))
            verify(view).showQuestionList(listQuestion);
    }

    private boolean isListNotEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

    @Test
    public void testOnSearchClick() throws Exception{

    }
}