package limited.it.planet.visicoolertracking.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.util.AllExistingDataModel;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.MyDatePicker;
import limited.it.planet.visicoolertracking.util.ServeyModel;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.setValueToSharedPreferences;

public class AllExistingDataViewActivity extends AppCompatActivity implements View.OnClickListener{
        String userId = "";
        String allExistingDataAPI = "";
        String allExistingDataTotalAPI = "";
        String dateSearchData = "";


        TableLayout tblLayoutAllExistingData;
    TableLayout tableLayout;
    private Dialog loadingDialog;
   static ArrayList<AllExistingDataModel> allExistingDataList ;
   TextView txvTotalResult,txvTotalDurationFirst,txvTotalDurationLast;
   Button btnSearchDate;
   EditText edtFirstDate,edtLastDate;
   ImageButton btnDateFirst,btnNavigationRight,btnNavigationBack,btnDateLast;

   Toolbar toolbar;
   public static int page = 0;
   public static int allInputData = 1;
   public static  int allInputDataAfterBackL =0;
   public static  int allInputDataAfterBackF=0 ;
   public static int allVisited = 0 ;
   public static boolean checkLast;
    public static int  increaseDATA = 0;
    public static int  decreaseDATA = 0;
    static String firstDate = "";
    static String lastDate = "";

    private int month;
    private int year;
    private Calendar calendar;
    public static String PATTERN = "dd-MM-yyyy";
    String systemDate = "";
    String firstDayOfMonth = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_existing_data_view);
        toolbar = (Toolbar)findViewById(R.id.toolbar_view_all_existing_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tblLayoutAllExistingData = (TableLayout)findViewById(R.id.tbl_layout_all_existing_data);
        txvTotalResult = (TextView)findViewById(R.id.txv_no_total);
        txvTotalDurationFirst = (TextView)findViewById(R.id.txv_total_duration_first);
        txvTotalDurationLast = (TextView)findViewById(R.id.txv_total_duration_last);
        btnNavigationBack =(ImageButton) findViewById(R.id.btn_navigation_back);
        btnNavigationRight = (ImageButton) findViewById(R.id.btn_navigation_right);
        btnDateFirst = (ImageButton) findViewById(R.id.btn_date_first);
        btnDateLast = (ImageButton)findViewById(R.id.btn_date_last);
        edtFirstDate = (EditText)findViewById(R.id.edt_date_first);
        edtLastDate = (EditText)findViewById(R.id.edt_date_last);
        btnSearchDate = (Button) findViewById(R.id.btn_search);
        btnSearchDate.setTransformationMethod(null);
        //loadingDialog = new ProgressDialog(AllExistingDataViewActivity.this);

        userId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("user_id",AllExistingDataViewActivity.this);
          systemDate = Constants.getCurrentDate();
          firstDayOfMonth =  getDefaultMonthDate();
        edtFirstDate.setText(firstDayOfMonth);
        edtLastDate.setText(systemDate);

        allExistingDataAPI = Constants.searchByDateAPI + userId + "/" + firstDayOfMonth + "/" + systemDate + "?" + "page=" + page ;
        //allExistingDataAPI = Constants.searchByDateAPI + userId + "/" + "01-04-2018" + "/" + systemDate + "?" + "page=" + page ;

        allExistingDataTotalAPI = Constants.allExistingDataTotalAPI + userId ;

        final boolean isAvailableInternet = Constants.isConnectingToInternet(AllExistingDataViewActivity.this);
        if(isAvailableInternet){
            GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
            getAllExistingDataTask.execute();
        }else {
            Toast.makeText(AllExistingDataViewActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
        }


        addHeaders();

        allExistingDataList = new ArrayList<AllExistingDataModel>();
        btnNavigationRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>=0){
                    page++;
                }
//                if(allInputData>1){
//                   // btnNavigationBack.setVisibility(View.VISIBLE);
//                }

                allExistingDataAPI = Constants.searchByDateAPI + userId + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;
                if(isAvailableInternet){
                    if(!checkLast){
                        allExistingDataList.clear();
                        redrawEverything();
                        addHeaders();
                        GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                        getAllExistingDataTask.execute();
                    }

                }else {
                    Toast.makeText(AllExistingDataViewActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnNavigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(page>-1){
                    page--;
                    if(page==0){
                        txvTotalDurationFirst.setText( String.valueOf("1"));
                        btnNavigationBack.setVisibility(View.GONE);
                        btnNavigationRight.setVisibility(View.VISIBLE);
                        redrawEverything();
                        addHeaders();

                    }
                }


                if(checkLast){

                    redrawEverything();
                    addHeaders();

                }
                allExistingDataAPI =  Constants.searchByDateAPI + userId + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;
                if(isAvailableInternet){
                    allExistingDataList.clear();
                    redrawEverything();
                    addHeaders();
                    GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                    getAllExistingDataTask.execute();

                }else {
                    Toast.makeText(AllExistingDataViewActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //all existing total data count

        GetAllExistingTotalDataTask getAllExistingTotalDataTask = new GetAllExistingTotalDataTask();
        getAllExistingTotalDataTask.execute();



        btnDateFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = new MyDatePicker();
//                newFragment.show(getSupportFragmentManager(), "date picker");

                openDatePickerFirst();

            }
        });

        btnDateLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = new MyDatePicker();
//                newFragment.show(getSupportFragmentManager(), "date picker");

                openDatePickerLast();

            }
        });

        edtFirstDate.setFocusableInTouchMode(false);
        edtLastDate.setFocusableInTouchMode(false);
        btnSearchDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             firstDate = edtFirstDate.getText().toString();
             lastDate = edtLastDate.getText().toString();
             page = 0;
            allExistingDataAPI =  Constants.searchByDateAPI + userId + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;

            btnNavigationRight.setVisibility(View.VISIBLE);
            btnNavigationBack.setVisibility(View.GONE);
            allExistingDataList.clear();
            redrawEverything();
            addHeaders();
            GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
            getAllExistingDataTask.execute();



        }
    });



    }
    private void redrawEverything()
    {
        tableLayout.removeAllViews();

    }


    @Override
    public void onClick(View view) {

    }

    public class GetAllExistingDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             loadingDialog = ProgressDialog.show(AllExistingDataViewActivity.this,"Loading","Please Wailt......");

        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(allExistingDataAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AllExistingDataViewActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();


                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {

                    if(checkLast){
                        allExistingDataList.clear();

                    }
                    responseBodyText = response.body().string();
                    JSONObject resultData = new JSONObject(responseBodyText);

                    JSONArray itemsArray = resultData.getJSONArray("items");

                    if(!itemsArray.isNull(0)){
                        for(int i=0;i<itemsArray.length();i++){

                            JSONObject jobject = itemsArray.getJSONObject(i);
                            String outletId = jobject.getString("outlet");
                            String channel = jobject.getString("channel");
                            String coolerStatus = jobject.getString("cooler_status");
                            String coolerPurity = jobject.getString("cooler_purity");
                            String coolerCharging = jobject.getString("cooler_charging");
                            String grb = jobject.getString("grb");
                            String can = jobject.getString("can");
                            String gopack = jobject.getString("go_pack");
                            String can400 = jobject.getString("can_400");
                            String can500 = jobject.getString("can_500");
                            String ltr1 = jobject.getString("ltr_1");
                            String ltr2 = jobject.getString("ltr_2");
                            String aquafina = jobject.getString("aquafina");
                            String coolerActive = jobject.getString("cooler_active");
                            String lightWorking = jobject.getString("light_working");
                            String shelves = jobject.getString("shelves");
                            String cleanliness = jobject.getString("cleanliness");
                            String primePosition = jobject.getString("prime_position");
                            String availabilty = jobject.getString("availability");
                            String remarks = jobject.getString("remarks");
                            String date = jobject.getString("date");

                            allExistingDataList.add(new AllExistingDataModel(outletId,channel,coolerStatus,
                                    coolerPurity,coolerCharging,grb,can,

                                    gopack,can400,can500,ltr1,ltr2,aquafina,coolerActive,lightWorking,shelves,cleanliness,primePosition,
                                    availabilty,remarks,date
                            ));


                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismiss();

                            }


                        });



                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AllExistingDataViewActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();
                                //btnNavigationRight.setVisibility(View.GONE);
                                btnNavigationBack.setVisibility(View.GONE);
                                btnNavigationRight.setVisibility(View.VISIBLE);
                                checkLast = true;
                            }


                        });


                    }



                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadingDialog.dismiss();

            if (page == 0) {
                txvTotalDurationFirst.setText(String.valueOf("0"));
                //allInputData = allExistingDataList.size();
               // checkLast = false;
                if(checkLast){
                    increaseDATA = 0;
                    decreaseDATA = 0;
                    allInputData = 0;
                    checkLast = false;
                    txvTotalDurationFirst.setText(String.valueOf("0"));
                }
            } else {
                txvTotalDurationFirst.setText(String.valueOf(allInputData));

            }
            allInputData = allExistingDataList.size();
            txvTotalResult.setText("Of " + allVisited);

                increaseDATA = increaseDATA + allInputData;
                decreaseDATA = increaseDATA - allInputData;




            if (checkLast){
                increaseDATA = decreaseDATA - allInputData;
                decreaseDATA = increaseDATA - allInputData;
                if(increaseDATA>0){
                    txvTotalDurationLast.setText(" -- " + String.valueOf(increaseDATA));
                    txvTotalDurationFirst.setText(String.valueOf(decreaseDATA));
                }

                if(page==1){
                    txvTotalDurationFirst.setText(String.valueOf(allInputData));
                }

            }else {

                txvTotalDurationLast.setText(" -- " + String.valueOf(allInputData));
                if(page!=0){
                    if(increaseDATA>0){
                        txvTotalDurationLast.setText(" -- " + String.valueOf(increaseDATA));
                        txvTotalDurationFirst.setText(String.valueOf(decreaseDATA));
                    }


                }

            }


            if(checkLast){
                btnNavigationRight.setVisibility(View.GONE);
                btnNavigationBack.setVisibility(View.VISIBLE);

            }


            tableLayout = findViewById(R.id.tbl_layout_all_existing_data);

            for (int i = 0; i < allInputData; i++) {
                TableRow tr = new TableRow(AllExistingDataViewActivity.this);
                tr.setLayoutParams(getLayoutParams());

                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getOutletId(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getChannel(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCoolerStatus(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCoolerPurity(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCoolerCharging(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getGrb(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCan(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getGoPack(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCan400(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCan500(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getLtr1(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getLtr2(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getAquafina(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCoolerActive(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getLightWorking(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getShelves(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getCleanliness(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getPrimePosition(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getAvailabilty(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getRemarks(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, allExistingDataList.get(i).getDate(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(AllExistingDataViewActivity.this, R.color.cell_background_color)));

                tableLayout.addView(tr, getTblLayoutParams());

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

        TableLayout tl = findViewById(R.id.tbl_layout_all_existing_data);
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
        tr.addView(getTextView(0, "Date", Color.WHITE, Typeface.BOLD, R.color.colorAccent));

        tl.addView(tr, getTblLayoutParams());
    }

    public class GetAllExistingTotalDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  loadingDialog = ProgressDialog.show(AllExistingDataViewActivity.this, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(allExistingDataTotalAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  Toast.makeText(AllExistingDataViewActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();


                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {

                    responseBodyText = response.body().string();
                    JSONObject resultData = new JSONObject(responseBodyText);

                        final String visitedTotal = resultData.getString("total");
                         final String thisMonthTotal = resultData.getString("this_month");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txvTotalResult.setText("Of "+ thisMonthTotal);
                                if(visitedTotal!=null && !visitedTotal.isEmpty()){
                                    allVisited = Integer.parseInt(visitedTotal);
                                }

                            }


                        });





                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadingDialog.dismiss();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
       // allExistingDataList.clear();
         increaseDATA = 0;
         decreaseDATA = 0;
    }

    public void openDatePickerFirst() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(AllExistingDataViewActivity.this, "selected date is " + view.getDayOfMonth() +
//                                " . " + (view.getMonth()+1) +
//                                " . " + view.getYear(), Toast.LENGTH_SHORT).show();

                        edtFirstDate.setText(view.getDayOfMonth() +
                                " - " + (view.getMonth()+1) +
                                " - " + view.getYear());

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void openDatePickerLast() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(AllExistingDataViewActivity.this, "selected date is " + view.getDayOfMonth() +
//                                " . " + (view.getMonth()+1) +
//                                " . " + view.getYear(), Toast.LENGTH_SHORT).show();

                        edtLastDate.setText(view.getDayOfMonth() +
                                " - " + (view.getMonth()+1) +
                                " - " + view.getYear());

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private String  getDefaultMonthDate() {
        // TODO Auto-generated method stub
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(calendar.getTime());


    }





}
