package limited.it.planet.visicoolertracking.activities;

import android.Manifest;
import android.app.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.firebase.FirebaseIDService;
import limited.it.planet.visicoolertracking.services.BasicInformationSendToServer;
import limited.it.planet.visicoolertracking.services.OfflineDataAutoSync;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.CsvFileExport;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.setValueToSharedPreferences;

public class BasicInformationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText txtTerritory, txtDistributor, txtDBArea, txtOutletName,
            txtRetailerName,
            txtRetailerMobile, txtAddress, txtClusterId;
    Button btnOK, btnNext, btnEditAllField;
    TextView txvTypeOutletCode,txvTown,txvTerritory,txvDistributor,txvDbArea,
              txvCluster,txvOutletName,txvRetailerName,txvRetailerMobile,txvAddress;

    EditText edtTown;


    EditText edtSearchOutletCode;
    String searchAPI = "";
    String searchOutletCode = "";
    private Dialog loadingDialog;

    int id;
    String mTown = "";
    String mTerritory = "";
    String mDistributor = "";
    String mDbArea = "";
    String mOutletName = "";
    String mRetailerName = "";
    String mRetailerMobile = "";
    String mAddress = "";
    int mClusterId;
    String mOutletCode = "";

    LogoutMenu logoutMenu;
    TextView txvUserName, txvNavUser;
    String userName = "";
    boolean canListenInput;
    boolean edtTextChangedByUser ;
    CsvFileExport csvFileExport;

    //basic information part
    String territory = "";
    String distributor = "";
    String outletName = "";
    String retailerName = "";
    String retailerMobile = "";
    String address = "";
    String outletId = "";
    String dbArea = "";
    String clusterId = "";

    BasicInformationSendToServer basicInformationSendToServer;


    OfflineDataAutoSync offlineDataReceiver;
    private BottomSheetDialog mBottomSheetDialog;

    public static boolean checkAutoStartPermission;
    Switch btnSwitch;
    //to use Firebase
    String fbToken = "";
    String gsm = "";
    String model = "";
    String mfg = "";
    String appVersion = "1.0.1";
    String userId = "";

    boolean isSendTokenToServer;
    FontCustomization fontCustomization;

    String checkUserStatus = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeUI();
        //Start broadcast receiver
//        offlineDataReceiver = new OfflineDataAutoSync();
//        registerWifiReceiver();
        fbToken = getValueFromSharedPreferences("token",BasicInformationActivity.this);
        userId = getValueFromSharedPreferences("user_id",BasicInformationActivity.this);
                //to use export all data from sqlite db
        csvFileExport = new CsvFileExport(BasicInformationActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userName = getValueFromSharedPreferences("user_id", BasicInformationActivity.this);
        if (userName != null && !userName.isEmpty()) {
            txvUserName.setText(userName);

        }
        View inflatedView = getLayoutInflater().inflate(R.layout.nav_header_basic_information, null);
        txvNavUser = (TextView) inflatedView.findViewById(R.id.txv_nav_user);
        if (userName != null && !userName.isEmpty()) {
            txvNavUser.setText(userName);
        }


        //hide keyboard
        btnEditAllField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasicInformationActivity.this, "Now You can Edit", Toast.LENGTH_SHORT).show();
                edtTown.setFocusableInTouchMode(true);

                txtTerritory.setFocusableInTouchMode(true);

                txtDistributor.setFocusableInTouchMode(true);

                txtDBArea.setFocusableInTouchMode(true);

                txtClusterId.setFocusableInTouchMode(true);

                txtOutletName.setFocusableInTouchMode(true);

                txtRetailerName.setFocusableInTouchMode(true);

                txtRetailerMobile.setFocusableInTouchMode(true);

                txtAddress.setFocusableInTouchMode(true);

                edtTextChangedByUser = true;


            }
        });
        //check text when user changed a text
        edtTown.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("town", edtTown.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });
        txtTerritory.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("territory", txtTerritory.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });
        txtDistributor.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("distributor", txtDistributor.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtDBArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("db_area", txtDBArea.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtClusterId.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("cluster_id", txtClusterId.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtOutletName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("outlet_name", txtOutletName.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtRetailerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("retailer_name", txtRetailerName.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtRetailerMobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("retailer_mobile", txtRetailerMobile.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtTextChangedByUser) {
                    saveToSharedPreferences("address", txtAddress.getText().toString(), BasicInformationActivity.this);
                    edtTextChangedByUser = true;
                }
            }
        });



        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUserStatus.equals("Enabled")) {

                    searchOutletCode = edtSearchOutletCode.getText().toString();
                    searchAPI = Constants.searchAPI + String.valueOf(searchOutletCode);
                    saveToSharedPreferences("outlet_id", searchOutletCode, BasicInformationActivity.this);

                    if (!searchOutletCode.equals("")) {
                        if (checkInternet()) {
                            GetSearchDataTask getSearchDataTask = new GetSearchDataTask();
                            getSearchDataTask.execute();
                        } else {
                            Toast.makeText(BasicInformationActivity.this, "Your Deveice is Offline", Toast.LENGTH_SHORT).show();
                        }

                    }

                    String startTime = Constants.getCurrentTime();


                    saveToSharedPreferences("start_time", startTime, BasicInformationActivity.this);
                    saveToSharedPreferences("outlet_code", searchOutletCode, BasicInformationActivity.this);
                    hideKeyboard(edtSearchOutletCode);

                    logoutMenu.clearWhenSearchNewOutletCode();
                }else {
                  //  Toast.makeText(BasicInformationActivity.this, "You are Suspend to use this app", Toast.LENGTH_SHORT).show();
                openDialog("You are Suspend to use this app");
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userId!=null && userId.length()>0){
                    boolean checkMobileNumber = isValidMobile(txtRetailerMobile.getText().toString());
                    if (edtSearchOutletCode.getText().toString().isEmpty()) {
                        Toast.makeText(BasicInformationActivity.this, "You did not enter any outlet code", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(edtTown.getText().toString().isEmpty()){
                        Toast.makeText(BasicInformationActivity.this, "You must give valid outlet Id", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (!checkMobileNumber) {
                        Toast.makeText(BasicInformationActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    }
                    if (!searchOutletCode.equals("")) {
                        Intent nextIntent = new Intent(BasicInformationActivity.this, CoolerStatusActivity.class);
                        startActivity(nextIntent);
                        // ActivityCompat.finishAffinity(BasicInformationActivity.this);


                    } else {
                        mTown = edtTown.getText().toString();
                        if (mTown.toString().length() > 0 && edtSearchOutletCode.toString().length() > 0) {
                            Intent nextIntent = new Intent(BasicInformationActivity.this, CoolerStatusActivity.class);
                            startActivity(nextIntent);
                            // ActivityCompat.finishAffinity(BasicInformationActivity.this);
                        }


                    }
                    //send basic edit data to server
                    if(edtTextChangedByUser){
                        territory = SharedPreferenceLocalMemory.getValueFromSharedPreferences("territory",BasicInformationActivity.this);
                        distributor = SharedPreferenceLocalMemory.getValueFromSharedPreferences("distributor",BasicInformationActivity.this);
                        outletName = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_name",BasicInformationActivity.this);
                        retailerName = SharedPreferenceLocalMemory.getValueFromSharedPreferences("retailer_name",BasicInformationActivity.this);
                        retailerMobile = SharedPreferenceLocalMemory.getValueFromSharedPreferences("retailer_mobile",BasicInformationActivity.this);
                        address =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("address",BasicInformationActivity.this);
                        outletId =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_code",BasicInformationActivity.this);
                        dbArea = SharedPreferenceLocalMemory.getValueFromSharedPreferences("db_area",BasicInformationActivity.this);
                        clusterId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cluster_id",BasicInformationActivity.this);


                        if(checkInternet()){
                            basicInformationSendToServer.sendBasicInformationToServer(outletId,distributor,
                                    dbArea,clusterId,outletName,retailerName,retailerMobile,address);
                        }else {
                            Toast.makeText(BasicInformationActivity.this, "Your Device is Offline", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {
                    Toast.makeText(BasicInformationActivity.this, "Your User Id is Missing ,Please Login Again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        gsm = getPhone();
        model = getAndroidDeviceModel();
        mfg = getDeviceName();
        isSendTokenToServer = getBoleanValueSharedPreferences("is_send_token",BasicInformationActivity.this);

        if(!isSendTokenToServer){
            if(fbToken!=null){
                if(fbToken.length()>0){
                    sendRegistrationToServer(userId, model, mfg, fbToken,appVersion);
                    saveBoleanValueSharedPreferences("is_send_token",true,BasicInformationActivity.this);
                }

            }


        }

    }


    protected boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initializeUI();
        String outletCode = getValueFromSharedPreferences("outlet_code",BasicInformationActivity.this);
        if(searchOutletCode!=null){
            edtSearchOutletCode.setText(outletCode);
        }

        String town = getValueFromSharedPreferences("town",BasicInformationActivity.this);
        if( town!=null){
            edtTown.setText(town);
        }
        String territory = getValueFromSharedPreferences("territory",BasicInformationActivity.this);
        if( territory!=null){
            txtTerritory.setText(territory);
        }
        String distributor = getValueFromSharedPreferences("distributor",BasicInformationActivity.this);
        if(distributor!=null){
            txtDistributor.setText(distributor);
        }
        String dbArea = getValueFromSharedPreferences("db_area",BasicInformationActivity.this);
        if(dbArea!=null){
            txtDBArea.setText(dbArea);
        }

        String clusterId = getValueFromSharedPreferences("cluster_id",BasicInformationActivity.this);
        if(clusterId!=null){
            txtClusterId.setText(clusterId);
        }

        String outletName = getValueFromSharedPreferences("outlet_name",BasicInformationActivity.this);
        if(outletName!=null){
            txtOutletName.setText(outletName);
        }

        String retailerName = getValueFromSharedPreferences("retailer_name",BasicInformationActivity.this);
        if(retailerName!=null){
            txtRetailerName.setText(retailerName);
        }

        String retailerMobile = getValueFromSharedPreferences("retailer_mobile",BasicInformationActivity.this);
        if(retailerMobile!=null){
            txtRetailerMobile.setText(retailerMobile);
        }

        String address = getValueFromSharedPreferences("address",BasicInformationActivity.this);
        if( address!=null){
            txtAddress.setText(address);
        }



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // unregisterWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean checkAppOn = getBoleanValueSharedPreferences("app_on_yes",BasicInformationActivity.this);
        if(btnSwitch!=null) {
            if (checkAppOn) {
                btnSwitch.setChecked(checkAppOn);
            } else {

                btnSwitch.setChecked(false);
            }
        }
    }
    public void initializeUI(){
        checkUserStatus = getValueFromSharedPreferences("check_status",BasicInformationActivity.this);

        txtTerritory = (EditText) findViewById(R.id.txt_territory);
        txtDistributor = (EditText) findViewById(R.id.txt_distributor);
        txtDBArea = (EditText) findViewById(R.id.txt_db_area);
        txtOutletName = (EditText) findViewById(R.id.txt_outlet_name);
        txtRetailerName = (EditText) findViewById(R.id.txt_retailer_name);
        txtRetailerMobile = (EditText) findViewById(R.id.txt_retailer_mobile);
        txtAddress = (EditText) findViewById(R.id.txt_address);
        edtSearchOutletCode = (EditText)findViewById(R.id.edt_search_outlet_code);
        //txtTown = (TextView) findViewById(R.id.txt_town);
        txtClusterId = (EditText) findViewById(R.id.txt_cluster);

        btnEditAllField = (Button)findViewById(R.id.btn_edt_all_field);
        btnEditAllField.setTransformationMethod(null);
        //txvUser = (TextView)findViewById(R.id.txv_user);
        txvUserName =(TextView)findViewById(R.id.txv_user_name);
     //   txvNavUser =(TextView)findViewById(R.id.txv_nav_user) ;

        btnOK = (Button)findViewById(R.id.btn_ok);
        btnOK.setTransformationMethod(null);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setTransformationMethod(null);
        //edtext
        edtTown = (EditText)findViewById(R.id.txt_town);
        edtTown.setFocusable(false);
        txtTerritory.setFocusable(false);
        txtDistributor.setFocusable(false);
        txtDBArea.setFocusable(false);
        txtClusterId.setFocusable(false);
        txtOutletName.setFocusable(false);
        txtRetailerName.setFocusable(false);
        txtRetailerMobile.setFocusable(false);
        txtAddress.setFocusable(false);
        logoutMenu = new LogoutMenu(BasicInformationActivity.this);
        basicInformationSendToServer = new BasicInformationSendToServer(BasicInformationActivity.this);
        mBottomSheetDialog = new BottomSheetDialog(BasicInformationActivity.this);
        //To use customize font
        fontCustomization = new FontCustomization(BasicInformationActivity.this);
        txvTypeOutletCode = (TextView)findViewById(R.id.txv_type_outlet_code);
        txvTown = (TextView)findViewById(R.id.txv_town);
        txvTerritory = (TextView)findViewById(R.id.txv_territory);
        txvDistributor = (TextView)findViewById(R.id.txv_distributor);
        txvDbArea = (TextView)findViewById(R.id.txv_db_area);
        txvCluster = (TextView)findViewById(R.id.txv_cluster);
        txvOutletName = (TextView)findViewById(R.id.txv_outlet_name);
        txvRetailerName = (TextView)findViewById(R.id.txv_retailer_name);
        txvRetailerMobile = (TextView)findViewById(R.id.txv_retailer_mobile);
        txvAddress = (TextView)findViewById(R.id.txv_address);

        txvTypeOutletCode.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvUserName.setTypeface(fontCustomization.getTexgyreHerosBold());
        btnEditAllField.setTypeface(fontCustomization.getTexgyreHerosBold());
        btnOK.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvTown.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvTerritory.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvDistributor.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvDbArea.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCluster.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvOutletName.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvRetailerName.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvRetailerMobile.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvAddress.setTypeface(fontCustomization.getTexgyreHerosBold());
        btnNext.setTypeface(fontCustomization.getTexgyreHerosBold());
        edtSearchOutletCode.setTypeface(fontCustomization.getTexgyreHerosRegular());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_input_data) {
            // Handle the camera action
            Intent viewInputData = new Intent(BasicInformationActivity.this,ViewInputDataActivity.class);
            startActivity(viewInputData);

        } else if (id == R.id.nav_all_existing_data) {
            Intent intent = new Intent(BasicInformationActivity.this,AllExistingDataViewActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_offline_data) {
            Intent intent = new Intent(BasicInformationActivity.this,OfflineDataActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_export_data) {
            csvFileExport.csvFileExport();
        }else if (id == R.id.nav_rejected_data) {
           Intent intent = new Intent(BasicInformationActivity.this,RejectedDataActivity.class);
           startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            logoutMenu.logoutNavigation();
            ActivityCompat.finishAffinity(BasicInformationActivity.this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic_information, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(BasicInformationActivity.this);
                break;
            case R.id.menu_switch_auto_sync:
                autoSyncLayout();
                boolean checkAppOn = getBoleanValueSharedPreferences("app_on_yes",BasicInformationActivity.this);
                if(btnSwitch!=null) {
                    if (checkAppOn) {
                        btnSwitch.setChecked(checkAppOn);
                    } else {

                        btnSwitch.setChecked(false);
                    }
                }
                break;

            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }

    public class GetSearchDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(BasicInformationActivity.this, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(searchAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            edtTown.setText("");
                            txtTerritory.setText("");
                            txtDistributor.setText("");
                            txtDBArea.setText("");
                            txtClusterId.setText(String.valueOf(""));
                            txtOutletName.setText("");
                            txtRetailerName.setText("");
                            txtRetailerMobile.setText("");
                            txtAddress.setText("");
                            Toast.makeText(BasicInformationActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();

                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {
                    responseBodyText = response.body().string();


                    JSONObject jobject = new JSONObject(responseBodyText);

                     id = jobject.getInt("id");
                     saveToSharedPreferences("id",String.valueOf(id),BasicInformationActivity.this);
                     mOutletCode = jobject.getString("outlet_code");
                     saveToSharedPreferences("outlet_code",mOutletCode,BasicInformationActivity.this);
                      mTerritory = jobject.getString("territory");
//                    String ceName = jobject.getString("ce_name");
                      setValueToSharedPreferences("territory",mTerritory,BasicInformationActivity.this);
                     mDistributor = jobject.getString("distributor");
                     saveToSharedPreferences("distributor",mDistributor,BasicInformationActivity.this);
                     mDbArea = jobject.getString("db_area");
                     saveToSharedPreferences("db_area",mDbArea,BasicInformationActivity.this);
//                    String psrName = jobject.getString("psr_name");
                     mOutletName = jobject.getString("outlet_name");
                     saveToSharedPreferences("outlet_name",mOutletName,BasicInformationActivity.this);
                     mRetailerName = jobject.getString("retailer_name");
                     saveToSharedPreferences("retailer_name",mRetailerName,BasicInformationActivity.this);
                    mRetailerMobile = jobject.getString("retailer_mobile");
                    saveToSharedPreferences("retailer_mobile",mRetailerMobile,BasicInformationActivity.this);
                    mAddress = jobject.getString("address");
                    saveToSharedPreferences("address",mAddress,BasicInformationActivity.this);
                    mTown = jobject.getString("town");
                    saveToSharedPreferences("town",mTown,BasicInformationActivity.this);
//                    String ceArea = jobject.getString("ce_area");
                     mClusterId = jobject.getInt("cluster_id");
                    saveToSharedPreferences("cluster_id",String.valueOf(mClusterId),BasicInformationActivity.this);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        edtTown.setText(mTown);
                        txtTerritory.setText(mTerritory);
                        txtDistributor.setText(mDistributor);
                        txtDBArea.setText(mDbArea);
                        txtClusterId.setText(String.valueOf(mClusterId));
                        txtOutletName.setText(mOutletName);
                        txtRetailerName.setText(mRetailerName);

                        //To add 0 in retailer Mobile no
                            if(!mRetailerMobile.contains("0")){
                                txtRetailerMobile.setText("0"+mRetailerMobile);
                            }else {
                                txtRetailerMobile.setText(mRetailerMobile);
                            }

                        txtAddress.setText(mAddress);

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


            // Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();

        }
    }
    private boolean isValidMobile(String phone)

    {

        Pattern pattern = Pattern.compile("^(?:\\+?88)?01[5-9]\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
          //  Toast.makeText(BasicInformationActivity.this, "valid Phone Number", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            //Toast.makeText(BasicInformationActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }



    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(offlineDataReceiver, filter);
    }
    private void unregisterWifiReceiver() {
        this.unregisterReceiver(offlineDataReceiver);
    }

    public void autoSyncLayout(){
        final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.auto_sync_layout, null);
        mBottomSheetDialog.setContentView(bottomSheetLayout);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.TOP);
        mBottomSheetDialog.show();

        btnSwitch = (Switch)bottomSheetLayout.findViewById(R.id.switch_auto_sync);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveBoleanValueSharedPreferences("app_on_yes",true,BasicInformationActivity.this);
                    saveBoleanValueSharedPreferences("app_on_no",false,BasicInformationActivity.this);
                    checkAutoStartPermission = getBoleanValueSharedPreferences("auto_start",BasicInformationActivity.this);

                    if(checkAutoStartPermission==false){
                        try {
                            Intent intent = new Intent();
                            String manufacturer = android.os.Build.MANUFACTURER;
                            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                                //checkAutoStartPermission = true;
                                saveBoleanValueSharedPreferences("auto_start",true,BasicInformationActivity.this);
                            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                                saveBoleanValueSharedPreferences("auto_start",true,BasicInformationActivity.this);
                            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                                saveBoleanValueSharedPreferences("auto_start",true,BasicInformationActivity.this);
                            }else if ("huawei".equalsIgnoreCase(manufacturer)) {
                                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                                saveBoleanValueSharedPreferences("auto_start",true,BasicInformationActivity.this);
                            }

                            List<ResolveInfo> list = BasicInformationActivity.this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                            if  (list.size() > 0) {
                               BasicInformationActivity.this.startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                } else {
                    saveBoleanValueSharedPreferences("app_on_yes",false,BasicInformationActivity.this);
                    saveBoleanValueSharedPreferences("app_on_no",true,BasicInformationActivity.this);
                    ComponentName component = new ComponentName(BasicInformationActivity.this, OfflineDataAutoSync.class);
                    PackageManager pm = BasicInformationActivity.this.getPackageManager();
                    pm.setComponentEnabledSetting(
                            component,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                }

            }
        });
    }
    private void sendRegistrationToServer(String userId, String model, String mfg, String deviceToken,String id) {
        //You can implement this method to store the token on your server
        //Not required for current project
        RegisterToken registerToken = new  RegisterToken(userId, model, mfg, deviceToken,id);
        registerToken.execute();
    }
    class RegisterToken extends AsyncTask<String, Void, String> {

        String mDeviceToken;
        String mModel;
        String mUserId;
        String mMfg;
        String mId;

        public RegisterToken(String gsm, String model, String mfg, String deviceToken,String id) {
            this.mUserId = gsm;
            this.mModel = model;
            this.mMfg = mfg;
            this.mDeviceToken = deviceToken;
            this.mId = id ;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("gsm", mUserId)
                        .add("model", mModel)
                        .add("mfg", mMfg)
                        .add("token", mDeviceToken)
                        .add("id", mId)
                        .build();


                Request request = new Request.Builder()
                        .url(Constants.pushNotificationAPI)
                        .post(requestBody)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                 //   Log.d(RESPONSE_LOG, result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }


    public String getAndroidDeviceModel() {
        return Build.MODEL;
    }

    public String getDeviceName() {
        String manufacturer = android.os.Build.MANUFACTURER;

        return manufacturer;
    }

    private String getPhone() {

        //to use phone state permission
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String phNumber = phoneMgr.getLine1Number();
        return phNumber;
    }

    public void openDialog(String msgId) {
        final Dialog dialog = new Dialog(BasicInformationActivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.custom_dialog);
        TextView txvResponseMsg = (TextView) dialog.findViewById(R.id.dialog_info);
        txvResponseMsg.setText(msgId);
        Button okButton = (Button) dialog.findViewById(R.id.dialog_ok);
//        Button cancleButton = (Button) dialog.findViewById(R.id.dialog_cancel);
        dialog.setCancelable(false);

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(BasicInformationActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                ActivityCompat.finishAffinity(BasicInformationActivity.this);
                dialog.dismiss();

            }
        });
//        cancleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }

}
