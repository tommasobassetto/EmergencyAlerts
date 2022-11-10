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

    String contactID, firstName, lastName, phone, email;
    EditText firstNameView, lastNameView, phoneView, emailView;
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

        firstNameView = findViewById(R.id.firstName);
        lastNameView = findViewById(R.id.lastName);
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
                String[] firstName = removeWhitespace(firstNameView.getText().toString());
                String[] lastName = removeWhitespace(lastNameView.getText().toString());
                String[] phone = removeWhitespace(phoneView.getText().toString());
                String[] email = removeWhitespace(emailView.getText().toString());

                // save contact info
                if (firstName[1].equals("")) {
                    editor.putString("firstName", firstName[1]);
                } else {
                    editor.putString("firstName", firstName[0]);
                } if (lastName[1].equals("")) {
                    editor.putString("firstName", lastName[1]);
                } else {
                    editor.putString("firstName", lastName[0]);
                } if (phone[1].equals("")) {
                    editor.putString("firstName", phone[1]);
                } else {
                    editor.putString("firstName", phone[0]);
                } if (email[1].equals("")) {
                    editor.putString("firstName", email[1]);
                } else {
                    editor.putString("firstName", email[0]);
                }
                editor.apply();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
                contact.edit().clear().apply();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // retrieve contact info
        SharedPreferences contact = getSharedPreferences(contactID, MODE_PRIVATE);
        firstName = contact.getString("firstName", "");
        lastName = contact.getString("lastName", "");
        phone = contact.getString("phone", "");
        email = contact.getString("email", "");

        // populate text fields
        if (!Objects.equals(firstName, "")) {
            firstNameView.setText(firstName);
        } if (!Objects.equals(lastName, "")) {
            lastNameView.setText(lastName);
        } if (!Objects.equals(phone, "")) {
            phoneView.setText(phone);
        } if (!Objects.equals(email, "")) {
            emailView.setText(email);
        }
    }

}