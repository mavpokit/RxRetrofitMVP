package com.mavpokit.rxretrofitmvp.Integration;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockWebServer;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 05.09.2016.
 */
public class QuestionsPresenterIntegrationTest extends BaseIntegrationTest {

    @Mock
    private IQuestionsView view;

    @Inject
    IQuestionsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);

        presenter.onCreate(view, null);
        presenter.setListQuestion(mListQuestion);
    }

    @Test
    public void testShowAnswers() throws Exception {
        final int INDEX = 0;
        presenter.showAnswers(INDEX);
        verify(view).openAnswers(mListQuestion.getItems().get(INDEX));
    }

    @Test
    public void testOpenLink() throws Exception {
        final int INDEX = 0;
        presenter.openLink(INDEX);

        Uri uri = Uri.parse(mListQuestion.getItems().get(INDEX).getLink());
        verify(view).openLink(uri);
    }

    @Test
    public void testOnCreateView() throws Exception {
        presenter.onCreateView();
        if (isListNotEmpty(mListQuestion))
            verify(view).showQuestionList(mListQuestion);
    }

    private boolean isListNotEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

    @Test
    public void testOnSearchClickResultsOk() throws Exception {
        presenter.onSearchClick(Consts.QUERY);

        verify(view).showSpinner();
        verify(view).showQuestionList(any());
        verify(view).hideSpinner();

        ListQuestion actualListQuestion = presenter.getListQuestion();
        for (int i = 0; i < actualListQuestion.getItems().size(); ++i) {
            Question actualQustion = actualListQuestion.getItems().get(i);
            Question expected = mListQuestion.getItems().get(i);
            assertEquals(actualQustion, expected);
        }
    }

    @Test
    public void testOnSearchClick0Results() throws Exception {
        presenter.onSearchClick(Consts.QUERY_NULL);
        verify(view).showSpinner();
        verify(view).showNothing();
        verify(view).hideSpinner();
    }

    @Test
    public void testOnSearchClickError() throws Exception {
        presenter.onSearchClick(Consts.QUERY_ERROR);
        verify(view).showSpinner();
        verify(view).showError(Consts.HTTP_404_CLIENT_ERROR);
        verify(view).hideSpinner();
    }
}
