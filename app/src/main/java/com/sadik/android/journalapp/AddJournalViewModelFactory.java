package com.sadik.android.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sadik.android.journalapp.database.AppDatabase;

public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final int mJournalId;

    public AddJournalViewModelFactory(AppDatabase mDb, int mJournalId) {
        this.mDb = mDb;
        this.mJournalId = mJournalId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddJournalViewModel(mDb,mJournalId);
    }
    //getter and setter

    public AppDatabase getmDb() {
        return mDb;
    }

    public int getmJournalId() {
        return mJournalId;
    }

}
