package com.seymour.todolist.DB;

/**
 * Created by seymour on 2016/05/27.
 */
public class DBValues {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String CONTENT="content";
    static final String DATE="date";

    //DB PROPS
    static final String DB_NAME="DB";
    static final String TB_NAME="TB";
    static final int DB_VERSION=1;


    //CREATE TABLE
    static final String CREATE_TB="CREATE TABLE TB(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL," +
            "content TEXT NOT NULL," +
            "date TEXT NOT NULL);";

    //DROP TB
    static final String DROP_TB="DRP TABLE IF EXISTS "+TB_NAME;
}
