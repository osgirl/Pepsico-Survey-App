package limited.it.planet.visicoolertracking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 1/29/2018.
 */

public class Constants {

    public  static  final String loginAPI = "https://server2.planetgroupbd.com/ords/pepsi/v1/auth/";
    public  static  final String serveyAPI = "https://server2.planetgroupbd.com/ords/pepsi/v1/data/survey/";
    public  static  final String searchAPI = "https://server2.planetgroupbd.com/ords/pepsi/v1/outlet/";
    public  static  final String photoUploadAPI = "https://server2.planetgroupbd.com/ords/pepsi/v1/upload/photo/";
    public  static  final String allExistingDataAPI= "http://server2.planetgroupbd.com/ords/pepsi/v1/data/visited/";
    public  static  final String allExistingDataTotalAPI= "http://server2.planetgroupbd.com/ords/pepsi/v1/total/visited/";
    public  static  final String basicInformationAPI= "https://server2.planetgroupbd.com/ords/pepsi/v1/data/outlet/";
    public  static  final String pushNotificationAPI= "https://server2.planetgroupbd.com/ords/pepsi/v1/device/token";
    public  static  final String rejectedDataAPI = "https://server2.planetgroupbd.com/ords/pepsi/v1/data/rejected/";
    public  static  final String searchByDateAPI = "http://server2.planetgroupbd.com/ords/pepsi/v1/data/visited/";
    public  static  final String checkValidUser = "https://server2.planetgroupbd.com/ords/pepsi/v1/user/status/";


    public static final String  LOG_TAG_RESPONSE = "LOG_TAG_RESPONSE";


    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getCurrentEntryDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
