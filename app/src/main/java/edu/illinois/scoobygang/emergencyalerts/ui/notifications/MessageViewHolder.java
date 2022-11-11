package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.illinois.scoobygang.emergencyalerts.R;

public class MessageViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    View view;

    MessageViewHolder(View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.messageTitle);
        view  = itemView;
    }
}
