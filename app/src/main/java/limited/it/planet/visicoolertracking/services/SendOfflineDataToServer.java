package limited.it.planet.visicoolertracking.services;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.ServeyModel;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;

/**
 * Created by Tarikul on 2/8/2018.
 */

public class SendOfflineDataToServer extends IntentService {
    private static final String TAG = "com.example.lenovotp.sender";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Calendar calendar = Calendar.getInstance();
    LocalStorageDB localStorageDB ;

    ServeyDataSendToServer serveyDataSendToServer ;
    ServeyModel serveyModel ;
    String coolerPrimePosition = "";
    String remarks = "";
    String channel = "";
    String coolerStatus = "";
    String dbArea = "";
    String town = "";
    int clusterId;
    String cluster = "";
    String noOfActiveCooler = "";
    String coolerCharging = "";
    //String syncStatus = "failled";
    String coolerPicCode = "";
    String outletPicCode = "";
    String startTime = "";
    String endTime = "";
    String entryDate = "";
    String userId = "";
    String outletId = "";
    String grb = "";
    String can500 = "";
    String shelves = "";
    String cleanliness = "";
    String primePosition = "";
    String availabilty = "";
    String latitude = "";
    String longitude = "";
    String coolerPid = "";
    String outletPid = "";
    String coolerPurity = "";
    String can = "";
    String goPack = "";
    String can400 = "";
    String ltr1 = "";
    String ltr2 = "";
    String aquafina = "";
    String coolerActive = "";
    String lightWorking = "";


    public SendOfflineDataToServer( ){
        super("");


    }
    @Override
    public void onCreate() {
        super.onCreate();
        localStorageDB = new LocalStorageDB(this);
        localStorageDB.open();
        serveyDataSendToServer = new ServeyDataSendToServer(this);
        serveyModel = new ServeyModel();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(this, SendOfflineDataToServer.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP,
                100000 * 6000, alarmIntent);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        //boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        if(isConnected==true){






        }

    }

}
