package com.mavpokit.rxretrofitmvp.Util;

import com.google.gson.Gson;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

//for load suggestions in app, for load MockWebServer responces in integration tests

/**
 * class for reading json files from resources
 */
public class JsonReader {
    /**
     * simply reads file from resources
     * @param filename filename with extension, located in resources folder
     * @return string with file content
     */
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

    /**
     * reads json file and converts json to ListQuestion class object
     * @param filename filename with extension, located in resources folder
     * @return ListQuestion class object
     */
    public ListQuestion getListQuestion(String filename) {
        ListQuestion listQuestion = (ListQuestion)new Gson().fromJson(this.read(filename), (Class)ListQuestion.class);
        return listQuestion;
    }

    public ListAnswer getListAnswer(String filename) {
        ListAnswer listAnswer = (ListAnswer)new Gson().fromJson(this.read(filename), (Class)ListAnswer.class);
        return listAnswer;
    }
}