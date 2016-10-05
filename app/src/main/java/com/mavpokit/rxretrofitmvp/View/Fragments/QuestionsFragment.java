package com.mavpokit.rxretrofitmvp.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapters.QuestionsAdapter;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import java.util.List;

import javax.inject.Inject;

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

    //private IQuestionsPresenter presenter=new QuestionsPresenter(this); before DI
    @Inject
    IQuestionsPresenter presenter;

    private QuestionsAdapter adapter;

    OnShowAnswersListener listener;

    SearchView searchView;
    private String searchViewText="";

    private static String LOGTAG="------------------";

    private SimpleCursorAdapter mAdapter;

    List<String> suggestionsList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(OnShowAnswersListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyApplication.getAppComponent().inject(this);
        presenter.onCreate(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        Log.d(LOGTAG,"Question Fragment onCreate "+this.hashCode());
        Log.d(LOGTAG,"Presenter "+presenter.hashCode());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_question_fragment, container, false);
        setHasOptionsMenu(true);

        ButterKnife.bind(this,view);

        initList();

        presenter.onCreateView();

        Log.d(LOGTAG,"Fragment onCreateView");

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(LOGTAG,"Fragment onSaveInstanceState");
        super.onSaveInstanceState(outState);
        //presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void showQuestionList(ListQuestion questionList) {
        adapter.setListQuestion(questionList);
        emptyTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
//        Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        Animation animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        Animation animation = new AlphaAnimation(0,1);
        animation.setDuration(1000);

        mRecyclerView.startAnimation(animation);


    }

    @Override
    public void showNothing() {
        mRecyclerView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        Toast.makeText(this.getActivity(),R.string.no_results_toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMessage) {
        mRecyclerView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(),"Error making network request "+errorMessage,Toast.LENGTH_LONG).show();


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
        Log.d(LOGTAG,"Question Fragment onPause");
    }

    @Override
    public void openAnswers(Question question)
    {
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

        setupSearchView(item);

        MenuItem itemOptions = menu.findItem(R.id.refresh);
        itemOptions.setOnMenuItemClickListener((menuitem) -> {
                    //BusProvider.getInstance().post(new Message(Message.SWIPED, null));
                    Toast.makeText(getContext(),"refreshed",Toast.LENGTH_SHORT).show();
                    return true;
                }
        );

    }

    void setupSearchView(MenuItem item) {
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setQuery(searchViewText, false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);// -----here we run search--------
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //show search suggestion
        if (mAdapter!=null)
            searchView.setSuggestionsAdapter(mAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                //select suggestion directly from view, without V-P-M-P-V chain
                searchView.setQuery(suggestionsList.get(position),false);
                //presenter.onSuggestionClick(position);
                return true;
            }
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
    }

    //moved from onCreateOptionsMenu method to separate method
    // wih purpose for run search in integration tests
    public void search(String query) {
        presenter.onSearchClick(query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG,"Question Fragment onDestroy");
        presenter.onDestroy();


    }

    public interface OnShowAnswersListener{
        void openAnswersFragment(Question question);
    }

    @Override
    public void initSuggestions(List<String> suggestions) {
        suggestionsList=suggestions;
        final String[] from = new String[] {"query_text"};
        final int[] to = new int[] {android.R.id.text1};
        MatrixCursor matrixCursor= new MatrixCursor(new String[]{ BaseColumns._ID, "query_text" });
        for (int i = 0; i <suggestions.size() ; i++)
            matrixCursor.addRow(new Object[]{i,suggestions.get(i)});

        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                matrixCursor,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        if (mAdapter!=null && searchView!=null)
            searchView.setSuggestionsAdapter(mAdapter);

    }

    @Override
    public void selectSuggestion(String suggestion) {
        searchView.setQuery(suggestion,false);
    }

}
