package limited.it.planet.visicoolertracking.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.services.OfflineServeyDataSendToServer;
import limited.it.planet.visicoolertracking.services.ServeyDataSendToServer;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.TableHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class OfflineDataActivity extends AppCompatActivity implements View.OnClickListener{
    public static TableLayout tableLayout,cloumnHeader;
    LocalStorageDB localStorageDB;
    TableHelper tableHelper;
    String []headers ;
    String [][]allRows;
    String [][]specificOfflineData;

    int todaysRowCount ;
    Toolbar toolbar;
    ArrayList dataList ;
    OfflineServeyDataSendToServer offlineServeyDataSendToServer;
    private ProgressBar progressBar;
    String outletCode = "";
    private Dialog loadingDialog;
    private Dialog OutletLoadingDialog;
    String channel = " ";
    String coolerstatus = " ";
    String coolerCharging = " ";
    String skuGRBAvailabilty = " ";
    String sku500mlAvailabilty = " ";
    String noOfShelves = " ";
    String cleanLiness = " ";
    String coolerPrimePosition = " ";
    String availabilty = " ";
    String remarks = " ";
    String coolerPurity = " ";
    String sku1LiterAvailabilty = " ";
    String sku2LiterAvailabilty = " ";
    String skuCANAvailabilty = " ";
    String skuGOPACKAvailabilty = " ";
    String sku400mlAvailabilty = "";
    String skuAquafinaAvailabilty = "";
    String noOfActiveCooler = "";
    String lightWorking = "";

    String userId = "";
    String latitude = "";
    String longitude = "";
    String startTime = "";
    String endTime = "";
    String entryDate = "";
    String coolerPid = " ";
    String outletPid = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar_offline_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tableLayout = (TableLayout) findViewById(R.id.gridview);

//        String outletCode = getValueFromSharedPreferences("outlet_id",OfflineDataActivity.this);
//        String entryDate = Constants.getCurrentEntryDate();

        //cloumnHeader =(TableLayout) findViewById(R.id.headergridview);

        localStorageDB = new LocalStorageDB(OfflineDataActivity.this,tableLayout,cloumnHeader);
        localStorageDB.open();
        tableHelper = new TableHelper(OfflineDataActivity.this);
        allRows = tableHelper.getAllOfflineData();


        addHeaders();
        //localStorageDB.getRecords();
        addData();
        offlineServeyDataSendToServer = new OfflineServeyDataSendToServer(OfflineDataActivity.this);
        progressBar = new ProgressBar(OfflineDataActivity.this);
    }
    @Override
    public void onClick(View v) {
//data send to server
        final boolean isAvailableInternet = Constants.isConnectingToInternet(OfflineDataActivity.this);
        TableRow tablerow = (TableRow)v.getParent();
        TextView itemsOutletId = (TextView) tablerow.getChildAt(0);
        TextView itemsChannel = (TextView) tablerow.getChildAt(1);
        TextView itemsCoolerStatus = (TextView) tablerow.getChildAt(2);
        TextView itemsCoolerPurity = (TextView) tablerow.getChildAt(3);
        TextView itemsCharging = (TextView) tablerow.getChildAt(4);
        TextView itemsGrb = (TextView) tablerow.getChildAt(5);
        TextView itemsCan = (TextView) tablerow.getChildAt(6);
        TextView itemsGoPack = (TextView) tablerow.getChildAt(7);
        TextView items400ml= (TextView) tablerow.getChildAt(8);
        TextView items500ml = (TextView) tablerow.getChildAt(9);
        TextView itemsLtr1 = (TextView) tablerow.getChildAt(10);
        TextView itemsLtr2= (TextView) tablerow.getChildAt(11);
        TextView itemsAquafina = (TextView) tablerow.getChildAt(12);
        TextView itemsCoolerActive = (TextView) tablerow.getChildAt(13);
        TextView itemsLightWorking= (TextView) tablerow.getChildAt(14);
        TextView itemsShelves= (TextView) tablerow.getChildAt(15);
        TextView itemsCleanlines= (TextView) tablerow.getChildAt(16);
        TextView itemsPrimePosition = (TextView) tablerow.getChildAt(17);
        TextView itemsAvailabilty= (TextView) tablerow.getChildAt(18);
        TextView itemsRemarks= (TextView) tablerow.getChildAt(19);
        TextView itemsCoolerPID = (TextView) tablerow.getChildAt(21);
        TextView itemsOutletPID = (TextView) tablerow.getChildAt(22);
        TextView itemsStartTime = (TextView) tablerow.getChildAt(24);
        TextView itemsEndTime = (TextView) tablerow.getChildAt(25);
        TextView itemsLatitude = (TextView) tablerow.getChildAt(26);
        TextView itemsLongitude = (TextView) tablerow.getChildAt(27);
        TextView itemsUserId = (TextView) tablerow.getChildAt(28);
        TextView itemsEntryDate = (TextView) tablerow.getChildAt(20);
        TextView itemsSyncStattus = (TextView) tablerow.getChildAt(23);

         coolerPid = itemsCoolerPID.getText().toString();
         outletPid = itemsOutletPID.getText().toString();
        outletCode = itemsOutletId.getText().toString();



        String syncStatus = itemsSyncStattus.getText().toString();
        if(syncStatus.equals("failled")){
            channel =  itemsChannel.getText().toString();
            coolerstatus =  itemsCoolerStatus.getText().toString();
            coolerCharging =  itemsCharging.getText().toString();
            skuGRBAvailabilty =  itemsGrb.getText().toString();
            sku500mlAvailabilty =  items500ml.getText().toString();
            noOfShelves =  itemsShelves.getText().toString();
            cleanLiness =  itemsCleanlines.getText().toString();
            coolerPrimePosition =  itemsPrimePosition.getText().toString();
            availabilty =  itemsAvailabilty.getText().toString();
            remarks =  itemsRemarks.getText().toString();
            coolerPurity =itemsCoolerPurity.getText().toString();
            sku1LiterAvailabilty = itemsLtr1.getText().toString();
            sku2LiterAvailabilty = itemsLtr2.getText().toString();
            skuCANAvailabilty = itemsCan.getText().toString();
            skuGOPACKAvailabilty = itemsGoPack.getText().toString();
            sku400mlAvailabilty = items400ml.getText().toString();
            skuAquafinaAvailabilty = itemsAquafina.getText().toString();
            noOfActiveCooler = itemsCoolerActive.getText().toString();
            lightWorking = itemsLightWorking.getText().toString();

            userId = itemsUserId.getText().toString();
            latitude = itemsLatitude.getText().toString();
            longitude = itemsLongitude.getText().toString();
            startTime = itemsStartTime.getText().toString();
            endTime = itemsEndTime.getText().toString();
            entryDate = itemsEntryDate.getText().toString();

            if(coolerPid!=null){
                if(coolerPid.equals("0") || coolerPid.equals("")){
                    String coolerPicPath =  localStorageDB.getCoolerPicPath(outletCode);
                    if(isAvailableInternet){
                        if(coolerPicPath!=null && coolerPicPath.length()>0){
                            new UploadCoolerPicToServer(coolerPicPath).execute(Uri.parse(coolerPicPath));
                        }
                    }

                }
            }


            if(outletPid!=null){
                if(outletPid.equals("0") || outletPid.equals("")){
                    String outletPicPath =  localStorageDB.getOutletPicPath(outletCode);
                    if(isAvailableInternet){
                        if(outletPicPath!=null && outletPicPath.length()>0){

                            new UpladOutletPicToServer(outletPicPath).execute(Uri.parse(outletPicPath));
                        }
                    }
                }
            }
                            if(isAvailableInternet){
                                redrawEverything();
                                addHeaders();
                                addData();
                                if (outletCode != null && outletCode.length()>0) {
                                    if (coolerPid != null && outletPid != null) {
                                        if ((!coolerPid.equals("0") && coolerPid.length() > 0) && (!outletPid.equals("0") && outletPid.length() > 0)) {
                                            offlineServeyDataSendToServer.serveyDataSendToServer(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                                    sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                                    remarks, latitude, longitude, userId, entryDate, coolerPid,
                                                    outletPid, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                                    sku1LiterAvailabilty, sku2LiterAvailabilty,
                                                    skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime);


                                        } else {

                                                    new Handler().postDelayed(new Runnable() {

                                                        @Override
                                                        public void run() {
                                            specificOfflineData = tableHelper.getSpecificOutletData(outletCode);
                                            channel =  specificOfflineData[0][1];
                                            coolerstatus =  specificOfflineData[0][2];
                                            coolerCharging =  specificOfflineData[0][3];
                                            skuGRBAvailabilty =  specificOfflineData[0][4];
                                            sku500mlAvailabilty =  specificOfflineData[0][5];
                                            noOfShelves =  specificOfflineData[0][6];
                                            cleanLiness =  specificOfflineData[0][7];
                                            coolerPrimePosition =  specificOfflineData[0][8];
                                            availabilty = specificOfflineData[0][9];
                                            remarks =  specificOfflineData[0][10];
                                            coolerPurity =specificOfflineData[0][17];
                                            sku1LiterAvailabilty = specificOfflineData[0][21];
                                            sku2LiterAvailabilty = specificOfflineData[0][22];
                                            skuCANAvailabilty = specificOfflineData[0][18];
                                            skuGOPACKAvailabilty = specificOfflineData[0][19];
                                            sku400mlAvailabilty = specificOfflineData[0][20];
                                            skuAquafinaAvailabilty = specificOfflineData[0][23];
                                            noOfActiveCooler = specificOfflineData[0][24];
                                            lightWorking = specificOfflineData[0][25];

                                            userId = specificOfflineData[0][13];
                                            latitude = specificOfflineData[0][11];
                                            longitude = specificOfflineData[0][12];
                                            startTime = specificOfflineData[0][26];
                                            endTime = specificOfflineData[0][27];
                                            entryDate = specificOfflineData[0][14];
                                            coolerPid = specificOfflineData[0][15];
                                            outletPid = specificOfflineData[0][16];
                                            if ((!coolerPid.equals("0") && coolerPid.length() > 0) && (!outletPid.equals("0") && outletPid.length() > 0)) {
                                                offlineServeyDataSendToServer.serveyDataSendToServer(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                                        sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                                        remarks, latitude, longitude, userId, entryDate, coolerPid,
                                                        outletPid, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                                        sku1LiterAvailabilty, sku2LiterAvailabilty,
                                                        skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime);


                                            }else {

                                                Toast.makeText(OfflineDataActivity.this, "Please Click Again this Failled Option", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },5000);// set time as per your requirement


                                        }
                                    }



                                }

                                // redrawEverything();

                            }else {
                                Toast.makeText(OfflineDataActivity.this,"Your Device is Offline",Toast.LENGTH_SHORT).show();
                            }




        }




    }
    public static void redrawEverything()
    {
        tableLayout.removeAllViews();


    }
    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }
    private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {

        TableLayout tl = findViewById(R.id.gridview);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "outletid", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Channel", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cooler Status", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cooler Purity", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cooler Charging", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "GRB", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Can", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Go Pack", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "400ml", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "500ml", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "ltr1", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "ltr2", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "aquafina", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cooler Active", Color.WHITE, Typeface.BOLD,R.color.colorAccent));
        tr.addView(getTextView(0, "Light Working", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Shelves", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cleanliness", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Prime Position", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Availabilty", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Remarks", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        //tr.addView(getTextView(0, "User Id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Entry Date", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Cooler PIC ID", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Outlet PIC ID", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Sync Status", Color.WHITE, Typeface.BOLD, R.color.colorAccent));

        tl.addView(tr, getTblLayoutParams());
    }
    public void addData() {
        dataList = new ArrayList();
        headers = tableHelper.tableHeaders;
        int allInputData = 0 ;
        if(allRows!=null){
            allInputData = allRows.length;

        }



        //   int pos = 0;

        //   int numCompanies = companies.length;
        TableLayout tl = findViewById(R.id.gridview);
        for (int i = 0; i < allInputData; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
//            tr.addView(getRowsTextView(i + 1, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  0, allRows[i][0], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  1, allRows[i][1], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  2, allRows[i][2], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  3, allRows[i][17], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            tr.addView(getRowsTextView(  4, allRows[i][3], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  5, allRows[i][4], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView( 6, allRows[i][18], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView( 7, allRows[i][19], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  8, allRows[i][20], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  9, allRows[i][5], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  10, allRows[i][21], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  11, allRows[i][22], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  12, allRows[i][23], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            tr.addView(getRowsTextView(  13, allRows[i][24], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  14, allRows[i][25], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            tr.addView(getRowsTextView( 15, allRows[i][6], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  16, allRows[i][7], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  17, allRows[i][8], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  18, allRows[i][9], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  19, allRows[i][10], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            tr.addView(getRowsTextView(  20, allRows[i][14], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            String coolerPicCode = allRows[i][15];
            if(coolerPicCode.equals("0")){
                tr.addView(getRowsTextView(  21,coolerPicCode , Color.RED, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }else {
                tr.addView(getRowsTextView(  21,coolerPicCode , Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }

            String outletPicCode = allRows[i][16];
            if(outletPicCode.equals("0")){
                tr.addView(getRowsTextView(  22,outletPicCode , Color.RED, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }else {
                tr.addView(getRowsTextView(  22,outletPicCode , Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }

            String syncStatus = allRows[i][28];
            if(syncStatus.equals("failled")){
                String uderLineText = allRows[i][28] ;

                tr.addView(getRowsTextView(  23,uderLineText , Color.RED, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }else {
                tr.addView(getRowsTextView(  23, allRows[i][28], Color.GREEN, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }



            //outletPid.setVisibility(View.GONE);
            TextView startTime =  getRowsTextView(  24, allRows[i][26], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
            TextView endTime =  getRowsTextView(  25, allRows[i][27], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
            TextView latitude = getRowsTextView(  26, allRows[i][11], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
            TextView longitude =getRowsTextView(  27, allRows[i][12], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
            TextView userId = getRowsTextView(  28, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));




            tr.addView(startTime);
            tr.addView(endTime);
            tr.addView(latitude);
            tr.addView(longitude);
            tr.addView(userId);
            startTime.setVisibility(View.GONE);
            endTime.setVisibility(View.GONE);
            latitude.setVisibility(View.GONE);
            longitude.setVisibility(View.GONE);
            userId.setVisibility(View.GONE);

//            tr.addView(getRowsTextView(i + 22, allRows[i][15], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//
//            tr.addView(getRowsTextView(i + 23, allRows[i][16], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//

            tl.addView(tr, getTblLayoutParams());





        }
    }

    public class UploadCoolerPicToServer extends AsyncTask<Uri, Integer, String> {
        String mContentFilePath;

        UploadCoolerPicToServer(String contentFilePath){
            this.mContentFilePath = contentFilePath;
        }
        private String getRealPathFromURI(Uri contentURI) {
            String result;
            Cursor cursor = OfflineDataActivity.this.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return  result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBar.setVisibility(View.VISIBLE);
            loadingDialog = ProgressDialog.show(OfflineDataActivity.this, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(Uri... params) {
            String responseBodyText = null;
            Uri uri = params[0];
            String mime = "image/jpg";

            okhttp3.MediaType MEDIA_TYPE_PNG = okhttp3.MediaType.parse(mime);
            File imageFile = new File(getRealPathFromURI(uri));
            String imageFileName = imageFile.getName();
            OkHttpClient client = new OkHttpClient();

            try {

                RequestBody req = new okhttp3.MultipartBody.Builder()
                        .setType(okhttp3.MultipartBody.FORM)
                        .addFormDataPart("imageUpload", imageFileName, RequestBody.create(MEDIA_TYPE_PNG, imageFile)).build();

                Request request = new Request.Builder()
                        .url(Constants.photoUploadAPI)
                        .post(req)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    saveToSharedPreferences("cooler_pic_code","0",OfflineDataActivity.this);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.cancel();
                            //loadingDialog.setCancelable(true);
                            Toast.makeText(OfflineDataActivity.this,"Cooler Photo Upload Failled ! Network Problem",Toast.LENGTH_SHORT).show();

                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {
                    responseBodyText = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseBodyText);

                       String coolerPicCode = jsonObject.getString("image_id");
                        //update to local db cooler pic code
                        if(outletCode!=null && !outletCode.isEmpty()){
                            // long ROWID = Long.parseLong(rowId);
                            localStorageDB.open();
                            localStorageDB.updateFailledCoolerPicCode(outletCode,coolerPicCode);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.cancel();

                                }
                            });

                        }



                    } catch (JSONException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.cancel();
                               // loadingDialog.setCancelable(true);
                            }
                        });
                        e.printStackTrace();
                    }



                }



            } catch (IOException e) {
                saveToSharedPreferences("cooler_pic_code","0",OfflineDataActivity.this);
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.cancel();
                       // loadingDialog.setCancelable(true);
                        Toast.makeText(OfflineDataActivity.this,"Cooler Photo Upload Failled ! Network Problem",Toast.LENGTH_SHORT).show();
                    }
                });
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // progressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(OfflineDataActivity.this,"Update Cooler PIC Code Successfully",Toast.LENGTH_SHORT).show();

        }
    }
    public class UpladOutletPicToServer extends AsyncTask<Uri, Integer, String> {
        String mContentFilePath;

        UpladOutletPicToServer(String contentFilePath){
            this.mContentFilePath = contentFilePath;
        }
        private String getRealPathFromURI(Uri contentURI) {
            String result;
            Cursor cursor = OfflineDataActivity.this.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return  result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   progressBar.setVisibility(View.VISIBLE);
            OutletLoadingDialog = ProgressDialog.show(OfflineDataActivity.this, "Please wait", "Loading...");
            OutletLoadingDialog.setCancelable(true);
        }


        @Override
        protected String doInBackground(Uri... params) {
            String responseBodyText = null;
            Uri uri = params[0];
            String mime = "image/jpg";

            okhttp3.MediaType MEDIA_TYPE_PNG = okhttp3.MediaType.parse(mime);
            File imageFile = new File(getRealPathFromURI(uri));
            String imageFileName = imageFile.getName();
            OkHttpClient client = new OkHttpClient();

            try {

                RequestBody req = new okhttp3.MultipartBody.Builder()
                        .setType(okhttp3.MultipartBody.FORM)
                        .addFormDataPart("imageUpload", imageFileName, RequestBody.create(MEDIA_TYPE_PNG, imageFile)).build();

                Request request = new Request.Builder()
                        .url(Constants.photoUploadAPI)
                        .post(req)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    saveToSharedPreferences("outlet_pic_code","0",OfflineDataActivity.this);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            OutletLoadingDialog.cancel();
                            //loadingDialog.setCancelable(true);
                            Toast.makeText(OfflineDataActivity.this,"Outlet Photo Upload Failled ! Network Problem",Toast.LENGTH_SHORT).show();

                        }
                    });
                    throw new IOException("Okhttp Error: " + response);



                } else {

                    responseBodyText = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseBodyText);
                        String outletPicCode = jsonObject.getString("image_id");
                        if(outletCode!=null && !outletCode.isEmpty()){
                            // long ROWID = Long.parseLong(rowId);
                            localStorageDB.open();
                           // String outletId = outletCode;
                            localStorageDB.updateFailledOutletPicCode(outletCode,outletPicCode);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    OutletLoadingDialog.cancel();

                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                OutletLoadingDialog.cancel();

                            }
                        });
                    }



                }



            } catch (IOException e) {
                saveToSharedPreferences("outlet_pic_code","0",OfflineDataActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OutletLoadingDialog.cancel();

                        Toast.makeText(OfflineDataActivity.this,"Outlet Photo Upload Failled ! Network Problem",Toast.LENGTH_SHORT).show();

                    }
                });
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
          //  progressBar.setVisibility(View.INVISIBLE);

        //    Toast.makeText(OfflineDataActivity.this,"Update Outlet PIC Code Successfully",Toast.LENGTH_SHORT).show();
            OutletLoadingDialog.cancel();

        }
    }
    @Override
    public void onPause() {
        super.onPause();
        //isActive = true;
        saveBoleanValueSharedPreferences("is_active_offline",true,OfflineDataActivity.this);
    }
    @Override
    public void onStop() {
        super.onStop();
       // isActive = false;
        saveBoleanValueSharedPreferences("is_active_offline",false,OfflineDataActivity.this);
    }
}
