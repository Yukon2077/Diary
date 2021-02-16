package diary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.yukon.diary.R;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import diary.database.DatabaseAdapter;
import diary.database.Entry;
import diary.util.LogAdapter;

public class writeContent extends AppCompatActivity {
    private DatabaseAdapter db;
    private LogAdapter adapter;
    private Button dateButton;
    private Button timeButton;
    private String selectedDate;
    private String selectedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_content);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdfDate.format(new Date());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdfTime.format(new Date());
        selectedDate=currentDate;
        selectedTime=currentTime;
        db = new DatabaseAdapter(this);
        dateButton=findViewById(R.id.dateButton);
        timeButton=findViewById(R.id.timeButton);
        dateButton.setText(currentDate);
        timeButton.setText(currentTime);
        adapter = new LogAdapter(this, getAllItems());

    }

    public void save(View view){
        TextView contentView=findViewById(R.id.content);
        String content=contentView.getText().toString();
        if(content != null && !content.equals("")) {
            db.insertEntry(content, selectedDate, selectedTime);
        }
        adapter.swapCursor(getAllItems());
        finish();
        /*Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);*/
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
}