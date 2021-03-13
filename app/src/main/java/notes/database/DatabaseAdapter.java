package notes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;


public class DatabaseAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "notes";


    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Entry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);

        onCreate(db);
    }

    public long insertEntry(String content,String date,String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_DATE, date);
        values.put(Entry.COLUMN_TIME, time);
        values.put(Entry.COLUMN_CONTENT, content);

        long id = db.insert(Entry.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Entry getEntry(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Entry.TABLE_NAME,
                new String[]{Entry.COLUMN_ID, Entry.COLUMN_DATE, Entry.COLUMN_TIME,Entry.COLUMN_CONTENT,Entry.COLUMN_CREATED_TIME},
                Entry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Entry entry = new Entry(
                cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Entry.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Entry.COLUMN_TIME)),
                cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CONTENT)),
                cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CREATED_TIME)));

        cursor.close();

        return entry;
    }

    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Entry.TABLE_NAME + " ORDER BY " +
                Entry.COLUMN_CREATED_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry();
                entry.setId(cursor.getInt(cursor.getColumnIndex(Entry.COLUMN_ID)));
                entry.setDate(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_DATE)));
                entry.setTime(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_TIME)));
                entry.setContent(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CONTENT)));
                entry.setCreatedTime(cursor.getString(cursor.getColumnIndex(Entry.COLUMN_CREATED_TIME)));

                entries.add(entry);
            } while (cursor.moveToNext());
        }

        db.close();

        return entries;
    }

    public int getEntryCount() {
        String countQuery = "SELECT  * FROM " + Entry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entry.COLUMN_CONTENT, entry.getContent());
        values.put(Entry.COLUMN_DATE, entry.getDate());
        values.put(Entry.COLUMN_TIME, entry.getTime());

        return db.update(Entry.TABLE_NAME, values, Entry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(entry.getId())});
    }

    public void deleteEntry(Entry note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Entry.TABLE_NAME, Entry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Entry.TABLE_NAME);
        //db.execSQL("DELETE table " + Entry.TABLE_NAME);
        db.close();
    }
}