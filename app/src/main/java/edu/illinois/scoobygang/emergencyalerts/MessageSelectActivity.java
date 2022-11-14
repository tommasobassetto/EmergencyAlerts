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

import edu.illinois.scoobygang.emergencyalerts.databinding.ActivityMessageSelectBinding;

public class MessageSelectActivity extends AppCompatActivity {

    private ActivityMessageSelectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button backButton = binding.SelectMessageBackButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MessageSelectActivity.this, ContactSelectActivity.class);
                startActivity(i);
            }
        });

        Button forwardButton = binding.SelectMessageNextButton;
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME - should pop from stack
                Intent i = new Intent(MessageSelectActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}