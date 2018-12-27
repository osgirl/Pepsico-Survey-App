package limited.it.planet.visicoolertracking.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.services.TrackGPS;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class VISICoolerStatus extends AppCompatActivity {
    RadioGroup radioGroupCoolerActive,radioGroupLigtWorking,
            radioGroupNoOfShelves ,radioGroupCleanless,
            radioGroupCoolerInPrimePosition;
    Button btnNext,btnBack,btnUpdate;

    RadioButton selectedCoolerActive,selectedLightWorking,selectedNoOfShelves,
            selectedCleanless,selectedCoolerInPostion,radiobtnOters,radioButtonThree,
            radioButtonFour,radioButtonFive,radioButtonSix;
    EditText edttypeOtherValue;


    String activeCooler ="";
    String lightWorking = "";
    String noOfShelves = "";
    String cleanlines = "";
    String coolerInPrimePosition = "";
    LinearLayout LinearTypeOthers;
    boolean checkOthers = false;

    RadioButton coolerAcYesRB,coolerAcNoRB,lightWorkingYesRB,lightWorkinNoRB,noOfShelvesSixRB,noOFShelvesFiveRB,
                 nofShelvesFourRB,noOfShelvesThree,noOfShelvesOthers,cleanlinesYesRB,cleanlinessNORB,primePositionYesRB,primePositionNoRB;
    LogoutMenu logoutMenu;
    Toolbar toolbar ;
    boolean checkActiviyRunning ;
//    public static VISICoolerStatus visiCoolerStatus;
    TextView txvCoolerActive,txvLightWorking,txvNoOfShelves,txvCleanliness,txvCoolerInPrimePosition;
    FontCustomization fontCustomization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_cooler_status);
        toolbar = (Toolbar)findViewById(R.id.toolbar_visi_color_status) ;
        setSupportActionBar(toolbar);

        initializeUI();

        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",VISICoolerStatus.this);
        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }


        hideKeyboard(edttypeOtherValue);
        radiobtnOters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTypeOthers.setVisibility(View.VISIBLE);
            }

        });
        radioButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTypeOthers.setVisibility(View.GONE);
                edttypeOtherValue.setText("");
            }
        });

        radioButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTypeOthers.setVisibility(View.GONE);
                edttypeOtherValue.setText("");
            }
        });
        radioButtonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTypeOthers.setVisibility(View.GONE);
                edttypeOtherValue.setText("");
            }
        });
        radioButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTypeOthers.setVisibility(View.GONE);
                edttypeOtherValue.setText("");
            }
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCoolerActive  = (RadioButton)findViewById(radioGroupCoolerActive.getCheckedRadioButtonId());
                selectedLightWorking = (RadioButton)findViewById(radioGroupLigtWorking.getCheckedRadioButtonId());
                selectedNoOfShelves = (RadioButton)findViewById(radioGroupNoOfShelves.getCheckedRadioButtonId());
                selectedCleanless = (RadioButton)findViewById(radioGroupCleanless.getCheckedRadioButtonId());
                selectedCoolerInPostion = (RadioButton)findViewById(radioGroupCoolerInPrimePosition.getCheckedRadioButtonId());

                if(coolerAcYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cooler_active_no",false,VISICoolerStatus.this);
                }
                if(coolerAcNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cooler_active_yes",false,VISICoolerStatus.this);
                }
                if(lightWorkingYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("light_working_no",false,VISICoolerStatus.this);
                }
                if(lightWorkinNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("light_working_yes",false,VISICoolerStatus.this);
                }
                if(noOfShelvesSixRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_six",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOFShelvesFiveRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_five",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(nofShelvesFourRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_four",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOfShelvesThree.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_three",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOfShelvesOthers.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_other",true,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value",edttypeOtherValue.getText().toString(),VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",true,VISICoolerStatus.this);
                }
                if(cleanlinesYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cleanlines_no",false,VISICoolerStatus.this);
                }
                if(cleanlinessNORB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cleanlines_yes",false,VISICoolerStatus.this);
                }
                if(primePositionYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("prime_position_no",false,VISICoolerStatus.this);
                }

                if(primePositionNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("prime_position_yes",false,VISICoolerStatus.this);
                }
                if( selectedCoolerActive!=null){
                    activeCooler = selectedCoolerActive.getText().toString();
                }

                saveToSharedPreferences("coolerActive",activeCooler,VISICoolerStatus.this);
                if( selectedLightWorking!=null){
                    lightWorking = selectedLightWorking.getText().toString();
                }
                saveToSharedPreferences("lightWorking",lightWorking,VISICoolerStatus.this);

                if( selectedNoOfShelves!=null){
                    if(radiobtnOters.isChecked()){
                        noOfShelves = edttypeOtherValue.getText().toString();
                        checkOthers = true;
                    } else{
                        noOfShelves = selectedNoOfShelves.getText().toString();
                    }

                }
                saveToSharedPreferences("noOfShelves",noOfShelves,VISICoolerStatus.this);
                if( selectedCleanless!=null){
                    cleanlines = selectedCleanless.getText().toString();
                }
                saveToSharedPreferences("cleanlines",cleanlines,VISICoolerStatus.this);

                if( selectedCoolerInPostion!=null){
                    coolerInPrimePosition = selectedCoolerInPostion.getText().toString();
                }
                saveToSharedPreferences("coolerInPrimePosition",coolerInPrimePosition,VISICoolerStatus.this);

                if(checkOthers){
                    if(!edttypeOtherValue.getText().toString().matches("")){
                        Intent intent= new Intent(VISICoolerStatus.this,ImagesAndLast.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(VISICoolerStatus.this, "Give Others value", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    if(radioGroupCoolerActive.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Cooler Active", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(radioGroupLigtWorking.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Light Working", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCleanless.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Cleanliness", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupNoOfShelves.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select No of shelves", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCoolerInPrimePosition.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Cooler In Prime Position", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                Intent intent = new Intent(VISICoolerStatus.this,ReconfirmedPage.class);
                startActivity(intent);
              //  ActivityCompat.finishAffinity(VISICoolerStatus.this);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCoolerActive  = (RadioButton)findViewById(radioGroupCoolerActive.getCheckedRadioButtonId());
                selectedLightWorking = (RadioButton)findViewById(radioGroupLigtWorking.getCheckedRadioButtonId());
                selectedNoOfShelves = (RadioButton)findViewById(radioGroupNoOfShelves.getCheckedRadioButtonId());
                selectedCleanless = (RadioButton)findViewById(radioGroupCleanless.getCheckedRadioButtonId());
                selectedCoolerInPostion = (RadioButton)findViewById(radioGroupCoolerInPrimePosition.getCheckedRadioButtonId());

                //save for onresume
                if(coolerAcYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_yes",true,VISICoolerStatus.this);
                }
                if(coolerAcNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_no",true,VISICoolerStatus.this);
                }
                if(lightWorkingYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_yes",true,VISICoolerStatus.this);
                }
                if(lightWorkinNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_no",true,VISICoolerStatus.this);
                }
                if(noOfShelvesSixRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_six",true,VISICoolerStatus.this);
                }
                if(noOFShelvesFiveRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_five",true,VISICoolerStatus.this);
                }
                if(nofShelvesFourRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_four",true,VISICoolerStatus.this);
                }
                if(noOfShelvesThree.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_three",true,VISICoolerStatus.this);
                }
                if(noOfShelvesOthers.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_other",true,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value",edttypeOtherValue.getText().toString(),VISICoolerStatus.this);
                }
                if(cleanlinesYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_yes",true,VISICoolerStatus.this);
                }
                if(cleanlinessNORB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_no",true,VISICoolerStatus.this);
                }
                if(primePositionYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_yes",true,VISICoolerStatus.this);
                }

                if(primePositionNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_no",true,VISICoolerStatus.this);
                }

                if( selectedCoolerActive!=null){
                    activeCooler = selectedCoolerActive.getText().toString();
                }

                saveToSharedPreferences("coolerActive",activeCooler,VISICoolerStatus.this);
                if( selectedLightWorking!=null){
                    lightWorking = selectedLightWorking.getText().toString();
                }
                saveToSharedPreferences("lightWorking",lightWorking,VISICoolerStatus.this);

                if( selectedNoOfShelves!=null){
                    if(radiobtnOters.isChecked()){
                        noOfShelves = edttypeOtherValue.getText().toString();
                        checkOthers = true;
                    } else{
                        noOfShelves = selectedNoOfShelves.getText().toString();
                    }

                }
                saveToSharedPreferences("noOfShelves",noOfShelves,VISICoolerStatus.this);
                if( selectedCleanless!=null){
                    cleanlines = selectedCleanless.getText().toString();
                }
                saveToSharedPreferences("cleanlines",cleanlines,VISICoolerStatus.this);

                if( selectedCoolerInPostion!=null){
                    coolerInPrimePosition = selectedCoolerInPostion.getText().toString();
                }
                saveToSharedPreferences("coolerInPrimePosition",coolerInPrimePosition,VISICoolerStatus.this);

                    if(checkOthers){
                        if(!edttypeOtherValue.getText().toString().matches("")){
                            Intent intent= new Intent(VISICoolerStatus.this,ImagesAndLast.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(VISICoolerStatus.this, "Give Others value", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        if(radioGroupCoolerActive.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select Cooler Active", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(radioGroupLigtWorking.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select Light Working", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(radioGroupCleanless.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select Cleanliness", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(radioGroupNoOfShelves.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select No of shelves", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(radioGroupCoolerInPrimePosition.getCheckedRadioButtonId()==-1)
                        {
                            Toast.makeText(getApplicationContext(), "Please select Cooler In Prime Position", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        Intent intent= new Intent(VISICoolerStatus.this,ImagesAndLast.class);
                        startActivity(intent);
                    }






            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coolerAcYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cooler_active_no",false,VISICoolerStatus.this);
                }
                if(coolerAcNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("cooler_active_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cooler_active_yes",false,VISICoolerStatus.this);
                }
                if(lightWorkingYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("light_working_no",false,VISICoolerStatus.this);
                }
                if(lightWorkinNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("light_working_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("light_working_yes",false,VISICoolerStatus.this);
                }
                if(noOfShelvesSixRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_six",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOFShelvesFiveRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_five",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(nofShelvesFourRB.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_four",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOfShelvesThree.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_three",true,VISICoolerStatus.this);

                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_other",false,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value","",VISICoolerStatus.this);
                }
                if(noOfShelvesOthers.isChecked()){
                    saveBoleanValueSharedPreferences("no_of_shelves_other",true,VISICoolerStatus.this);
                    saveToSharedPreferences("other_value",edttypeOtherValue.getText().toString(),VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_six",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_five",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_four",false,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("no_of_shelves_three",true,VISICoolerStatus.this);
                }
                if(cleanlinesYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cleanlines_no",false,VISICoolerStatus.this);
                }
                if(cleanlinessNORB.isChecked()){
                    saveBoleanValueSharedPreferences("cleanlines_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("cleanlines_yes",false,VISICoolerStatus.this);
                }
                if(primePositionYesRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_yes",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("prime_position_no",false,VISICoolerStatus.this);
                }

                if(primePositionNoRB.isChecked()){
                    saveBoleanValueSharedPreferences("prime_position_no",true,VISICoolerStatus.this);
                    saveBoleanValueSharedPreferences("prime_position_yes",false,VISICoolerStatus.this);
                }

                if(checkOthers){
                    if(!edttypeOtherValue.getText().toString().matches("")){
                        Intent intent= new Intent(VISICoolerStatus.this,SKUActivity.class);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(VISICoolerStatus.this);
                    }else {
                        Toast.makeText(VISICoolerStatus.this, "Give Others value", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Intent intent= new Intent(VISICoolerStatus.this,SKUActivity.class);
                    startActivity(intent);
                   // ActivityCompat.finishAffinity(VISICoolerStatus.this);

                }


            }
        });

    }

    public  void initializeUI(){
        radioGroupCoolerActive = (RadioGroup) findViewById(R.id.radio_group_cooler_active);
        radioGroupLigtWorking = (RadioGroup)findViewById(R.id.radio_group_light_working);
        radioGroupNoOfShelves = (RadioGroup)findViewById(R.id.radio_group_no_of_shelves);
        radioGroupCleanless = (RadioGroup)findViewById(R.id.radio_group_cleanless);
        radioGroupCoolerInPrimePosition = (RadioGroup)findViewById(R.id.radio_group_cooler_in_prime_position);

        radiobtnOters = (RadioButton)findViewById(R.id.btn_radio_others) ;
        LinearTypeOthers = (LinearLayout)findViewById(R.id.linear_type_value) ;
        radioButtonThree = (RadioButton) findViewById(R.id.btn_radio_three) ;
        radioButtonFour = (RadioButton) findViewById(R.id.btn_radio_four) ;
        radioButtonFive = (RadioButton) findViewById(R.id.btn_radio_five) ;
        radioButtonSix= (RadioButton) findViewById(R.id.btn_radio_six) ;

        edttypeOtherValue = (EditText) findViewById(R.id.edt_type_other_value) ;

        btnNext = (Button) findViewById(R.id.visi_btn_next);
        btnBack = (Button)findViewById(R.id.vis_btn_back);
        btnUpdate = (Button)findViewById(R.id.btn_visi_cooler_update);
        btnNext.setTransformationMethod(null);
        btnBack.setTransformationMethod(null);
        btnUpdate.setTransformationMethod(null);

        coolerAcYesRB = (RadioButton)findViewById(R.id.btn_radio_col_yes);
        coolerAcNoRB =(RadioButton)findViewById(R.id.btn_radio_col_no);
        noOfShelvesSixRB =(RadioButton)findViewById(R.id.btn_radio_six);
        noOFShelvesFiveRB =(RadioButton)findViewById(R.id.btn_radio_five);
        nofShelvesFourRB = (RadioButton) findViewById(R.id.btn_radio_four);
        noOfShelvesThree = (RadioButton) findViewById(R.id.btn_radio_three);
        noOfShelvesOthers =(RadioButton)findViewById(R.id.btn_radio_others);
        cleanlinesYesRB =(RadioButton)findViewById(R.id.btn_cooler_cleanliness_yes);
        cleanlinessNORB = (RadioButton)findViewById(R.id.btn_cooler_cleanliness_no);
        lightWorkingYesRB =(RadioButton) findViewById(R.id.btn_radio_light_yes);
        lightWorkinNoRB =(RadioButton)findViewById(R.id.btn_radio_light_no);
        primePositionYesRB =(RadioButton)findViewById(R.id.btn_cooler_cooler_prime_postion_yes);
        primePositionNoRB =(RadioButton)findViewById(R.id.btn_cooler_cooler_prime_postion_no);

        logoutMenu = new LogoutMenu(VISICoolerStatus.this);

        //To use Font Customization
        fontCustomization = new FontCustomization(VISICoolerStatus.this);
        txvCoolerActive = (TextView)findViewById(R.id.txv_cooler_active);
        txvLightWorking =  (TextView)findViewById(R.id.txv_light_working);
        txvNoOfShelves =  (TextView)findViewById(R.id.txv_no_of_shelves);
        txvCleanliness = (TextView)findViewById(R.id.txv_cleanliness);
        txvCoolerInPrimePosition =  (TextView)findViewById(R.id.txv_cooler_prime_position);

        txvCoolerActive.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvLightWorking .setTypeface(fontCustomization.getTexgyreHerosBold());
        txvNoOfShelves .setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCleanliness .setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCoolerInPrimePosition .setTypeface(fontCustomization.getTexgyreHerosBold());

        radiobtnOters .setTypeface(fontCustomization.getTexgyreHerosRegular());

        radioButtonThree .setTypeface(fontCustomization.getTexgyreHerosRegular());
        radioButtonFour .setTypeface(fontCustomization.getTexgyreHerosRegular());
        radioButtonFive .setTypeface(fontCustomization.getTexgyreHerosRegular());
        radioButtonSix.setTypeface(fontCustomization.getTexgyreHerosRegular());

        edttypeOtherValue.setTypeface(fontCustomization.getTexgyreHerosRegular());

        btnNext .setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnBack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnUpdate.setTypeface(fontCustomization.getTexgyreHerosRegular());

        coolerAcYesRB .setTypeface(fontCustomization.getTexgyreHerosRegular());
        coolerAcNoRB .setTypeface(fontCustomization.getTexgyreHerosRegular());
        noOfShelvesSixRB.setTypeface(fontCustomization.getTexgyreHerosRegular());
        noOFShelvesFiveRB.setTypeface(fontCustomization.getTexgyreHerosRegular());
        nofShelvesFourRB.setTypeface(fontCustomization.getTexgyreHerosRegular());
        noOfShelvesThree .setTypeface(fontCustomization.getTexgyreHerosRegular());
        noOfShelvesOthers .setTypeface(fontCustomization.getTexgyreHerosRegular());
        cleanlinesYesRB.setTypeface(fontCustomization.getTexgyreHerosRegular());
        cleanlinessNORB .setTypeface(fontCustomization.getTexgyreHerosRegular());
        lightWorkingYesRB .setTypeface(fontCustomization.getTexgyreHerosRegular());
        lightWorkinNoRB .setTypeface(fontCustomization.getTexgyreHerosRegular());
        primePositionYesRB.setTypeface(fontCustomization.getTexgyreHerosRegular());
        primePositionNoRB.setTypeface(fontCustomization.getTexgyreHerosRegular());

    }
    //menu option



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visi_cooler_status, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_visi_cooler_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(VISICoolerStatus.this);
                break;


            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();

        coolerAcYesRB.setChecked(getBoleanValueSharedPreferences("cooler_active_yes",VISICoolerStatus.this));
        coolerAcNoRB.setChecked(getBoleanValueSharedPreferences("cooler_active_no",VISICoolerStatus.this));
        lightWorkingYesRB.setChecked(getBoleanValueSharedPreferences("light_working_yes",VISICoolerStatus.this));
        lightWorkinNoRB.setChecked(getBoleanValueSharedPreferences("light_working_no",VISICoolerStatus.this));
        noOfShelvesSixRB.setChecked(getBoleanValueSharedPreferences("no_of_shelves_six",VISICoolerStatus.this));
        noOFShelvesFiveRB.setChecked(getBoleanValueSharedPreferences("no_of_shelves_five",VISICoolerStatus.this));
        nofShelvesFourRB.setChecked(getBoleanValueSharedPreferences("no_of_shelves_four",VISICoolerStatus.this));
        noOfShelvesThree.setChecked(getBoleanValueSharedPreferences("no_of_shelves_three",VISICoolerStatus.this));
        noOfShelvesOthers.setChecked(getBoleanValueSharedPreferences("no_of_shelves_other",VISICoolerStatus.this));
        cleanlinesYesRB.setChecked(getBoleanValueSharedPreferences("cleanlines_yes",VISICoolerStatus.this));
        cleanlinessNORB.setChecked(getBoleanValueSharedPreferences("cleanlines_no",VISICoolerStatus.this));
        boolean primePosNo = getBoleanValueSharedPreferences("prime_position_no",VISICoolerStatus.this);

        primePositionNoRB.setChecked(primePosNo);
        boolean primePosYes =getBoleanValueSharedPreferences("prime_position_yes",VISICoolerStatus.this);
        primePositionYesRB.setChecked(primePosYes);

        String otherValue = getValueFromSharedPreferences("other_value",VISICoolerStatus.this);
        if(otherValue!=null){
            edttypeOtherValue.setText(otherValue);
        }

        if(radiobtnOters.isChecked()){
            LinearTypeOthers.setVisibility(View.VISIBLE);
        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
