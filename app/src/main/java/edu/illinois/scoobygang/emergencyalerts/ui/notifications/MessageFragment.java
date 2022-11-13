package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

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

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessagesViewModel messagesViewModel =
                new ViewModelProvider(this).get(MessagesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_message, container, false);

        templateList = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index){
//                onButtonShowPopupWindowClick(root, inflater);
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

//        Toolbar toolbar = binding.messageToolbar;
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

//        final TextView textView = binding.MessagesViewModel;
//        MessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // Sample data for RecyclerView
    private ArrayList<Message> getData()
    {
        ArrayList<Message> list = new ArrayList<>();
        list.add(new Message("I HATE ANDROID", "I HATE ANDROID"));
        list.add(new Message("REALLY I DO", "REALLY I DO"));

        return list;
    }

    public void onButtonShowPopupWindowClick(View view, LayoutInflater inflater) {
        // inflate the layout of the popup window
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
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
            String user = inputTitle.getText().toString();
            String pass = inputBody.getText().toString();
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
            String user = inputTitle.getText().toString();
            String pass = inputBody.getText().toString();


            Toast.makeText(view.getContext(), "Message Template Saved", Toast.LENGTH_LONG).show();
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}