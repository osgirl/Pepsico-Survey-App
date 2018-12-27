package limited.it.planet.visicoolertracking.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import limited.it.planet.visicoolertracking.activities.AllExistingDataViewActivity;
import limited.it.planet.visicoolertracking.activities.BasicInformationActivity;
import limited.it.planet.visicoolertracking.activities.CoolerStatusActivity;
import limited.it.planet.visicoolertracking.activities.ImagesAndLast;
import limited.it.planet.visicoolertracking.activities.LoginActivity;
import limited.it.planet.visicoolertracking.activities.ReconfirmedPage;
import limited.it.planet.visicoolertracking.activities.SKUActivity;
import limited.it.planet.visicoolertracking.activities.ViewInputDataActivity;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

/**
 * Created by Tarikul on 2/11/2018.
 */

public class LogoutMenu {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public  static final int REQUEST_CODE_ASK_PERMISSIONS =99;
    Context mContext ;
    public LogoutMenu(Context context){
        this.mContext = context;
    }

    public  void logoutNavigation() {
        saveToSharedPreferences("status",null,mContext);
        Intent intent = new Intent(mContext,
                LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);

        clearAllLogOutSaveData ();

    }

    public  void clearAllLogOutSaveData (){
        //when logout then clear
        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_code","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("town","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("territory","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("distributor","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("db_area","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("cluster_id","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_name","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("retailer_name","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("retailer_mobile","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("address","",mContext);
        //cooler status page mememory clear
        saveBoleanValueSharedPreferences("check_pepsico",false,mContext);
        saveBoleanValueSharedPreferences("pran",false,mContext);
        saveBoleanValueSharedPreferences("mojo",false,mContext);
        saveBoleanValueSharedPreferences("other",false,mContext);


        saveBoleanValueSharedPreferences("cocacola",false,mContext);


        saveBoleanValueSharedPreferences("check_grocery",false,mContext);
        saveBoleanValueSharedPreferences("check_restaurent",false,mContext);
        saveBoleanValueSharedPreferences("charging_one",false,mContext);
        saveBoleanValueSharedPreferences("charging_two",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);
        saveBoleanValueSharedPreferences("purity_no",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);

        //sku activity clear
        saveBoleanValueSharedPreferences("grb_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("grb_7up",false,mContext);
        saveBoleanValueSharedPreferences("grb_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("grb_mdew",false,mContext);
        saveBoleanValueSharedPreferences("grb_slice",false,mContext);

        saveBoleanValueSharedPreferences("can_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("can_7up",false,mContext);
        saveBoleanValueSharedPreferences("can_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("can_mdew",false,mContext);
        saveBoleanValueSharedPreferences("can_pepsi_diet",false,mContext);
        saveBoleanValueSharedPreferences("can_pepsi_black",false,mContext);

        saveBoleanValueSharedPreferences("go_pack_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_7up",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mdew",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_pepsi_black",false,mContext);

        saveBoleanValueSharedPreferences("400ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("400ml_mdew",false,mContext);

        saveBoleanValueSharedPreferences("500ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mdew",false,mContext);
        saveBoleanValueSharedPreferences("500ml_pepsi_diet",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up_light",false,mContext);

        saveBoleanValueSharedPreferences("1ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_7up",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mdew",false,mContext);

        saveBoleanValueSharedPreferences("2ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("2ltr_7up",false,mContext);


        saveBoleanValueSharedPreferences("aquafina_500ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1000ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1500ml",false,mContext);
        //visi cooler status clear
        saveBoleanValueSharedPreferences("cooler_active_yes",false,mContext);

        saveBoleanValueSharedPreferences("cooler_active_no",false,mContext);

        saveBoleanValueSharedPreferences("light_working_yes",false,mContext);

        saveBoleanValueSharedPreferences("light_working_no",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_six",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_five",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_four",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_three",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_other",false,mContext);


        saveBoleanValueSharedPreferences("cleanlines_yes",false,mContext);

        saveBoleanValueSharedPreferences("cleanlines_no",false,mContext);

        saveBoleanValueSharedPreferences("prime_position_yes",false,mContext);


        saveBoleanValueSharedPreferences("prime_position_no",false,mContext);
        //clear image and last

        saveBoleanValueSharedPreferences("shop_signage",false,mContext);
        saveBoleanValueSharedPreferences("posm",false,mContext);
        saveBoleanValueSharedPreferences("table_top",false,mContext);
        saveBoleanValueSharedPreferences("dangler",false,mContext);
        saveToSharedPreferences("input_remarks","",mContext);

        ///clear sharedpreference memory value
        SharedPreferenceLocalMemory.saveToSharedPreferences("latitude","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("longitude","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("OutletImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("other_value","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("user_id","",mContext);

        saveBoleanValueSharedPreferences("check_first_time",false,mContext);

        //clear piccode
        saveToSharedPreferences("cooler_pic_code","",mContext);
        saveToSharedPreferences("outlet_pic_code","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImageName","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("outletImageName","",mContext);

        //list item clear when user logout
        clearAllListItem();
        //when logout then clear allexisting static data
        clearAllStaticAllExistingData();
    }
    public void clearAllForConfirmedPage(){
        //when logout then clear
        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_code","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("town","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("territory","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("distributor","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("db_area","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("cluster_id","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_name","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("retailer_name","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("retailer_mobile","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("address","",mContext);
        //cooler status page mememory clear
        saveBoleanValueSharedPreferences("check_pepsico",false,mContext);
        saveBoleanValueSharedPreferences("pran",false,mContext);
        saveBoleanValueSharedPreferences("mojo",false,mContext);
        saveBoleanValueSharedPreferences("other",false,mContext);


        saveBoleanValueSharedPreferences("cocacola",false,mContext);


        saveBoleanValueSharedPreferences("check_grocery",false,mContext);
        saveBoleanValueSharedPreferences("check_restaurent",false,mContext);
        saveBoleanValueSharedPreferences("charging_one",false,mContext);
        saveBoleanValueSharedPreferences("charging_two",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);
        saveBoleanValueSharedPreferences("purity_no",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);

        //sku activity clear
        saveBoleanValueSharedPreferences("grb_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("grb_7up",false,mContext);
        saveBoleanValueSharedPreferences("grb_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("grb_mdew",false,mContext);
        saveBoleanValueSharedPreferences("grb_slice",false,mContext);

        saveBoleanValueSharedPreferences("can_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("can_7up",false,mContext);
        saveBoleanValueSharedPreferences("can_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("can_mdew",false,mContext);
        saveBoleanValueSharedPreferences("can_pepsi_diet",false,mContext);
        saveBoleanValueSharedPreferences("can_pepsi_black",false,mContext);

        saveBoleanValueSharedPreferences("go_pack_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_7up",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mdew",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_pepsi_black",false,mContext);

        saveBoleanValueSharedPreferences("400ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("400ml_mdew",false,mContext);

        saveBoleanValueSharedPreferences("500ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mdew",false,mContext);
        saveBoleanValueSharedPreferences("500ml_pepsi_diet",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up_light",false,mContext);

        saveBoleanValueSharedPreferences("1ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_7up",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mdew",false,mContext);

        saveBoleanValueSharedPreferences("2ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("2ltr_7up",false,mContext);

        saveBoleanValueSharedPreferences("aquafina_500ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1000ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1500ml",false,mContext);
        //visi cooler status clear
        saveBoleanValueSharedPreferences("cooler_active_yes",false,mContext);

        saveBoleanValueSharedPreferences("cooler_active_no",false,mContext);

        saveBoleanValueSharedPreferences("light_working_yes",false,mContext);

        saveBoleanValueSharedPreferences("light_working_no",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_six",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_five",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_four",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_three",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_other",false,mContext);


        saveBoleanValueSharedPreferences("cleanlines_yes",false,mContext);

        saveBoleanValueSharedPreferences("cleanlines_no",false,mContext);

        saveBoleanValueSharedPreferences("prime_position_yes",false,mContext);


        saveBoleanValueSharedPreferences("prime_position_no",false,mContext);

        //clear image and last

        saveBoleanValueSharedPreferences("shop_signage",false,mContext);
        saveBoleanValueSharedPreferences("posm",false,mContext);
        saveBoleanValueSharedPreferences("table_top",false,mContext);
        saveBoleanValueSharedPreferences("dangler",false,mContext);
        saveToSharedPreferences("input_remarks","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("latitude","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("longitude","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("OutletImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("other_value","",mContext);



        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImageName","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("outletImageName","",mContext);

        //clear piccode
        saveToSharedPreferences("cooler_pic_code","",mContext);
        saveToSharedPreferences("outlet_pic_code","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_id","",mContext);
        saveBoleanValueSharedPreferences("is_active",false,mContext);

        clearAllListItem();
    }
    public  void clearAllListItem(){
        //Clear all save data
        CoolerStatusActivity.coolerStatuslist.clear();

        SKUActivity.skuGRBlist.clear();
        SKUActivity.skuCanlist.clear();
        SKUActivity.skuGoPacklist.clear();
        SKUActivity.sku500mllist.clear();
        SKUActivity.sku400mllist.clear();
        SKUActivity.sku1Literlist.clear();
        SKUActivity.sku2Literlist.clear();
        SKUActivity.skuAquafinalist.clear();

        ImagesAndLast.availabiltyList.clear();
        //clear all next ,update static data
       // ReconfirmedPage.isActive = false;

    }

    public  void clearWhenSearchNewOutletCode (){

        //cooler status page mememory clear
        saveBoleanValueSharedPreferences("check_pepsico",false,mContext);
        saveBoleanValueSharedPreferences("pran",false,mContext);
        saveBoleanValueSharedPreferences("mojo",false,mContext);
        saveBoleanValueSharedPreferences("other",false,mContext);


        saveBoleanValueSharedPreferences("cocacola",false,mContext);


        saveBoleanValueSharedPreferences("check_grocery",false,mContext);
        saveBoleanValueSharedPreferences("check_restaurent",false,mContext);
        saveBoleanValueSharedPreferences("charging_one",false,mContext);
        saveBoleanValueSharedPreferences("charging_two",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);
        saveBoleanValueSharedPreferences("purity_no",false,mContext);
        saveBoleanValueSharedPreferences("purity_yes",false,mContext);

        //sku activity clear
        saveBoleanValueSharedPreferences("grb_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("grb_7up",false,mContext);
        saveBoleanValueSharedPreferences("grb_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("grb_mdew",false,mContext);
        saveBoleanValueSharedPreferences("grb_slice",false,mContext);

        saveBoleanValueSharedPreferences("can_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("can_7up",false,mContext);
        saveBoleanValueSharedPreferences("can_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("can_mdew",false,mContext);
        saveBoleanValueSharedPreferences("can_pepsi_diet",false,mContext);

        saveBoleanValueSharedPreferences("go_pack_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_7up",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("go_pack_mdew",false,mContext);

        saveBoleanValueSharedPreferences("400ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("400ml_mdew",false,mContext);

        saveBoleanValueSharedPreferences("500ml_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("500ml_mdew",false,mContext);
        saveBoleanValueSharedPreferences("500ml_pepsi_diet",false,mContext);
        saveBoleanValueSharedPreferences("500ml_7up_light",false,mContext);

        saveBoleanValueSharedPreferences("1ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_7up",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mirinda",false,mContext);
        saveBoleanValueSharedPreferences("1ltr_mdew",false,mContext);

        saveBoleanValueSharedPreferences("2ltr_pepsi",false,mContext);
        saveBoleanValueSharedPreferences("2ltr_7up",false,mContext);

        saveBoleanValueSharedPreferences("aquafina_500ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1000ml",false,mContext);
        saveBoleanValueSharedPreferences("aquafina_1500ml",false,mContext);
        //visi cooler status clear
        saveBoleanValueSharedPreferences("cooler_active_yes",false,mContext);

        saveBoleanValueSharedPreferences("cooler_active_no",false,mContext);

        saveBoleanValueSharedPreferences("light_working_yes",false,mContext);

        saveBoleanValueSharedPreferences("light_working_no",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_six",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_five",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_four",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_three",false,mContext);

        saveBoleanValueSharedPreferences("no_of_shelves_other",false,mContext);


        saveBoleanValueSharedPreferences("cleanlines_yes",false,mContext);

        saveBoleanValueSharedPreferences("cleanlines_no",false,mContext);

        saveBoleanValueSharedPreferences("prime_position_yes",false,mContext);


        saveBoleanValueSharedPreferences("prime_position_no",false,mContext);

        //clear image and last

        saveBoleanValueSharedPreferences("shop_signage",false,mContext);
        saveBoleanValueSharedPreferences("posm",false,mContext);
        saveBoleanValueSharedPreferences("table_top",false,mContext);
        saveBoleanValueSharedPreferences("dangler",false,mContext);
        saveToSharedPreferences("input_remarks","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("latitude","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("longitude","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("OutletImagePath","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("other_value","",mContext);

//clear piccode
        saveToSharedPreferences("cooler_pic_code","",mContext);
        saveToSharedPreferences("outlet_pic_code","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("coolerImageName","",mContext);
        SharedPreferenceLocalMemory.saveToSharedPreferences("outletImageName","",mContext);

        SharedPreferenceLocalMemory.saveToSharedPreferences("outlet_id","",mContext);

        saveBoleanValueSharedPreferences("is_active",false,mContext);

        clearAllListItem();
    }

    public void clearAllStaticAllExistingData(){
        AllExistingDataViewActivity.page = 0;
        AllExistingDataViewActivity.allInputData = 1;
        AllExistingDataViewActivity.allVisited = 0;
        AllExistingDataViewActivity.increaseDATA =0;
        AllExistingDataViewActivity.decreaseDATA=0 ;
        AllExistingDataViewActivity.checkLast = false;
        ViewInputDataActivity.todaysRowCount = 0;

    }

}
