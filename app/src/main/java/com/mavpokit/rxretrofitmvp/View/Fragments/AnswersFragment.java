package com.mavpokit.rxretrofitmvp.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapters.AnswersAdapter;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;
import com.mavpokit.rxretrofitmvp.View.Util.HeightAnimation;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.questionBodyScrollView)
    ScrollView questionBodyScrollView;
    @BindView(R.id.answers_fragment_container)
    LinearLayout answers_fragment_container;



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

        animateAppearance();

        initAnswersList();

        presenter.onCreateView();

        Log.d(LOGTAG,"AnswersFragment onCreateView");

        return view;
    }

    private void animateAppearance() {
//        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(1000);
        textViewQuestionBody.startAnimation(animation);

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
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG,"AnswersFragment onDestroy");
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

    @OnClick(R.id.textViewQuestionBody)
    public void onTextViewQuestionBodyClick(View v){
        presenter.textViewQuestionBodyClick();
    }

    @Override
    public void setAnswerBodySize(int answerBodySize) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int target_height_px=height/4;
        int start_height_px=height/2;

        if (answerBodySize==2) {
            target_height_px=height/2;
            start_height_px=height/4;
        }

        HeightAnimation resizeAnimation = new HeightAnimation(questionBodyScrollView,start_height_px,target_height_px);
        resizeAnimation.setDuration(500);
        questionBodyScrollView.startAnimation(resizeAnimation);

//        old version without animation
//        questionBodyScrollView.setLayoutParams(
//                new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT, target_height_px)
//        );

        //Log.d("****************   ",String.valueOf(target_height_px));

    }



}
