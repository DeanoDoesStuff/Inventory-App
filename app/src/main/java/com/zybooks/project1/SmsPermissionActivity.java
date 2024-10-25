package com.zybooks.project1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsPermissionActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        // Get the item name passed from the adapter
        itemName = getIntent().getStringExtra("itemName");

        // Check if SMS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Explain to the user why SMS permission is required
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "We need SMS permission to send alerts when items are out of stock.", Toast.LENGTH_LONG).show();
            }
            // Request SMS permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            sendSMS();
        }
    }

    private void sendSMS() {
        // Set a default phone number for testing
        String phoneNumber = "1234567890"; // Replace with a valid test number
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null,
                "Item " + itemName + " is out of stock!", null, null);
        Toast.makeText(this, "SMS sent for " + itemName, Toast.LENGTH_SHORT).show();
        finish();  // Close the activity after sending SMS
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity if permission is denied
            }
        }
    }
}
