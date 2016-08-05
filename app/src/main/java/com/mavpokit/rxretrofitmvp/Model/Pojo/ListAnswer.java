package com.mavpokit.rxretrofitmvp.Model.Pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 01.08.2016.
 */
public class ListAnswer implements Serializable {
    List<Answer> items=new ArrayList<>();

    public void setItems(List<Answer> items) {
        this.items = items;
    }

    public List<Answer> getItems() {

        return items;
    }
}
