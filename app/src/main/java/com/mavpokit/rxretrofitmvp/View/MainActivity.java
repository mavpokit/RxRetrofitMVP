package com.mavpokit.rxretrofitmvp.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mavpokit.rxretrofitmvp.R;

public class MainActivity extends AppCompatActivity{

    private static String LOGTAG="***************";
    private static String FRAGMENT_TAG="FRAGMENT_TAG";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(FRAGMENT_TAG)==null)
            replaceFragment(new QuestionsFragment(),false);

        Log.d(LOGTAG,"Activity onCreate "+this.hashCode());

    }

    private void replaceFragment(Fragment fragment,boolean flag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, FRAGMENT_TAG);
        if (flag) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOGTAG,"Activity onDestroy");

    }
}
