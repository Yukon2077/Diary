package diary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yukon.diary.R;

import java.util.ArrayList;
import java.util.List;

import diary.database.DatabaseAdapter;
import diary.database.Entry;
import diary.util.LogAdapter;

public class MainActivity extends AppCompatActivity {
    private DatabaseAdapter db;
    private LogAdapter adapter;
    private SQLiteDatabase mdb;
    //List<Entry> entryList=new ArrayList<>();

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

        /*entryList.addAll(db.getAllEntries());
        LinearLayout lay=findViewById(R.id.main_layout);
        for(Entry entry: entryList){
            Log.d("content",entry.getContent());
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText("Date :"+entry.getDate()+ " \nTime: "+entry.getTime()+ "\nCreated Time :"+entry.getCreatedTime()+" \nContent :"+ entry.getContent() + "\n");
            lay.addView(tv);
        }*/
    }

    public void gotoSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void gotoWriteContent(View view){
        Intent writeContent = new Intent(this, diary.activity.writeContent.class) ;
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

}