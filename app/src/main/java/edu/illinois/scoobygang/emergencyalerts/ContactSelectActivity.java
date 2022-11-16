package edu.illinois.scoobygang.emergencyalerts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.databinding.ActivityContactSelectBinding;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ClickListener;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactAdapter;

public class ContactSelectActivity extends AppCompatActivity {

    private ActivityContactSelectBinding binding;
    private LinearLayout contactList;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Contact> list = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.contacts_recycler);
//        listener = new ClickListener() {
//            @Override
//            public void click(int index){
//                Toast.makeTexT(this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
//            }
//        };
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactSelectActivity.this));
        recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter(list, listener);
        recyclerView.setAdapter(adapter);

        Button backButton = binding.SelectContactBackButton;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactSelectActivity.super.onBackPressed();
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