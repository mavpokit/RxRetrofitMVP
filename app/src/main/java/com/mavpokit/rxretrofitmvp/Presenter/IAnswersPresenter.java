package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

/**
 * Created by Alex on 04.08.2016.
 */
public interface IAnswersPresenter {
    public void onCreate(Bundle savedInstanceState);
    public void onCreateView(Bundle savedInstanceState);
    void onSaveInstanceState(Bundle outState);
    void onStop();
    public void openLink();
}
