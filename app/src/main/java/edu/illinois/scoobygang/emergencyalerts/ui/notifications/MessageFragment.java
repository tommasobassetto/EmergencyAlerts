package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment {
    private View root;
    private SearchView searchbar;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentMessageBinding binding;
    private ClickListener listener;
    private ArrayList<Message> templateList;
    private String currSearchKey = null;

    // variables for data storing
    private static final String SHARED_PREFS = "saved_messages";
    private static final String MESSAGE_KEY = "messages_json";
    private SharedPreferences sharedpreferences;
    private EditText messageEdit;
    private final String storageFileName = "message_storage.json";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel messagesViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        root = inflater.inflate(R.layout.fragment_message, container, false);

        // retrieve data from local json file
        sharedpreferences = root.getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        templateList = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index){
                EditMessageClicked(root, index);
            }
        };

        FloatingActionButton myFab = (FloatingActionButton) root.findViewById(R.id.add_message_fab);
        myFab.setOnClickListener(v -> AddMessageClicked(root));

        recyclerView = root.findViewById(R.id.message_recycler);
        adapter = new MessageAdapter(templateList, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        searchbar = root.findViewById(R.id.message_searchbar);
        searchbar.setQueryHint("Search messages...");

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("DEBUG", query);
                currSearchKey = query;
                filter(currSearchKey);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchKey) {
                Log.d("DEBUG", searchKey);
                currSearchKey = searchKey;
                filter(currSearchKey);

                return false;
            }
        });

        return root;
    }

    private void filter(String text) {
        ArrayList<Message> newList = new ArrayList<>();

        for (Message item : templateList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                newList.add(item);
            }
        }

        adapter.update(newList);
    }


    // Sample data for RecyclerView
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

        Collections.sort(list, new MessageComparator());

        return list;
    }

    public void AddMessageClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_message_dialog, null);
        final TextInputEditText inputTitle = alertLayout.findViewById(R.id.tiet_add_message_title);
        final TextInputEditText inputBody = alertLayout.findViewById(R.id.tiet_add_message_body);

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Add Message");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(view.getContext(), "Changes Discarded", Toast.LENGTH_SHORT).show());

        alert.setPositiveButton("Done", (dialog, which) -> {
            String title = inputTitle.getText().toString();
            String body = inputBody.getText().toString();

            Message new_template = new Message(title, body);
            templateList.add(new_template);
            Collections.sort(templateList, new MessageComparator());

            if (currSearchKey != null && !currSearchKey.equals("")) {
                filter(currSearchKey);
            }
            else {
                adapter.notifyItemInserted(templateList.size());
                adapter.notifyItemRangeChanged(0, templateList.size());
            }

            String json_string = new Gson().toJson(templateList);
            saveMessage(json_string);

            Toast.makeText(view.getContext(), "Message Saved", Toast.LENGTH_LONG).show();
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void EditMessageClicked(View view, int index) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.edit_message_dialog, null);
        final TextInputEditText inputTitle = alertLayout.findViewById(R.id.tiet_edit_message_title);
        final TextInputEditText inputBody = alertLayout.findViewById(R.id.tiet_edit_message_body);

        Message msg = adapter.list.get(index);

        inputTitle.setText(msg.getTitle());
        inputBody.setText(msg.getBody());

        // find the real index (in template list instead of the filtered result)
        int real_index = templateList.indexOf(msg);

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Edit Message");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(view.getContext(), "Changes Discarded", Toast.LENGTH_SHORT).show());

        alert.setPositiveButton("Done", (dialog, which) -> {
            String title = inputTitle.getText().toString();
            String body = inputBody.getText().toString();

            Message new_template = new Message(title, body);
            templateList.set(real_index, new_template);
            Collections.sort(templateList, new MessageComparator());

            if (currSearchKey != null && !currSearchKey.equals("")) {
                filter(currSearchKey);
            }
            else {
                adapter.notifyItemChanged(real_index);
                adapter.notifyItemRangeChanged(0, templateList.size());
            }

            String json_string = new Gson().toJson(templateList);
            saveMessage(json_string);

            Toast.makeText(view.getContext(), "Message Saved", Toast.LENGTH_LONG).show();
        });

        alert.setNeutralButton("Delete", (dialog, which) -> {
            templateList.remove(real_index);

            if (currSearchKey != null && !currSearchKey.equals("")) {
                filter(currSearchKey);
            }
            else {
                adapter.notifyItemRemoved(real_index);
                adapter.notifyItemRangeRemoved(real_index, templateList.size());
            }

            String json_string = new Gson().toJson(templateList);
            saveMessage(json_string);

            Toast.makeText(view.getContext(), "Message Deleted", Toast.LENGTH_LONG).show();
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void saveMessage(String msg) {
        Log.d("Debug", "saving message");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MESSAGE_KEY, msg);
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        Log.d("Debug", "on destroy message fragment");

        super.onDestroyView();
        binding = null;
    }

    class MessageComparator implements Comparator<Message> {
        public int compare(Message msg1, Message msg2) {
            return msg1.getTitle().compareTo(msg2.getTitle());
        }
    }
}