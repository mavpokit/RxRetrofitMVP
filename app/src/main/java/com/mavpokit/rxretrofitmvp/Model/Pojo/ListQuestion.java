package com.mavpokit.rxretrofitmvp.Model.Pojo;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 27.07.2016.
 */
public class ListQuestion implements Serializable{
    List<Question> items=new ArrayList<>();

    public ListQuestion() {
    }

    public List<Question> getItems() {
        return items;
    }

    public void setItems(List<Question> items) {
        this.items = items;
    }

}
