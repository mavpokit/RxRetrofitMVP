package com.mavpokit.rxretrofitmvp.DI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Alex on 04.10.2016.
 */

@Module
public class RealmProvider {
    @Provides
    @Singleton
    Realm provideRealm(){return Realm.getDefaultInstance();}
}
