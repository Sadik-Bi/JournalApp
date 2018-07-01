package com.sadik.android.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sadik.android.journalapp.database.AppDatabase;
import com.sadik.android.journalapp.database.JournalEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<JournalEntry>> journals;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        journals = database.journalDao().loadAllJournals();
    }

    public LiveData<List<JournalEntry>> getJournals() {
        return journals;
    }

    public void setJournals(LiveData<List<JournalEntry>> journals) {
        this.journals = journals;
    }
}
