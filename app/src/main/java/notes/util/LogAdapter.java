package notes.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yukon.notes.R;

import notes.activity.UpdateActivity;
import notes.database.Entry;

public class LogAdapter extends RecyclerView.Adapter <LogAdapter.LogViewHolder> {
    private Context mContext;
    private Cursor mCursor;


    public LogAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder{

        public TextView dateText;
        public TextView timeText;
        public TextView createdTimeText;
        public TextView contentText;
        public View layout;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            dateText = itemView.findViewById(R.id.textView_date);
            timeText = itemView.findViewById(R.id.textView_time);
            createdTimeText = itemView.findViewById(R.id.textView_created_time);
            contentText = itemView.findViewById(R.id.textView_content);
            layout = itemView.findViewById(R.id.linearLayout2);

        }


    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view =  inflater.inflate(R.layout.entry_list, parent, false);
        return  new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {

        if(!mCursor.moveToPosition(position)){
            return;
        }

        String date = mCursor.getString(mCursor.getColumnIndex(Entry.COLUMN_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(Entry.COLUMN_TIME));
        String createdtime = mCursor.getString(mCursor.getColumnIndex(Entry.COLUMN_CREATED_TIME));
        String content = mCursor.getString(mCursor.getColumnIndex(Entry.COLUMN_CONTENT));


        int id = mCursor.getInt(mCursor.getColumnIndex(Entry.COLUMN_ID));

        holder.dateText.setText(date);
        holder.timeText.setText(time);
        holder.createdTimeText.setText(createdtime);
        holder.contentText.setText(content);
        holder.itemView.setTag(id);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(mContext, UpdateActivity.class);
                updateIntent.putExtra("ID", id);
                updateIntent.putExtra("CONTENT", String.valueOf(content));
                updateIntent.putExtra("DATE", String.valueOf(date));
                updateIntent.putExtra("TIME", String.valueOf(time));
                updateIntent.putExtra("CTIME", String.valueOf(createdtime));

                mContext.startActivity(updateIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;

        if(newCursor!=null) {
            notifyDataSetChanged();
        }
    }

}
