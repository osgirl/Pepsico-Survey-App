package limited.it.planet.visicoolertracking.services;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import limited.it.planet.visicoolertracking.activities.BasicInformationActivity;
import limited.it.planet.visicoolertracking.activities.OfflineDataActivity;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.ServeyModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

/**
 * Created by Master on 2/6/2018.
 */

public class OfflineDataAutoSync extends BroadcastReceiver{
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


    @Override
    public void onReceive(final Context context, Intent intent) {

        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            // Do something
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
                }

            }
            localStorageDB = new LocalStorageDB(context);
            localStorageDB.open();
            serveyDataSendToServer = new ServeyDataSendToServer(context);
            serveyModel = new ServeyModel();

//            if(checkInternet(context)){
//                //send data to server
//                ArrayList<ServeyModel> failedList = localStorageDB.checkFailedDataFromTable("failled");
//                if(failedList.size()>0){
//                    for (int i = 0; i < failedList.size(); i++) {
//                        outletId = failedList.get(i).getOutletId();
//                        channel = failedList.get(i).getChannel();
//                        coolerStatus = failedList.get(i).getCoolerStatus();
//                        coolerCharging = failedList.get(i).getCoolerCharging();
//                        grb = failedList.get(i).getGrb();
//                        can500 = failedList.get(i).getCan500();
//                        shelves = failedList.get(i).getShelves();
//                        cleanliness = failedList.get(i).getCleanliness();
//                        primePosition = failedList.get(i).getPrimePosition();
//                        availabilty = failedList.get(i).getAvailabilty();
//                        remarks = failedList.get(i).getRemarks();
//                        latitude = failedList.get(i).getLatitude();
//                        longitude = failedList.get(i).getLongitude();
//                        userId = failedList.get(i).getUserId();
//                        entryDate = failedList.get(i).getEntryDate();
//                        coolerPid = failedList.get(i).getCoolerPID();
//                        outletPid = failedList.get(i).getOutletPID();
//                        coolerPicCode = failedList.get(i).getCoolerPicCode();
//                        outletPicCode = failedList.get(i).getOutletPicCode();
//                        coolerPurity = failedList.get(i).getCoolerPurity();
//                        can = failedList.get(i).getCan();
//                        goPack = failedList.get(i).getGoPack();
//                        can400 = failedList.get(i).getCan400();
//                        ltr1 = failedList.get(i).getLtr1();
//                        ltr2 = failedList.get(i).getLtr2();
//                        aquafina = failedList.get(i).getAquafina();
//                        coolerActive = failedList.get(i).getCoolerActive();
//                        lightWorking = failedList.get(i).getLightWorking();
//                        startTime = failedList.get(i).getStartTime();
//                        endTime = failedList.get(i).getEndTime();
//
//                        if (coolerPicCode!=null){
//                            if(coolerPicCode.equals("0") || coolerPicCode.equals("")){
//                                String coolerPicPath =  localStorageDB.getCoolerPicPath(outletId);
//
//                                if(coolerPicPath!=null && coolerPicPath.length()>0){
//
//                                    UpladCoolerPicToServer upladCoolerPicToServer = new UpladCoolerPicToServer(coolerPicPath,context);
//                                    upladCoolerPicToServer.execute(Uri.parse(coolerPicPath));
//
//                                }
//                        }
//                        }
//
//                        if(outletPicCode!=null){
//                            if(outletPicCode.equals("0") || outletPicCode.equals("")){
//                                String outletPicPath =  localStorageDB.getOutletPicPath(outletId);
//
//                                if(outletPicPath!=null && outletPicPath.length()>0){
//                                    UpladOutletPicToServer upladOutletPicToServer = new UpladOutletPicToServer(outletPicPath,context);
//                                    upladOutletPicToServer.execute(Uri.parse(outletPicPath));
//
//                                }
//                            }
//                        }
//
//                    }
//
//
//
//                    if (outletId != null && outletId.length()>0) {
//                        if(coolerPicCode!=null && outletPicCode!=null){
//                            if((!coolerPicCode.equals("0") && coolerPicCode.length()>0 )&& (!outletPicCode.equals("0") && outletPicCode.length()>0)){
//                                serveyDataSendToServer(outletId, channel, coolerStatus, coolerCharging, grb,
//                                        can500, shelves, cleanliness, primePosition, availabilty,
//                                        remarks, latitude, longitude, userId, entryDate, coolerPicCode,
//                                        outletPicCode, coolerPurity, can, goPack, can400,
//                                        ltr1, ltr2,
//                                        aquafina, coolerActive, lightWorking, startTime, endTime,context);
//                            }
//                        }
//
//
//                    }
//                }
//
//
//            }
        }






    }
    protected boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public void serveyDataSendToServer(String outletId, String channel, String coolerStatus, String coolerCharging, String grb,
                                       String can500, String shelves, String cleanliness, String primePosition, String availabilty,
                                       String remarks, String latitude , String longitude, String userId, String entryDate, String coolerPID,
                                       String outletPID, String culerPurity, String can , String goPack, String can400, String ltr1, String ltr2,
                                       String aquafina, String coolerActive, String lightWorking, String startTime, String endTime,final Context context){
        class serveyDataSendTask extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              //  loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
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
                            if(outletId!=null && !outletId.isEmpty()){
                                // long ROWID = Long.parseLong(rowId);
                                localStorageDB.updateSyncStatus(outletId,responseResult);
                            }



                        }else {
                            Toast.makeText(context, "Error Found", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



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

                /// Toast.makeText(getApplicationContext(), "Your offline data Add Successfully To Server", Toast.LENGTH_LONG).show();
                String responseString = result;

              //  loadingDialog.dismiss();
            }
        }

        serveyDataSendTask la = new serveyDataSendTask();
        la.execute( outletId, channel, coolerStatus, coolerCharging,  grb,
                can500,  shelves,  cleanliness,  primePosition,  availabilty,
                remarks,  latitude ,  longitude,  userId,  entryDate,  coolerPID,
                outletPID,  culerPurity,  can ,  goPack,  can400,  ltr1,  ltr2,
                aquafina,  coolerActive,  lightWorking,  startTime,  endTime);
    }


     class UpladCoolerPicToServer extends AsyncTask<Uri, Integer, String> {
         String mContentFilePath;
         Context mContext;

         UpladCoolerPicToServer(String contentFilePath,Context context){
             this.mContentFilePath = contentFilePath;
             this.mContext = context;
         }
         private String getRealPathFromURI(Uri contentURI) {
             String result;
             Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
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
             //progressBar.setVisibility(View.VISIBLE);
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
                     saveToSharedPreferences("cooler_pic_code","0",mContext);

                     throw new IOException("Okhttp Error: " + response);

                 } else {
                     responseBodyText = response.body().string();
                     JSONObject jsonObject = null;
                     try {
                         jsonObject = new JSONObject(responseBodyText);

                         String coolerPicCode = jsonObject.getString("image_id");
                         //update to local db cooler pic code
                         if(outletId!=null && !outletId.isEmpty()){
                             // long ROWID = Long.parseLong(rowId);
                             localStorageDB.open();
                             localStorageDB.updateFailledCoolerPicCode(outletId,coolerPicCode);


                         }



                     } catch (JSONException e) {
                         e.printStackTrace();
                     }




                 }



             } catch (IOException e) {
                 saveToSharedPreferences("cooler_pic_code","0",mContext);
                 e.printStackTrace();
             }


             return responseBodyText;


         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);




         }
     }

     class UpladOutletPicToServer extends AsyncTask<Uri, Integer, String> {
         String mContentFilePath;
         Context mContext;

         UpladOutletPicToServer(String contentFilePath,Context context){
             this.mContentFilePath = contentFilePath;
             this.mContext = context;
         }
         private String getRealPathFromURI(Uri contentURI) {
             String result;
             Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
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
                     saveToSharedPreferences("outlet_pic_code","0",mContext);

                     throw new IOException("Okhttp Error: " + response);



                 } else {
                     responseBodyText = response.body().string();
                     JSONObject jsonObject = null;
                     try {
                         jsonObject = new JSONObject(responseBodyText);
                         String outletPicCode = jsonObject.getString("image_id");
                         if(outletId!=null && !outletId.isEmpty()){
                             // long ROWID = Long.parseLong(rowId);
                             localStorageDB.open();
                             // String outletId = outletCode;
                             localStorageDB.updateFailledOutletPicCode(outletId,outletPicCode);

                         }


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }



                 }



             } catch (IOException e) {
                 saveToSharedPreferences("outlet_pic_code","0",mContext);
                 e.printStackTrace();
             }


             return responseBodyText;


         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
             //progressBar.setVisibility(View.INVISIBLE);

             //Toast.makeText(context,"Update Outlet PIC Code Successfully",Toast.LENGTH_SHORT).show();

         }
     }






}
