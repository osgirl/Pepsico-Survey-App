package limited.it.planet.visicoolertracking.util;

import android.content.Context;

import java.util.ArrayList;

import limited.it.planet.visicoolertracking.activities.BasicInformationActivity;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;

/**
 * Created by Master on 2/10/2018.
 */

public class TableHelper {
    Context mContext;
   String userId = "";

    public String[] tableHeaders={"outletid","channel","cooler Status","cooler charging","grb","500ml",
            "shelves","cleanliness","prime_position","availability","remarks","latitude","longitude","userid",
          "entry date","can","gopack","400ml","1liter","2liter","aquafina","cooler active","light working","start working","end time","sync status"};
    public String[][] allStorageData;
    public String[][] specificOfflineData;
    ServeyModel serveyModel;
    LocalStorageDB localStorageDB;
    public String[][] allTodaysData;

    public TableHelper(Context c) {
        this.mContext = c;
        serveyModel= new ServeyModel();
        localStorageDB = new LocalStorageDB(c);
        userId = getValueFromSharedPreferences("user_id",mContext);


    }

    public String[] getTableHeaders()
    {
        return tableHeaders;
    }


    public  String[][] getAllInputData()
    {
        String loginDate = Constants.getCurrentEntryDate();

        localStorageDB.open();
        if(userId!=null && !userId.isEmpty()){
            ArrayList<ServeyModel> surveylist=localStorageDB.getALLInputData(userId,loginDate);
            allStorageData= new String[surveylist.size()][31];

            for (int i=0;i<surveylist.size();i++) {

                serveyModel=surveylist.get(i);

                allStorageData[i][0]=serveyModel.getOutletId();
                allStorageData[i][1]=serveyModel.getChannel();
                allStorageData[i][2]=serveyModel.getCoolerStatus();
                allStorageData[i][3]=serveyModel.getCoolerCharging();
                allStorageData[i][4]=serveyModel.getGrb();
                allStorageData[i][5]=serveyModel.getCan500();
                allStorageData[i][6]=serveyModel.getShelves();
                allStorageData[i][7]=serveyModel.getCleanliness();
                allStorageData[i][8]=serveyModel.getPrimePosition();
                allStorageData[i][9]=serveyModel.getAvailabilty();
                allStorageData[i][10]=serveyModel.getRemarks();
                allStorageData[i][11]=serveyModel.getLatitude();
                allStorageData[i][12]=serveyModel.getLongitude();
                allStorageData[i][13]=serveyModel.getUserId();
                allStorageData[i][14]=serveyModel.getEntryDate();
                allStorageData[i][15]=serveyModel.getCoolerPID();
                allStorageData[i][16]=serveyModel.getOutletPID();
                allStorageData[i][17]=serveyModel.getCoolerPurity();
                allStorageData[i][18]=serveyModel.getCan();
                allStorageData[i][19]=serveyModel.getGoPack();
                allStorageData[i][20]=serveyModel.getCan400();
                allStorageData[i][21]=serveyModel.getLtr1();
                allStorageData[i][22]=serveyModel.getLtr2();
                allStorageData[i][23]=serveyModel.getAquafina();
                allStorageData[i][24]=serveyModel.getCoolerActive();
                allStorageData[i][25]=serveyModel.getLightWorking();
                allStorageData[i][26]=serveyModel.getStartTime();
                allStorageData[i][27]=serveyModel.getEndTime();
                allStorageData[i][28]=serveyModel.getSyncStatus();


            }
        }



        return allStorageData;
    }
    public  String[][] getAllOfflineData()
    {
        String checkSyncStatus = "failled";

        localStorageDB.open();
        if(userId!=null && !userId.isEmpty()){
            ArrayList<ServeyModel> surveylist=localStorageDB.getAllOfflineData(userId,checkSyncStatus);
            allStorageData= new String[surveylist.size()][31];

            for (int i=0;i<surveylist.size();i++) {

                serveyModel=surveylist.get(i);

                allStorageData[i][0]=serveyModel.getOutletId();
                allStorageData[i][1]=serveyModel.getChannel();
                allStorageData[i][2]=serveyModel.getCoolerStatus();
                allStorageData[i][3]=serveyModel.getCoolerCharging();
                allStorageData[i][4]=serveyModel.getGrb();
                allStorageData[i][5]=serveyModel.getCan500();
                allStorageData[i][6]=serveyModel.getShelves();
                allStorageData[i][7]=serveyModel.getCleanliness();
                allStorageData[i][8]=serveyModel.getPrimePosition();
                allStorageData[i][9]=serveyModel.getAvailabilty();
                allStorageData[i][10]=serveyModel.getRemarks();
                allStorageData[i][11]=serveyModel.getLatitude();
                allStorageData[i][12]=serveyModel.getLongitude();
                allStorageData[i][13]=serveyModel.getUserId();
                allStorageData[i][14]=serveyModel.getEntryDate();
                allStorageData[i][15]=serveyModel.getCoolerPID();
                allStorageData[i][16]=serveyModel.getOutletPID();
                allStorageData[i][17]=serveyModel.getCoolerPurity();
                allStorageData[i][18]=serveyModel.getCan();
                allStorageData[i][19]=serveyModel.getGoPack();
                allStorageData[i][20]=serveyModel.getCan400();
                allStorageData[i][21]=serveyModel.getLtr1();
                allStorageData[i][22]=serveyModel.getLtr2();
                allStorageData[i][23]=serveyModel.getAquafina();
                allStorageData[i][24]=serveyModel.getCoolerActive();
                allStorageData[i][25]=serveyModel.getLightWorking();
                allStorageData[i][26]=serveyModel.getStartTime();
                allStorageData[i][27]=serveyModel.getEndTime();
                allStorageData[i][28]=serveyModel.getSyncStatus();


            }
        }



        return allStorageData;
    }
    public   String[][] getSpecificOutletData(String outletCode)
    {
        //String loginDate = Constants.getCurrentEntryDate();

        localStorageDB.open();
        if(userId!=null && !userId.isEmpty()){
            ArrayList<ServeyModel> surveylist=localStorageDB.getSpecificOfflineData(outletCode,userId);
            specificOfflineData= new String[surveylist.size()][31];

            for (int i=0;i<surveylist.size();i++) {

                serveyModel=surveylist.get(i);

                specificOfflineData[i][0]=serveyModel.getOutletId();
                specificOfflineData[i][1]=serveyModel.getChannel();
                specificOfflineData[i][2]=serveyModel.getCoolerStatus();
                specificOfflineData[i][3]=serveyModel.getCoolerCharging();
                specificOfflineData[i][4]=serveyModel.getGrb();
                specificOfflineData[i][5]=serveyModel.getCan500();
                specificOfflineData[i][6]=serveyModel.getShelves();
                specificOfflineData[i][7]=serveyModel.getCleanliness();
                specificOfflineData[i][8]=serveyModel.getPrimePosition();
                specificOfflineData[i][9]=serveyModel.getAvailabilty();
                specificOfflineData[i][10]=serveyModel.getRemarks();
                specificOfflineData[i][11]=serveyModel.getLatitude();
                specificOfflineData[i][12]=serveyModel.getLongitude();
                specificOfflineData[i][13]=serveyModel.getUserId();
                specificOfflineData[i][14]=serveyModel.getEntryDate();
                specificOfflineData[i][15]=serveyModel.getCoolerPID();
                specificOfflineData[i][16]=serveyModel.getOutletPID();
                specificOfflineData[i][17]=serveyModel.getCoolerPurity();
                specificOfflineData[i][18]=serveyModel.getCan();
                specificOfflineData[i][19]=serveyModel.getGoPack();
                specificOfflineData[i][20]=serveyModel.getCan400();
                specificOfflineData[i][21]=serveyModel.getLtr1();
                specificOfflineData[i][22]=serveyModel.getLtr2();
                specificOfflineData[i][23]=serveyModel.getAquafina();
                specificOfflineData[i][24]=serveyModel.getCoolerActive();
                specificOfflineData[i][25]=serveyModel.getLightWorking();
                specificOfflineData[i][26]=serveyModel.getStartTime();
                specificOfflineData[i][27]=serveyModel.getEndTime();
                specificOfflineData[i][28]=serveyModel.getSyncStatus();


            }
        }



        return specificOfflineData;
    }
}
