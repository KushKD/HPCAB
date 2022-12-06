package com.dit.himachal.rms.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dit.himachal.rms.modal.OfflineDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    boolean bool = false;

    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "RMSV1.db";



    //Offiline Data Table
    private static final String TABLE_OFFLINE_STORAGE = "Offline_Storage";
    private static final String OFFLINE_DATA_ID = "id";
    private static final String OFFLINE_DATA_URL = "url";
    private static final String OFFLINE_DATA_REQUEST_PARAMS = "params";
    private static final String OFFLINE_DATA_RESPOSE ="response";
    private static final String OFFLINE_DATA_FunctionName = "function";
    private static final String OFFLINE_DATA_USERID = "userId";
    private static final String OFFLINE_DATA_ROLLID = "rollId";
    private static final String OFFLINE_DATA_BIFURCATION = "bifurcation";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * @param db  Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {





        String CREATE_OFFLINE_DATA_TABLE = "CREATE TABLE " + TABLE_OFFLINE_STORAGE + "("
                + OFFLINE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + OFFLINE_DATA_URL + " TEXT,"
                + OFFLINE_DATA_REQUEST_PARAMS + " TEXT,"
                + OFFLINE_DATA_RESPOSE + " TEXT ,"
                + OFFLINE_DATA_FunctionName + " TEXT, "
                + OFFLINE_DATA_USERID + " TEXT, "
                + OFFLINE_DATA_ROLLID + " TEXT ,"
                + OFFLINE_DATA_BIFURCATION + " TEXT " + ")";



        db.execSQL(CREATE_OFFLINE_DATA_TABLE);


    }


    /**
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_STORAGE);

        // Create tables again
        onCreate(db);


    }





    /**
     * @param offline_object
     * @return
     */
    public Boolean addOfflineAccess(OfflineDataModel offline_object) {
        SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(OFFLINE_DATA_URL, offline_object.getUrl());
            values.put(OFFLINE_DATA_REQUEST_PARAMS, offline_object.getParams());
            values.put(OFFLINE_DATA_RESPOSE, offline_object.getResponse());
            values.put(OFFLINE_DATA_FunctionName, offline_object.getFunctionName());
            values.put(OFFLINE_DATA_USERID, offline_object.getUserId());
            values.put(OFFLINE_DATA_ROLLID, offline_object.getRoleId());
            values.put(OFFLINE_DATA_BIFURCATION, offline_object.getBifurcation());
            db.insert(TABLE_OFFLINE_STORAGE, null, values);



        db.close(); // Closing database connection

        try {
           // exportDatabse(DATABASE_NAME);
            return true;
        } catch (Exception e) {
            Log.d("Got Error ..", e.getLocalizedMessage());
            return false;
        }

    }


    /**
     * @return  List of all Offline Data
     */
    public List<OfflineDataModel> GetAllOfflineData() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_OFFLINE_STORAGE ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  null);
        List<OfflineDataModel> offline_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            OfflineDataModel data = new OfflineDataModel();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            data.setUrl(cursor.getString(1));
            data.setParams(cursor.getString(2));
            data.setResponse(cursor.getString(3));
            data.setFunctionName(cursor.getString(4));
            data.setUserId(cursor.getString(5));
            data.setRoleId(cursor.getString(6));
            data.setBifurcation(cursor.getString(7));

            offline_db.add(data);
        }
        db.close(); // Closing database connection
        return offline_db;

    }


    /**
     *
     * @param functionName
     * @param userId
     * @param roleID
     * @param bifurcation  is the parent Id OR any other parameter with same object and different level
     * @return
     */
    public List<OfflineDataModel> GetAllOfflineDataViaFunction(String functionName,String userId, String roleID, String bifurcation) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_OFFLINE_STORAGE  + " WHERE function = '" + functionName + "' AND userId = '"+ userId +"' AND rollId = '"+ roleID +"' AND bifurcation = '"+ bifurcation +"'  " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  null);
        List<OfflineDataModel> offline_db = new ArrayList<>();
        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            OfflineDataModel data = new OfflineDataModel();
            //Log.d(KEY_ID_DB, cursor.getString(0));
            data.setUrl(cursor.getString(1));
            data.setParams(cursor.getString(2));
            data.setResponse(cursor.getString(3));
            data.setFunctionName(cursor.getString(4));
            data.setUserId(cursor.getString(5));
            data.setRoleId(cursor.getString(6));
            data.setBifurcation(cursor.getString(7));

            offline_db.add(data);
        }
        db.close(); // Closing database connection
        return offline_db;

    }


    /**
     * @param userId
     * @param roleID
     * @param function
     * @param bifurcation
     * @return
     */

    public int getNoOfRowsBeforeOfflineSave(String userId, String roleID, String function , String bifurcation) {
        String countQuery = "SELECT  * FROM " + TABLE_OFFLINE_STORAGE + " WHERE  userId = '"+ userId +"' AND rollId = '"+ roleID +"' AND function = '"+ function + "' AND bifurcation = '"+ bifurcation +"' " ;
        Log.e("Query",countQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }


    /**
     * @param offline_object
     * @return
     */
    public boolean updateData(OfflineDataModel offline_object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFLINE_DATA_URL, offline_object.getUrl());
        values.put(OFFLINE_DATA_REQUEST_PARAMS, offline_object.getParams());
        values.put(OFFLINE_DATA_RESPOSE, offline_object.getResponse());
        values.put(OFFLINE_DATA_FunctionName, offline_object.getFunctionName());
        values.put(OFFLINE_DATA_USERID, offline_object.getUserId());
        values.put(OFFLINE_DATA_ROLLID, offline_object.getRoleId());
        values.put(OFFLINE_DATA_BIFURCATION,offline_object.getBifurcation());
        db.update(TABLE_OFFLINE_STORAGE, values, "function = ? AND userId = ? AND rollId = ? AND bifurcation = ?",new String[] { offline_object.getFunctionName(),offline_object.getUserId(), offline_object.getRoleId() , offline_object.getBifurcation() });
        return true;
    }


    /**
     * @param hash
     * @return
     */
    //TODO Delete all the Previous Records before Save
    public Integer deleteAllExistingOfflineData(String hash){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_OFFLINE_STORAGE, "hash = ?",new String[] {hash});


    }

    /**
     * @param userID
     * @param roleID
     * @param function
     * @param bifurcation
     * @return
     */
    //TODO Delete all the Previous Records before Save
    public Integer deleteAllExistingOfflineData(String userID, String roleID, String function, String bifurcation){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_OFFLINE_STORAGE, "userID = ? AND rollId = ? AND function = ? AND bifurcation = ?",new String[] {userID,roleID,function, bifurcation});


    }

    /**
     * @param userID
     * @param roleID
     * @return
     */
    public Integer deleteAllExistingOfflineData(String userID, String roleID){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_OFFLINE_STORAGE, "userID = ? AND rollId = ? ",new String[] {userID,roleID});


    }






}