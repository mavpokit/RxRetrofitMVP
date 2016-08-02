package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

/**
 * Created by Alex on 27.07.2016.
 */
public interface IPresenter {
    void onSearchClick(String query);
    void onStop();
    void onCreate(Bundle savedInstanceState);
    void onSaveInstanceState(Bundle outState);
}
