package com.reenexample.datepicker;

/**
 * Created by reen on 7/11/16.
 */
public class Constants {

    static final String ROW_ID = "id";
    static final String DATE = "date";
    static final String TIME = "time";
    static final String TITLE = "title";
    static final String DETAILS = "details";

    static final String DB_NAME = "d_DB";
    static final String TB_NAME = "d_TB";
    static final int DB_VERSION = '1';

    static final String CREATE_TB = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
            " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE + " TEXT, " + TIME + " TEXT, " + TITLE + " TEXT, " +
            DETAILS + " TEXT )";


}
