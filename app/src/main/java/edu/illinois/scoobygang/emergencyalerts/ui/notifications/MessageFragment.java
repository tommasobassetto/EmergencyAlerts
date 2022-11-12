package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
                onButtonShowPopupWindowClick(root, inflater);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}