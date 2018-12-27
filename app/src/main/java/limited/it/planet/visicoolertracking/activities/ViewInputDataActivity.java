package limited.it.planet.visicoolertracking.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.services.ServeyDataSendToServer;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import limited.it.planet.visicoolertracking.util.TableHelper;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;


public class ViewInputDataActivity extends AppCompatActivity  implements View.OnClickListener{
    private TableLayout tableLayout,cloumnHeader;
    LocalStorageDB localStorageDB;
    TableHelper tableHelper;
    String []headers ;
    String [][]allRows;
    TextView txvNoOfRows,todaysAllRows ;
    public static int todaysRowCount ;
    Toolbar toolbar;
    ArrayList dataList ;
    ServeyDataSendToServer serveyDataSendToServer;

   String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_input_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar_view_input_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tableLayout = (TableLayout) findViewById(R.id.gridview);
        txvNoOfRows = (TextView)findViewById(R.id.txv_no_total) ;
        todaysAllRows = (TextView)findViewById(R.id.txv_today_no_rows) ;
        String outletCode = getValueFromSharedPreferences("outlet_id",ViewInputDataActivity.this);
        userId = getValueFromSharedPreferences("user_id",ViewInputDataActivity.this);
        String entryDate = Constants.getCurrentEntryDate();

        //cloumnHeader =(TableLayout) findViewById(R.id.headergridview);

        localStorageDB = new LocalStorageDB(ViewInputDataActivity.this,tableLayout,cloumnHeader);
        localStorageDB.open();
        tableHelper = new TableHelper(ViewInputDataActivity.this);
        allRows = tableHelper.getAllInputData();
        todaysRowCount = localStorageDB.getTodaysNoOfRows(entryDate,userId);

        if(todaysRowCount>0){
            todaysAllRows.setText(String.valueOf(todaysRowCount));
        }

//        Cursor cursor = localStorageDB.getAllInputRecords();
//        int totalInputRecord = cursor.getCount();
//
//        if(totalInputRecord>0){
//            txvNoOfRows.setText(String.valueOf(totalInputRecord));
//        }




        addHeaders();
        //localStorageDB.getRecords();
        addData();
        serveyDataSendToServer = new ServeyDataSendToServer(ViewInputDataActivity.this);
//       // String  retLatitude = SharedPreferenceLocalMemory.getValueFromSharedPreferences("latitude",ViewInputDataActivity.this);
//        latitude = String.valueOf(retLatitude);
//
//        String retLongitude = SharedPreferenceLocalMemory.getValueFromSharedPreferences("longitude",ViewInputDataActivity.this);
//        longitude = String.valueOf(retLongitude);



    }


    @Override
    public void onClick(View v) {

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
                TextView itemsCoolerPID = (TextView) tablerow.getChildAt(22);
                TextView itemsOutletPID = (TextView) tablerow.getChildAt(23);
                TextView itemsStartTime = (TextView) tablerow.getChildAt(24);
                TextView itemsEndTime = (TextView) tablerow.getChildAt(25);
                TextView itemsLatitude = (TextView) tablerow.getChildAt(26);
                TextView itemsLongitude = (TextView) tablerow.getChildAt(27);
                TextView itemsUserId = (TextView) tablerow.getChildAt(28);
                TextView itemsEntryDate = (TextView) tablerow.getChildAt(20);
                TextView itemsSyncStattus = (TextView) tablerow.getChildAt(21);

                String syncStatus = itemsSyncStattus.getText().toString();
                if(syncStatus.equals("failled")){

                String outletCode = itemsOutletId.getText().toString();
                String channel =  itemsChannel.getText().toString();
                String coolerstatus =  itemsCoolerStatus.getText().toString();
                String coolerCharging =  itemsCharging.getText().toString();
                String skuGRBAvailabilty =  itemsGrb.getText().toString();
                String sku500mlAvailabilty =  items500ml.getText().toString();
                String noOfShelves =  itemsShelves.getText().toString();
                String cleanLiness =  itemsCleanlines.getText().toString();
                String coolerPrimePosition =  itemsPrimePosition.getText().toString();
                String availabilty =  itemsAvailabilty.getText().toString();
                String remarks =  itemsRemarks.getText().toString();
                String coolerPurity =itemsCoolerPurity.getText().toString();
                String sku1LiterAvailabilty = itemsLtr1.getText().toString();
                String sku2LiterAvailabilty = itemsLtr2.getText().toString();
                String skuCANAvailabilty = itemsCan.getText().toString();
                String skuGOPACKAvailabilty = itemsGoPack.getText().toString();
                 String sku400mlAvailabilty = items400ml.getText().toString();
                 String skuAquafinaAvailabilty = itemsAquafina.getText().toString();
                 String noOfActiveCooler = itemsCoolerActive.getText().toString();
                 String lightWorking = itemsLightWorking.getText().toString();
                 String coolerPid = itemsCoolerPID.getText().toString();
                 String outletPid = itemsOutletPID.getText().toString();
                 String userId = itemsUserId.getText().toString();
                 String latitude = itemsLatitude.getText().toString();
                 String longitude = itemsLongitude.getText().toString();
                 String startTime = itemsStartTime.getText().toString();
                 String endTime = itemsEndTime.getText().toString();
                 String entryDate = itemsEntryDate.getText().toString();

                //data send to server
                boolean isAvailableInternet = Constants.isConnectingToInternet(ViewInputDataActivity.this);

                    if(isAvailableInternet){
                        if(coolerPid!=null && outletPid!=null){
                            if((!coolerPid.equals("0") && coolerPid.length()>0) && (!outletPid.equals("0") && outletPid.length()>0)){
                                serveyDataSendToServer.serveyDataSendToServer(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                        sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                        remarks, latitude, longitude, userId, entryDate, coolerPid,
                                        outletPid, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                        sku1LiterAvailabilty, sku2LiterAvailabilty,
                                        skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime);
                            }else {
                                Toast.makeText(ViewInputDataActivity.this,"Your cooler pic and outlet pic code missing,please update offline data",Toast.LENGTH_SHORT).show();
                            }
                        }


                    }else {
                        Toast.makeText(ViewInputDataActivity.this,"Your Device is Offline",Toast.LENGTH_SHORT).show();
                    }

                }




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
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
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
//        tr.addView(getTextView(0, "Start Time", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
//        tr.addView(getTextView(0, "End Time", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Sync Status", Color.WHITE, Typeface.BOLD, R.color.colorAccent));

        tl.addView(tr, getTblLayoutParams());
    }
    public void addData() {
        dataList = new ArrayList();
        headers = tableHelper.tableHeaders;
        int allInputData = 0 ;
        if(allRows!=null){
           allInputData = allRows.length;
            txvNoOfRows.setText(String.valueOf(allInputData));
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


//            tr.addView(getRowsTextView(i + 23, allRows[i][26], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//            tr.addView(getRowsTextView(i + 24, allRows[i][27], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            String syncStatus = allRows[i][28];
            if(syncStatus.equals("failled")){
                String uderLineText = allRows[i][28] ;

                tr.addView(getRowsTextView(  21,uderLineText , Color.RED, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }else {
                tr.addView(getRowsTextView(  21, allRows[i][28], Color.GREEN, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            }


         TextView coolerPid = getRowsTextView(  22, allRows[i][15], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
                    coolerPid.setVisibility(View.GONE);
         TextView outletPid =getRowsTextView(  23, allRows[i][16], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
                 outletPid.setVisibility(View.GONE);
          TextView startTime =  getRowsTextView(  24, allRows[i][26], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
          TextView endTime =  getRowsTextView(  25, allRows[i][27], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
          TextView latitude = getRowsTextView(  26, allRows[i][11], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
          TextView longitude =getRowsTextView(  27, allRows[i][12], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));
          TextView userId = getRowsTextView(  28, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color));


            tr.addView(coolerPid);
            tr.addView(outletPid);
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
}
