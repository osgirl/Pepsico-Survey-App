package limited.it.planet.visicoolertracking.activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import limited.it.planet.visicoolertracking.database.LoginDB;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import limited.it.planet.visicoolertracking.R;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUserName, edtPassword;
    String loginAPI = "";
    String userName = "";
    String password = "";
    LoginDB loginDB;
    boolean checkAPI = false;
    boolean checkUser =false;
    String checkUserStatus = "";
    LogoutMenu logoutMenu;
    TextView txvAppTitle;
    FontCustomization fontCustomization;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_sign_in);
        edtUserName = (EditText) findViewById(R.id.edt_user_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        txvAppTitle =(TextView)findViewById(R.id.txv_app_title);

        loginDB = new LoginDB(LoginActivity.this);
        loginDB.open();
        logoutMenu = new LogoutMenu(LoginActivity.this);


        checkUserStatus  = SharedPreferenceLocalMemory.getValueFromSharedPreferences("status",LoginActivity.this);

//        //to use phone state permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//            }
//
//        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all save data
              //  logoutMenu.clearAllSaveData();
                logoutMenu.clearAllForConfirmedPage();
                userName = edtUserName.getText().toString();
                password = edtPassword.getText().toString();

                loginAPI = Constants.loginAPI + userName + "/" + password;
                if(!userName.equals("") && !password.equals("")){
                    checkUser =   loginDB.checkUser(userName,password);
                }

                boolean checkInternet = checkInternet();

                //check local db
                if(checkUserStatus== null ) {
                    if(checkInternet){
                        GetLoginDataTask getLoginDataTask = new GetLoginDataTask();
                        getLoginDataTask.execute();

                    }else {
                        if (checkUser == true) {
                            Intent loginIntent = new Intent(LoginActivity.this, BasicInformationActivity.class);
                            startActivity(loginIntent);
                            ActivityCompat.finishAffinity(LoginActivity.this);
                            Toast.makeText(LoginActivity.this, "Your Device is offline and login locally", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(LoginActivity.this, "Your Device is offline ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else if(checkUserStatus.equals("1")){
                    if (checkUser == true) {
                        Intent loginIntent = new Intent(LoginActivity.this, BasicInformationActivity.class);
                        startActivity(loginIntent);
                        ActivityCompat.finishAffinity(LoginActivity.this);

                    } else if (checkAPI == false) {
                        //check server api
                        Toast.makeText(LoginActivity.this, "Invalid User ", Toast.LENGTH_SHORT).show();

                    }
                }
//                else if(checkUserStatus.equals("0")){
//                    Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
//                }
                // loginDB.close();
                edtUserName.setText("");
                edtPassword.setText("");



            }
        });

        //To use font customization
        fontCustomization = new FontCustomization(LoginActivity.this);
        txvAppTitle.setTypeface(fontCustomization.getTexgyreHerosBold());
        edtUserName.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnLogin.setTypeface(fontCustomization.getTexgyreHerosBold());
        edtPassword.setTypeface(fontCustomization.getTexgyreHerosRegular());
    }
    protected boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public class GetLoginDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            loadingDialog.setCancelable(true);
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(loginAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    checkAPI = false;
                   // SharedPreferenceLocalMemory.saveToSharedPreferences("status","0",LoginActivity.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"Invalid User",Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {
                    loadingDialog.dismiss();
                    responseBodyText = response.body().string();
                    checkAPI = true;
                    //Check user First time
                    saveBoleanValueSharedPreferences("check_first_time",true,LoginActivity.this);


                    if(!userName.equals("") && !password.equals("")){
                        loginDB.saveLoginEntry(userName,password);
                    }

                    loginDB.close();
                    SharedPreferenceLocalMemory.saveToSharedPreferences("status","1",LoginActivity.this);
                    JSONObject jobject = new JSONObject(responseBodyText);
                    String userName = jobject.getString("user_id");
                    saveToSharedPreferences("user_id",userName,LoginActivity.this);
                    //login valid user
                    Intent loginIntent = new Intent(LoginActivity.this,BasicInformationActivity.class);
                    startActivity(loginIntent);
                    ActivityCompat.finishAffinity(LoginActivity.this);



                    String address = jobject.getString("address");


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



            // Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
//        saveToSharedPreferences("user_name",userName,LoginActivity.this);
//        saveToSharedPreferences("password",userName,LoginActivity.this);
    }

}
