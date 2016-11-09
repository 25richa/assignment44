package com.reenexample.datepicker;

/**
 * Created by reen on 7/11/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    Context c;
    SQLiteDatabase db;
    DBHelper helper;
    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }
    //OPEN DATABASE
    public DBAdapter openDB()
    {
        try {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    //CLOSE DATABASE
    public void closeDB()
    {
        try {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //INSERT
    public long add(String date,String time, String title, String details)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.DATE,date);
            cv.put(Constants.TIME, time);
            cv.put(Constants.TITLE, title);
            cv.put(Constants.DETAILS, details);
            return db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    //RETRIEVE
    public Cursor getAllItems()
    {
        String[] columns={Constants.ROW_ID,Constants.DATE,Constants.TIME, Constants.TITLE, Constants.DETAILS};
        return db.query(Constants.TB_NAME,columns,null,null,null,null,null);
    }
}
