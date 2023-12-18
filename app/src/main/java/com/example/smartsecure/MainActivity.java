package com.example.smartsecure;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView lockImageView;
    TextView lockedTextView;
    Button changeButton;

    private AppBarConfiguration mAppBarConfiguration;

    // track the current lock state
    boolean isLocked = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigationView);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        lockImageView = findViewById(R.id.lockImageView);
        lockedTextView = findViewById(R.id.lockedTextView);
        changeButton = findViewById(R.id.changeButton);

        // Set the initial state
        updateLockState();

        // Set a click listener for the change button
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the lock state and update the UI
                isLocked = !isLocked;
                updateLockState();
            }
        });

        // handle NFC communication and door status
    }

    // Method to update the lock state and UI
    private void updateLockState() {
        if (isLocked) {
            lockImageView.setImageResource(R.drawable.ic_baseline_lock_24);
            lockImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_light));
            lockedTextView.setText("Door is locked");
        } else {
            // Change the image and text when the door is unlocked
            lockImageView.setImageResource(R.drawable.ic_baseline_lock_open_24);
            lockImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_green_light));
            lockedTextView.setText("Door is unlocked");
        }
    }
}