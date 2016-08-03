package com.mavpokit.rxretrofitmvp.Model.Pojo;

/**
 * Created by Alex on 01.08.2016.
 */
public class Answer {
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
}

