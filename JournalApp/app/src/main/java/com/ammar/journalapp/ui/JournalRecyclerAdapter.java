package com.ammar.journalapp.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.journalapp.R;
import com.ammar.journalapp.model.Journal;
import com.bumptech.glide.Glide;

import java.util.List;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Journal> journalList;

    public JournalRecyclerAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public JournalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.journal_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerAdapter.ViewHolder holder, int position) {
        Journal journal = journalList.get(position);
        String imageUrl;

        holder.titleTV.setText(journal.getTitle());
        holder.thoughtsTV.setText(journal.getThoughts());
        holder.nameTV.setText(journal.getUsername());

        imageUrl = journal.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                journal.getTimeAdded().getSeconds() * 1000);

        holder.dateAddedTV.setText(timeAgo);

        /**
         * Using Glide Library to load image into ImageView
         * */

        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .into(holder.journalIV);
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTV, thoughtsTV, dateAddedTV, nameTV;
        public ImageView journalIV;
        public ImageView shareBtn;
        String userId;
        String username;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;
            // // These widgets: Belongs to Journal_ row .xml
            titleTV = itemView.findViewById(R.id.journal_title_list);
            thoughtsTV = itemView.findViewById(R.id.journal_thought_list);
            dateAddedTV = itemView.findViewById(R.id.journal_timestamp_list);
            nameTV = itemView.findViewById(R.id.journal_row_username);

            journalIV = itemView.findViewById(R.id.journal_image_list);
            shareBtn = itemView.findViewById(R.id.journal_row_share_button);

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // share the post!!!!!!!
                }
            });
        }
    }




}
