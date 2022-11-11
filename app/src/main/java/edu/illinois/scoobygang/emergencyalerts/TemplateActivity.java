package edu.illinois.scoobygang.emergencyalerts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.data.Message;

public class TemplateActivity extends AppCompatActivity {

//    private ActivityTemplateBinding binding;
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityTemplateBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.fragment_message);

        RecyclerView messageRV = findViewById(R.id.message_recycler);


    }

}
