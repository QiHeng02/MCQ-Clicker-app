package com.example.clicker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:9999/clicker/select?choice=";
    private static final String QUESTION_URL = "http://10.0.2.2:9999/clicker/getQuestions"; // URL for getting the questions

    private TextView usernameTextView;
    private TextView questionTextView; // TextView for displaying the question
    private Button btnA, btnB, btnC, btnD;
    private Button btnLogout;
    private FrameLayout feedbackContainer;
    private Handler handler = new Handler(); // for delay purposes

    private JSONArray questions;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize the username
        usernameTextView = findViewById(R.id.usernameTextView);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        //display username
        if (username != null && !username.isEmpty()) {
            usernameTextView.setText("Welcome, " + username);
        } else {
            usernameTextView.setText("Welcome"); // Or handle the case where the username is not passed
        }

        // Initialize the TextView and Buttons
        questionTextView = findViewById(R.id.questionText);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        feedbackContainer = findViewById(R.id.feedbackContainer);

        Button btnNext = findViewById(R.id.btnNext);
        Button btnPrevious = findViewById(R.id.btnPrevious);
        btnLogout = findViewById(R.id.btnLogout);

        btnNext.setOnClickListener(v -> {
            if (questions != null && currentIndex < questions.length() - 1) {
                currentIndex++;
                showCurrentQuestion();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (questions != null && currentIndex > 0) {
                currentIndex--;
                showCurrentQuestion();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions here
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Fetch all questions from the server
        fetchAllQuestions();
    }

    public void onButtonClick(View view) {
        String choice = "";
        String userId = "mobile_user";

        int id = view.getId();
        if (id == R.id.btnA) {
            choice = "a";
        } else if (id == R.id.btnB) {
            choice = "b";
        } else if (id == R.id.btnC) {
            choice = "c";
        } else if (id == R.id.btnD) {
            choice = "d";
        }

        if (!choice.isEmpty()) {
            // Show feedback
            feedbackContainer.setVisibility(View.VISIBLE);

            // Hide feedback after a short delay
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    feedbackContainer.setVisibility(View.GONE);
                }
            }, 2000); //rmb this is in ms if want to change

            // Pass userId and questionId along with the choice
            String url = BASE_URL + choice + "&userId=" + userId + "&questionId=" + (currentIndex + 1); // increment currentIndex for question ID
            Log.d("MainActivity", "Triggering URL: " + url);
            new SendHttpRequestTask().execute(url);
        } else {
            Log.w("MainActivity", "Unknown button clicked");
        }
    }

    private static class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String urlString = urls[0];
            String responseMessage = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(false);
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                Log.d("SendHttpRequestTask", "Response Code: " + responseCode);

                if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                    responseMessage = "Request successful, response code: " + responseCode;
                } else {
                    responseMessage = "Request failed with response code: " + responseCode;
                }

                conn.disconnect();
            } catch (IOException e) {
                Log.e("SendHttpRequestTask", "Error sending request: " + e.getMessage());
                responseMessage = "Error: " + e.getMessage();
            }

            return responseMessage;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("SendHttpRequestTask", "Result: " + result);
        }
    }

    private void fetchAllQuestions() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                StringBuilder result = new StringBuilder();
                try {
                    URL url = new URL(QUESTION_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    Log.e("MainActivity", "Error fetching questions: " + e.getMessage());
                }
                return result.toString();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    questions = new JSONArray(result);
                    currentIndex = 0;
                    showCurrentQuestion();
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Failed to parse questions", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showCurrentQuestion() {
        try {
            JSONObject q = questions.getJSONObject(currentIndex);
            String questionText = q.getString("question_text");
            String a = q.getString("option_a");
            String b = q.getString("option_b");
            String c = q.getString("option_c");
            String d = q.getString("option_d");

            questionTextView.setText(questionText);
            btnA.setText("A: " + a);
            btnB.setText("B: " + b);
            btnC.setText("C: " + c);
            btnD.setText("D: " + d);
        } catch (JSONException e) {
            Log.e("MainActivity", "Error showing question: " + e.getMessage());
        }
    }
}
