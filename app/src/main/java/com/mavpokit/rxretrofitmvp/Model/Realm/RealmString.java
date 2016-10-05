package com.mavpokit.rxretrofitmvp.Model.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alex on 04.10.2016.
 */

public class RealmString extends RealmObject {
    @PrimaryKey
    String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
