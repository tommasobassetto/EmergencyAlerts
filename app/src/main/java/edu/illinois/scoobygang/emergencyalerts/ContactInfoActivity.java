package edu.illinois.scoobygang.emergencyalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import edu.illinois.scoobygang.emergencyalerts.data.Contact;

public class ContactInfoActivity extends AppCompatActivity {

    private static final String FILENAME = "contacts.csv";

    EditText firstNameView;
    EditText lastNameView;
    EditText phoneView;
    EditText emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        firstNameView = (EditText) findViewById(R.id.firstName);
        lastNameView = (EditText) findViewById(R.id.lastName);
        phoneView = (EditText) findViewById(R.id.phone);
        emailView = (EditText) findViewById(R.id.email);

        // read contact info from file
        Contact contact = new Contact();


    }

    public void saveToFile(View v) throws IOException {
        String firstNameText = firstNameView.getText().toString() + ",";
        String lastNameText = lastNameView.getText().toString() + ",";
        String phoneText = phoneView.getText().toString() + ",";
        String emailText = emailView.getText().toString() + ",";

        FileOutputStream out = null;
        try {
            out = openFileOutput(FILENAME, MODE_PRIVATE);
            out.write(firstNameText.getBytes());
            out.write(lastNameText.getBytes());
            out.write(phoneText.getBytes());
            out.write(emailText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}