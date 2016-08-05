package com.mavpokit.rxretrofitmvp.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapter.QuestionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 03.08.2016.
 */
public class QuestionsFragment extends Fragment implements IQuestionsView {

    @BindView(R.id.questions_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    private IQuestionsPresenter presenter=new QuestionsPresenter(this);

    private QuestionsAdapter adapter;

    OnShowAnswersListener listener;

    SearchView searchView;
    private String searchViewText="";

    private static String LOGTAG="------------------";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(OnShowAnswersListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        Log.d(LOGTAG,"Fragment onCreate "+this.hashCode());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_question_fragment, container, false);
        setHasOptionsMenu(true);

        ButterKnife.bind(this,view);

        initList();

        presenter.onCreateView(savedInstanceState);

        Log.d(LOGTAG,"Fragment onCreateView");

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(LOGTAG,"Fragment onSaveInstanceState");
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void showQuestionList(ListQuestion questionList) {
        adapter.setListQuestion(questionList);
        emptyTextView.setVisibility(View.GONE);

    }

    @Override
    public void showNoAnswer() {

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(),errorMessage,Toast.LENGTH_LONG).show();
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
    public void openLink(Uri link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, link);
        if (intent.resolveActivity(getContext().getPackageManager())!=null)
            startActivity(Intent.createChooser(intent,"Choose"));

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOGTAG,"Fragment onPause");
    }

    @Override
    public void openAnswers(Question question) {
        listener.openAnswersFragment(question);
    }

    private void initList() {
        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new QuestionsAdapter(presenter);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionsmenu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setQuery(searchViewText, false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearchClick(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        MenuItem itemOptions = menu.findItem(R.id.refresh);
        itemOptions.setOnMenuItemClickListener((menuitem) -> {
                    //BusProvider.getInstance().post(new Message(Message.SWIPED, null));
                    Toast.makeText(getContext(),"refreshed",Toast.LENGTH_SHORT).show();
                    return true;
                }
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG,"Fragment onDestroy");
    }

    public interface OnShowAnswersListener{
        void openAnswersFragment(Question question);
    }
}
