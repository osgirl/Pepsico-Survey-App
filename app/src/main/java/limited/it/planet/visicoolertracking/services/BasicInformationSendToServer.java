package limited.it.planet.visicoolertracking.services;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import limited.it.planet.visicoolertracking.util.Constants;

/**
 * Created by Tarikul on 3/1/2018.
 */

public class BasicInformationSendToServer {
    Context mContext ;
    public BasicInformationSendToServer (Context context){
        this.mContext = context;
    }

   public void sendBasicInformationToServer(String outletId, final String distributor, String dbArea, String cluster, String outletName,
                                            String retailerName, String retailerMobile, String retailerAddress){
       class serveyDataSendTask extends AsyncTask<String, Void, String> {
           String responseResult = "";

           private Dialog loadingDialog;

           @Override
           protected void onPreExecute() {
               super.onPreExecute();
               loadingDialog = ProgressDialog.show(mContext, "Please wait", "Loading...");
           }

           @Override
           protected String doInBackground(String... params) {
               final String outletId = params[0];
               final String distributor =params[1];
               final String dbArea = params[2];

               final int cluster = Integer.parseInt(params[3]);
               final String outletName = params[4];
               final String retailerName = params[5];
               final String retailerMobile = params[6];
               final String retailerAddress = params[7];

               InputStream is = null;
               List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

               nameValuePairs.add(new BasicNameValuePair("outlet", outletId));
               nameValuePairs.add(new BasicNameValuePair("distributor", distributor));
               nameValuePairs.add(new BasicNameValuePair("db_area", dbArea));
               nameValuePairs.add(new BasicNameValuePair("cluster", Integer.toString(cluster)));
               nameValuePairs.add(new BasicNameValuePair("outlet_name", outletName));
               nameValuePairs.add(new BasicNameValuePair("retailer_name", retailerName));
               nameValuePairs.add(new BasicNameValuePair("retailer_mobile", retailerMobile));
               nameValuePairs.add(new BasicNameValuePair("retailer_address", retailerAddress));


               String result = null;

               try{
                   HttpClient httpClient = new DefaultHttpClient();
                   HttpPost httpPost = new HttpPost(
                           Constants.basicInformationAPI);
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
                      responseResult = jsonObject.getString("Response");



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
               return responseResult;
           }

           @Override
           protected void onPostExecute(String result){
               loadingDialog.dismiss();

               String responseString = result;
              if(responseString.equals("success")){
                  Toast.makeText(mContext, "Your Edit Information Add Successfully To Server", Toast.LENGTH_LONG).show();
              }

           }
       }

       serveyDataSendTask la = new serveyDataSendTask();
       la.execute( outletId, distributor, dbArea, cluster,  outletName,
               retailerName,  retailerMobile,  retailerAddress);
   }
}
