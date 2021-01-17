package diary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.yukon.diary.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = openOrCreateDatabase("Log",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Log(ID INT,Date DATE,Time TIME,Content VARCHAR(1000000));");

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