package diary.util;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yukon.diary.R;

import diary.database.Entry;

public class LogAdapter extends RecyclerView.Adapter <LogAdapter.LogViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public LogAdapter (Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder {

        public TextView dateText;
        public TextView timeText;
        public TextView createdTimeText;
        public TextView contentText;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            dateText = itemView.findViewById(R.id.textView_date);
            timeText = itemView.findViewById(R.id.textView_time);
            createdTimeText = itemView.findViewById(R.id.textView_created_time);
            contentText = itemView.findViewById(R.id.textView_content);

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

        long id = mCursor.getLong(mCursor.getColumnIndex(Entry.COLUMN_ID));

        holder.dateText.setText(date);
        holder.timeText.setText(time);
        holder.createdTimeText.setText(createdtime);
        holder.contentText.setText(content);
        holder.itemView.setTag(id);

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
