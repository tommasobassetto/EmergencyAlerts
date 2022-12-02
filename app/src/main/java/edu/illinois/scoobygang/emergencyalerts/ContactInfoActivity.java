package edu.illinois.scoobygang.emergencyalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;

import java.io.File;
import java.util.Objects;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;
import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactFragment;

public class ContactInfoActivity extends AppCompatActivity {

    String contactID, name, phone, email;
    EditText nameView, phoneView, emailView;
    Button save, back, delete;
    CheckBox email_select, phone_select;

    private void deleteSharedPrefsFile(String contactID) {
        String path = getApplicationInfo().dataDir + "/shared_prefs/";
        String filename = contactID + ".xml";
        String fullname = path + filename;
        File prefsFile = new File(fullname);
        if (prefsFile.delete()) {
            Log.d("status", "successful!");
        } else {
            Log.d("status", "not successful");
        }
    }

    private boolean fieldsValid() {
        if (nameView.getText().toString().trim().length() == 0) {
            nameView.setError("This field is required");
            Toast.makeText(this, "Add a contact name", Toast.LENGTH_SHORT).show();
            return false;
        } if (emailView.getText().toString().trim().length() == 0 && phoneView.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Add at least one means of contact", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean defaultValid() {
        if (email_select.isChecked() || phone_select.isChecked()) {
            return true;
        } else {
            Toast.makeText(this, "Please select a default communication method by selecting a checkbox", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private final View.OnClickListener saveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (fieldsValid() && defaultValid()) {
                // open editor to contact's preferences file
                SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
                SharedPreferences.Editor contactEditor = contact.edit();

                // read data from text views
                String name = nameView.getText().toString().trim();
                String phone = phoneView.getText().toString().trim();
                String email = emailView.getText().toString().trim();

                // save contact info
                contactEditor.putString("name", name);
                contactEditor.putString("phone", phone);
                contactEditor.putString("email", email);

                // save checkbox selections
                if (email_select.isChecked()) {
                    contactEditor.putString("default", "email");
                } else if (phone_select.isChecked()) {
                    contactEditor.putString("default", "phone");
                }

                contactEditor.apply();

                Toast.makeText(getApplicationContext(),"Save successful!",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ContactInfoActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    };

    private final View.OnClickListener deleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            SharedPreferences contactPrefs = getSharedPreferences(contactID, MODE_PRIVATE);
//            contactPrefs.edit().clear().apply();
            deleteSharedPrefsFile(contactID);

            Toast.makeText(getApplicationContext(),"Deleted contact",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(ContactInfoActivity.this, MainActivity.class);
            startActivity(i);
        }
    };

    private final View.OnClickListener emailClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            phone_select.setChecked(false);
            phone_select.setText("");
            email_select.setText("Default");
        }
    };

    private final View.OnClickListener phoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email_select.setChecked(false);
            email_select.setText("");
            phone_select.setText("Default");
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        nameView = findViewById(R.id.name);
        phoneView = findViewById(R.id.phone);
        emailView = findViewById(R.id.email);

        contactID = getIntent().getStringExtra("CONTACT_ID");

        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);

        save.setOnClickListener(this.saveClicked);
        delete.setOnClickListener(this.deleteClicked);

        email_select = findViewById(R.id.select_email);
        phone_select = findViewById(R.id.select_phone);

        email_select.setOnClickListener(this.emailClicked);
        phone_select.setOnClickListener(this.phoneClicked);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // retrieve contact info
        SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
        name = contact.getString("name", "");
        phone = contact.getString("phone", "");
        email = contact.getString("email", "");

        String defaultPlatform = contact.getString("default", "");

        // populate text fields
        if (!Objects.equals(name, "")) {
            nameView.setText(name);
        }
        if (!Objects.equals(phone, "")) {
            phoneView.setText(phone);
        }
        if (!Objects.equals(email, "")) {
            emailView.setText(email);
        }

        // populate checkbox selections
        if (Objects.equals(defaultPlatform, "email")) {
            email_select.setChecked(true);
            email_select.setText("Default");
        } else if (Objects.equals(defaultPlatform, "phone")) {
            phone_select.setChecked(true);
            phone_select.setText("Default");
        }
    }
}