package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.content.Intent;


public class Sign_up extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText mobileEditText;
    private Button signUpButton;

    private static final String SIGN_UP_URL = "http://10.0.2.2:9999/clicker/signup"; // Replace with actual values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Temporarily allow network on main thread for simplicity (not best practice)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        usernameEditText = findViewById(R.id.signUpUsernameEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        mobileEditText = findViewById(R.id.signUpMobileEditText);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    usernameEditText.setError("Username cannot be empty");
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Password cannot be empty");
                    return;
                }

                if (mobile.isEmpty()) {
                    mobileEditText.setError("Mobile number cannot be empty");
                    return;
                } else if (!mobile.matches("\\d{8}")) {
                    mobileEditText.setError("Mobile number must be exactly 8 digits");
                    return;
                }

                // Call method to perform POST request
                sendSignUpRequest(username, password, mobile);
            }
        });
    }

    private void sendSignUpRequest(String username, String password, String mobile) {
        try {
            String fullUrl = SIGN_UP_URL + "?username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&mobile=" + URLEncoder.encode(mobile, "UTF-8");

            URL url = new URL(fullUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                runOnUiThread(() -> {
                    if (response.toString().toLowerCase().contains("success")) {
                        Toast.makeText(Sign_up.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                        // Redirect to LoginActivity after a short delay
                        new android.os.Handler().postDelayed(() -> {
                            Intent intent = new Intent(Sign_up.this, LoginActivity.class); // Change LoginActivity to your actual class
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish(); // close SignUpActivity so user can’t go back
                        }, 1000); // 1 second delay for user to see the toast
                    } else {
                        Toast.makeText(Sign_up.this, "Failed: " + response.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                runOnUiThread(() ->
                        Toast.makeText(Sign_up.this, "Server Error: " + responseCode, Toast.LENGTH_LONG).show()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
            runOnUiThread(() ->
                    Toast.makeText(Sign_up.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
            );
        }
    }
}
