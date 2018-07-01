package com.sadik.android.journalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadik.android.journalapp.database.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private static final String TIME_FORMAT="h:mm a";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<JournalEntry> mJournalEntries;
    private final Context mContext;
    // Date formatter
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private final SimpleDateFormat timeFormat= new SimpleDateFormat(TIME_FORMAT,Locale.getDefault());



    public JournalAdapter(Context context,ItemClickListener listener) {
        mContext=context;
        this.mItemClickListener = listener;
    }

    @Override
    public JournalAdapter.JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_layout, parent, false);

        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalAdapter.JournalViewHolder holder, int position) {
        // Determine the values of the wanted data
        JournalEntry journalEntry = mJournalEntries.get(position);
        String title = journalEntry.getTitle();

        String createdAt = dateFormat.format(journalEntry.getCreatedDate());
        //Implement Time Format Here
        String time= timeFormat.format(journalEntry.getCreatedDate());

        //Set values
        holder.journalTitleView.setText(title);
        holder.createddAtView.setText(createdAt);
        holder.timeView.setText(time);
    }

    @Override
    public int getItemCount() {
        if (mJournalEntries == null) {
            return 0;
        }
        return mJournalEntries.size();

    }
    public List<JournalEntry> getJournals() {
        return mJournalEntries;
    }

    public void setJournals(List<JournalEntry> journalEntries) {
        mJournalEntries = journalEntries;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
   public  class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       private final TextView journalTitleView;
       private final TextView createddAtView;
       private final TextView timeView;
     public JournalViewHolder(View itemView){
         super(itemView);
         journalTitleView=itemView.findViewById(R.id.journalTitle);
         createddAtView=itemView.findViewById(R.id.journalCreatedAt);
         timeView=itemView.findViewById(R.id.textView);
         itemView.setOnClickListener(this);
     }

       @Override
       public void onClick(View v) {
           int itmeId=mJournalEntries.get(getAdapterPosition()).getId();
           mItemClickListener.onItemClickListener(itmeId);
       }
   }
}
