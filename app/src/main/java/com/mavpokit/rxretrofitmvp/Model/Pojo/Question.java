package com.mavpokit.rxretrofitmvp.Model.Pojo;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Alex on 27.07.2016.
 */
public class Question implements Serializable{
    String link;
    String title;
    int answer_count;
    long question_id;

    public Question() {
    }

    public Question(String link, String title, int answer_count, long question_id) {
        this.link = link;
        this.title = title;
        this.answer_count = answer_count;
        this.question_id = question_id;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }
}
