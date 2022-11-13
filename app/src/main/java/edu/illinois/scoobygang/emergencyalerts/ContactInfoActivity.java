package edu.illinois.scoobygang.emergencyalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class ContactInfoActivity extends AppCompatActivity {

    String contactID, name, phone, email;
    EditText nameView, phoneView, emailView;
    Button save, back, delete;

    private String[] removeWhitespace(String s) {
        String[] data = new String[2];
        String v = s.replace(" ", "");
        v = v.replace("\t", "");
        v = v.replace("\n", "");
        data[0] = s;
        data[1] = v;
        return data;
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
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);

        save.setOnClickListener(new View.OnClickListener() {
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
                } if (phone[1].equals("")) {
                    editor.putString("phone", phone[1]);
                } else {
                    editor.putString("phone", phone[0]);
                } if (email[1].equals("")) {
                    editor.putString("email", email[1]);
                } else {
                    editor.putString("email", email[0]);
                }
                editor.apply();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences contactPrefs = getSharedPreferences(contactID, MODE_PRIVATE);
                contactPrefs.edit().clear().apply();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // retrieve contact info
        SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
        name = contact.getString("name", "");
        phone = contact.getString("phone", "");
        email = contact.getString("email", "");

        // populate text fields
        if (!Objects.equals(name, "")) {
            nameView.setText(name);
        } if (!Objects.equals(phone, "")) {
            phoneView.setText(phone);
        } if (!Objects.equals(email, "")) {
            emailView.setText(email);
        }
    }

}