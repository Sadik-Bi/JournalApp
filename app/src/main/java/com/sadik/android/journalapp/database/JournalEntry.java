package com.sadik.android.journalapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="Journal")
public class JournalEntry {
    @PrimaryKey(autoGenerate=true)
    private int id;
    private String title;
    private String detailNote;
    @ColumnInfo(name = "created_Date")
    private Date createdDate;

    public JournalEntry(int id, String title, String detailNote, Date createdDate) {
        this.id = id;
        this.title = title;
        this.detailNote = detailNote;
        this.createdDate = createdDate;
    }
    @Ignore
    public JournalEntry(String title, String detailNote, Date createdDate) {
        this.title = title;
        this.detailNote = detailNote;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailNote() {
        return detailNote;
    }

    public void setDetailNote(String detailNote) {
        this.detailNote = detailNote;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
