package edu.illinois.scoobygang.emergencyalerts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.data.ContactPlatform;
import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.databinding.ActivityMessageSelectBinding;
import edu.illinois.scoobygang.emergencyalerts.ui.notifications.ClickListener;
import edu.illinois.scoobygang.emergencyalerts.ui.notifications.MessageAdapter;
import edu.illinois.scoobygang.emergencyalerts.ui.notifications.MessageFragment;

public class MessageSelectActivity extends AppCompatActivity {

    private ActivityMessageSelectBinding binding;
    MessageAdapter adapter;
    RecyclerView recyclerView;
    ClickListener listener;
    private Message selectedMessage;

    private List<ContactPlatform> targets;

    private SharedPreferences sharedpreferences;
    private static final String SHARED_PREFS = "saved_messages";
    private static final String MESSAGE_KEY = "messages_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedpreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        List<Message> list;
        list = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index){
                if (list.get(index) == null) return;
                selectedMessage = list.get(index);
            }
        };

        recyclerView = findViewById(R.id.message_recycler);
        adapter = new MessageAdapter(list, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageSelectActivity.this));

        Button backButton = binding.SelectMessageBackButton;
        backButton.setOnClickListener(view -> MessageSelectActivity.super.onBackPressed());

        Button forwardButton = binding.SelectMessageNextButton;
        forwardButton.setOnClickListener(view -> {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.confirmation_dialog, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle("Are you sure you want to send this alert?");
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            alert.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(view.getContext(), "Changes Discarded", Toast.LENGTH_SHORT).show());

            alert.setPositiveButton("Send", (dialog, which) -> {
                Toast.makeText(this, "Sending Messages...", Toast.LENGTH_LONG).show();

                for (ContactPlatform p: targets) {
                    p.onSend(null, selectedMessage.getBody());
                }

                // Bring up popup for confirmation
                AlertDialog.Builder confirmation = new AlertDialog.Builder(view.getContext());
                confirmation.setTitle("Your messages have been sent!");
                confirmation.setPositiveButton("Finish", (dialog2, which2) -> {
                    Intent i = new Intent(MessageSelectActivity.this, MainActivity.class);
                    startActivity(i);
                });
                AlertDialog popup = confirmation.create();
                popup.show();

            });

            AlertDialog dialog = alert.create();
            dialog.show();
        });

        targets = new ArrayList<>();
    }

    private ArrayList<Message> getData()
    {
        ArrayList<Message> list = new ArrayList<>();

        String json_string = sharedpreferences.getString(MESSAGE_KEY, null);
        if (json_string == null || json_string.length() == 0) {
            return list;
        }

        try {
            JSONArray json_array = new JSONArray(json_string);
            for (int i = 0; i < json_array.length(); i++){
                JSONObject json_obj = json_array.getJSONObject(i);
                String title = json_obj.getString("title");
                String body = json_obj.getString("body");
                list.add(new Message(title, body));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list.sort(new MessageComparator());

        return list;
    }
    static class MessageComparator implements Comparator<Message> {
        public int compare(Message msg1, Message msg2) {
            return msg1.getTitle().compareTo(msg2.getTitle());
        }
    }

}