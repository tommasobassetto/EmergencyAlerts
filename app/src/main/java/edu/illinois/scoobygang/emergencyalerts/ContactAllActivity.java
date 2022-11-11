/*
reference: https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
 */

package edu.illinois.scoobygang.emergencyalerts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.data.ContactAdapter;

public class ContactAllActivity extends AppCompatActivity {

    // creating variables for
    // our ui components.
    private RecyclerView contactsRV;

    // variable for our adapter
    // class and array list
    private ContactAdapter adapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // initializing our variables.
        contactsRV = findViewById(R.id.list_of_contacts);

        // calling method to
        // build recycler view.
        buildRecyclerView();
    }

    // calling on create option menu
    // layout to inflate our menu file.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // below line is to get our inflater
//        MenuInflater inflater = getMenuInflater();
//
//        // inside inflater we are inflating our menu file.
//        inflater.inflate(R.menu.search_bar, menu);
//
//        // below line is to get our menu item.
//        MenuItem searchItem = menu.findItem(R.id.actionSearch);
//
//        // getting search view of our item.
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        // below line is to call set on query text listener method.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
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
//        return true;
//    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Contact> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Contact contact : contacts) {
            // checking if the entered string matched with any item of our recycler view.
            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(contact);
            }
        }

        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Matching Contacts...", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        contacts = new ArrayList<>();

        // below line is to add data to our array list.
//        String[] filenames = null;
//        File sharedPrefsDir = new File(getApplicationInfo().dataDir,"shared_prefs");
//        if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory()) {
//            filenames = sharedPrefsDir.list();
//        }
//
//        if (filenames != null) {
//            for (String filename : filenames) {
//                SharedPreferences contactPrefs = getSharedPreferences(filename, MODE_PRIVATE);
//                Contact contact = new Contact();
//                contact.setName(contactPrefs.getString("name", ""));
//                contacts.add(contact);
//            }
//        }

        Contact c1, c2;
        c1 = c2 = new Contact();
        c1.setName("John Smith");
        c2.setName("Jane Doe");
        contacts.add(c1);
        contacts.add(c2);

        // initializing our adapter class.
        adapter = new ContactAdapter(contacts, ContactAllActivity.this);

        // adding layout manager to our recycler view.
        contactsRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        contactsRV.setLayoutManager(new LinearLayoutManager(this));

        // setting adapter to
        // our recycler view.
        contactsRV.setAdapter(adapter);
    }
}