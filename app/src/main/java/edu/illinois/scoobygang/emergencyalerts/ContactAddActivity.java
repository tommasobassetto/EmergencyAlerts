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

import edu.illinois.scoobygang.emergencyalerts.ui.home.ContactFragment;

public class ContactAddActivity extends AppCompatActivity {

    Button back, save;
    EditText name, phone, email;
    String nameStr, phoneStr, emailStr;

    private boolean fieldsValid() {
        if (nameStr.length() == 0) {
            name.setError("This field is required");
            return false;
        } if (emailStr.length() == 0 && phoneStr.length() == 0) {
            Toast.makeText(this, "Add at least one means of contact", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private final View.OnClickListener saveClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            nameStr = name.getText().toString().trim();
            phoneStr = phone.getText().toString().trim();
            emailStr = email.getText().toString().trim();

            if (fieldsValid()) {
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
                contactEditor.apply();

                Intent i = new Intent(ContactAddActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    };

    private final View.OnClickListener backClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ContactAddActivity.this, MainActivity.class);
            startActivity(i);
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
        back = findViewById(R.id.back);

        save.setOnClickListener(this.saveClicked);
        back.setOnClickListener(this.backClicked);
    }


}