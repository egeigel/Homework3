package com.example.edward.triviaapp;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edward on 10/3/17.
 */

public class GetContentAsync extends AsyncTask<String , Integer , ArrayList<Question>> {
    String[] allQuestions;
    String result;

    @Override
    protected ArrayList<Question> doInBackground(String... params) {ArrayList<Question> questions=new ArrayList<>();
        String buffer;
        int flag=0;
        ArrayList<String> answers= new ArrayList<>();

        HttpURLConnection connection;
        try{
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(connection.getInputStream(), "UTF8");
            }

            allQuestions = result.split("\n");

            for (int i =0 ;i<allQuestions.length; i++){
                ArrayList<String> questionContent = new ArrayList<String>(Arrays.asList(allQuestions[i].split(";")));
                Question question = new Question();
                question.questionNumber = Integer.parseInt(questionContent.get(0));
                question.questionText = questionContent.get(1);
                question.image = questionContent.get(2);
                for (int j=3; flag<1; j++) {
                    buffer = questionContent.get(j);
                    try {
                        int ansBuf = Integer.parseInt(buffer);
                        question.answer = ansBuf;
                        flag++;
                    } catch (NumberFormatException e) {
                        answers.add(buffer);
                    }
                }
                question.answers = answers;

                questions.add(question);
            }



        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
    /*
    @Override
    protected void onPostExecute(String s) {
        ArrayList<Question> questions=new ArrayList<>();
        allQuestions = result.split("\n");
        String buffer;
        int flag=0;
        ArrayList<String> answers= new ArrayList<>();
        for (int i =0 ;i<allQuestions.length; i++){
            ArrayList<String> questionContent = new ArrayList<String>(Arrays.asList(allQuestions[i].split(";")));
            Question question = new Question();
            question.questionNumber = Integer.parseInt(questionContent.get(0));
            question.questionText = questionContent.get(1);
            question.image = questionContent.get(2);
            for (int j=3; flag<1; j++) {
                buffer = questionContent.get(j);
                try {
                    int ansBuf = Integer.parseInt(buffer);
                    question.answer = ansBuf;
                    flag++;
                } catch (NumberFormatException e) {
                    answers.add(buffer);
                }
            }
            question.answers = answers;

            questions.add(question);
        }

        getQuestions(questions);
    }*/

    protected ArrayList<Question> getQuestions(ArrayList<Question> questions){

        return questions;
    }
}
