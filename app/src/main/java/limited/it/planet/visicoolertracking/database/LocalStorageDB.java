package limited.it.planet.visicoolertracking.database;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.activities.ImagesAndLast;
import limited.it.planet.visicoolertracking.util.ServeyModel;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

/**
 * Created by User on 2/3/2018.
 */

public class LocalStorageDB {
    //to local storage table
    public static final String KEY_ROWID = "id";

    public static final String KEY_OUTLET_ID = "outlet_id";
    public static final String KEY_ENTRY_DATE = "entry_date";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TOWN = "town";

    public static final String KEY_TERRITORY = "territory";
    public static final String KEY_DISTRIBUTOR = "distributor";
    public static final String KEY_DB_AREA = "db_area";
    public static final String KEY_CLUSTER_ID = "cluster_id";

    public static final String KEY_OUTLET_NAME = "outlet_name";
    public static final String KEY_RETAILER_NAME = "retailer_name";
    public static final String KEY_RETAILER_MOBILE= "retailer_mobile";
    public static final String KEY_ADDRESS = "address";

    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_COOLER_STATUS = "cooler_status";
    public static final String KEY_COOLER_PURITY= "cooler_purity";
    public static final String KEY_COOLER_CHARGING = "cooler_charging";

    public static final String KEY_SKU_GRB = "sku_grb";
    public static final String KEY_SKU_CAN = "sku_can";
    public static final String KEY_SKU_GO_PACK = "sku_go_pack";
    public static final String KEY_CAN_400_ML = "sku_400_ml";
    public static final String KEY_CAN_500_ML = "sku_500_ml";
    public static final String KEY_SKU_1_LITER = "sku_1_liter";
    public static final String KEY_SKU_2_LITER = "sku_2_liter";
    public static final String KEY_SKU_AQUAFINA = "sku_aquafina";


    public static final String KEY_NO_OF_ACTIVE_COOLER = "no_of_active_cooler";
    public static final String KEY_LIGHT_WORKING= "light_working";
    public static final String KEY_NO_OF_SHELVES= "no_of_shelves";
    public static final String KEY_CLEANLINESS= "cleanliness";
    public static final String KEY_PRIME_POSITION= "prime_position";
    public static final String KEY_REMARKS = "remarks";

    public static final String KEY_AVAILABILTY = "availability";
    public static final String KEY_SYNC_STATUS = "sync_status";

    public static final String KEY_COOLER_PIC_PATH = "cooler_pic_path";
    public static final String KEY_OUTLET_PIC_PATH = "outlet_pic_path";

    public static final String KEY_COOLER_PIC_CODE = "cooler_pic_code";
    public static final String KEY_OUTLET_PIC_CODE = "outlet_pic_code";

    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    // db version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "localstorage";
    private static final String DATABASE_TABLE_SERVEY_STORAGE = "table_servey_storage";
    private static final String DATABASE_TABLE_BASIC_INFORMATION = "table_basic_information";

    private LocalStorageDB.DBHelper dbhelper;
    private final Context context;
    private SQLiteDatabase database;

    private TableLayout mtableLayout,mCloumnHeader;

    private static class DBHelper extends SQLiteOpenHelper {

        @SuppressLint("NewApi")
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create table to store msgs
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_SERVEY_STORAGE + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_OUTLET_ID + " TEXT, "
                    + KEY_CHANNEL + " TEXT, "
                    + KEY_COOLER_STATUS + " TEXT, "
                    + KEY_COOLER_CHARGING + " TEXT, "
                    + KEY_SKU_GRB + " TEXT, "
                    + KEY_CAN_500_ML + " TEXT, "
                    + KEY_NO_OF_SHELVES + " TEXT, "
                    + KEY_CLEANLINESS + " TEXT, "
                    + KEY_PRIME_POSITION + " TEXT, "
                    + KEY_AVAILABILTY + " TEXT, "
                    + KEY_REMARKS + " TEXT, "
                    + KEY_LATITUDE + " TEXT, "
                    + KEY_LONGITUDE + " TEXT, "
                    + KEY_USER_ID + " TEXT, "
                    + KEY_ENTRY_DATE + " TEXT, "
                    + KEY_COOLER_PIC_PATH + " TEXT, "
                    + KEY_OUTLET_PIC_PATH + " TEXT, "
                    + KEY_COOLER_PIC_CODE + " TEXT, "
                    + KEY_OUTLET_PIC_CODE + " TEXT, "
                    + KEY_COOLER_PURITY + " TEXT, "
                    + KEY_SKU_CAN + " TEXT, "
                    + KEY_SKU_GO_PACK + " TEXT, "
                    + KEY_CAN_400_ML + " TEXT, "
                    + KEY_SKU_1_LITER + " TEXT, "
                    + KEY_SKU_2_LITER + " TEXT, "
                    + KEY_SKU_AQUAFINA + " TEXT, "
                    + KEY_NO_OF_ACTIVE_COOLER + " TEXT, "
                    + KEY_LIGHT_WORKING + " TEXT, "
                    + KEY_START_TIME + " TEXT, "
                    + KEY_END_TIME + " TEXT, "
                    + KEY_SYNC_STATUS+ " TEXT );");
            //table basic information

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_BASIC_INFORMATION + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_OUTLET_ID + " TEXT, "
                    + KEY_TOWN + " TEXT, "
                    + KEY_TERRITORY + " TEXT, "
                    + KEY_DISTRIBUTOR + " TEXT, "
                    + KEY_DB_AREA + " TEXT, "
                    + KEY_CLUSTER_ID + " INTEGER, "
                    + KEY_OUTLET_NAME + " TEXT, "
                    + KEY_RETAILER_NAME + " TEXT, "
                    + KEY_RETAILER_MOBILE + " TEXT, "
                    + KEY_ADDRESS+ " TEXT );");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SERVEY_STORAGE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BASIC_INFORMATION);

            onCreate(db);
        }

    }
    // constructor
    public LocalStorageDB(Context c) {
        context = c;


    }
    public LocalStorageDB(Context c,TableLayout tableLayout,TableLayout cloumnHeader) {
        context = c;
         this.mtableLayout=tableLayout;
         this.mCloumnHeader = cloumnHeader;

    }

    // open db
    public LocalStorageDB open() {
        dbhelper = new  DBHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    // close db
    public void close() {
        dbhelper.close();
    }

    // insert login information
    public long saveServeyStorageEntry(String outletId,String channel,String coolerStatus,
                               String coolerCharging,String skuGRB,String sku500ml,String noOfShelves,
                                      String cleanLiness,String primePosition
                                        ,String availability,String remarks,
                                      String latitude,
                                      String longitude,String userID,String entryDate,
                                      String coolerPicPath,String outletPicPath,String coolerPicCode,String outletPicCode,String coolerPurity,
                                      String skuCan,String skuGOPack,String sku400ml,
                               String skuOneLiter,String skuTwoLiter,String aquafina
                               ,String noOfActiveCooler,
                               String lightWorking,String startTime,String endTime,String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_ID, outletId);

        cv.put(KEY_CHANNEL,  channel);
        cv.put(KEY_COOLER_STATUS,  coolerStatus);
        cv.put(KEY_COOLER_CHARGING,  coolerCharging);
        cv.put(KEY_SKU_GRB,  skuGRB);
        cv.put(KEY_CAN_500_ML,  sku500ml);
        cv.put(KEY_NO_OF_SHELVES,  noOfShelves);
        cv.put(KEY_CLEANLINESS,  cleanLiness);
        cv.put(KEY_PRIME_POSITION,  primePosition);
        cv.put(KEY_AVAILABILTY,  availability);
        cv.put(KEY_REMARKS,  remarks);
        cv.put(KEY_LATITUDE,  latitude);
        cv.put(KEY_LONGITUDE,  longitude);
        cv.put(KEY_USER_ID,  userID);
        cv.put(KEY_ENTRY_DATE,  entryDate);
        cv.put(KEY_COOLER_PIC_PATH,  coolerPicPath);
        cv.put(KEY_OUTLET_PIC_PATH,  outletPicPath);
        cv.put(KEY_COOLER_PIC_CODE,  coolerPicCode);
        cv.put(KEY_OUTLET_PIC_CODE,  outletPicCode);
        cv.put(KEY_COOLER_PURITY,  coolerPurity);
        cv.put(KEY_SKU_CAN,  skuCan);
        cv.put(KEY_SKU_GO_PACK,  skuGOPack);
        cv.put(KEY_CAN_400_ML,  sku400ml);
        cv.put(KEY_SKU_1_LITER,  skuOneLiter);
        cv.put(KEY_SKU_2_LITER,  skuTwoLiter);
        cv.put(KEY_SKU_AQUAFINA,  aquafina);
        cv.put(KEY_NO_OF_ACTIVE_COOLER,  noOfActiveCooler);
        cv.put(KEY_LIGHT_WORKING,  lightWorking);
        cv.put(KEY_START_TIME,  startTime);
        cv.put(KEY_END_TIME,  endTime);

        cv.put(KEY_SYNC_STATUS,  syncStatus);

        long dbInsert = database.insert(DATABASE_TABLE_SERVEY_STORAGE, null, cv);
        saveToSharedPreferences("rowid",String.valueOf(dbInsert),context);

        if(dbInsert != -1) {
            Toast.makeText(context, "New row added in local storage, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }

        return dbInsert;

    }
    public long saveBasicInformation(String outletId,String town,String territory,String distributor,String dbArea,int clusterId,String ouletName,
                                      String retailerName,
                                      String retailerMobile,String address ){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_ID, outletId);
        cv.put(KEY_TOWN, town);
        cv.put(KEY_TERRITORY, territory);
        cv.put(KEY_DISTRIBUTOR,  distributor);
        cv.put(KEY_DB_AREA,  dbArea);
        cv.put(KEY_CLUSTER_ID,  clusterId);

        cv.put(KEY_OUTLET_NAME,  ouletName);
        cv.put(KEY_RETAILER_NAME,  retailerName);
        cv.put(KEY_RETAILER_MOBILE,  retailerMobile);
        cv.put(KEY_ADDRESS, address);

        long dbInsert = database.insert(DATABASE_TABLE_SERVEY_STORAGE, null, cv);

        if(dbInsert != -1) {
            saveToSharedPreferences("rowid",String.valueOf(dbInsert),context);

            Toast.makeText(context, "New row added in Basic Information, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }

        return dbInsert;

    }

//    public void updateSyncStatus(long ROWID,String syncResult){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_SYNC_STATUS, syncResult);
//        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "id=" + ROWID, null);
//        //saveToSharedPreferences("rowid","",context);
//
//    }
public void updateSyncStatus(String outletId,String syncResult){
    ContentValues cv = new ContentValues();
    cv.put(KEY_SYNC_STATUS, syncResult);
    database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);

}

    public void updateFailledCoolerPicCode(String outletId,String coolerPicCode){
        ContentValues cv = new ContentValues();
        cv.put(KEY_COOLER_PIC_CODE, coolerPicCode);
        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);

    }

    public void updateFailledOutletPicCode(String outletId,String outletPicCode){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_PIC_CODE, outletPicCode);
        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);

    }
    public  String selectOutletID (String userId){
        String outletId = "";
        String select_query = "SELECT  outlet_id FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            outletId = cursor.getString(iOutletId);

        }

        return  outletId;
    }

    public boolean checkOutletId(String outletId) throws SQLException
    {
        Cursor cursor = database.query(DATABASE_TABLE_SERVEY_STORAGE, null, " outlet_id=? ",
                new String[] { outletId }, null, null, null);

        if (cursor != null) {
            if(cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public  int getTodaysNoOfRows(String entryDate,String userId){
        ArrayList allEntryDateList = new ArrayList();
        int listSize = 0;

        String select_query = "SELECT  entry_date FROM " + DATABASE_TABLE_SERVEY_STORAGE +
        " WHERE " + KEY_ENTRY_DATE + " = '" + entryDate + "'" + " And " + KEY_USER_ID + " = '" + userId + "'" ;

        Cursor cursor = database.rawQuery(select_query, null);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
          String   lastEntryDate = cursor.getString(iEntryDate);
          allEntryDateList.add(lastEntryDate);
            listSize = allEntryDateList.size();

        }


        return listSize;
    }
//+ KEY_OUTLET_ID + " = '" + outletId + "'" + " AND " +
    public  String selectLastEntryDate(String outletId){
        String lastEntryDate = "";
        String select_query = "SELECT  entry_date FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            lastEntryDate = cursor.getString(iEntryDate);

        }

        return  lastEntryDate;
    }
    public void updateServeyDataForSameUser(String outletId,String channel,String coolerStatus,
                                            String coolerCharging,String skuGRB,String sku500ml,String noOfShelves,
                                            String cleanLiness,String primePosition
            ,String availability,String remarks,
                                            String latitude,
                                            String longitude,String userID,String entryDate,
                                            String coolerPicPath,String outletPicPath,String coolerPicCode,String outletPicCode,String coolerPurity,
                                            String skuCan,String skuGOPack,String sku400ml,
                                            String skuOneLiter,String skuTwoLiter,String aquafina
            ,String noOfActiveCooler,
                                            String lightWorking,String startTime,String endTime,String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_ID, outletId);

        cv.put(KEY_CHANNEL,  channel);
        cv.put(KEY_COOLER_STATUS,  coolerStatus);
        cv.put(KEY_COOLER_CHARGING,  coolerCharging);
        cv.put(KEY_SKU_GRB,  skuGRB);
        cv.put(KEY_CAN_500_ML,  sku500ml);
        cv.put(KEY_NO_OF_SHELVES,  noOfShelves);
        cv.put(KEY_CLEANLINESS,  cleanLiness);
        cv.put(KEY_PRIME_POSITION,  primePosition);
        cv.put(KEY_AVAILABILTY,  availability);
        cv.put(KEY_REMARKS,  remarks);
        cv.put(KEY_LATITUDE,  latitude);
        cv.put(KEY_LONGITUDE,  longitude);
        cv.put(KEY_USER_ID,  userID);
        cv.put(KEY_ENTRY_DATE,  entryDate);
        cv.put(KEY_COOLER_PIC_PATH,  coolerPicPath);
        cv.put(KEY_OUTLET_PIC_PATH,  outletPicPath);
        cv.put(KEY_COOLER_PIC_CODE,  coolerPicCode);
        cv.put(KEY_OUTLET_PIC_CODE,  outletPicCode);
        cv.put(KEY_COOLER_PURITY,  coolerPurity);
        cv.put(KEY_SKU_CAN,  skuCan);
        cv.put(KEY_SKU_GO_PACK,  skuGOPack);
        cv.put(KEY_CAN_400_ML,  sku400ml);
        cv.put(KEY_SKU_1_LITER,  skuOneLiter);
        cv.put(KEY_SKU_2_LITER,  skuTwoLiter);
        cv.put(KEY_SKU_AQUAFINA,  aquafina);
        cv.put(KEY_NO_OF_ACTIVE_COOLER,  noOfActiveCooler);
        cv.put(KEY_LIGHT_WORKING,  lightWorking);
        cv.put(KEY_START_TIME,  startTime);
        cv.put(KEY_END_TIME,  endTime);

        cv.put(KEY_SYNC_STATUS,  syncStatus);

       long dbUpdate = database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);

        if(dbUpdate != -1) {
            saveToSharedPreferences("rowid",String.valueOf(dbUpdate),context);

            Toast.makeText(context, "New row upadated in Basic Information, row id: " + dbUpdate, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }


    }


    public ArrayList checkFailedDataFromTable(String syncStatus){

        ArrayList<ServeyModel> serveyList = new ArrayList<>();
        String select_query = "SELECT * FROM " + DATABASE_TABLE_SERVEY_STORAGE + " WHERE " + KEY_SYNC_STATUS + " = '" + syncStatus + "'";

        Cursor cursor = database.rawQuery(select_query,null);
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
        int iChannel = cursor.getColumnIndex(KEY_CHANNEL);
        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
        int iCoolerCharging = cursor.getColumnIndex(KEY_COOLER_CHARGING);
        int iGRB = cursor.getColumnIndex(KEY_SKU_GRB);
        int iCan500 = cursor.getColumnIndex(KEY_CAN_500_ML);
        int iShelves = cursor.getColumnIndex(KEY_NO_OF_SHELVES);
        int iCleanlines = cursor.getColumnIndex(KEY_CLEANLINESS);
        int iPrimePosition = cursor.getColumnIndex(KEY_PRIME_POSITION);
        int iAvailabilty = cursor.getColumnIndex(KEY_AVAILABILTY);
        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
        int iUserId = cursor.getColumnIndex(KEY_USER_ID);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        int iCoolerPID = cursor.getColumnIndex(KEY_COOLER_PIC_PATH);
        int iOutletPID = cursor.getColumnIndex(KEY_OUTLET_PIC_PATH);
        int iCoolerPicCode = cursor.getColumnIndex(KEY_COOLER_PIC_CODE);
        int iOutletPicCode = cursor.getColumnIndex(KEY_OUTLET_PIC_CODE);

        int iCulerPurity = cursor.getColumnIndex(KEY_COOLER_PURITY);
        int iCan = cursor.getColumnIndex(KEY_SKU_CAN);
        int iGoPack = cursor.getColumnIndex(KEY_SKU_GO_PACK);
        int iCan400 = cursor.getColumnIndex(KEY_CAN_400_ML);
        int iLtr1 = cursor.getColumnIndex(KEY_SKU_1_LITER);
        int iLtr2 = cursor.getColumnIndex(KEY_SKU_2_LITER);
        int iAquafina = cursor.getColumnIndex(KEY_SKU_AQUAFINA);
        int iCoolerActive = cursor.getColumnIndex(KEY_NO_OF_ACTIVE_COOLER);
        int iLightWorking = cursor.getColumnIndex(KEY_LIGHT_WORKING);
        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            ServeyModel serveyModel = new ServeyModel();
            serveyModel.setOutletId(cursor.getString(iOutletId));
            serveyModel.setChannel(cursor.getString(iChannel));
            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
            serveyModel.setCoolerCharging(cursor.getString(iCoolerCharging));

            serveyModel.setGrb(cursor.getString(iGRB));
            serveyModel.setCan500(cursor.getString(iCan500));
            serveyModel.setShelves(cursor.getString(iShelves));
            serveyModel.setCleanliness(cursor.getString(iCleanlines));

            serveyModel.setPrimePosition(cursor.getString(iPrimePosition));
            serveyModel.setAvailabilty(cursor.getString(iAvailabilty));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));

            serveyModel.setUserId(cursor.getString(iUserId));

            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setCoolerPID(cursor.getString(iCoolerPID));
            serveyModel.setOutletPID(cursor.getString(iOutletPID));
            serveyModel.setCoolerPicCode(cursor.getString(iCoolerPicCode));
            serveyModel.setOutletPicCode(cursor.getString(iOutletPicCode));

            serveyModel.setCoolerPurity(cursor.getString(iCulerPurity));
            serveyModel.setCan(cursor.getString(iCan));
            serveyModel.setGoPack(cursor.getString(iGoPack));
            serveyModel.setCan400(cursor.getString(iCan400));

            serveyModel.setLtr1(cursor.getString(iLtr1));
            serveyModel.setLtr2(cursor.getString(iLtr2));
            serveyModel.setAquafina(cursor.getString(iAquafina));
            serveyModel.setCoolerActive(cursor.getString(iCoolerActive));

            serveyModel.setLightWorking(cursor.getString(iLightWorking));
            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setSyncStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);

        }
        cursor.close();
        return serveyList;
    }

    public ArrayList getALLInputData(String userId,String loginDate){

        ArrayList<ServeyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        //Tr: Modified
        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_ID + " = '" + userId + "'" + " AND " + KEY_ENTRY_DATE + " = '" + loginDate + "'" ;

        Cursor cursor = database.rawQuery(select_query,null);

       // if(cursor != null && cursor.moveToFirst()){
            //int iDbId = cursor.getColumnIndex(KEY_ROWID);
            int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
            int iChannel = cursor.getColumnIndex(KEY_CHANNEL);
            int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
            int iCoolerCharging = cursor.getColumnIndex(KEY_COOLER_CHARGING);
            int iGRB = cursor.getColumnIndex(KEY_SKU_GRB);
            int iCan500 = cursor.getColumnIndex(KEY_CAN_500_ML);
            int iShelves = cursor.getColumnIndex(KEY_NO_OF_SHELVES);
            int iCleanlines = cursor.getColumnIndex(KEY_CLEANLINESS);
            int iPrimePosition = cursor.getColumnIndex(KEY_PRIME_POSITION);
            int iAvailabilty = cursor.getColumnIndex(KEY_AVAILABILTY);
            int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
            int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
            int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
            int iUserId = cursor.getColumnIndex(KEY_USER_ID);
            int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

            int iCoolerPID = cursor.getColumnIndex(KEY_COOLER_PIC_CODE);
            int iOutletPID = cursor.getColumnIndex(KEY_OUTLET_PIC_CODE);
            int iCulerPurity = cursor.getColumnIndex(KEY_COOLER_PURITY);
            int iCan = cursor.getColumnIndex(KEY_SKU_CAN);
            int iGoPack = cursor.getColumnIndex(KEY_SKU_GO_PACK);
            int iCan400 = cursor.getColumnIndex(KEY_CAN_400_ML);
            int iLtr1 = cursor.getColumnIndex(KEY_SKU_1_LITER);
            int iLtr2 = cursor.getColumnIndex(KEY_SKU_2_LITER);
            int iAquafina = cursor.getColumnIndex(KEY_SKU_AQUAFINA);
            int iCoolerActive = cursor.getColumnIndex(KEY_NO_OF_ACTIVE_COOLER);
            int iLightWorking = cursor.getColumnIndex(KEY_LIGHT_WORKING);
            int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
            int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
            int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

            for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
                //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

                ServeyModel serveyModel = new ServeyModel();
                serveyModel.setOutletId(cursor.getString(iOutletId));
                serveyModel.setChannel(cursor.getString(iChannel));
                serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
                serveyModel.setCoolerCharging(cursor.getString(iCoolerCharging));

                serveyModel.setGrb(cursor.getString(iGRB));
                serveyModel.setCan500(cursor.getString(iCan500));
                serveyModel.setShelves(cursor.getString(iShelves));
                serveyModel.setCleanliness(cursor.getString(iCleanlines));

                serveyModel.setPrimePosition(cursor.getString(iPrimePosition));
                serveyModel.setAvailabilty(cursor.getString(iAvailabilty));

                serveyModel.setRemarks(cursor.getString(iRemarks));
                serveyModel.setLatitude(cursor.getString(iLatitude));
                serveyModel.setLongitude(cursor.getString(iLongitude));

                serveyModel.setUserId(cursor.getString(iUserId));

                serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setCoolerPID(cursor.getString(iCoolerPID));
            serveyModel.setOutletPID(cursor.getString(iOutletPID));
                serveyModel.setCoolerPurity(cursor.getString(iCulerPurity));
                serveyModel.setCan(cursor.getString(iCan));
                serveyModel.setGoPack(cursor.getString(iGoPack));
                serveyModel.setCan400(cursor.getString(iCan400));

                serveyModel.setLtr1(cursor.getString(iLtr1));
                serveyModel.setLtr2(cursor.getString(iLtr2));
                serveyModel.setAquafina(cursor.getString(iAquafina));
                serveyModel.setCoolerActive(cursor.getString(iCoolerActive));

                serveyModel.setLightWorking(cursor.getString(iLightWorking));
                serveyModel.setStartTime(cursor.getString(iStartTime));
                serveyModel.setEndTime(cursor.getString(iEndTime));
                serveyModel.setSyncStatus(cursor.getString(iSyncStatus));

                serveyList.add(serveyModel);

           // }
            //
        }
        cursor.close();
        return serveyList;
    }

    public ArrayList getAllOfflineData(String userId,String checkSyncStatus){

        ArrayList<ServeyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;

        //Tr: Modified
        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_ID + " = '" + userId + "'" + " AND " + KEY_SYNC_STATUS + " = '" + checkSyncStatus + "'" ;


        Cursor cursor = database.rawQuery(select_query,null);

        // if(cursor != null && cursor.moveToFirst()){
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
        int iChannel = cursor.getColumnIndex(KEY_CHANNEL);
        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
        int iCoolerCharging = cursor.getColumnIndex(KEY_COOLER_CHARGING);
        int iGRB = cursor.getColumnIndex(KEY_SKU_GRB);
        int iCan500 = cursor.getColumnIndex(KEY_CAN_500_ML);
        int iShelves = cursor.getColumnIndex(KEY_NO_OF_SHELVES);
        int iCleanlines = cursor.getColumnIndex(KEY_CLEANLINESS);
        int iPrimePosition = cursor.getColumnIndex(KEY_PRIME_POSITION);
        int iAvailabilty = cursor.getColumnIndex(KEY_AVAILABILTY);
        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
        int iUserId = cursor.getColumnIndex(KEY_USER_ID);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        int iCoolerPID = cursor.getColumnIndex(KEY_COOLER_PIC_CODE);
        int iOutletPID = cursor.getColumnIndex(KEY_OUTLET_PIC_CODE);
        int iCulerPurity = cursor.getColumnIndex(KEY_COOLER_PURITY);
        int iCan = cursor.getColumnIndex(KEY_SKU_CAN);
        int iGoPack = cursor.getColumnIndex(KEY_SKU_GO_PACK);
        int iCan400 = cursor.getColumnIndex(KEY_CAN_400_ML);
        int iLtr1 = cursor.getColumnIndex(KEY_SKU_1_LITER);
        int iLtr2 = cursor.getColumnIndex(KEY_SKU_2_LITER);
        int iAquafina = cursor.getColumnIndex(KEY_SKU_AQUAFINA);
        int iCoolerActive = cursor.getColumnIndex(KEY_NO_OF_ACTIVE_COOLER);
        int iLightWorking = cursor.getColumnIndex(KEY_LIGHT_WORKING);
        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            ServeyModel serveyModel = new ServeyModel();
            serveyModel.setOutletId(cursor.getString(iOutletId));
            serveyModel.setChannel(cursor.getString(iChannel));
            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
            serveyModel.setCoolerCharging(cursor.getString(iCoolerCharging));

            serveyModel.setGrb(cursor.getString(iGRB));
            serveyModel.setCan500(cursor.getString(iCan500));
            serveyModel.setShelves(cursor.getString(iShelves));
            serveyModel.setCleanliness(cursor.getString(iCleanlines));

            serveyModel.setPrimePosition(cursor.getString(iPrimePosition));
            serveyModel.setAvailabilty(cursor.getString(iAvailabilty));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));

            serveyModel.setUserId(cursor.getString(iUserId));

            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setCoolerPID(cursor.getString(iCoolerPID));
            serveyModel.setOutletPID(cursor.getString(iOutletPID));
            serveyModel.setCoolerPurity(cursor.getString(iCulerPurity));
            serveyModel.setCan(cursor.getString(iCan));
            serveyModel.setGoPack(cursor.getString(iGoPack));
            serveyModel.setCan400(cursor.getString(iCan400));

            serveyModel.setLtr1(cursor.getString(iLtr1));
            serveyModel.setLtr2(cursor.getString(iLtr2));
            serveyModel.setAquafina(cursor.getString(iAquafina));
            serveyModel.setCoolerActive(cursor.getString(iCoolerActive));

            serveyModel.setLightWorking(cursor.getString(iLightWorking));
            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setSyncStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);

            // }
            //
        }
        cursor.close();
        return serveyList;
    }

    public String getCoolerPicPath(String outletId){

        String coolerPicPath = "";
        String select_query = "SELECT  cooler_pic_path FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iCoolerPicPath = cursor.getColumnIndex(KEY_COOLER_PIC_PATH);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            coolerPicPath = cursor.getString(iCoolerPicPath);

        }

        return  coolerPicPath;
    }

    public String getOutletPicPath(String outletId){

        String outletPicPath = "";
        String select_query = "SELECT  outlet_pic_path FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iOutletPicPath = cursor.getColumnIndex(KEY_OUTLET_PIC_PATH);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            outletPicPath = cursor.getString(iOutletPicPath);

        }

        return  outletPicPath;
    }
    public Cursor selectALlRecords() {

        Cursor c = null;
        try {
            c = database.rawQuery("Select * from "
                    + DATABASE_TABLE_SERVEY_STORAGE, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return c;
    }

    public Cursor getAllInputRecords() {

        Cursor c = null;
        try {
            c = database.rawQuery("Select * from "
                    + DATABASE_TABLE_SERVEY_STORAGE, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return c;
    }

    public ArrayList getSpecificOfflineData(String outletId,String userId){

        ArrayList<ServeyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        //Tr: Modified
        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_ID + " = '" + userId + "'" + " AND " + KEY_OUTLET_ID + " = '" + outletId + "'" ;

        Cursor cursor = database.rawQuery(select_query,null);

        // if(cursor != null && cursor.moveToFirst()){
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
        int iChannel = cursor.getColumnIndex(KEY_CHANNEL);
        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
        int iCoolerCharging = cursor.getColumnIndex(KEY_COOLER_CHARGING);
        int iGRB = cursor.getColumnIndex(KEY_SKU_GRB);
        int iCan500 = cursor.getColumnIndex(KEY_CAN_500_ML);
        int iShelves = cursor.getColumnIndex(KEY_NO_OF_SHELVES);
        int iCleanlines = cursor.getColumnIndex(KEY_CLEANLINESS);
        int iPrimePosition = cursor.getColumnIndex(KEY_PRIME_POSITION);
        int iAvailabilty = cursor.getColumnIndex(KEY_AVAILABILTY);
        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
        int iUserId = cursor.getColumnIndex(KEY_USER_ID);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        int iCoolerPID = cursor.getColumnIndex(KEY_COOLER_PIC_CODE);
        int iOutletPID = cursor.getColumnIndex(KEY_OUTLET_PIC_CODE);
        int iCulerPurity = cursor.getColumnIndex(KEY_COOLER_PURITY);
        int iCan = cursor.getColumnIndex(KEY_SKU_CAN);
        int iGoPack = cursor.getColumnIndex(KEY_SKU_GO_PACK);
        int iCan400 = cursor.getColumnIndex(KEY_CAN_400_ML);
        int iLtr1 = cursor.getColumnIndex(KEY_SKU_1_LITER);
        int iLtr2 = cursor.getColumnIndex(KEY_SKU_2_LITER);
        int iAquafina = cursor.getColumnIndex(KEY_SKU_AQUAFINA);
        int iCoolerActive = cursor.getColumnIndex(KEY_NO_OF_ACTIVE_COOLER);
        int iLightWorking = cursor.getColumnIndex(KEY_LIGHT_WORKING);
        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            ServeyModel serveyModel = new ServeyModel();
            serveyModel.setOutletId(cursor.getString(iOutletId));
            serveyModel.setChannel(cursor.getString(iChannel));
            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
            serveyModel.setCoolerCharging(cursor.getString(iCoolerCharging));

            serveyModel.setGrb(cursor.getString(iGRB));
            serveyModel.setCan500(cursor.getString(iCan500));
            serveyModel.setShelves(cursor.getString(iShelves));
            serveyModel.setCleanliness(cursor.getString(iCleanlines));

            serveyModel.setPrimePosition(cursor.getString(iPrimePosition));
            serveyModel.setAvailabilty(cursor.getString(iAvailabilty));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));

            serveyModel.setUserId(cursor.getString(iUserId));

            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setCoolerPID(cursor.getString(iCoolerPID));
            serveyModel.setOutletPID(cursor.getString(iOutletPID));
            serveyModel.setCoolerPurity(cursor.getString(iCulerPurity));
            serveyModel.setCan(cursor.getString(iCan));
            serveyModel.setGoPack(cursor.getString(iGoPack));
            serveyModel.setCan400(cursor.getString(iCan400));

            serveyModel.setLtr1(cursor.getString(iLtr1));
            serveyModel.setLtr2(cursor.getString(iLtr2));
            serveyModel.setAquafina(cursor.getString(iAquafina));
            serveyModel.setCoolerActive(cursor.getString(iCoolerActive));

            serveyModel.setLightWorking(cursor.getString(iLightWorking));
            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setSyncStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);

            // }
            //
        }
        cursor.close();
        return serveyList;
    }
}
