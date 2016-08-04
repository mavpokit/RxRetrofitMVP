package com.mavpokit.rxretrofitmvp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.mavpokit.rxretrofitmvp.Presenter.IPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.MainActivityPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Adapter.MyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 03.08.2016.
 */
public class QuestionListFragment extends Fragment implements IView {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    private IPresenter presenter;
    private MyAdapter adapter;

    SearchView searchView;
    private String searchViewText="";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_question_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,view);

        presenter = new MainActivityPresenter(this);

        initList();

        presenter.onCreate(savedInstanceState);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

    private void initList() {
        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter();
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
}
