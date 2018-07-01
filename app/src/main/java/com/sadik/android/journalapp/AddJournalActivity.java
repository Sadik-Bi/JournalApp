package com.sadik.android.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sadik.android.journalapp.database.AppDatabase;
import com.sadik.android.journalapp.database.JournalEntry;

import java.util.Date;

public class AddJournalActivity extends AppCompatActivity {
    // Extra for the task ID to be received in the intent
    public static final String EXTRA_Journal_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    private static final String INSTANCE_journal_ID = "instanceTaskId";

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_journal_ID = -1;
    // Constant for logging
    private static final String TAG = AddJournalActivity.class.getSimpleName();
    private AppDatabase mDb;
    // Fields for views
    private EditText mTitleEditText;
    private EditText mNoteEditText;



    private Menu menu;
    private MenuItem mItem;

    private int mJournalId = DEFAULT_journal_ID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_add_journal);

        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_journal_ID)) {
            mJournalId = savedInstanceState.getInt(INSTANCE_journal_ID, DEFAULT_journal_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_Journal_ID)) {

            //mItem.setTitle("Update");
            //mButton.setText(R.string.update_button);
            if (mJournalId == DEFAULT_journal_ID) {
                // populate the UI
                mJournalId = intent.getIntExtra(EXTRA_Journal_ID, DEFAULT_journal_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddJournalViewModelFactory factory = new AddJournalViewModelFactory(mDb, mJournalId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddJournalViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddJournalViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getJournal().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getJournal().removeObserver(this);
                        populateUI(journalEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_journal_ID, mJournalId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mTitleEditText = findViewById(R.id.editTextJournalTitle);
        mNoteEditText=findViewById(R.id.editTextJournalDetailNote);
//
    }

    private void onSaveButtonClicked() {
        String title = mTitleEditText.getText().toString();
        String detailNote=mNoteEditText.getText().toString();
        Date date = new Date();
        if(!title.isEmpty() && !detailNote.isEmpty()) {
            final JournalEntry journal = new JournalEntry(title, detailNote, date);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mJournalId == DEFAULT_journal_ID) {
                        // insert new task
                        mDb.journalDao().insertJournal(journal);
                    } else {
                        //update task
                        journal.setId(mJournalId);

                        mDb.journalDao().updateJournal(journal);
                    }
                    finish();
                }
            });
        }
        else{
            Context context = AddJournalActivity.this;
            String textToShow = "Inputs Required";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
        }
    }
    private void populateUI(JournalEntry journal) {
        if (journal == null) {
            return;
        }

        mTitleEditText.setText(journal.getTitle());
        mNoteEditText.setText(journal.getDetailNote());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        this.menu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_save) {

            onSaveButtonClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onSaveButtonClicked();
    }
}
