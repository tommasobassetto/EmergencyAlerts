package edu.illinois.scoobygang.emergencyalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;

import java.io.File;
import java.util.Objects;

public class ContactInfoActivity extends AppCompatActivity {

    String contactID, name, phone, email;
    EditText nameView, phoneView, emailView;
    Button save, back, delete;
    CheckBox email_select, phone_select;

    private String[] removeWhitespace(String s) {
        String[] data = new String[2];
        String v = s.replace(" ", "");
        v = v.replace("\t", "");
        v = v.replace("\n", "");
        data[0] = s;
        data[1] = v;
        return data;
    }

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

    private final View.OnClickListener saveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // open editor to contact's preferences file
            SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
            SharedPreferences.Editor editor = contact.edit();

            // read data from text views
            String[] name = removeWhitespace(nameView.getText().toString());
            String[] phone = removeWhitespace(phoneView.getText().toString());
            String[] email = removeWhitespace(emailView.getText().toString());

            // save contact info
            if (name[1].equals("")) {
                editor.putString("name", name[1]);
            } else {
                editor.putString("name", name[0]);
            }
            if (phone[1].equals("")) {
                editor.putString("phone", phone[1]);
            } else {
                editor.putString("phone", phone[0]);
            }
            if (email[1].equals("")) {
                editor.putString("email", email[1]);
            } else {
                editor.putString("email", email[0]);
            }

            // save checkbox selections
            if (email_select.isChecked()) {
                editor.putString("email_selected", "Y");
            }
            else {
                editor.putString("email_selected", "N");
            }

            if (phone_select.isChecked()) {
                editor.putString("phone_selected", "Y");
            }
            else {
                editor.putString("phone_selected", "N");
            }

            editor.apply();

            Toast.makeText(getApplicationContext(),"Save successful!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ContactInfoActivity.this, MainActivity.class);
            startActivity(i);
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

    private final View.OnClickListener backClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ContactInfoActivity.this, MainActivity.class);
            startActivity(i);
        }
    };

    private final View.OnClickListener emailClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            phone_select.setChecked(false);
        }
    };

    private final View.OnClickListener phoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email_select.setChecked(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        nameView = findViewById(R.id.name);
        phoneView = findViewById(R.id.phone);
        emailView = findViewById(R.id.email);

        contactID = getIntent().getStringExtra("CONTACT_ID");

        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);

        save.setOnClickListener(this.saveClicked);
        back.setOnClickListener(this.backClicked);
        delete.setOnClickListener(this.deleteClicked);

        email_select = findViewById(R.id.select_email);
        phone_select = findViewById(R.id.select_phone);

        email_select.setOnClickListener(this.emailClicked);
        phone_select.setOnClickListener(this.phoneClicked);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // retrieve contact info
        SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
        name = contact.getString("name", "");
        phone = contact.getString("phone", "");
        email = contact.getString("email", "");

        String email_selected = contact.getString("email_selected", "");
        String phone_selected = contact.getString("phone_selected", "");

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
        if (Objects.equals(email_selected, "Y")) {
            email_select.setChecked(true);
        }
        if (Objects.equals(phone_selected, "Y")) {
            phone_select.setChecked(true);
        }
    }
}