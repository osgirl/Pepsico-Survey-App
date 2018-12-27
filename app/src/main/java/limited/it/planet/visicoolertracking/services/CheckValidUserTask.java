package limited.it.planet.visicoolertracking.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import limited.it.planet.visicoolertracking.util.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

/**
 * Created by Tarikul on 7/30/2018.
 */

public class CheckValidUserTask {
    Context mContext;
    String validUser = " ";
    String checkValidUserAPI = " ";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public CheckValidUserTask(Context context){
        this.mContext = context;
        checkValidUserAPI = Constants.checkValidUser;
    }

  public void checkValidUser(String userName){
            CheckValidUser checkValidUser = new CheckValidUser(userName);
            checkValidUser.execute();
  }

    public class CheckValidUser extends AsyncTask<String, Integer, String> {

        String mUserName;

        // private Dialog loadingDialog;
        public CheckValidUser (String userName){
            mUserName = userName;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  loadingDialog = ProgressDialog.show(mContext, "Please wait", "Loading...");

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {

               // JSONObject json = new JSONObject();

                //RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));


                Request request = new Request.Builder()
                        .url(checkValidUserAPI + mUserName)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    // Log.d(RESPONSE_LOG,result);

                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        validUser = jsonObject.getString("status");
                        if(validUser.length()>0){
                            saveToSharedPreferences("check_status",validUser,mContext);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    saveToSharedPreferences("check_status","invalid",mContext);

                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return validUser;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }


    }

}
