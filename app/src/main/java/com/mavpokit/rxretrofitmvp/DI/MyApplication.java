package com.mavpokit.rxretrofitmvp.DI;

import android.app.Application;

import com.mavpokit.rxretrofitmvp.Model.Realm.RealmString;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Alex on 12.08.2016.
 */
public class MyApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //Init Dagger
        appComponent=buildComponent();

        initRealm();


    }

    void initRealm() {
        //Init Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .initialData(createInitTransaction())//instead of deleteRealm + createTestData
                .build();
        Realm.setDefaultConfiguration(config);
    }


    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder().build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private Realm.Transaction createInitTransaction() {
        return realm -> {
            String[] suggestions = {"java","android","realm"};
            for (String value:suggestions)
                realm.copyToRealm(new RealmString(value));
        };
    }


}
