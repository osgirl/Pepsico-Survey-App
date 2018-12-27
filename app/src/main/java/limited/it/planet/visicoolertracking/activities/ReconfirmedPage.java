package limited.it.planet.visicoolertracking.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;

import limited.it.planet.visicoolertracking.services.ServeyDataSendToServer;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.ServeyModel;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class ReconfirmedPage extends AppCompatActivity {
    LocalStorageDB localStorageDB;
    TextView edtOutletCode,edtChannel,edtCoolerStatus,edtCoolerPurity,edtCoolerCharging,
            edtSKUGRB,edtSKUCAN,edtSKUGOPACK,edt400ML,edt500ML,edtOneLiter,edtTwoLiter,edtAquafina,
               edtNoOfActiveCooler,edtLightWorking,edtNoOfShelves,
                edtCleanLiness,edtPrimePosition,edtAvailabilty,
            edtRemarks;


    Button  btnEditChannel,btnEditCoolerStatus,btnEditCoolerCharging,btnEditGrb,btnEditShelves,btnEditCleanliness,
           btnEditPrimePosition,btnEditAvailability,btnEditRemarks,btnEditCoolerPurity,btnEditCan,btnEditGoPack,btnEdit400ml,btnEdit500ml,   btnEditltr1,btnEditltr2,btnEditAquafina,btnEditCoolerActive,btnEditLightWorking;

    Button btnConfirmed,btnBack;
    String latitude = "";
    String longitude = "";

    String territory = "";
    String distributor = "";
    String outletName = "";
    String retailerName = "";
    String retailerMobile = "";
    String address = "";
    String coolerPurity = "";
    String coolerCharging = "";
    String skuGRBAvailabilty = "";
    String skuCANAvailabilty = "";
    String skuGOPACKAvailabilty = "";
    String sku400mlAvailabilty = "";
    String sku500mlAvailabilty = "";
    String sku1LiterAvailabilty = "";
    String sku2LiterAvailabilty = "";
    String skuAquafinaAvailabilty = "";
    String coolerPicImagePath = "";
    String outletPicImagePath = "";


    String presenceOfCooler = "";
//    String sizeOfCooler = "";
//    String noOfActiveCooler = "";
    String lightWorking = "";
    String noOfShelves = "";
    String cleanLiness = "";
    String availabilty = "";
    String outletCode = "";

//    String presenceOfPOSM = "";
//    String tableTop = "";
//    String dangler = "";
    String coolerPrimePosition = "";
    String remarks = "";
    String channel = "";
    String coolerstatus = "";
    String dbArea = "";
    String town = "";
    int clusterId ;
    String cluster = "";
    String noOfActiveCooler = "";
    String syncStatus = "failled";
    String coolerPicCode = "";
    String outletPicCode = "";
    String startTime = "";
    String endTime = "";
    String entryDate = "";
    String userId ="";

    //all list value
    ArrayList<String> coolerStatusList = new ArrayList<String>();

    //sku availabilty
     ArrayList<String> skuGRBlist = new ArrayList<String>();
     ArrayList<String> skuCanlist = new ArrayList<String>();
     ArrayList<String> skuGoPacklist = new ArrayList<String>();
     ArrayList<String> sku400mllist = new ArrayList<String>();
     ArrayList<String> sku500mllist = new ArrayList<String>();
     ArrayList<String> sku1Literlist = new ArrayList<String>();
     ArrayList<String> sku2Literlist = new ArrayList<String>();
     ArrayList<String> skuAquafinalist = new ArrayList<String>();

     //availabilty
      ArrayList<String> availabiltyList = new ArrayList<String>();

    private static String LIST_SEPARATOR = ":";
    ServeyModel serveyModel;

    ImageView imgCoolerPic,imgOutletPic ;
    ServeyDataSendToServer serveyDataSendToServer ;
    LogoutMenu logoutMenu;

    Toolbar toolbar;
    public static boolean isActive = false;
    Date date1 ;
    Date date2;
    boolean checkOutletId ;
    //Check Activtiy
    Activity imageAndLastActivity;

    FontCustomization fontCustomization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconfirmed_page);
        toolbar = (Toolbar)findViewById(R.id.toolbar_suumery) ;
        setSupportActionBar(toolbar);

        localStorageDB = new LocalStorageDB(ReconfirmedPage.this);

        localStorageDB.open();
        initializeUI() ;
        //alarm service



       // serveyModel = new ServeyModel();
        serveyDataSendToServer = new ServeyDataSendToServer(ReconfirmedPage.this);

        territory = SharedPreferenceLocalMemory.getValueFromSharedPreferences("territory",ReconfirmedPage.this);
        distributor = SharedPreferenceLocalMemory.getValueFromSharedPreferences("distributor",ReconfirmedPage.this);
        outletName = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_name",ReconfirmedPage.this);
        retailerName = SharedPreferenceLocalMemory.getValueFromSharedPreferences("retailer_name",ReconfirmedPage.this);
        retailerMobile = SharedPreferenceLocalMemory.getValueFromSharedPreferences("retailer_mobile",ReconfirmedPage.this);
        address =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("address",ReconfirmedPage.this);
        channel = SharedPreferenceLocalMemory.getValueFromSharedPreferences("channel",ReconfirmedPage.this);
        coolerPurity = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cooler_purity",ReconfirmedPage.this);
        coolerCharging = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cooler_charging",ReconfirmedPage.this);

        presenceOfCooler = SharedPreferenceLocalMemory.getValueFromSharedPreferences("colorActive",ReconfirmedPage.this);
      //  sizeOfCooler =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("colorActive",ReconfirmedPage.this);
       // noOfActiveCooler =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("colorActive",ReconfirmedPage.this);
        lightWorking = SharedPreferenceLocalMemory.getValueFromSharedPreferences("lightWorking",ReconfirmedPage.this);
        noOfShelves = SharedPreferenceLocalMemory.getValueFromSharedPreferences("noOfShelves",ReconfirmedPage.this);
        cleanLiness = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cleanlines",ReconfirmedPage.this);
        remarks = SharedPreferenceLocalMemory.getValueFromSharedPreferences("input_remarks",ReconfirmedPage.this);
        coolerPrimePosition = SharedPreferenceLocalMemory.getValueFromSharedPreferences("coolerInPrimePosition",ReconfirmedPage.this);
        dbArea = SharedPreferenceLocalMemory.getValueFromSharedPreferences("db_area",ReconfirmedPage.this);
        town = SharedPreferenceLocalMemory.getValueFromSharedPreferences("town",ReconfirmedPage.this);
        noOfActiveCooler = SharedPreferenceLocalMemory.getValueFromSharedPreferences("coolerActive",ReconfirmedPage.this);
        outletCode =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_code",ReconfirmedPage.this);

        coolerPicCode = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cooler_pic_code",ReconfirmedPage.this);
       outletPicCode = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_pic_code",ReconfirmedPage.this);

       if(coolerPicCode!=null){
           if(coolerPicCode.equals("0") || coolerPicCode.equals("")){
               syncStatus = "failled";
           }
       }
      if(outletPicCode!=null){
          if(outletPicCode.equals("0") || outletPicCode.equals("")){
              syncStatus = "failled";
          }
      }


         startTime = SharedPreferenceLocalMemory.getValueFromSharedPreferences("start_time",ReconfirmedPage.this);
         endTime = Constants.getCurrentTime();
         entryDate = Constants.getCurrentEntryDate();
         userId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("user_id",ReconfirmedPage.this);


        cluster = SharedPreferenceLocalMemory.getValueFromSharedPreferences("cluster_id",ReconfirmedPage.this);

        try {
            clusterId = Integer.parseInt(cluster);
        }catch (Exception e){
            e.printStackTrace();
        }

        coolerStatusList = CoolerStatusActivity.coolerStatuslist;
        if(coolerStatusList.size()>0){
            coolerstatus = convertListToString(coolerStatusList);
        }


        skuGRBlist = SKUActivity.skuGRBlist;
        if(skuGRBlist.size()>0){
            skuGRBAvailabilty = convertListToString(skuGRBlist);
        }


        skuCanlist = SKUActivity.skuCanlist;
        if(skuCanlist.size()>0){
            skuCANAvailabilty = convertListToString(skuCanlist);
        }


        skuGoPacklist = SKUActivity.skuGoPacklist;
        if(skuGoPacklist.size()>0){
            skuGOPACKAvailabilty = convertListToString(skuGoPacklist);
        }


        sku400mllist = SKUActivity.sku400mllist;
        if(sku400mllist.size()>0){
            sku400mlAvailabilty = convertListToString(sku400mllist);
        }


        sku500mllist = SKUActivity.sku500mllist;
        if(sku500mllist.size()>0){
            sku500mlAvailabilty = convertListToString(sku500mllist);
        }



        sku1Literlist = SKUActivity.sku1Literlist;
        if(sku1Literlist.size()>0){
            sku1LiterAvailabilty = convertListToString(sku1Literlist);
        }



        sku2Literlist = SKUActivity.sku2Literlist;
        if(sku2Literlist.size()>0){
            sku2LiterAvailabilty = convertListToString(sku2Literlist);
        }


        skuAquafinalist = SKUActivity.skuAquafinalist;
        if(skuAquafinalist.size()>0){
            skuAquafinaAvailabilty = convertListToString(skuAquafinalist);
        }


        availabiltyList = ImagesAndLast.availabiltyList;
        if(availabiltyList.size()>0){
            availabilty = convertListToString(availabiltyList);
        }

        //latitude and longitude
       String  retLatitude = SharedPreferenceLocalMemory.getValueFromSharedPreferences("latitude",ReconfirmedPage.this);
        latitude = String.valueOf(retLatitude);

        String retLongitude = SharedPreferenceLocalMemory.getValueFromSharedPreferences("longitude",ReconfirmedPage.this);
        longitude = String.valueOf(retLongitude);


         //Image Path
         coolerPicImagePath =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("coolerImagePath",ReconfirmedPage.this);
         outletPicImagePath =  SharedPreferenceLocalMemory.getValueFromSharedPreferences("OutletImagePath",ReconfirmedPage.this);



         if(coolerPicImagePath!=null && coolerPicImagePath.length()>0){
             File coolerPhotoFile = new File(coolerPicImagePath);
             Picasso.with(this).load(coolerPhotoFile).into(imgCoolerPic);

         }

         if(outletPicImagePath!=null && outletPicImagePath.length()>0){
            File outletPhotoFile = new File(outletPicImagePath);
             Picasso.with(this).load(outletPhotoFile).into(imgOutletPic);
         }


        edtOutletCode.setText(outletCode);
        edtChannel.setText(channel);
        edtCoolerStatus.setText(coolerstatus);
        edtCoolerPurity.setText(coolerPurity);
        edtCoolerCharging.setText(coolerCharging);
        edtSKUGRB.setText(skuGRBAvailabilty);
        edtSKUCAN.setText(skuCANAvailabilty);
        edtSKUGOPACK.setText(skuGOPACKAvailabilty);
        edt400ML.setText(sku400mlAvailabilty);
        edt500ML.setText(sku500mlAvailabilty);
        edtOneLiter.setText(sku1LiterAvailabilty);
        edtTwoLiter.setText(sku2LiterAvailabilty);
        edtAquafina.setText(skuAquafinaAvailabilty);
        edtNoOfActiveCooler.setText(noOfActiveCooler);
        edtLightWorking.setText(lightWorking);
        edtNoOfShelves.setText(noOfShelves);
        edtCleanLiness.setText(cleanLiness);
        edtPrimePosition.setText(coolerPrimePosition);
        edtAvailabilty.setText(availabilty);
        edtRemarks.setText(remarks);

        //coolerPurity = SharedPreferenceLocalMemory.getValueFromSharedPreferences("address",ReconfirmedPage.this);


        //all edit button click lisener




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReconfirmedPage.this,ImagesAndLast.class);
                startActivity(intent);
            }
        });
        btnConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Just Check

                //save locale storage
             //   String outletId = getValueFromSharedPreferences("outlet_id",ReconfirmedPage.this);

                if(outletCode!=null && !outletCode.isEmpty()){
                   checkOutletId = localStorageDB.checkOutletId(outletCode);
                }



                String currentEntryDate = entryDate;
                String checkLastEntryDate =localStorageDB.selectLastEntryDate(outletCode);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int checkCurrentEM=0;
                int checkLastEntM=0;
                try {
                    if(checkLastEntryDate!=null && !checkLastEntryDate.isEmpty()){
                        date1 = sdf.parse(currentEntryDate);
                        date2 = sdf.parse(checkLastEntryDate);
                         checkCurrentEM= date1.getMonth();
                         checkLastEntM = date2.getMonth();
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(checkOutletId && checkCurrentEM==checkLastEntM){
                    if(coolerPicCode!=null && outletPicCode!=null){
                        localStorageDB.updateServeyDataForSameUser(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                remarks, latitude, longitude, userId, entryDate,coolerPicImagePath,outletPicImagePath , coolerPicCode,
                                outletPicCode, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                sku1LiterAvailabilty, sku2LiterAvailabilty,
                                skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime,syncStatus);
                    }

                }else if( checkCurrentEM>checkLastEntM){
                    if(coolerPicCode!=null && outletPicCode!=null){
                        localStorageDB.saveServeyStorageEntry(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                remarks, latitude, longitude, userId, entryDate,coolerPicImagePath,outletPicImagePath , coolerPicCode,
                                outletPicCode, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                sku1LiterAvailabilty, sku2LiterAvailabilty,
                                skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime,syncStatus);
                    }


                }else {
                    if(coolerPicCode!=null && outletPicCode!=null){
                        localStorageDB.saveServeyStorageEntry(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                remarks, latitude, longitude, userId, entryDate,coolerPicImagePath,outletPicImagePath , coolerPicCode,
                                outletPicCode, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                sku1LiterAvailabilty, sku2LiterAvailabilty,
                                skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime,syncStatus);
                    }

                }


                //data send to server
                boolean isAvailableInternet = Constants.isConnectingToInternet(ReconfirmedPage.this);

                if(userId!=null && userId.length()>0){
                    if(isAvailableInternet) {
                             if(coolerPicCode!=null && outletPicCode!=null){
                                 if((!coolerPicCode.equals("0") && coolerPicCode.length()>0) && (!outletPicCode.equals("0") && outletPicCode.length()>0)){
                                     serveyDataSendToServer.serveyDataSendToServer(outletCode, channel, coolerstatus, coolerCharging, skuGRBAvailabilty,
                                             sku500mlAvailabilty, noOfShelves, cleanLiness, coolerPrimePosition, availabilty,
                                             remarks, latitude, longitude, userId, entryDate, coolerPicCode,
                                             outletPicCode, coolerPurity, skuCANAvailabilty, skuGOPACKAvailabilty, sku400mlAvailabilty,
                                             sku1LiterAvailabilty, sku2LiterAvailabilty,
                                             skuAquafinaAvailabilty, noOfActiveCooler, lightWorking, startTime, endTime);
                                 }else {
                                     Toast.makeText(ReconfirmedPage.this,"Your Outlet pic or cooler pic missing",Toast.LENGTH_SHORT).show();
                                     Intent backIntent = new Intent(ReconfirmedPage.this, BasicInformationActivity.class);
                                     startActivity(backIntent);
                                     logoutMenu.clearAllForConfirmedPage();
                                     ActivityCompat.finishAffinity(ReconfirmedPage.this);
                                 }
                             }

                    }else {
                        Toast.makeText(ReconfirmedPage.this,"Your Device is Offline",Toast.LENGTH_SHORT).show();
                        Intent backIntent = new Intent(ReconfirmedPage.this, BasicInformationActivity.class);
                        startActivity(backIntent);
                        logoutMenu.clearAllForConfirmedPage();
                        ActivityCompat.finishAffinity(ReconfirmedPage.this);
//                        ActivityCompat.finishAffinity(imageAndLastActivity);
//                        ActivityCompat.finishAffinity((Activity) VISICoolerStatus.visiCoolerStatus);
//                        ActivityCompat.finishAffinity((Activity) CoolerStatusActivity.coolerStatusActivity);
//                        ActivityCompat.finishAffinity((Activity) SKUActivity.skuActivity);

                    }


                }


                //save basic information in local db

//                localStorageDB.saveBasicInformation(outletId,town,territory,distributor,dbArea,clusterId,
//                        outletName,retailerName,retailerMobile ,address );


                logoutMenu.clearAllForConfirmedPage();



            }

        });


    }


    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summery_page, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_summery_page_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(ReconfirmedPage.this);
                break;


            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }


    public static String convertListToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : stringList) {
            stringBuffer.append(str).append(LIST_SEPARATOR);
        }
        try {
            // Remove last separator
            int lastIndex = stringBuffer.lastIndexOf(LIST_SEPARATOR);
            stringBuffer.delete(lastIndex, lastIndex + LIST_SEPARATOR.length() + 1);
        }catch (Exception e){
            e.getStackTrace();

        }


        return stringBuffer.toString();
    }


    public void initializeUI(){


        edtOutletCode = (TextView) findViewById(R.id.edt_outlet_code);

       edtChannel =(TextView) findViewById(R.id.edt_channel);
       edtCoolerStatus = (TextView) findViewById(R.id.edt_cooler_status);
       edtCoolerPurity = (TextView) findViewById(R.id.edt_cooler_purity);
       edtCoolerCharging = (TextView) findViewById(R.id.edt_cooler_charging);

       edtSKUGRB = (TextView) findViewById(R.id.edt_sku_grb);
       edtSKUCAN = (TextView) findViewById(R.id.edt_sku_can);
       edtSKUGOPACK = (TextView) findViewById(R.id.edt_sku_go_pack);
        edt400ML = (TextView) findViewById(R.id.edt_sku_400ml);
        edt500ML = (TextView) findViewById(R.id.edt_sku_500ml);
        edtOneLiter = (TextView) findViewById(R.id.edt_sku_1_liter);
        edtTwoLiter = (TextView) findViewById(R.id.edt_sku_2_liter);
        edtAquafina = (TextView) findViewById(R.id.edt_sku_aquafina);

       edtNoOfActiveCooler = (TextView) findViewById(R.id.edt_active_cooler);
       edtLightWorking = (TextView) findViewById(R.id.edt_light_working);
       edtNoOfShelves = (TextView) findViewById(R.id.edt_no_of_shelves);
       edtCleanLiness = (TextView) findViewById(R.id.edt_cleanliness);
       edtPrimePosition = (TextView) findViewById(R.id.edt_prime_position);
       edtAvailabilty  = (TextView) findViewById(R.id.edt_availabilty);

       edtRemarks = (TextView) findViewById(R.id.edt_remarks);
       imgCoolerPic = (ImageView)findViewById(R.id.img_cooler_pic);
       imgOutletPic = (ImageView)findViewById(R.id.img_outlet_pic);


        btnEditChannel =(Button)findViewById(R.id.edt_channel_customize);
        btnEditCoolerStatus =(Button)findViewById(R.id.edt_cooler_status_customize) ;
        btnEditGrb = (Button)findViewById(R.id.edt_sku_grb_customize) ;
        btnEditShelves =(Button)findViewById(R.id.edt_no_of_shelves_customize);
        btnEditCleanliness =(Button)findViewById(R.id.edt_cleanliness_customize);
        btnEditRemarks = (Button)findViewById(R.id.edt_remarks_customize);
        btnEditCoolerPurity =(Button)findViewById(R.id.edt_cooler_purity_customize);
        btnEditCan= (Button)findViewById(R.id.edt_sku_can_customize);
        btnEditGoPack = (Button)findViewById(R.id.edt_sku_go_pack_customize);
        btnEdit400ml =(Button)findViewById(R.id.edt_sku_400ml_customize);
        btnEditltr1 =(Button)findViewById(R.id.edt_sku_1_liter_customize) ;
        btnEditltr2 = (Button)findViewById(R.id.edt_sku_2_liter_customize);
        btnEditAquafina =(Button)findViewById(R.id.edt_sku_aquafina_customize);
        btnEditCoolerActive =(Button)findViewById(R.id.edt_active_cooler_customize);
        btnEditLightWorking =(Button)findViewById(R.id.edt_light_working_customize);
        btnEditCoolerCharging=(Button)findViewById(R.id.edt_cooler_charging_customize);
        btnEditAvailability =(Button)findViewById(R.id.edt_availabilty_customize);
        btnEdit500ml = (Button)findViewById(R.id.edt_sku_500ml_customize) ;
        btnEditPrimePosition =(Button)findViewById(R.id.edt_prime_position_customize);

        btnEditChannel.setTransformationMethod(null);
        btnEditCoolerStatus.setTransformationMethod(null);
        btnEditGrb .setTransformationMethod(null);
        btnEditShelves .setTransformationMethod(null);
        btnEditCleanliness .setTransformationMethod(null);
        btnEditRemarks .setTransformationMethod(null);
        btnEditCoolerPurity .setTransformationMethod(null);
        btnEditCan.setTransformationMethod(null);
        btnEditGoPack.setTransformationMethod(null);
        btnEdit400ml.setTransformationMethod(null);
        btnEditltr1.setTransformationMethod(null);
        btnEditltr2.setTransformationMethod(null);
        btnEditAquafina .setTransformationMethod(null);
        btnEditCoolerActive .setTransformationMethod(null);
        btnEditLightWorking .setTransformationMethod(null);
        btnEditCoolerCharging.setTransformationMethod(null);
        btnEditAvailability .setTransformationMethod(null);
        btnEdit500ml.setTransformationMethod(null);
        btnEditPrimePosition .setTransformationMethod(null);



        btnConfirmed = (Button) findViewById(R.id.btn_confirmed);
        btnBack = (Button)findViewById(R.id.reconfirmed_btn_back);

        btnBack.setTransformationMethod(null);
        btnConfirmed.setTransformationMethod(null);

        performAllEditButton();
        logoutMenu = new LogoutMenu(ReconfirmedPage.this);

        imageAndLastActivity = ReconfirmedPage.this;




    }
    public  void  performAllEditButton(){
        btnEditChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,CoolerStatusActivity.class);
                startActivity(coolerstatus);

            }
        });
        btnEditCoolerStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,CoolerStatusActivity.class);
                startActivity(coolerstatus);
            }
        });


        btnEditCoolerCharging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,CoolerStatusActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditGrb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditShelves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,VISICoolerStatus.class);
                startActivity(coolerstatus);
            }
        });
        btnEditCleanliness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,VISICoolerStatus.class);
                startActivity(coolerstatus);
            }
        });

        btnEditPrimePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,VISICoolerStatus.class);
                startActivity(coolerstatus);
            }
        });

        btnEditAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,ImagesAndLast.class);
                startActivity(coolerstatus);
            }
        });
        btnEditRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,ImagesAndLast.class);
                startActivity(coolerstatus);
            }
        });
        btnEditCoolerPurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,CoolerStatusActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditGoPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEdit400ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEdit500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditltr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditltr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditAquafina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,SKUActivity.class);
                startActivity(coolerstatus);
            }
        });
        btnEditCoolerActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,VISICoolerStatus.class);
                startActivity(coolerstatus);
            }
        });
        btnEditLightWorking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPage.this,VISICoolerStatus.class);
                startActivity(coolerstatus);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        isActive = true;
        saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPage.this);
    }
    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
        saveBoleanValueSharedPreferences("is_active",false,ReconfirmedPage.this);
    }
}
