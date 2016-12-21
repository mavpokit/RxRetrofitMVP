package com.mavpokit.rxretrofitmvp.View;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Fragments.AnswersFragment;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragment;

public class MainActivity extends AppCompatActivity implements
        QuestionsFragment.OnShowAnswersListener
{

    private static String LOGTAG = "***************";

    private static String QUESTION_TAG = "QUESTION_TAG";
    private static String ANSWER_TAG = "ANSWER_TAG";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.soicon);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(QUESTION_TAG) == null)
            replaceFragment(new QuestionsFragment(), QUESTION_TAG, false);

        //display or hide UP button when navigating to other fragment
        fragmentManager.addOnBackStackChangedListener(() -> displayUp());
        //display or hide UP button when config changes (screen rotate)
        displayUp();

        Log.d(LOGTAG, "Activity onCreate " + this.hashCode());
    }

    /**
     * displays UP navigation button in ActionBar if we are not in the top of backstack
     * and hides if on the top
     */
    private void displayUp() {
        int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (stackEntryCount>0) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Log.d(LOGTAG, "Back stack: " + stackEntryCount);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG, "Activity onDestroy");
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Pop back stack when the UP button is pressed
        getSupportFragmentManager().popBackStack();
        return false;
    }




}


