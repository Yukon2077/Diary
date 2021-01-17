package diary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private DatabaseAdapter db;
    List<Entry> entryList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseAdapter(this);
        entryList.addAll(db.getAllEntries());
        LinearLayout lay=findViewById(R.id.main_layout);
        for(Entry entry: entryList){
            Log.d("content",entry.getContent());
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText("Date :"+entry.getDate()+ " Time: "+entry.getTime()+ "Created Time :"+entry.getCreatedTime()+" Content :"+ entry.getContent());
            lay.addView(tv);
        }
    }

    public void gotoSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void createLogBox(View view){

    }

    public void gotoWriteContent(View view){
        Intent writeContent = new Intent(this, diary.activity.writeContent.class) ;
        startActivity(writeContent);
    }

}