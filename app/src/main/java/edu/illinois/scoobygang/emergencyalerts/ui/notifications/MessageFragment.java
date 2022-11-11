package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.TemplateActivity;
import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment {
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentMessageBinding binding;
    private ClickListener listener;

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

        List<Message> list = new ArrayList<>();
        list = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index){
                Toast.makeText(root.getContext(),"clicked item index is "+index,Toast.LENGTH_LONG).show();
            }
        };

        recyclerView = root.findViewById(R.id.message_recycler);
        adapter = new MessageAdapter(list, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

//        Toolbar toolbar = binding.messageToolbar;
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

//        final TextView textView = binding.MessagesViewModel;
//        MessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // Sample data for RecyclerView
    private List<Message> getData()
    {
        List<Message> list = new ArrayList<>();
        list.add(new Message("I HATE ANDROID", "I HATE ANDROID"));
        list.add(new Message("REALLY I DO", "REALLY I DO"));

        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}