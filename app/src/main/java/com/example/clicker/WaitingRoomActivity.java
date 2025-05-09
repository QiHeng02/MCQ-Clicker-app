package com.example.clicker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WaitingRoomActivity extends AppCompatActivity {

    EditText codeEditText;
    Button submitCodeButton;
    TextView feedbackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);

        codeEditText = findViewById(R.id.codeEditText);
        submitCodeButton = findViewById(R.id.submitCodeButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);

        submitCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = codeEditText.getText().toString();

                if (enteredCode.length() == 4 && TextUtils.isDigitsOnly(enteredCode)) {
                    new ValidateCodeTask(enteredCode).execute();
                } else {
                    feedbackTextView.setText("Please enter a 4-digit numeric code.");
                }
            }
        });
    }

    private class ValidateCodeTask extends AsyncTask<Void, Void, String> {
        private final String code;

        public ValidateCodeTask(String code) {
            this.code = code;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String urlString = "http://10.0.2.2:9999/clicker/validateCode?code=" + code; // Corrected URL with code parameter
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return "error: HTTP error " + responseCode;
                }
            } catch (IOException e) {
                return "error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if ("success".equals(result)) {
                // Navigate to the question activity
                Intent intent = new Intent(WaitingRoomActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                feedbackTextView.setText("Invalid code. Please try again.");
                Log.e("ValidateCode", "Server response: " + result);
            }
        }
    }
}