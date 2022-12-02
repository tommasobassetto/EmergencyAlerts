package edu.illinois.scoobygang.emergencyalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactFragment;

public class ContactAddActivity extends AppCompatActivity {

    Button back, save;
    EditText name, phone, email;
    String nameStr, phoneStr, emailStr;
    CheckBox email_select, phone_select, whatsapp_select;

    private boolean fieldsValid() {
        if (nameStr.length() == 0) {
            name.setError("This field is required");
            return false;
        } if (emailStr.length() == 0 && phoneStr.length() == 0) {
            Toast.makeText(this, "Add at least one platform of contact", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean defaultValid() {
        if (email_select.isChecked() || phone_select.isChecked() || whatsapp_select.isChecked()) {
            return true;
        } else {
            Toast.makeText(this, "Please select a default communication platform by selecting a checkbox", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private final View.OnClickListener saveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            nameStr = name.getText().toString().trim();
            phoneStr = phone.getText().toString().trim();
            emailStr = email.getText().toString().trim();

            if (fieldsValid() && defaultValid()) {
                SharedPreferences contactIdPrefs = getSharedPreferences("contactID", MODE_PRIVATE);
                SharedPreferences.Editor contactIDEditor = contactIdPrefs.edit();

                int contactID = contactIdPrefs.getInt("count", -1) + 1;
                contactIDEditor.putInt("count", contactID);
                contactIDEditor.apply();

                SharedPreferences contactPrefs = getSharedPreferences(Integer.toString(contactID), MODE_PRIVATE);
                SharedPreferences.Editor contactEditor = contactPrefs.edit();

                contactEditor.putString("contactID", Integer.toString(contactID));
                contactEditor.putString("name", nameStr);
                contactEditor.putString("phone", phoneStr);
                contactEditor.putString("email", emailStr);

                // save checkbox selections
                if (email_select.isChecked()) {
                    contactEditor.putString("default", "email");
                } else if (phone_select.isChecked()) {
                    contactEditor.putString("default", "phone");
                }

                contactEditor.apply();

                Intent i = new Intent(ContactAddActivity.this, MainActivity.class);
                i.putExtra("Contact", "true");
                startActivity(i);
            }
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

    private final View.OnClickListener emailClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            phone_select.setChecked(false);
            whatsapp_select.setChecked(false);
        }
    };

    private final View.OnClickListener phoneClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email_select.setChecked(false);
            whatsapp_select.setChecked(false);
        }
    };

    private final View.OnClickListener whatsappClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            phone_select.setChecked(false);
            email_select.setChecked(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        save = findViewById(R.id.save);

        save.setOnClickListener(this.saveClicked);

        email_select = findViewById(R.id.select_email);
        phone_select = findViewById(R.id.select_phone);
        whatsapp_select = findViewById(R.id.select_whatsapp);

        email_select.setOnClickListener(this.emailClicked);
        phone_select.setOnClickListener(this.phoneClicked);
        whatsapp_select.setOnClickListener(this.whatsappClicked);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


}