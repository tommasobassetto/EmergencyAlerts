package edu.illinois.scoobygang.emergencyalerts.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.illinois.scoobygang.emergencyalerts.ContactAddActivity;
import edu.illinois.scoobygang.emergencyalerts.ContactAllActivity;
import edu.illinois.scoobygang.emergencyalerts.ContactInfoActivity;
import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentHomeBinding;


public class ContactFragment extends Fragment {
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private ClickListener listener;
    private Button add;

    List<Contact> list;

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//    }

    private final View.OnClickListener addClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(), ContactAddActivity.class);
            startActivity(i);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = getData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listener = new ClickListener() {
            @Override
            public void click(int index){
                Intent i = new Intent(getActivity(), ContactInfoActivity.class);
                i.putExtra("CONTACT_ID", list.get(index).getContactID());
                startActivity(i);
            }
        };

        recyclerView = root.findViewById(R.id.contacts_recycler);
        adapter = new ContactAdapter(list, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        add = root.findViewById(R.id.add_contact);
        add.setOnClickListener(this.addClicked);

//        Toolbar toolbar = binding.messageToolbar;
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

//        final TextView textView = binding.MessagesViewModel;
//        MessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // Sample data for RecyclerView
    private List<Contact> getData()
    {
        List<Contact> contacts = new ArrayList<>();

        String[] filenames = null;
        try {
            File sharedPrefsDir = new File(getActivity().getApplicationInfo().dataDir,"shared_prefs");
            if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory()) {
                filenames = sharedPrefsDir.list();
            }
            if (filenames != null) {
                for (String filename : filenames) {
                    if (!Objects.equals(filename, "contactID.xml")) {
                        filename = filename.replace(".xml", "");
                        SharedPreferences contactPrefs = getActivity().getSharedPreferences(filename, MODE_PRIVATE);
                        Contact contact = new Contact();
                        contact.setName(contactPrefs.getString("name", "pizza"));
                        contact.setContactID(contactPrefs.getString("contactID", "pie"));
                        contacts.add(contact);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}