//package com.gautamsolar.creda;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//
//public class DatabaseHelper  extends SQLiteAssetHelper {
//
//
//    //Constants for Database name, table name, and column names
//    public static final String DB_NAME = "CREDA.db";
//    public static final String TABLE_NAME = "CREADA7";
//    public static final String COLUMN_ID = "id";
//    public static final String TABLE_FOUNDATION = "foundation";
//    public static final String TABLE_SITE = "site_survey";
//    public static final String TABLE_INSTALL = "installation";
//    public static final String TABLE_MATERIAL = "material_dispatch";
//    public static final String FNAME = "fname";
//    public static final String REG_NO = "reg_no";
//
//    public static final String FLAT = "flat";
//    public static final String FLON = "flon";
//    public static final String ILAT = "ilat";
//    public static final String ILON = "ilon";
//    public static final String FKEY_POTO1 = "fphoto1";
//    public static final String FKEY_POTO2 = "fphoto2";
//    public static final String FKEY_POTO3 = "fphoto3";
//    public static final String FKEY_POTO4 = "fphoto4";
//    public static final String FKEY_POTO5 = "fphoto5";
//    public static final String IKEY_POTO1 = "iphoto1";
//    public static final String IKEY_POTO2 = "iphoto2";
//    public static final String IKEY_POTO3 = "iphoto3";
//    public static final String IKEY_POTO4 = "iphoto4";
//    public static final String IKEY_POTO5 = "iphoto5";
//    public static final String IKEY_POTO6 = "iphoto6";
//    public static final String IKEY_POTO7 = "iphoto7";
//    public static final String FENGINEER_CONTACT = "fengineer_contact";
//
//    public static final String FDATETIME = "fdatetime";
//    public static final String IDATETIME = "idatetime";
//    public static final String IENGINEER_CONTACT = "iengineer_contact";
//
//    public static final String SLAT = "slat";
//    public static final String SLON = "slon";
//    public static final String SENGINEER_CONTACT = "sengineercontact";
//
//    public static final String SDATETIME = "sdatetime";
//    public static final String SPASSBOOK_IMAGE = "spassbookimage";
//    public static final String SADHAR_IMAGE = "sadharimage";
//    public static final String SPASSPORT_IMAGE = "spassportimage";
//    public static final String SMARKED_IMAGE = "smarkedimage";
//
//    public static final String WATER_LEVEL = "waterlevel";
//    public static final String BORE_DEPTH = "boredepth";
//    public static final String BORE_SIZE = "boresize";
//
//    public static final String BORESTATUS = "borestatus";
//    public static final String EXISTING_MOTOR_RUNNING = "existing_motor_running";
//    private static final int DB_VERSION = 1;
//
//    public DatabaseHelper(Context context)
//    {
//        super(context,DB_NAME,null,DB_VERSION);
//    }
//
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "DROP TABLE IF EXISTS CREADA4";
//        db.execSQL(sql);
//        onCreate(db);
//    }
//
//
//    public boolean addfoundation(String photo1, String photo2, String photo3,
//                           String photo4,String photo5, String lat, String lon, String engineer_contact, String reg_no , String datetime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(FKEY_POTO1, photo1);
//        contentValues.put(FKEY_POTO2, photo2);
//        contentValues.put(FKEY_POTO3, photo3);
//        contentValues.put(FKEY_POTO4, photo4);
//        contentValues.put(FKEY_POTO5, photo5);
//        contentValues.put(FLAT, lat);
//        contentValues.put(FLON, lon);
//        contentValues.put(FENGINEER_CONTACT, engineer_contact);
//        contentValues.put(REG_NO, reg_no);
//        contentValues.put(FDATETIME, datetime);
//
////        db.update(TABLE_NAME, contentValues, REG_NO + "=" + reg_no, null);
//
//        long yourresult=  db.insert(TABLE_NAME, null, contentValues);
//        db.close();
//        if (yourresult==-1)
//        {
//            return  false;
//        }
//        else
//        {
//            return true;
//
//        }
//    }
//
//    public boolean addInstallation(String photo1, String photo2, String photo3,
//                            String photo4,String photo5,String photo6,String photo7, String lat, String lon, String engineer_contact, String reg_no ,String datetime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(IKEY_POTO1, photo1);
//        contentValues.put(IKEY_POTO2, photo2);
//        contentValues.put(IKEY_POTO3, photo3);
//        contentValues.put(IKEY_POTO4, photo4);
//        contentValues.put(IKEY_POTO5, photo5);
//        contentValues.put(IKEY_POTO6, photo6);
//        contentValues.put(IKEY_POTO7, photo7);
//        contentValues.put(ILAT, lat);
//        contentValues.put(ILON, lon);
//        contentValues.put(IENGINEER_CONTACT, engineer_contact);
//        contentValues.put(REG_NO, reg_no);
//        contentValues.put(IDATETIME, datetime);
//
////        db.insert(null, TABLE_NAME, contentValues);
//
//        long result=db.insert(TABLE_NAME, null, contentValues);
//        db.close();
//        if (result==-1)
//        {
//            return  false;
//        }
//        else
//            {
//                return true;
//
//        }
//
//
//    }
//
//    public boolean addNameB(String photo1, String reg_no , int status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(REG_NO, reg_no);
//
//
//        db.update(TABLE_NAME, contentValues, REG_NO + "=" + reg_no, null);
//
//        /*  db.insert(TABLE_NAME, null, contentValues);*/
//        db.close();
//        return true;
//    }
//
//
//
//    public boolean addsitesurvey(String photo1, String photo2, String photo3, String photo4, String boredepth,
//                            String boresize, String waterlevel, String bore_status, String existing_motor_string ,
//                            String lat, String lon, String engineer_contact, String reg_no ,
//                           String datetime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(SPASSPORT_IMAGE, photo1);
//        contentValues.put(SADHAR_IMAGE, photo2);
//        contentValues.put(SPASSBOOK_IMAGE, photo3);
//        contentValues.put(SMARKED_IMAGE, photo4);
//        contentValues.put(BORE_DEPTH, boredepth);
//        contentValues.put(BORE_SIZE, boresize);
//        contentValues.put(WATER_LEVEL, waterlevel);
//        contentValues.put(BORESTATUS, bore_status);
//        contentValues.put(EXISTING_MOTOR_RUNNING, existing_motor_string);
//        contentValues.put(SLAT, lat);
//        contentValues.put(SLON, lon);
//        contentValues.put(SENGINEER_CONTACT, engineer_contact);
//        contentValues.put(REG_NO, reg_no);
//        contentValues.put(SDATETIME, datetime);
////        db.update(TABLE_NAME, contentValues, REG_NO + "=" + reg_no, null);
//
//      long myresult=   db.insert(TABLE_NAME, null, contentValues);
//        db.close();
//        if (myresult==-1)
//        {
//            return  false;
//        }
//        else
//        {
//            return true;
//
//        }
//
//    }
//
//
//
//
//
//    public boolean updateNameStatus(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
//        db.close();
//        return true;
//    }
//
//    public boolean updateNameStatusI(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
//        db.close();
//        return true;
//    }
//    public boolean updateNameStatusS(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
//        db.close();
//        return true;
//    }
//    public boolean updateNameStatusB(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
//        db.close();
//        return true;
//    }
//
//
//
//    public Cursor getNames() {
//        SQLiteDatabase db= getWritableDatabase();
//        Cursor cursor = db.rawQuery(" Select * from "+TABLE_NAME, null);
//
//        return cursor;
//
//    }
//    public String[] getAppCategoryDetail() {
//
//
//
//        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db  = this.getReadableDatabase();
//        Cursor cursor      = db.rawQuery(selectQuery, null);
//        String[] data      = null;
//
//        if (cursor.moveToFirst()) {
//            do {
//                // get the data into array, or class variable
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return data;
//    }
//
//    public Cursor getNames1(String reg_no) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c= db.rawQuery( "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " +
//                DatabaseHelper.REG_NO + " =? ",new String[]{reg_no});
//        /*  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + REGISTRATION_NUMBER + " = Registration_number;";
//         *//* String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";*//*
//        Cursor c = db.rawQuery(sql, null);*/
//        return c;
//    }
//    public Cursor getNamesS(String reg_no) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c= db.rawQuery( "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " +
//                DatabaseHelper.REG_NO + " =? ",new String[]{reg_no});
//        /*  String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + REGISTRATION_NUMBER + " = Registration_number;";
//         *//* String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";*//*
//        Cursor c = db.rawQuery(sql, null);*/
//        return c;
//    }
//
//
//    /*
//     * this method is for getting all the unsynced name
//     * so that we can sync it with database
//     * */
//    public Cursor getUnsyncedNames() {
//        SQLiteDatabase db= getWritableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME +"WHERE" ;
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//    public Cursor getUnsyncedNamesI() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME ;
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//    public Cursor getUnsyncedNamesS() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME ;
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//    public Cursor getUnsyncedNamesB() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME ;
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//
//
//}
