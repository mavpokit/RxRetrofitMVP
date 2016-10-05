package com.mavpokit.rxretrofitmvp.Model.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Alex on 04.10.2016.
 */

public class RealmSuggestionList extends RealmObject {
    RealmList<RealmString> list;

    public RealmList<RealmString> getList() {
        return list;
    }
}
