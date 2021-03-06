package diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yukon.diary.R;

import diary.database.DatabaseAdapter;
import diary.database.Entry;
import diary.util.LogAdapter;

public class MainActivity extends AppCompatActivity{
    private DatabaseAdapter db;
    private LogAdapter adapter;
    private SQLiteDatabase mdb;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseAdapter(this);
        SQLiteDatabase mdb = db.getReadableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter(this, getAllItems());
        recyclerView.setAdapter(adapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

    }

    public void gotoSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void gotoWriteContent(View view){
        Intent writeContent = new Intent(this, WriteContent.class) ;
        startActivity(writeContent);
    }

    private Cursor getAllItems(){
        SQLiteDatabase mdb = db.getReadableDatabase();
        return mdb.query(Entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Entry.COLUMN_ID + " DESC");
    }

    private void removeItem(long id){
        SQLiteDatabase mdb = db.getWritableDatabase();
        mdb.delete(Entry.TABLE_NAME, Entry.COLUMN_ID + "=" + id, null);
        adapter.swapCursor(getAllItems());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter(this, getAllItems());
        recyclerView.setAdapter(adapter);
    }
}
