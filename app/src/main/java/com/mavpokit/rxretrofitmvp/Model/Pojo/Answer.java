package com.mavpokit.rxretrofitmvp.Model.Pojo;

import java.io.Serializable;

/**
 * Created by Alex on 01.08.2016.
 */
public class Answer implements Serializable {
    int answer_id;
    boolean is_accepted;
    String body;

    public Answer() {
    }

    public Answer(int answer_id, boolean is_accepted, String body) {
        this.answer_id = answer_id;
        this.is_accepted = is_accepted;
        this.body = body;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public boolean is_accepted() {
        return is_accepted;
    }

    public String getBody() {
        return body;
    }

    public boolean equals(Object o) {
        Answer equal = (Answer)o;
        if (this.body != null && equal.body != null && !this.body.equals(equal.body)) {
            return false;
        }
        if (this.answer_id != equal.answer_id) {
            return false;
        }
        if (this.is_accepted != equal.is_accepted) {
            return false;
        }
        return true;
    }

}

