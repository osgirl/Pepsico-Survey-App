package limited.it.planet.visicoolertracking.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.util.AllExistingDataModel;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.RejectedDataModel;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RejectedDataActivity extends AppCompatActivity implements View.OnClickListener{
    String userId = "";
    String rejectedDataAPI = "";
   // String allExistingDataTotalAPI = "";

    TableLayout tblLayoutRejectedData;
    TableLayout tableLayout;
    private Dialog loadingDialog;
    static ArrayList<RejectedDataModel> rejectedDataModelArrayList ;
    TextView txvTotalResult,txvTotalDurationFirst,txvTotalDurationLast;
    ImageButton btnNavigationRight,btnNavigationBack;

    Toolbar toolbar;
    public static int page = 0;
    public static int allInputData = 0;
    public static  int allInputDataAfterBackL =0;
    public static  int allInputDataAfterBackF=0 ;
    public static int allVisited ;
    public static boolean checkLast;
    public static int  increaseDATA = 0;
    public static int  decreaseDATA = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar_view_rejected_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tblLayoutRejectedData = (TableLayout)findViewById(R.id.tbl_layout_rejected_data);
        btnNavigationBack =(ImageButton) findViewById(R.id.btn_navigation_back);
        btnNavigationRight = (ImageButton) findViewById(R.id.btn_navigation_right);
        txvTotalDurationFirst = (TextView)findViewById(R.id.txv_total_duration_first);
        txvTotalDurationLast = (TextView)findViewById(R.id.txv_total_duration_last);

        rejectedDataModelArrayList = new ArrayList<RejectedDataModel>();

        userId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("user_id",RejectedDataActivity.this);
        //rejectedDataAPI = Constants.allExistingDataAPI + userId + "?" + "page=" + page ;

        rejectedDataAPI = Constants.rejectedDataAPI + userId + "?" + "page=" + page ;

       // allExistingDataTotalAPI = Constants.allExistingDataTotalAPI + userId ;

        final boolean isAvailableInternet = Constants.isConnectingToInternet(RejectedDataActivity.this);
        if(isAvailableInternet){
            GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
            getAllExistingDataTask.execute();
        }else {
            Toast.makeText(RejectedDataActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
        }
        addHeaders();
        btnNavigationRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>=0){
                    page++;
                }
//                if(allInputData>1){
//                   // btnNavigationBack.setVisibility(View.VISIBLE);
//                }

                rejectedDataAPI = Constants.rejectedDataAPI + userId + "?" + "page=" + page ;
                if(isAvailableInternet){
                    if(!checkLast){
                        rejectedDataModelArrayList.clear();
                        redrawEverything();
                        addHeaders();
                        GetAllExistingDataTask getAllExistingDataTask = new  GetAllExistingDataTask();
                        getAllExistingDataTask.execute();
                    }

                }else {
                    Toast.makeText(RejectedDataActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnNavigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(page>-1){
                    page--;
                    if(page==0){
                        txvTotalDurationFirst.setText( String.valueOf("0"));
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
                rejectedDataAPI = Constants.rejectedDataAPI + userId + "?" + "page=" + page ;
                if(isAvailableInternet){
                    rejectedDataModelArrayList.clear();
                    redrawEverything();
                    addHeaders();
                  GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                    getAllExistingDataTask.execute();

                }else {
                    Toast.makeText(RejectedDataActivity.this,"Sorry,Your Device is Offline",Toast.LENGTH_SHORT).show();
                }

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

        TableLayout tl = findViewById(R.id.tbl_layout_rejected_data);
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

    public class GetAllExistingDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(RejectedDataActivity.this, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(rejectedDataAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RejectedDataActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();


                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {

                    if(checkLast){
                        rejectedDataModelArrayList.clear();

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

                            rejectedDataModelArrayList.add(new RejectedDataModel(outletId,channel,coolerStatus,coolerPurity,coolerCharging,grb,can,

                                    gopack,can400,can500,ltr1,ltr2,aquafina,coolerActive,lightWorking,shelves,cleanliness,primePosition,
                                    availabilty,date,remarks
                            ));


                        }

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnNavigationRight.setVisibility(View.GONE);
                                // btnNavigationBack.setVisibility(View.GONE);
                                btnNavigationBack.setVisibility(View.VISIBLE);
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
            allInputData = rejectedDataModelArrayList.size();


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




            tableLayout = findViewById(R.id.tbl_layout_rejected_data);

            for (int i = 0; i < allInputData; i++) {
                TableRow tr = new TableRow(RejectedDataActivity.this);
                tr.setLayoutParams(getLayoutParams());

                tr.addView(getRowsTextView(0, rejectedDataModelArrayList.get(i).getOutletId(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(0, rejectedDataModelArrayList.get(i).getChannel(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCoolerStatus(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(3, rejectedDataModelArrayList.get(i).getCoolerPurity(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(4, rejectedDataModelArrayList.get(i).getCoolerCharging(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(5, rejectedDataModelArrayList.get(i).getGrb(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCan(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getGoPack(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCan400(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCan500(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getLtr1(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getLtr2(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getAquafina(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCoolerActive(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getLightWorking(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getShelves(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getCleanliness(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getPrimePosition(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getAvailabilty(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));

                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getRemarks(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));
                tr.addView(getRowsTextView(2, rejectedDataModelArrayList.get(i).getDate(), Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(RejectedDataActivity.this, R.color.cell_background_color)));

                tableLayout.addView(tr, getTblLayoutParams());

            }
        }





    }
}
