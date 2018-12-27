package limited.it.planet.visicoolertracking.services;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

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
import java.util.List;

import limited.it.planet.visicoolertracking.activities.BasicInformationActivity;
import limited.it.planet.visicoolertracking.activities.ReconfirmedPage;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;

/**
 * Created by Tarikul on 2/7/2018.
 */

public class ServeyDataSendToServer {
    public Context mContext;
    LocalStorageDB localStorageDB;
    boolean isActiveOfflineDataActivity;


    public ServeyDataSendToServer(Context context){
        this.mContext = context;
        localStorageDB = new LocalStorageDB(mContext);

    }

    public void serveyDataSendToServer(String outletId, String channel, String coolerStatus, String coolerCharging, String grb,
                                       String can500, String shelves, String cleanliness,  String primePosition, String availabilty,
                                       String remarks, String latitude , String longitude, String userId, String entryDate, String coolerPID,
                                       String outletPID, String culerPurity, String can , String goPack, String can400, String ltr1, String ltr2,
                                       String aquafina, String coolerActive, String lightWorking, String startTime, String endTime){
        class serveyDataSendTask extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(mContext, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                final String outletId = params[0];
                final String channel = params[1];
                final String coolerStatus = params[2];
                final String coolerCharging = params[3];
                final String grb = params[4];
                final String can500 = params[5];
                final String shelves = params[6];
                final String cleanlines = params[7];
                final String primePosition = params[8];
                final String availability = params[9];
                final String remarks = params[10];
                final String latitude = params[11];
                final String longitude = params[12];
                final String userId = params[13];
                final String entryDate = params[14];
                final String coolerPID = params[15];
                final String outletPID = params[16];
                final String coolerPurity = params[17];
                final String can = params[18];
                final String goPack = params[19];
                final String can400 = params[20];
                final String ltr1 = params[21];
                final String ltr2 = params[22];
                final String aquafina = params[23];
                final String coolerActive = params[24];
                final String lightWoring = params[25];
                final String startTime = params[26];
                final String endTime = params[27];
                final String appVersion = "1.0.1";

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("outlet_id", outletId));
                nameValuePairs.add(new BasicNameValuePair("channel", channel));
                nameValuePairs.add(new BasicNameValuePair("cooler_status", coolerStatus));
                nameValuePairs.add(new BasicNameValuePair("cooler_charging", coolerCharging));
                nameValuePairs.add(new BasicNameValuePair("grb", grb));
                nameValuePairs.add(new BasicNameValuePair("can_500", can500));
                nameValuePairs.add(new BasicNameValuePair("shelves", shelves));
                nameValuePairs.add(new BasicNameValuePair("cleanliness", cleanlines));
                nameValuePairs.add(new BasicNameValuePair("prime_position", primePosition));
                nameValuePairs.add(new BasicNameValuePair("availability", availability));
                nameValuePairs.add(new BasicNameValuePair("remarks", remarks));
                nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
                nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
                nameValuePairs.add(new BasicNameValuePair("user_id", userId));
                nameValuePairs.add(new BasicNameValuePair("entry_date", entryDate));
                nameValuePairs.add(new BasicNameValuePair("cooler_pid", coolerPID));
                nameValuePairs.add(new BasicNameValuePair("outlet_pid", outletPID));
                nameValuePairs.add(new BasicNameValuePair("cooler_purity", coolerPurity));
                nameValuePairs.add(new BasicNameValuePair("can", can));
                nameValuePairs.add(new BasicNameValuePair("go_pack", goPack));
                nameValuePairs.add(new BasicNameValuePair("can_400", can400));
                nameValuePairs.add(new BasicNameValuePair("ltr_1", ltr1));
                nameValuePairs.add(new BasicNameValuePair("ltr_2", ltr2));
                nameValuePairs.add(new BasicNameValuePair("aquafina", aquafina));
                nameValuePairs.add(new BasicNameValuePair("cooler_active", coolerActive));
                nameValuePairs.add(new BasicNameValuePair("light_working", lightWoring));
                nameValuePairs.add(new BasicNameValuePair("start_time", startTime));
                nameValuePairs.add(new BasicNameValuePair("end_time", endTime));
                nameValuePairs.add(new BasicNameValuePair("apk", appVersion));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Constants.serveyAPI);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        //  sb.append(line + "\n");
                        sb.append(line);

                    }
                    result = sb.toString();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String responseResult = jsonObject.getString("response");
                        if(responseResult.equals("success")){
                            localStorageDB.open();

                            try {
                               // String outletId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_id",mContext);
                                if(outletId!=null && !outletId.isEmpty()){
                                   // long ROWID = Long.parseLong(rowId);
                                    localStorageDB.updateSyncStatus(outletId,responseResult);
                                    ((Activity)mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                        }
                                    });
                                }


                            }catch (NumberFormatException e){
                                e.getMessage();
                            }



                        }else {

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


//                    String checkRegister = " created";
//
//                    if (result.equals(checkRegister)){
//                        ((Activity)mContext).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//
//                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                loadingDialog.dismiss();

                String responseString = result;


                    Toast.makeText(mContext, "Your Information Add Successfully To Server", Toast.LENGTH_LONG).show();
                    Intent backIntent = new Intent(mContext, BasicInformationActivity.class);
                    mContext.startActivity(backIntent);
                    ActivityCompat.finishAffinity((Activity)mContext);



            }
        }

        serveyDataSendTask la = new serveyDataSendTask();
        la.execute( outletId, channel, coolerStatus, coolerCharging,  grb,
                can500,  shelves,  cleanliness,  primePosition,  availabilty,
                 remarks,  latitude ,  longitude,  userId,  entryDate,  coolerPID,
                 outletPID,  culerPurity,  can ,  goPack,  can400,  ltr1,  ltr2,
                 aquafina,  coolerActive,  lightWorking,  startTime,  endTime);
    }



}
