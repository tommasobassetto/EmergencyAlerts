package edu.illinois.scoobygang.emergencyalerts.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.illinois.scoobygang.emergencyalerts.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    View view;

    ContactViewHolder(View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.contact_name);
        view  = itemView;
    }
}