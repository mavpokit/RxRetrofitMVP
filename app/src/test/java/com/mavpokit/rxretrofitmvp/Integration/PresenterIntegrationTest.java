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

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 05.09.2016.
 */
public class PresenterIntegrationTest extends BaseIntegrationTest {

    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    Question question = new Question(Consts.LINK, "title", 1, Consts.QUESTION_ID);
    private ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS_FILE);

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
//        presenter.setListQuestion(mListQuestion);
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

        Uri uri = Uri.parse("/");
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
    public void testOnSearchClick1result() throws Exception {
        presenter.setListQuestion(null);
        //when(model.getQuestionList(QUERY)).thenReturn(Observable.just(mListQuestion));

        presenter.onSearchClick(Consts.QUERY);

        verify(view).showSpinner();
        verify(view).showQuestionList(mListQuestion);
        verify(view).hideSpinner();
    }

    @Test
    public void testOnSearchClick0results() throws Exception {
        presenter.setListQuestion(null);
        //when(model.getQuestionList(QUERY)).thenReturn(Observable.just(null));
        presenter.onSearchClick(Consts.QUERY);
        verify(view).showSpinner();
        verify(view).showNothing();
        verify(view).hideSpinner();
    }

    @Test
    public void testOnSearchClickError() throws Exception {
        presenter.setListQuestion(null);
        //when(model.getQuestionList(QUERY)).thenReturn(Observable.error(new Throwable(ERROR_MESSAGE)));
        presenter.onSearchClick(Consts.QUERY);
        verify(view).showSpinner();
        verify(view).showError(ERROR_MESSAGE);
        verify(view).hideSpinner();
    }
}
