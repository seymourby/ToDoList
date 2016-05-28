package com.seymour.todolist.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by seymour on 2016/05/27.
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    //OPEN DB
    public void openDB() {
        try {
            db=helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //CLOSE DB
    public void closeDB() {
        try {
            helper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT/SAVE
    public boolean add(String name,String content,String date) {
        try {
            ContentValues cv=new ContentValues();
            cv.put(DBValues.NAME, name);
            cv.put(DBValues.CONTENT, content);
            cv.put(DBValues.DATE,date);
            db.insert(DBValues.TB_NAME,null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //SELECT/RETRIEVE
    public Cursor retrieve() {
        String[] columns={DBValues.ROW_ID,DBValues.NAME,DBValues.CONTENT,DBValues.DATE};
        return db.query(DBValues.TB_NAME,columns,null,null,null,null,"date asc");
    }

    //DELETE/REMOVE
    public boolean delete(String id) {
        try {
            int result=db.delete(DBValues.TB_NAME,DBValues.ROW_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
