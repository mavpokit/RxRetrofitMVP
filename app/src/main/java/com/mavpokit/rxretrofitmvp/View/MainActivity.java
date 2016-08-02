package com.mavpokit.rxretrofitmvp.View;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.emptyTextView) TextView emptyTextView;

    //private RecyclerView mRecyclerView;
    private String searchViewText="";
    private IPresenter presenter;
    private MyAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(this);

        initList();

        presenter.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
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
                    Toast.makeText(MainActivity.this,"refreshed",Toast.LENGTH_SHORT).show();
                    return true;
                }
        );

        return true;
    }

    private void initList() {
        //mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);

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
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }
}
