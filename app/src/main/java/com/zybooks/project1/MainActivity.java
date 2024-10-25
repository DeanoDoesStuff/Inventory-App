package com.zybooks.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button createAccountButton;

    private LoginDatabase loginDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the database
        loginDatabase = new LoginDatabase(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the username, password, login, and create account buttons
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccount);

        // Initially disable the login button
        loginButton.setEnabled(false);

        // Add a TextWatcher to both username and password fields to enable/disable the login button
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Enable the login button if both username and password are non-empty
                checkInputs();
            }
        };

        // Attach the TextWatcher to both username and password fields
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(v -> {
            // Logic for handling login will go here
            Intent intent = new Intent(MainActivity.this, DatabaseViewActivity.class);
            startActivity(intent);
        });

        // Set OnClickListener for the create account button
        createAccountButton.setOnClickListener(v -> {
            createNewAccount();
        });
    }

    // Method to check if both inputs are filled
    private void checkInputs() {
        String usernameInput = username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        loginButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
    }

    // Method to handle creating a new account
    private void createNewAccount() {
        String usernameInput = username.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        // Check if inputs are empty
        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(MainActivity.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get writable database
        SQLiteDatabase db = loginDatabase.getWritableDatabase();

        // Insert the new user into the database
        ContentValues values = new ContentValues();
        values.put(LoginDatabase.LoginInfo.COLUMN_USERNAME, usernameInput);
        values.put(LoginDatabase.LoginInfo.COLUMN_PASSWORD, passwordInput);

        long newRowId = db.insert(LoginDatabase.LoginInfo.TABLE, null, values);

        if (newRowId != -1) {
            Toast.makeText(MainActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
        }

        db.close();  // Close the database after use
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDatabase.close(); // Ensure to close the database when the activity is destroyed
    }
}
