package com.sadik.android.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sadik.android.journalapp.database.AppDatabase;
import com.sadik.android.journalapp.database.JournalEntry;

public class AddJournalViewModel extends ViewModel {

    private final LiveData<JournalEntry> journal;


    public AddJournalViewModel(AppDatabase database, int journalId) {
        journal = database.journalDao().loadJournalsById(journalId);
    }


    public LiveData<JournalEntry> getJournal() {
        return journal;
    }
}
