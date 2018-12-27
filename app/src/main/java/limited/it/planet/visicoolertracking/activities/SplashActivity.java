package limited.it.planet.visicoolertracking.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import limited.it.planet.visicoolertracking.services.CheckValidUserTask;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;

public class SplashActivity extends AppCompatActivity {
    boolean checkFirstTimeUser = false;
    CheckValidUserTask checkValidUserTask;
    String userName = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkValidUserTask = new CheckValidUserTask(SplashActivity.this);
        checkFirstTimeUser = getBoleanValueSharedPreferences("check_first_time", SplashActivity.this);
        userName = getValueFromSharedPreferences("user_id",SplashActivity.this);

        checkValidUserTask.checkValidUser(userName);

        if (checkFirstTimeUser) {
            Intent intent = new Intent(SplashActivity.this, BasicInformationActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(SplashActivity.this);
        } else {

            Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            ActivityCompat.finishAffinity(SplashActivity.this);
        }
        //to use phone state permission


    }


}
