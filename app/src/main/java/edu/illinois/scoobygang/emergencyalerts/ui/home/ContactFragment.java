package edu.illinois.scoobygang.emergencyalerts.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.ContactAddActivity;
import edu.illinois.scoobygang.emergencyalerts.ContactInfoActivity;
import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentHomeBinding;


public class ContactFragment extends Fragment {
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private ClickListener listener;
    private FloatingActionButton add;
    private SearchView search;

    private List<Contact> contacts;
    private String currSearchKey = null;


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
        contacts = getData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listener = new ClickListener() {
            @Override
            public void click(int index) {
                Intent i = new Intent(getActivity(), ContactInfoActivity.class);
                Contact contact_info = adapter.contacts.get(index);
                i.putExtra("CONTACT_ID", contact_info.getContactID());
                startActivity(i);
            }
        };

        recyclerView = root.findViewById(R.id.contacts_recycler);
        adapter = new ContactAdapter(contacts, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // getting search view of our item.
        search = root.findViewById(R.id.contact_searchbar);
        search.setQueryHint("Search contacts...");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        add = root.findViewById(R.id.add_contact);
        add.setOnClickListener(addClicked);

        return root;
    }

    private void filter(String text) {
        ArrayList<Contact> newList = new ArrayList<>();

        for (Contact item : contacts) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                newList.add(item);
            }
        }

        adapter.update(newList);
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
                    filename = filename.replace(".xml", "");
                    if (filename.matches("[0-9]+")) {
                        SharedPreferences contactPrefs = getActivity().getSharedPreferences(filename, MODE_PRIVATE);
                        Contact contact = new Contact();
                        contact.setName(contactPrefs.getString("name", "pizza"));
                        contact.setContactID(contactPrefs.getString("contactID", "pie"));
                        contact.setDefaultPlatform(contactPrefs.getString("default", "toast"));
                        contacts.add(contact);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contacts.sort(new ContactComparator());
        return contacts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    static class ContactComparator implements Comparator<Contact> {
        public int compare(Contact c1, Contact c2) {
            String n1 = c1.getName().toLowerCase();
            String n2 = c2.getName().toLowerCase();
            return n1.compareTo(n2);
        }
    }
}