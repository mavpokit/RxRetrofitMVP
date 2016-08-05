package com.mavpokit.rxretrofitmvp.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.R;

public class MainActivity extends AppCompatActivity implements QuestionsFragment.OnShowAnswersListener {

    private static String LOGTAG = "***************";
    private static String QUESTION_TAG = "QUESTION_TAG";
    private static String ANSWER_TAG = "ANSWER_TAG";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(QUESTION_TAG) == null)
            replaceFragment(new QuestionsFragment(), QUESTION_TAG, false);

        Log.d(LOGTAG,"Activity onCreate "+this.hashCode());

    }

    private void replaceFragment(Fragment fragment, String FRAGMENT_TAG, boolean addToBackStackFlag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, FRAGMENT_TAG);
        if (addToBackStackFlag) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void openAnswersFragment(Question question) {
        replaceFragment(AnswersFragment.getInstance(question), ANSWER_TAG, true);
    }
}


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(LOGTAG,"Activity onDestroy");
//
//    }
