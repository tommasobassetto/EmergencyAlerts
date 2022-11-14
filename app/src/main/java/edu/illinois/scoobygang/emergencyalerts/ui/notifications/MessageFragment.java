package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment {
    private SearchView searchbar;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentMessageBinding binding;
    private ClickListener listener;
    private ArrayList<Message> templateList;
    private final String storageFileName = "message_storage.json";
    private JSONObject message_json;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel messagesViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_message, container, false);

//         retrieve data from local json file
        boolean isFilePresent = isFilePresent(root.getContext(), storageFileName);
        if(isFilePresent) {
            String jsonString = read(root.getContext(), storageFileName);
            try {
                message_json = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //do the json parsing here and do the rest of functionality of app
        } else {
            boolean isFileCreated = create(root.getContext(), storageFileName, "{}");
        }

        templateList = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index){
                EditMessageClicked(root, index);
//                Toast.makeText(root.getContext(),"clicked item index is "+index,Toast.LENGTH_LONG).show();
            }
        };

        recyclerView = root.findViewById(R.id.message_recycler);
        adapter = new MessageAdapter(templateList, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        searchbar = root.findViewById(R.id.message_searchbar);
        searchbar.setQueryHint("Search Message...");

        return root;
    }

    // Sample data for RecyclerView
    private ArrayList<Message> getData()
    {
        ArrayList<Message> list = new ArrayList<>();
        list.add(new Message("I HATE ANDROID", "I HATE ANDROID"));
        list.add(new Message("REALLY I DO", "REALLY I DO"));

        try {
            JSONArray messages = message_json.getJSONArray("{}");
            for (int i = 0; i < messages.length(); i++) {
                JSONObject m = messages.getJSONObject(i);
                String title = m.getString("title");
                String body = m.getString("body");

                Message msg = new Message(title, body);

                list.add(msg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            // TODO: save the data
            String title = inputTitle.getText().toString();
            String body = inputBody.getText().toString();


            Toast.makeText(view.getContext(), "Message Template Saved", Toast.LENGTH_LONG).show();
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void EditMessageClicked(View view, int index) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.edit_message_dialog, null);
        final TextInputEditText inputTitle = alertLayout.findViewById(R.id.tiet_edit_message_title);
        final TextInputEditText inputBody = alertLayout.findViewById(R.id.tiet_edit_message_body);

        inputTitle.setText(templateList.get(index).getTitle());
        inputBody.setText(templateList.get(index).getBody());

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Edit Message");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(view.getContext(), "Changes Discarded", Toast.LENGTH_SHORT).show());

        alert.setPositiveButton("Done", (dialog, which) -> {
            // TODO: save the data
            String title = inputTitle.getText().toString();
            String body = inputBody.getText().toString();

            Message new_template = new Message(title, body);
            templateList.set(index, new_template);

            adapter.notifyItemChanged(index);
            adapter.notifyItemRangeChanged(index, templateList.size());

            Toast.makeText(view.getContext(), "Message Template Saved", Toast.LENGTH_LONG).show();
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

//    @Override
//    public void onBackPressed()
//    {
//        Log.d("Debug", "back press");
//        super.onBackPressed();
//    }

    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private boolean create(Context context, String fileName, String jsonString){
        String FILENAME = fileName;
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);

        return file.exists();
    }

    @Override
    public void onDestroyView() {
        Log.d("Debug", "on destroy message fragment");
        // TODO: save the existing data every time destroy the fragment

        super.onDestroyView();
        binding = null;
    }
}