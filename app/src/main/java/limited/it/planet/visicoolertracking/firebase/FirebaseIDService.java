package limited.it.planet.visicoolertracking.firebase;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by interspeed.com.bd on 21-Aug-16.
 */
public class FirebaseIDService extends FirebaseInstanceIdService {
    public static final String TAG = "MyFirebaseIIDService";
    public static final String RESPONSE_LOG = Constants.LOG_TAG_RESPONSE;

    String refreshedToken = "";
    String gsm = "";
    String model = "";
    String mfg = "";
    String id = "1.0";


    //String URL ="http://10.0.0.16/fcm/register.php";
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        gsm = getPhone();
        model = getAndroidDeviceModel();
        mfg = getDeviceName();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //sendRegistrationToServer(gsm, model, mfg, refreshedToken,id);
        SharedPreferenceLocalMemory.saveToSharedPreferences("token",refreshedToken,this);


    }

    private void sendRegistrationToServer(String gsm, String model, String mfg, String deviceToken,String id) {
        //You can implement this method to store the token on your server
        //Not required for current project
        RegisterToken registerToken = new RegisterToken(gsm, model, mfg, deviceToken,id);
        registerToken.execute();
    }

    class RegisterToken extends AsyncTask<String, Void, String> {

        String mDeviceToken;
        String mModel;
        String mGsm;
        String mMfg;
        String mId;

        public RegisterToken(String gsm, String model, String mfg, String deviceToken,String id) {
            this.mGsm = gsm;
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
                        .add("gsm", mGsm)
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
                    Log.d(RESPONSE_LOG, result);
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

}
