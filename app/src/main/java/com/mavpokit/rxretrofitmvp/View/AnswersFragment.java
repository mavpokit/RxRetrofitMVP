package com.mavpokit.rxretrofitmvp.View;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapter.AnswersAdapter;
import com.mavpokit.rxretrofitmvp.View.Adapter.QuestionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 04.08.2016.
 */
public class AnswersFragment extends Fragment implements IAnswersView {
    private static final String TAG = "TAG";
    @BindView(R.id.textViewQuestionLink)    TextView textViewQuestionLink;
    @BindView(R.id.textViewQuestionTitle)   TextView textViewQuestionTitle;
    @BindView(R.id.textViewQuestionBody)    TextView textViewQuestionBody;
    @BindView(R.id.answers_recycler_view)   RecyclerView mRecyclerView;

    private Question question;

    private IAnswersPresenter presenter;
    private AnswersAdapter adapter;


    public static AnswersFragment getInstance(Question question){
        AnswersFragment fragment = new AnswersFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAG, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_answer_fragment, container, false);
        ButterKnife.bind(this,view);
        question=(Question) getArguments().getSerializable(TAG);

        presenter = new AnswersPresenter(this,question);

        initAnswersList();

        presenter.onCreate(savedInstanceState);

        //Log.d(LOGTAG,"Fragment onCreateView");

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    private void initAnswersList() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AnswersAdapter(presenter);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showAnswerList(ListAnswer answerList) {

    }

    @Override
    public void showQuestion(Question question) {
        textViewQuestionLink.setText(question.getLink());
        textViewQuestionTitle.setText(question.getTitle());
        textViewQuestionBody.setText(question.getBody());

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showSpinner() {

    }

    @Override
    public void hideSpinner() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }
}
