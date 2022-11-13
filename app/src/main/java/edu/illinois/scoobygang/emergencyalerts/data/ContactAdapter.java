/*
reference: https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
 */

package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.illinois.scoobygang.emergencyalerts.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Contact> contacts;

    // creating a constructor for our variables.
    public ContactAdapter(ArrayList<Contact> contacts, Context context) {
        this.contacts = contacts;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Contact> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        contacts = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Contact contact = contacts.get(position);
        holder.contact_name.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return contacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            @NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView contact_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            contact_name = itemView.findViewById(R.id.contact_name);
        }
    }
}
