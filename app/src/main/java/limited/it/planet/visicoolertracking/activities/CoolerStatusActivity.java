package limited.it.planet.visicoolertracking.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;


import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class CoolerStatusActivity extends AppCompatActivity {
    RadioGroup radioGroupChannel ,radioGroupCoolerPurity,radioGroupCoolerCharging;

    RadioButton selectedChannel;
    RadioButton selectedCoolerPurity,btnCoolerPurityRadioYes,btnCoolerPurityRadioNo,btnRadioCoolerChargingOne,btnRadioCoolerChargingTwo;
    RadioButton selectedCoolerCharging;
    RadioButton btnRadioGrocery,btnRadioRestaurent;

    String restaurent = "";

    String channel = "";
    String coolerPurity = "";
    String coolerCharging = "";

    Button btnNext,btnBack,btnUpdate;
    CheckBox chkPepsico,chkCocacola,chkPran,chkMojo,chkOthers;

    String selectedPepsico = "";
    String selectedCocacola = "";
    String selectedPran = "";
    String selectedMojo = "";
    String selectedOthers = "";

    LinearLayout checkPepsicoLayout;
   // HashMap<String,  String> selectedCheckItemM = new HashMap<String,  String>();
   public static ArrayList<String> coolerStatuslist = new ArrayList<String>();
   LogoutMenu logoutMenu;
   Toolbar toolbar;
    String userVisitStatus ="";
    boolean checkActiviyRunning ;

//    public static CoolerStatusActivity coolerStatusActivity;
    TextView txvChannel,txvCoolerStatus,txvCoolerPurity,txvCoolerCharging;
    FontCustomization fontCustomization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooler_status);
        toolbar = (Toolbar)findViewById(R.id.toolbar_color_status) ;
        setSupportActionBar(toolbar);

        initializeUI();

        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",CoolerStatusActivity.this);

        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedChannel  = (RadioButton)findViewById(radioGroupChannel.getCheckedRadioButtonId());
                selectedCoolerPurity = (RadioButton) findViewById(radioGroupCoolerPurity.getCheckedRadioButtonId());
                selectedCoolerCharging = (RadioButton) findViewById(radioGroupCoolerCharging.getCheckedRadioButtonId());


                if( selectedChannel!=null){
                    channel = selectedChannel.getText().toString();

                }
                if(btnRadioGrocery.isChecked()){
                    saveBoleanValueSharedPreferences("check_grocery",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("check_restaurent",false,CoolerStatusActivity.this);
                }
                if(btnRadioRestaurent.isChecked()){
                    saveBoleanValueSharedPreferences("check_restaurent",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("check_grocery",false,CoolerStatusActivity.this);
                }
                if(btnCoolerPurityRadioYes.isChecked()){
                    saveBoleanValueSharedPreferences("purity_yes",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("purity_no",false,CoolerStatusActivity.this);
                }
                if(btnCoolerPurityRadioNo.isChecked()){
                    saveBoleanValueSharedPreferences("purity_no",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("purity_yes",false,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingOne.isChecked()){
                    saveBoleanValueSharedPreferences("charging_one",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("charging_two",false,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingTwo.isChecked()){
                    saveBoleanValueSharedPreferences("charging_two",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("charging_one",false,CoolerStatusActivity.this);
                }

                saveToSharedPreferences("channel",channel,CoolerStatusActivity.this);

                // select checkbox

                if (chkPepsico.isChecked()) {
                    selectedPepsico = chkPepsico.getText().toString();
                    if(!coolerStatuslist.contains("PepsiCo")){
                        coolerStatuslist.add(selectedPepsico);
                    }
                    saveBoleanValueSharedPreferences("check_pepsico",true,CoolerStatusActivity.this);

                }
                if(chkCocacola.isChecked()){
                    selectedCocacola = chkCocacola.getText().toString();
                    if(!coolerStatuslist.contains("CocaCola")){
                        coolerStatuslist.add(selectedCocacola);
                    }
                    saveBoleanValueSharedPreferences("cocacola",true,CoolerStatusActivity.this);
                }
                if (chkPran.isChecked()){
                    selectedPran = chkPran.getText().toString();
                    if(!coolerStatuslist.contains("Pran")){
                        coolerStatuslist.add(selectedPran);
                    }
                    saveBoleanValueSharedPreferences("pran",true,CoolerStatusActivity.this);

                }
                if(chkMojo.isChecked()){
                    selectedMojo = chkMojo.getText().toString();
                    if(!coolerStatuslist.contains("Mojo")){
                        coolerStatuslist.add( selectedMojo);
                    }
                    saveBoleanValueSharedPreferences("mojo",true,CoolerStatusActivity.this);
                }
                if (chkOthers.isChecked()){
                    selectedOthers = chkOthers.getText().toString();
                    if(!coolerStatuslist.contains("Other")){
                        coolerStatuslist.add(selectedOthers);
                    }
                    saveBoleanValueSharedPreferences("other",true,CoolerStatusActivity.this);
                }

                // selectedCheckItemM.put("cooler_status",selectedCheckItems);
                //saveToSharedPreferences("selected_cooler_status",selectedCoolerStatus,CoolerStatusActivity.this);

                if( selectedCoolerPurity!=null){
                    coolerPurity = selectedCoolerPurity.getText().toString();
                }
                saveToSharedPreferences("cooler_purity",coolerPurity,CoolerStatusActivity.this);

                if( selectedCoolerCharging!=null){
                    coolerCharging = selectedCoolerCharging.getText().toString();
                }
                saveToSharedPreferences("cooler_charging",coolerCharging,CoolerStatusActivity.this);

                //check multiple click in checkbox




                if(chkPepsico.isChecked()){

                    if(radioGroupChannel.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Channel", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCoolerPurity.getCheckedRadioButtonId()==-1){
                        Toast.makeText(getApplicationContext(), "Please select Cooler Purity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCoolerCharging.getCheckedRadioButtonId()==-1){
                        Toast.makeText(getApplicationContext(), "Please select Cooler Charging", Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    Intent skuPage = new Intent(CoolerStatusActivity.this,SKUActivity.class);
//                    startActivity(skuPage);
//                    ActivityCompat.finishAffinity(CoolerStatusActivity.this);
                    Intent intent = new Intent(CoolerStatusActivity.this,ReconfirmedPage.class);
                    startActivity(intent);
                  //  ActivityCompat.finishAffinity(CoolerStatusActivity.this);
                }else {
                    Toast.makeText(CoolerStatusActivity.this, "Pepsico  and Channel not Checked", Toast.LENGTH_SHORT).show();
                }


            }


        });

        btnRadioGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnRadioGrocery.isChecked()){
                    saveBoleanValueSharedPreferences("check_grocery",true,CoolerStatusActivity.this);
                }else {
                    saveBoleanValueSharedPreferences("check_grocery",false,CoolerStatusActivity.this);
                }
            }
        });
        btnRadioRestaurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnRadioRestaurent.isChecked()){
                    saveBoleanValueSharedPreferences("check_restaurent",true,CoolerStatusActivity.this);
                }else {
                    saveBoleanValueSharedPreferences("check_restaurent",false,CoolerStatusActivity.this);
                }
            }
        });



        chkPepsico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkPepsico.isChecked()) {
                    checkPepsicoLayout.setVisibility(View.VISIBLE);
                    selectedPepsico = chkPepsico.getText().toString();
                    if(!coolerStatuslist.contains("PepsiCo")){
                        coolerStatuslist.add(selectedPepsico);
                    }
                    saveBoleanValueSharedPreferences("check_pepsico",true,CoolerStatusActivity.this);

                }else {
                    saveBoleanValueSharedPreferences("check_pepsico",false,CoolerStatusActivity.this);
                    coolerStatuslist.clear();
                    checkPepsicoLayout.setVisibility(View.GONE);
                }
            }
        });
        chkCocacola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkCocacola.isChecked()){
                    selectedCocacola = chkCocacola.getText().toString();
                    if(!coolerStatuslist.contains("CocaCola")){
                        coolerStatuslist.add(selectedCocacola);
                    }
                    saveBoleanValueSharedPreferences("cocacola",true,CoolerStatusActivity.this);
                }else {
                    saveBoleanValueSharedPreferences("cocacola",false,CoolerStatusActivity.this);
                    coolerStatuslist.clear();
                }
            }
        });
       chkPran.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (chkPran.isChecked()){
                   selectedPran = chkPran.getText().toString();
                   if(!coolerStatuslist.contains("Pran")){
                       coolerStatuslist.add(selectedPran);
                   }
                   saveBoleanValueSharedPreferences("pran",true,CoolerStatusActivity.this);

               }else {
                   saveBoleanValueSharedPreferences("pran",false,CoolerStatusActivity.this);
                   coolerStatuslist.clear();
               }
           }
       });
      chkMojo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(chkMojo.isChecked()){
                  selectedMojo = chkMojo.getText().toString();
                  if(!coolerStatuslist.contains("Mojo")){
                      coolerStatuslist.add( selectedMojo);
                  }
                  saveBoleanValueSharedPreferences("mojo",true,CoolerStatusActivity.this);
              }else {
                  saveBoleanValueSharedPreferences("mojo",false,CoolerStatusActivity.this);
                  coolerStatuslist.clear();
              }
          }
      });
       chkOthers.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (chkOthers.isChecked()){
                   selectedOthers = chkOthers.getText().toString();
                   if(!coolerStatuslist.contains("Other")){
                       coolerStatuslist.add(selectedOthers);
                   }
                   saveBoleanValueSharedPreferences("other",true,CoolerStatusActivity.this);
               }else {
                   saveBoleanValueSharedPreferences("other",false,CoolerStatusActivity.this);
                   coolerStatuslist.clear();
               }

           }
       });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //select radio button
                selectedChannel  = (RadioButton)findViewById(radioGroupChannel.getCheckedRadioButtonId());
                selectedCoolerPurity = (RadioButton) findViewById(radioGroupCoolerPurity.getCheckedRadioButtonId());
                selectedCoolerCharging = (RadioButton) findViewById(radioGroupCoolerCharging.getCheckedRadioButtonId());


                if( selectedChannel!=null){
                    channel = selectedChannel.getText().toString();

                }
                if(btnRadioGrocery.isChecked()){
                   saveBoleanValueSharedPreferences("check_grocery",true,CoolerStatusActivity.this);
                }
                if(btnRadioRestaurent.isChecked()){
                   saveBoleanValueSharedPreferences("check_restaurent",true,CoolerStatusActivity.this);
                }

                if(btnCoolerPurityRadioYes.isChecked()){
                    saveBoleanValueSharedPreferences("purity_yes",true,CoolerStatusActivity.this);
                }
                if(btnCoolerPurityRadioNo.isChecked()){
                     saveBoleanValueSharedPreferences("purity_no",true,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingOne.isChecked()){
                   saveBoleanValueSharedPreferences("charging_one",true,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingTwo.isChecked()){
                    saveBoleanValueSharedPreferences("charging_two",true,CoolerStatusActivity.this);
                }

                saveToSharedPreferences("channel",channel,CoolerStatusActivity.this);

               // select checkbox

                if (chkPepsico.isChecked()) {
                    selectedPepsico = chkPepsico.getText().toString();
                    if(!coolerStatuslist.contains("PepsiCo")){
                        coolerStatuslist.add(selectedPepsico);
                    }
                   saveBoleanValueSharedPreferences("check_pepsico",true,CoolerStatusActivity.this);

                }
                if(chkCocacola.isChecked()){
                    selectedCocacola = chkCocacola.getText().toString();
                    if(!coolerStatuslist.contains("CocaCola")){
                        coolerStatuslist.add(selectedCocacola);
                    }
                saveBoleanValueSharedPreferences("cocacola",true,CoolerStatusActivity.this);
                }
                if (chkPran.isChecked()){
                    selectedPran = chkPran.getText().toString();
                    if(!coolerStatuslist.contains("Pran")){
                        coolerStatuslist.add(selectedPran);
                    }
                  saveBoleanValueSharedPreferences("pran",true,CoolerStatusActivity.this);

                }
                if(chkMojo.isChecked()){
                    selectedMojo = chkMojo.getText().toString();
                    if(!coolerStatuslist.contains("Mojo")){
                        coolerStatuslist.add( selectedMojo);
                    }
                   saveBoleanValueSharedPreferences("mojo",true,CoolerStatusActivity.this);
                }
                if (chkOthers.isChecked()){
                    selectedOthers = chkOthers.getText().toString();
                    if(!coolerStatuslist.contains("Other")){
                        coolerStatuslist.add(selectedOthers);
                    }
              saveBoleanValueSharedPreferences("other",true,CoolerStatusActivity.this);
                }

               // selectedCheckItemM.put("cooler_status",selectedCheckItems);
                //saveToSharedPreferences("selected_cooler_status",selectedCoolerStatus,CoolerStatusActivity.this);

                if( selectedCoolerPurity!=null){
                    coolerPurity = selectedCoolerPurity.getText().toString();
                }
                saveToSharedPreferences("cooler_purity",coolerPurity,CoolerStatusActivity.this);

                if( selectedCoolerCharging!=null){
                    coolerCharging = selectedCoolerCharging.getText().toString();
                }
                saveToSharedPreferences("cooler_charging",coolerCharging,CoolerStatusActivity.this);

                //check multiple click in checkbox




                if(chkPepsico.isChecked()){

                    if(radioGroupChannel.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(getApplicationContext(), "Please select Channel", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCoolerPurity.getCheckedRadioButtonId()==-1){
                        Toast.makeText(getApplicationContext(), "Please select Cooler Purity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(radioGroupCoolerCharging.getCheckedRadioButtonId()==-1){
                        Toast.makeText(getApplicationContext(), "Please select Cooler Charging", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent skuPage = new Intent(CoolerStatusActivity.this,SKUActivity.class);
                    startActivity(skuPage);
                   // ActivityCompat.finishAffinity(CoolerStatusActivity.this);
                }else {
                    Toast.makeText(CoolerStatusActivity.this, "Pepsico  and Channel not Checked", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(btnRadioGrocery.isChecked()){
                    saveBoleanValueSharedPreferences("check_grocery",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("check_restaurent",false,CoolerStatusActivity.this);
                }
                if(btnRadioRestaurent.isChecked()){
                    saveBoleanValueSharedPreferences("check_restaurent",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("check_grocery",false,CoolerStatusActivity.this);
                }
                if(btnCoolerPurityRadioYes.isChecked()){
                    saveBoleanValueSharedPreferences("purity_yes",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("purity_no",false,CoolerStatusActivity.this);
                }
                if(btnCoolerPurityRadioNo.isChecked()){
                    saveBoleanValueSharedPreferences("purity_no",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("purity_yes",false,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingOne.isChecked()){
                    saveBoleanValueSharedPreferences("charging_one",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("charging_two",false,CoolerStatusActivity.this);
                }
                if(btnRadioCoolerChargingTwo.isChecked()){
                    saveBoleanValueSharedPreferences("charging_two",true,CoolerStatusActivity.this);
                    saveBoleanValueSharedPreferences("charging_one",false,CoolerStatusActivity.this);
                }

                Intent intent = new Intent(CoolerStatusActivity.this,BasicInformationActivity.class);
                startActivity(intent);
              //  ActivityCompat.finishAffinity(CoolerStatusActivity.this);

            }
        });



    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cooler_status, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_cooler_status_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(CoolerStatusActivity.this);
                break;


            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();
       // mCheckBox.setChecked(load());
        initializeUI();
        if(btnRadioGrocery!=null){
            btnRadioGrocery.setChecked(getBoleanValueSharedPreferences("check_grocery",CoolerStatusActivity.this));
        }
        if(btnRadioRestaurent!=null){
            btnRadioRestaurent.setChecked(getBoleanValueSharedPreferences("check_restaurent",CoolerStatusActivity.this));
        }


        if(btnRadioCoolerChargingOne!=null){
            btnRadioCoolerChargingOne.setChecked(getBoleanValueSharedPreferences("charging_one",CoolerStatusActivity.this));
        }
        if(btnRadioCoolerChargingTwo!=null){
            btnRadioCoolerChargingTwo.setChecked(getBoleanValueSharedPreferences("charging_two",CoolerStatusActivity.this));
        }

        boolean checkYes =getBoleanValueSharedPreferences("purity_yes",CoolerStatusActivity.this);
        boolean checkNo = getBoleanValueSharedPreferences("purity_no",CoolerStatusActivity.this);
        if(checkYes){
            btnCoolerPurityRadioYes.setChecked(checkYes);
        }else {
            btnCoolerPurityRadioNo.setChecked(checkNo);
        }


//    if(checkNo){
//                btnCoolerPurityRadioNo.setChecked(checkNo);
//    }

        if(chkPepsico!=null){
            boolean isPepsico=getBoleanValueSharedPreferences("check_pepsico",CoolerStatusActivity.this);
                    chkPepsico.setChecked(isPepsico);
            if(isPepsico){
                checkPepsicoLayout.setVisibility(View.VISIBLE);
            }

        }



      if(chkCocacola!=null){
            boolean isCacacola= getBoleanValueSharedPreferences("cocacola",CoolerStatusActivity.this);
          chkCocacola.setChecked(isCacacola);
      }
      if(chkPran!=null){
          boolean isPran = getBoleanValueSharedPreferences("pran",CoolerStatusActivity.this);
          chkPran.setChecked(isPran);
      }
       if(chkMojo!=null){
          boolean isMojo= getBoleanValueSharedPreferences("mojo",CoolerStatusActivity.this);
           chkMojo.setChecked(isMojo);
       }
        if(chkOthers!=null){
           boolean isOther= getBoleanValueSharedPreferences("other",CoolerStatusActivity.this);

            chkOthers.setChecked(isOther);
        }


        //userVisitStatus = getValueFromSharedPreferences("edit_second",CoolerStatusActivity.this);


    }



    public void initializeUI(){
        radioGroupChannel = (RadioGroup)findViewById(R.id.radio_group_channel);
        btnRadioGrocery = (RadioButton)findViewById(R.id.btn_radio_grocery);
        btnRadioRestaurent = (RadioButton) findViewById(R.id.btn_radio_restaurent);
        radioGroupCoolerPurity =(RadioGroup)findViewById(R.id.radio_group_cooler_purity) ;
        radioGroupCoolerCharging = (RadioGroup) findViewById(R.id.radio_group_cooler_charging);
        btnRadioCoolerChargingOne =(RadioButton)findViewById(R.id.btn_cooler_charging_one) ;
        btnRadioCoolerChargingTwo = (RadioButton) findViewById(R.id.btn_cooler_charging_two);

        btnCoolerPurityRadioYes =(RadioButton)findViewById(R.id.btn_purity_radio_yes) ;
        btnCoolerPurityRadioNo =(RadioButton)findViewById(R.id.btn_purity_radio_no);

        chkPepsico = (CheckBox) findViewById(R.id.checkbox_pepsico);
        chkCocacola = (CheckBox) findViewById(R.id.checkbox_cocacola);
        chkPran = (CheckBox) findViewById(R.id.checkbox_pran);
        chkMojo = (CheckBox) findViewById(R.id.checkbox_mojo);
        chkOthers = (CheckBox) findViewById(R.id.checkbox_others);
        checkPepsicoLayout = (LinearLayout)findViewById(R.id.check_pepsico_layout);

        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnBack = (Button)findViewById(R.id.btn_back);

        btnNext.setTransformationMethod(null);
        btnBack.setTransformationMethod(null);
        btnUpdate.setTransformationMethod(null);

        logoutMenu = new LogoutMenu(CoolerStatusActivity.this);
        txvChannel = (TextView)findViewById(R.id.txv_channel);
        txvCoolerStatus = (TextView)findViewById(R.id.txv_cooler_status);
        txvCoolerPurity =(TextView)findViewById(R.id.txv_cooler_purity);
        txvCoolerCharging =(TextView)findViewById(R.id.txv_cooler_charging);

//To use font customization
        fontCustomization = new FontCustomization(CoolerStatusActivity.this);
        txvChannel.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCoolerStatus.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCoolerPurity.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCoolerCharging.setTypeface(fontCustomization.getTexgyreHerosBold());

        btnNext .setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnBack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnUpdate.setTypeface(fontCustomization.getTexgyreHerosRegular());

        btnRadioGrocery.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnRadioRestaurent.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkPepsico.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkCocacola.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkPran.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkMojo.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkOthers.setTypeface(fontCustomization.getTexgyreHerosRegular());

        btnCoolerPurityRadioYes.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnCoolerPurityRadioNo.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnRadioCoolerChargingTwo.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnRadioCoolerChargingOne.setTypeface(fontCustomization.getTexgyreHerosRegular());




    }


}
