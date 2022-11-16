package edu.illinois.scoobygang.emergencyalerts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private SharedPreferences sharedpreferences;
    private static final String SHARED_PREFS = "saved_messages";
    private static final String MESSAGE_KEY = "messages_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedpreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        List<Message> list = new ArrayList<>();
        list = getData();

        recyclerView = (RecyclerView) findViewById(R.id.message_recycler);
//        listener = new ClickListener() {
//            @Override
//            public void click(int index){
//                Toast.makeTexT(this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
//            }
//        };
        adapter = new MessageAdapter(list, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageSelectActivity.this));

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

        Collections.sort(list, new MessageSelectActivity.MessageComparator());

        return list;
    }
    class MessageComparator implements Comparator<Message> {
        public int compare(Message msg1, Message msg2) {
            return msg1.getTitle().compareTo(msg2.getTitle());
        }
    }

}