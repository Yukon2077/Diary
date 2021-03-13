package notes.database;


public class Entry {
    public static final String TABLE_NAME = "entries";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CREATED_TIME = "createdOn";

    private int id;
    private String date;
    private String time;
    private String content;
    private String createdOn;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " DATE,"
                    + COLUMN_TIME + " TIME,"
                    + COLUMN_CONTENT + " TEXT,"
                    + COLUMN_CREATED_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Entry() {
    }

    public Entry(int id, String content, String date,String time,String createdOn) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.content=content;
        this.createdOn=createdOn;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getCreatedTime() {
        return createdOn;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setCreatedTime(String createdOn) {
        this.createdOn = createdOn;
    }
}