package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 18.08.2016.
 */
public class QuestionsPresenterTest extends BaseTest {

    private static final String LINK="https://github.com";
    private static final String QUERY="java";
    private static final String ERROR_MESSAGE="ERROR_MESSAGE";
    private static final String[] SUGGESTIONS={"A","B","C"};
    private static final List<String> LIST_SUGGESTIONS= Arrays.asList(SUGGESTIONS);

    Question question;
    ArrayList<Question> questionsList=new ArrayList<>();
    private ListQuestion mListQuestion =new ListQuestion();

    @Mock
    private IQuestionsView view;

    IQuestionsPresenter presenter=new QuestionsPresenter();

    @Inject
    IModel model;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);

        question=new Question(LINK,"title",1,"0");
        questionsList.add(question);
        mListQuestion.setItems(questionsList);

        //presenter=new QuestionsPresenter();
        when(model.loadSuggestions()).thenReturn(Observable.just(LIST_SUGGESTIONS));
        presenter.onCreate(view,null);

    }

    @Test
    public void testOnCreate() throws Exception {
        verify(view).initSuggestions(LIST_SUGGESTIONS);
    }

    @Test
    public void testShowAnswers() throws Exception {
        presenter.setListQuestion(mListQuestion);
        final int INDEX=0;
        presenter.showAnswers(INDEX);
        verify(view).openAnswers(mListQuestion.getItems().get(INDEX));
    }

    @Test
    public void testOpenLink() throws Exception {
        presenter.setListQuestion(mListQuestion);
        final int INDEX=0;
        presenter.openLink(INDEX);

        Uri uri=Uri.parse(LINK);
        verify(view).openLink(uri);
    }

    @Test
    public void testOnCreateView() throws Exception{
        presenter.setListQuestion(mListQuestion);
        presenter.onCreateView();
        if (isListNotEmpty(mListQuestion))
            verify(view).showQuestionList(mListQuestion);
    }

    private boolean isListNotEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

    @Test
    public void testOnSearchClick1result() throws Exception{
        presenter.setListQuestion(null);
        when(model.getQuestionList(QUERY)).thenReturn(Observable.just(mListQuestion));
        presenter.onSearchClick(QUERY);
        verify(view).showSpinner();
        verify(view).showQuestionList(mListQuestion);
        verify(view).hideSpinner();
    }

    @Test
    public void testOnSearchClick0results() throws Exception{
        presenter.setListQuestion(mListQuestion);
        when(model.getQuestionList(QUERY)).thenReturn(Observable.just(null));
        presenter.onSearchClick(QUERY);
        verify(view).showSpinner();
        verify(view).showNothing();
        verify(view).hideSpinner();
        assertNull(presenter.getListQuestion());
    }
    @Test
    public void testOnSearchClickError() throws Exception{
        presenter.setListQuestion(null);
        when(model.getQuestionList(QUERY)).thenReturn(Observable.error(new Throwable(ERROR_MESSAGE)));
        presenter.onSearchClick(QUERY);
        verify(view).showSpinner();
        verify(view).showError(ERROR_MESSAGE);
        verify(view).hideSpinner();
    }
}