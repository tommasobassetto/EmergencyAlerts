package edu.illinois.scoobygang.emergencyalerts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.databinding.ActivityContactSelectBinding;

public class ContactSelectActivity extends AppCompatActivity {

    private ActivityContactSelectBinding binding;
    private LinearLayout contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button backButton = binding.SelectContactBackButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactSelectActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button forwardButton = binding.SelectContactNextButton;
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactSelectActivity.this, MessageSelectActivity.class);
                startActivity(i);
            }
        });
    }
}