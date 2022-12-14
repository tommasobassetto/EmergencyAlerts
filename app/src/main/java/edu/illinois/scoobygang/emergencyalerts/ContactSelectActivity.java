package edu.illinois.scoobygang.emergencyalerts;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.data.ContactPlatform;
import edu.illinois.scoobygang.emergencyalerts.databinding.ActivityContactSelectBinding;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ClickListener;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactAdapter;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactFragment;

public class ContactSelectActivity extends AppCompatActivity {

    private ActivityContactSelectBinding binding;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    ClickListener listener;
    private List<ContactPlatform> sendTargets;

    static class ContactComparator implements Comparator<Contact> {
        public int compare(Contact c1, Contact c2) {
            String n1 = c1.getName().toLowerCase();
            String n2 = c2.getName().toLowerCase();
            return n1.compareTo(n2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Contact> list;
        list = getData();

        listener = new ClickListener() {
            @Override
            public void click(int index) {
                //sendTargets.addAll(list.get(index).getPlatforms());
                Toast.makeText(ContactSelectActivity.this, "Contact Added!", Toast.LENGTH_LONG).show();
            }
        };

        recyclerView = findViewById(R.id.contacts_select_recycler);
        adapter = new ContactAdapter(list, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactSelectActivity.this));

        Button backButton = binding.SelectContactBackButton;
        backButton.setOnClickListener(view -> ContactSelectActivity.super.onBackPressed());

        Button forwardButton = binding.SelectContactNextButton;
        forwardButton.setOnClickListener(view -> {
            Intent i = new Intent(ContactSelectActivity.this, MessageSelectActivity.class);
            startActivity(i);
        });

        // FIXME - Select/deselect all
    }
    // Sample data for RecyclerView
    private List<Contact> getData()
    {
        List<Contact> contacts = new ArrayList<>();

        String[] filenames = null;
        try {
            File sharedPrefsDir = new File(this.getApplicationInfo().dataDir,"shared_prefs");
            if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory()) {
                filenames = sharedPrefsDir.list();
            }
            if (filenames != null) {
                for (String filename : filenames) {
                    filename = filename.replace(".xml", "");
                    if (filename.matches("[0-9]+")) {
                        SharedPreferences contactPrefs = this.getSharedPreferences(filename, MODE_PRIVATE);
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

}