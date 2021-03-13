package notes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yukon.notes.R;

import java.sql.Time;
import java.util.Calendar;

import notes.database.DatabaseAdapter;
import notes.database.Entry;
import notes.util.LogAdapter;

import static java.lang.Integer.parseInt;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseAdapter db;
    private SQLiteDatabase mdb;
    private LogAdapter adapter;
    public TextView writeContent;
    public Button dateButton;
    public Button timeButton;
    private String selectedDate;
    private String selectedTime;
    public Entry entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new DatabaseAdapter(this);
        SQLiteDatabase mdb = db.getWritableDatabase();
        /*int id =0;

        try {
            id = Integer.parseInt(getIntent().getStringExtra("ID"));
        } catch (NumberFormatException e) {
            finish();
        }*/
        int id = getIntent().getIntExtra("ID",0);
        //int idint = Integer.parseInt(id.trim());
        String date = getIntent().getStringExtra("DATE");
        String time = getIntent().getStringExtra("TIME");
        String content = getIntent().getStringExtra("CONTENT");
        String ctime = getIntent().getStringExtra("CTIME");

        entry= new Entry( id, content, date, time, ctime);


        writeContent = findViewById(R.id.updatecontent);
        writeContent.setText(content);

        dateButton = findViewById(R.id.dateButton);
        dateButton.setText(date);

        timeButton = findViewById(R.id.timeButton);
        timeButton.setText(time);


        adapter = new LogAdapter(this, getAllItems());
    }

    public void save(View view){
        db = new DatabaseAdapter(this);
        SQLiteDatabase mdb = db.getWritableDatabase();
        String content= writeContent.getText().toString();
        String date = dateButton.getText().toString();
        String time= timeButton.getText().toString();
        if(content != null && !content.equals("")) {
            entry.setContent(content);
            entry.setDate(date);
            entry.setTime(time);
            db.updateEntry(entry);
        }
        adapter.swapCursor(getAllItems());
        finish();
    }

    public Cursor getAllItems(){
        SQLiteDatabase mdb = db.getReadableDatabase();
        return mdb.query(Entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Entry.COLUMN_ID + " DESC");
    }

    public void showDateDialog(View view){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateButton.setText(dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                        //Date d = new Date(year, monthOfYear+1, dayOfMonth);
                        selectedDate = (String) dateButton.getText();//d.toString();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void showTimeDialog(View view){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        selectedTime= mHour + ":" + mMinute+ ":" + "00";
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String time=hourOfDay + ":" + minute+ ":" + "00";
                        timeButton.setText(hourOfDay + ":" + minute+":"+"00");
                        selectedTime= Time.valueOf(time).toString();
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void back(View view){
        finish();
    }
}