package edu.illinois.scoobygang.emergencyalerts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.ui.notifications.ClickListener;
import edu.illinois.scoobygang.emergencyalerts.ui.notifications.MessageAdapter;

public class TemplateActivity extends AppCompatActivity {

    MessageAdapter adapter;
    RecyclerView recyclerView;
    ClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.message_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

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
        recyclerView.setLayoutManager(new LinearLayoutManager(TemplateActivity.this));
    }

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//    }

    // Sample data for RecyclerView
    private List<Message> getData()
    {
        List<Message> list = new ArrayList<>();
        list.add(new Message("I HATE ANDROID", "I HATE ANDROID"));
        list.add(new Message("REALLY I DO", "REALLY I DO"));

        return list;
    }
}
