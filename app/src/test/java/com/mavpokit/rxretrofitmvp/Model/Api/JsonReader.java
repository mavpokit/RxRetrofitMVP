package com.mavpokit.rxretrofitmvp.Model.Api;

import com.google.gson.Gson;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class JsonReader {
    public String read(String filename) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        try {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return sb.toString();
    }

    public ListQuestion getListQuestion(String filename) {
        ListQuestion listQuestion = (ListQuestion)new Gson().fromJson(this.read(filename), (Class)ListQuestion.class);
        return listQuestion;
    }

    public ListAnswer getListAnswer(String filename) {
        ListAnswer listAnswer = (ListAnswer)new Gson().fromJson(this.read(filename), (Class)ListAnswer.class);
        return listAnswer;
    }
}