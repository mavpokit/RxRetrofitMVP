package com.mavpokit.rxretrofitmvp.Model.Pojo;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Alex on 27.07.2016.
 */
public class Question implements Serializable{
    String link;
    String title;
    String body;


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

    public int getAnswer_count() {
        return answer_count;
    }

    public String getBody() {return body;}

    public long getQuestion_id() {
        return question_id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    @Override
    public String toString() {
        return title+"\n"+link;
    }
    public boolean equals(Object o) {
        Question equal = (Question)o;
        if (!this.link.equals(equal.link)) {
            return false;
        }
        if (!this.title.equals(equal.title)) {
            return false;
        }
        if (this.body != null && equal.body != null && !this.body.equals(equal.body)) {
            return false;
        }
        if (this.answer_count != equal.answer_count) {
            return false;
        }
        if (this.question_id != equal.question_id) {
            return false;
        }
        return true;
    }

}
