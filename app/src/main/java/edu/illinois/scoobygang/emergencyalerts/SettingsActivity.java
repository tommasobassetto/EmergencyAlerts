package edu.illinois.scoobygang.emergencyalerts;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},1);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // enable back press to home screen.
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @SuppressLint("Range")
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            // import phone contacts.
            Preference phoneContacts = findPreference("phone_contacts");
            assert phoneContacts != null;
            phoneContacts.setOnPreferenceClickListener(preference -> {
                importPhoneContacts();
                // popup flow.
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_import_contact_success, null);
                ShowSuccessPopUp(inflater, popupView, getView());

                return true;
            });

            // enable auto-sending for third-party apps.
            Preference auto_send = findPreference("enable_auto_send");
            auto_send.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);

                return true;
            });
        }

        @SuppressLint("Range")
        public void importPhoneContacts() {
            ContentResolver cr = getActivity().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    @SuppressLint("Range") String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            SharedPreferences contactIdPrefs = getActivity()
                                    .getSharedPreferences("contactID", MODE_PRIVATE);
                            SharedPreferences.Editor contactIDEditor = contactIdPrefs.edit();

                            int contactID = contactIdPrefs.getInt("count", -1) + 1;
                            contactIDEditor.putInt("count", contactID);
                            contactIDEditor.apply();
                            SharedPreferences contactPrefs = getActivity().
                                    getSharedPreferences(Integer.toString(contactID), MODE_PRIVATE);
                            SharedPreferences.Editor contactEditor = contactPrefs.edit();
                            contactEditor.putString("contactID", Integer.toString(contactID));
                            contactEditor.putString("name", name);
                            contactEditor.putString("phone", phoneNo);
                            contactEditor.putString("email", "");
                            contactEditor.putString("defaultPlatform", "phone");
                            contactEditor.apply();

                            // Todo: add phone platform
                            Log.i(TAG, "Name: " + name);
                            Log.i(TAG, "Phone Number: " + phoneNo);
                        }
                        pCur.close();
                    }
                }
            }
            if(cur!=null){
                cur.close();
            }
        }

        public void ShowSuccessPopUp(LayoutInflater inflater, View popupView, View view) {
            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });
        }
    }

}