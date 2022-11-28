package edu.illinois.scoobygang.emergencyalerts.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.illinois.scoobygang.emergencyalerts.ContactAddActivity;
import edu.illinois.scoobygang.emergencyalerts.ContactAllActivity;
import edu.illinois.scoobygang.emergencyalerts.ContactInfoActivity;
import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.data.Message;
import edu.illinois.scoobygang.emergencyalerts.databinding.FragmentHomeBinding;


public class ContactFragment extends Fragment {
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private ClickListener listener;
    private FloatingActionButton add;
    private SearchView search;

    List<Contact> contacts;

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

        contacts = getData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactViewModel contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listener = new ClickListener() {
            @Override
            public void click(int index){
                Intent i = new Intent(getActivity(), ContactInfoActivity.class);
                i.putExtra("CONTACT_ID", contacts.get(index).getContactID());
                startActivity(i);
            }
        };

        recyclerView = root.findViewById(R.id.contacts_recycler);
        adapter = new ContactAdapter(contacts, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // getting search view of our item.
        search = root.findViewById(R.id.contact_searchbar);
        search.setQueryHint("Search Contact...");

        // below line is to call set on query text listener method.
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filter(newText);
//                return false;
//            }
//        });

        add = root.findViewById(R.id.add_contact);
        add.setOnClickListener(addClicked);

//        Toolbar toolbar = binding.messageToolbar;
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);

//        final TextView textView = binding.MessagesViewModel;
//        MessagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
//
//    private void filter(String text) {
//        // creating a new array list to filter our data.
//        ArrayList<Contact> filteredlist = new ArrayList<>();
//
//        // running a for loop to compare elements.
//        for (Contact contact : contacts) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(contact);
//            }
//        }
////        if (filteredlist.isEmpty()) {
////            // if no item is added in filtered list we are
////            // displaying a toast message as no data found.
////            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
////        } else {
////            // at last we are passing that filtered
////            // list to our adapter class.
////            adapter.filterList(filteredlist);
////        }
//        adapter.filterList(filteredlist);
//    }

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