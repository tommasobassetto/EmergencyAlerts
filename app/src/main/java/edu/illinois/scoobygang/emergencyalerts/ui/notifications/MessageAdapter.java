package edu.illinois.scoobygang.emergencyalerts.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import edu.illinois.scoobygang.emergencyalerts.R;
import edu.illinois.scoobygang.emergencyalerts.data.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>{
    List<Message> list = Collections.emptyList();

//    Context context;
//    ClickListener listener;

    public MessageAdapter(List<Message> list)
    {
        this.list = list;
//        this.context = context;
//        this.listener = listener;
    }

    @Override
    public MessageViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View photoView = inflater.inflate(R.layout.item_message, parent, false);

        MessageViewHolder viewHolder = new MessageViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final MessageViewHolder viewHolder, final int position) {
//        final index = viewHolder.getAdapterPosition();
        viewHolder.title.setText(list.get(position).title);
//        viewHolder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                listener.click(index);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
