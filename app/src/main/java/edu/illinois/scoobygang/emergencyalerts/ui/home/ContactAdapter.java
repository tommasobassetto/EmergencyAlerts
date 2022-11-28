package edu.illinois.scoobygang.emergencyalerts.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Contact;


public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>{
    List<Contact> contacts = Collections.emptyList();

//    Context context;
    ClickListener listener;

    public ContactAdapter(List<Contact> list, ClickListener listener)
    {
        this.contacts = list;
        this.listener = listener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.list_item_contact, parent, false);

        ContactViewHolder viewHolder = new ContactViewHolder(itemView);
        return viewHolder;
    }

//    // method for filtering our recyclerview items.
//    public void filterList(ArrayList<Contact> filterlist) {
//        // below line is to add our filtered
//        // list in our course array list.
//        contacts = filterlist;
//        // below line is to notify our adapter
//        // as change in recycler view data.
//        notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(final ContactViewHolder viewHolder, final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.title.setText(contacts.get(position).getName());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}