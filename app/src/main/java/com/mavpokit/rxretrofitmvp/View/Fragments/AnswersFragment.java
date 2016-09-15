package com.mavpokit.rxretrofitmvp.View.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapters.AnswersAdapter;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 04.08.2016.
 */
public class AnswersFragment extends Fragment implements IAnswersView {
    private static final String TAG = "TAG";
    private static final String LOGTAG = "//////////////";
    @BindView(R.id.textViewQuestionLink)
    TextView textViewQuestionLink;
    @BindView(R.id.textViewQuestionTitle)
    TextView textViewQuestionTitle;
    @BindView(R.id.textViewQuestionBody)
    TextView textViewQuestionBody;
    @BindView(R.id.answers_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.answersProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.textView0answers)
    TextView textView0answers;


    private Question question;

    @Inject
    IAnswersPresenter presenter;

    private AnswersAdapter adapter;


    public static AnswersFragment getInstance(Question question) {
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
        ButterKnife.bind(this, view);

        initAnswersList();

        presenter.onCreateView();

        Log.d(LOGTAG,"AnswersFragment onCreateView");

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyApplication.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(TAG);
        //presenter = new AnswersPresenter(this,question);//before DI
        presenter.onCreate(this, question/*, savedInstanceState*/);
        Log.d(LOGTAG,"AnswersFragment onCreate");
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
        mRecyclerView.setVisibility(View.VISIBLE);
        adapter.setListAnswer(answerList);
    }

    @Override
    public void showQuestion(Question question) {
        if (question == null) return;
        textViewQuestionLink.setText(question.getLink());
        textViewQuestionTitle.setText(question.getTitle());
        textViewQuestionBody.setText(Html.fromHtml(question.getBody()));
        textViewQuestionLink.setOnClickListener(v -> presenter.openLink());

    }

    @Override
    public void showError(String errorMessage) {
        textView0answers.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        //Log.d("////////////////","|||||||||||||||||");
    }

    @Override
    public void showSpinner() {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSpinner() {
        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNothing() {
        textView0answers.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void openLink(Uri link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        if (intent.resolveActivity(getContext().getPackageManager()) != null)
            startActivity(Intent.createChooser(intent, "Choose"));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }


}
