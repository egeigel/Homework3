package com.example.edward.triviaapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button backButton;
    TextView welcomeView;
    ImageView triviaImage;
    TextView readyView;
    ArrayList<Question> questions=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        triviaImage = (ImageView) findViewById(R.id.triviaImage);
        readyView = (TextView) findViewById(R.id.readyView);
        triviaImage.setEnabled(false);
        readyView.setEnabled(false);

        if (isConnected()){
            AsyncTask asyncTask = new GetContentAsync().execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
            try {
                questions = (ArrayList<Question>)asyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }


    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

}




