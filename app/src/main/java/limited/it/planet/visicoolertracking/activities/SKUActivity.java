package limited.it.planet.visicoolertracking.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.interconn.salmaanahmed.saexpandablebutton.ExpandableButton;

import java.util.ArrayList;
import java.util.HashMap;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;

public class SKUActivity extends AppCompatActivity {
    ExpandableButton expandableBtnGRB,expandableLayoutCAN,
            expandableLayoutGOPACK,expandableLayout400ml,expandableLayout500ml,
            expandableLayoutOneLiter,expandableLayoutTwoLiter,expandableLayoutAquafina;


    CheckBox grbPepsi,grb7UP,grbMirinda,grbMDew,grbSlice;
    CheckBox canPepsi,can7UP,canMirinda,canMDew,canPepsiDiet,canPepsiBlack;
    CheckBox goPackPepsi,goPack7UP,goPackMirinda,goPackMDew,goPackPepsiBlack;
    CheckBox ml400Pepsi,ml400MDew;
    CheckBox ml500Pepsi,ml5007UP,ml500Mirinda,ml500MDew,ml500PepsiDiet,ml5007UPLight;
    CheckBox oneLiterPepsi,oneLiter7UP,oneLiterMirinda,oneLiterMDew;
    CheckBox twoLiterPepsi,twoLiter7UP;
    CheckBox aquafina500ml,aquafina1500ml,aquafina1000ml;

    Button btnNext,btnBack,btnUpdate;

   public static ArrayList<String> skuGRBlist = new ArrayList<String>();
    public static ArrayList<String> skuCanlist = new ArrayList<String>();
    public static ArrayList<String> skuGoPacklist = new ArrayList<String>();
   public static ArrayList<String> sku400mllist = new ArrayList<String>();
    public static ArrayList<String> sku500mllist = new ArrayList<String>();
   public   static ArrayList<String> sku1Literlist = new ArrayList<String>();
    public static ArrayList<String> sku2Literlist = new ArrayList<String>();
   public static ArrayList<String> skuAquafinalist = new ArrayList<String>();

    //grb
    String selectedgrbPepsi = "";
    String selectedgrb7UP = "";
    String selectedgrbMirinda = "";
    String selectedgrbDew = "";
    String selectedgrbSlice = "";

    //can
    String selectedcanPepsi = "";
    String selectedcan7UP = "";
    String selectedcanMirinda = "";
    String selectedcanDew = "";
    String selectedCanPepsiDiet = "";
    String selectedCanPepsiBlack = "";
     //go pack
     String selectedgoPackPepsi = "";
    String selectedgoPack7UP = "";
    String selectedgoPackMirinda = "";
    String selectedgoPackDew = "";
    String selectedgoPackPepsiBlack = "";
    //400 ml
    String selected400mlPepsi = "";
    String selected400mlDew = "";
    //500 ml
    String selected500mlPepsi = "";
    String selected500ml7UP = "";
    String selected500mlMirinda = "";
    String selected500mlDew = "";
    String selected500mlPepsiDiet = "";
    String selected500ml7UPLight = "";

    // 1 Liter
    String selectedOneLiterPepsi = "";
    String selectedOneLiter7UP = "";
    String selectedOneLiterMirinda = "";
    String selectedOneLiterMDew = "";
    //2 Liter
    String selected2LiterPepsi = "";
    String selected2Liter7UP = "";

    // aquafina
    String selectedaquafina500ml = "";
    String selectedaquafina1000ml = "";
    String selectedaquafina1500ml = "";

    LogoutMenu logoutMenu;

    Toolbar toolbar;
    boolean checkActiviyRunning ;

    //font customize
    FontCustomization fontCustomization;

//    public static SKUActivity skuActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sku);
        toolbar = (Toolbar)findViewById(R.id.toolbar_sku_activity) ;
        setSupportActionBar(toolbar);

       initializeUI();

        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",SKUActivity.this);

        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SKUActivity.this,CoolerStatusActivity.class);
                startActivity(intent);
              //  ActivityCompat.finishAffinity(SKUActivity.this);
            }
        });
//listen all checkbox for again edit
        grbPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grbPepsi.isChecked()) {
                    selectedgrbPepsi = grbPepsi.getText().toString();
                    if(!skuGRBlist.contains("Pepsi")){
                        skuGRBlist.add(selectedgrbPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_pepsi",false,SKUActivity.this);
                    skuGRBlist.clear();
                }
            }
        });
        grb7UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grb7UP.isChecked()) {
                    selectedgrb7UP = grb7UP.getText().toString();
                    if(!skuGRBlist.contains("7up")){
                        skuGRBlist.add(selectedgrb7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_7up",false,SKUActivity.this);
                    skuGRBlist.clear();
                }
            }
        });

        grbMDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grbMDew.isChecked()) {
                    selectedgrbDew = grbMDew.getText().toString();
                    if(!skuGRBlist.contains("MDEW")){
                        skuGRBlist.add(selectedgrbDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mdew",false,SKUActivity.this);
                    skuGRBlist.clear();
                }
            }
        });
        grbMirinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grbMirinda.isChecked()) {
                    selectedgrbMirinda = grbMirinda.getText().toString();
                    if(!skuGRBlist.contains("Mirinda")){
                        skuGRBlist.add(selectedgrbMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mirinda",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mirinda",false,SKUActivity.this);
                    skuGRBlist.clear();
                }
            }
        });
        grbSlice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (grbSlice.isChecked()) {
                    selectedgrbSlice = grbSlice.getText().toString();
                    if(!skuGRBlist.contains("Slice")){
                        skuGRBlist.add(selectedgrbSlice);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_slice",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_slice",false,SKUActivity.this);
                    skuGRBlist.clear();
                }
            }
        });

        //can
        canPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canPepsi.isChecked()) {
                    selectedcanPepsi = canPepsi.getText().toString();
                    if(!skuCanlist.contains("Pepsi")){
                        skuCanlist.add(selectedcanPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi",false,SKUActivity.this);
                    skuCanlist.clear();
                }
            }
        });
        can7UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (can7UP.isChecked()) {
                    selectedcan7UP = can7UP.getText().toString();
                    if(!skuCanlist.contains("7up")){
                        skuCanlist.add(selectedcan7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_7up",false,SKUActivity.this);
                    skuCanlist.clear();
                }
            }
        });
        canMirinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canMirinda.isChecked()) {
                    selectedcanMirinda = canMirinda.getText().toString();
                    if(!skuCanlist.contains("Mirinda")){
                        skuCanlist.add(selectedcanMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mirinda",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mirinda",false,SKUActivity.this);
                    skuCanlist.clear();
                }

            }
        });

        canMDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canMDew.isChecked()) {
                    selectedcanDew = canMDew.getText().toString();
                    if(!skuCanlist.contains("MDEW")){
                        skuCanlist.add(selectedcanDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mdew",false,SKUActivity.this);
                    skuCanlist.clear();
                }
            }
        });
        canPepsiDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canPepsiDiet.isChecked()) {
                    selectedCanPepsiDiet = canPepsiDiet.getText().toString();
                    if(!skuCanlist.contains("Pepsi Diet")){
                        skuCanlist.add(selectedCanPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_diet",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_diet",false,SKUActivity.this);
                    skuCanlist.clear();
                }
            }
        });

        canPepsiBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canPepsiBlack.isChecked()) {
                    selectedCanPepsiBlack = canPepsiBlack.getText().toString();
                    if(!skuCanlist.contains("Pepsi Black")){
                        skuCanlist.add(selectedCanPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_black",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_black",false,SKUActivity.this);
                    skuCanlist.clear();
                }
            }
        });

        //gopack
        goPackPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goPackPepsi.isChecked()) {
                    selectedgoPackPepsi = goPackPepsi.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi")){
                        skuGoPacklist.add(selectedgoPackPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi",false,SKUActivity.this);
                    skuGoPacklist.clear();
                }
            }
        });
        goPack7UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goPack7UP.isChecked()) {
                    selectedgoPack7UP = goPack7UP.getText().toString();
                    if(!skuGoPacklist.contains("7up")){
                        skuGoPacklist.add(selectedgoPack7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_7up",false,SKUActivity.this);
                    skuGoPacklist.clear();

                }
            }
        });

        goPackMDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goPackMDew.isChecked()) {
                    selectedgoPackDew = goPackMDew.getText().toString();
                    if(!skuGoPacklist.contains("MDEW")){
                        skuGoPacklist.add(selectedgoPackDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mdew",false,SKUActivity.this);
                    skuGoPacklist.clear();
                }
            }
        });

        goPackMirinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goPackMirinda.isChecked()) {
                    selectedgoPackMirinda = goPackMirinda.getText().toString();
                    if(!skuGoPacklist.contains("Mirinda")){
                        skuGoPacklist.add(selectedgoPackMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mirinda",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mirinda",false,SKUActivity.this);
                    skuGoPacklist.clear();
                }
            }
        });
        goPackPepsiBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goPackPepsiBlack.isChecked()) {
                    selectedgoPackPepsiBlack = goPackPepsiBlack.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi Black")){
                        skuGoPacklist.add(selectedgoPackPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi_black",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi_black",false,SKUActivity.this);
                    skuGoPacklist.clear();
                }
            }
        });
        //400ml
        ml400Pepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml400Pepsi.isChecked()) {
                    selected400mlPepsi = ml400Pepsi.getText().toString();
                    if(!sku400mllist.contains("Pepsi")){
                        sku400mllist.add(selected400mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_pepsi",false,SKUActivity.this);
                    sku400mllist.clear();
                }
            }
        });

        ml400MDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml400MDew.isChecked()) {
                    selected400mlDew = ml400MDew.getText().toString();
                    if(!sku400mllist.contains("MDEW")){
                        sku400mllist.add(selected400mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_mdew",false,SKUActivity.this);
                    sku400mllist.clear();
                }
            }
        });

        //500ml
        ml500Pepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml500Pepsi.isChecked()) {
                    selected500mlPepsi = ml500Pepsi.getText().toString();
                    if(!sku500mllist.contains("Pepsi")){
                        sku500mllist.add(selected500mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi",true,SKUActivity.this);
                }else {

                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });
        ml5007UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml5007UP.isChecked()) {
                    selected500ml7UP = ml5007UP.getText().toString();
                    if(!sku500mllist.contains("7up")){
                        sku500mllist.add(selected500ml7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });

        ml500Mirinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml500Mirinda.isChecked()) {
                    selected500mlMirinda = ml500Mirinda.getText().toString();
                    if(!sku500mllist.contains("Mirinda")){
                        sku500mllist.add(selected500mlMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mirinda",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mirinda",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });

        ml500MDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml500MDew.isChecked()) {
                    selected500mlDew = ml500MDew.getText().toString();
                    if(!sku500mllist.contains("MDEW")){
                        sku500mllist.add(selected500mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mdew",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });
        ml500PepsiDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml500PepsiDiet.isChecked()) {
                    selected500mlPepsiDiet = ml500PepsiDiet.getText().toString();
                    if(!sku500mllist.contains("Pepsi Diet")){
                        sku500mllist.add(selected500mlPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi_diet",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi_diet",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });

        ml5007UPLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ml5007UPLight.isChecked()) {
                    selected500ml7UPLight = ml5007UPLight.getText().toString();
                    if(!sku500mllist.contains("7up Light")){
                        sku500mllist.add(selected500ml7UPLight);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up_light",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up_light",false,SKUActivity.this);
                    sku500mllist.clear();
                }
            }
        });
        //1 liter
        oneLiterPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oneLiterPepsi.isChecked()) {
                    selectedOneLiterPepsi = oneLiterPepsi.getText().toString();
                    if(!sku1Literlist.contains("Pepsi")){
                        sku1Literlist.add(selectedOneLiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_pepsi",false,SKUActivity.this);
                    sku1Literlist.clear();

                }
            }
        });
        oneLiter7UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oneLiter7UP.isChecked()) {
                    selectedOneLiter7UP = oneLiter7UP.getText().toString();
                    if(!sku1Literlist.contains("7up")){
                        sku1Literlist.add(selectedOneLiter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_7up",false,SKUActivity.this);
                    sku1Literlist.clear();

                }
            }
        });
        oneLiterMirinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oneLiterMirinda.isChecked()) {
                    selectedOneLiterMirinda = oneLiterMirinda.getText().toString();
                    if(!sku1Literlist.contains("Mirinda")){
                        sku1Literlist.add(selectedOneLiterMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mirinda",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mirinda",false,SKUActivity.this);

                    sku1Literlist.clear();
                }

            }
        });

        oneLiterMDew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oneLiterMDew.isChecked()) {
                    selectedOneLiterMDew = oneLiterMDew.getText().toString();
                    if(!sku1Literlist.contains("MDEW")){
                        sku1Literlist.add(selectedOneLiterMDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mdew",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mdew",false,SKUActivity.this);

                    sku1Literlist.clear();
                }

            }
        });

        // 2 Liter
        twoLiterPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoLiterPepsi.isChecked()) {
                    selected2LiterPepsi = twoLiterPepsi.getText().toString();
                    if(!sku2Literlist.contains("Pepsi")){
                        sku2Literlist.add(selected2LiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_pepsi",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_pepsi",false,SKUActivity.this);
                    sku2Literlist.clear();
                }
            }
        });
        twoLiter7UP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoLiter7UP.isChecked()) {
                    selected2Liter7UP = twoLiter7UP.getText().toString();
                    if(!sku2Literlist.contains("7up")){
                        sku2Literlist.add(selected2Liter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_7up",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_7up",false,SKUActivity.this);
                    sku2Literlist.clear();
                }

            }
        });

        //aquafina

        aquafina500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aquafina500ml.isChecked()) {
                    selectedaquafina500ml = aquafina500ml.getText().toString();
                    if(!skuAquafinalist.contains("500 ml")){
                        skuAquafinalist.add(selectedaquafina500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_500ml",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_500ml",false,SKUActivity.this);
                    skuAquafinalist.clear();
                }

            }
        });

        aquafina1000ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aquafina1000ml.isChecked()) {
                    selectedaquafina1000ml = aquafina1000ml.getText().toString();
                    if(!skuAquafinalist.contains("1000 ml")){
                        skuAquafinalist.add(selectedaquafina1000ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1000ml",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1000ml",false,SKUActivity.this);
                    skuAquafinalist.clear();

                }

            }
        });

        aquafina1500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aquafina1500ml.isChecked()) {
                    selectedaquafina1500ml = aquafina1500ml.getText().toString();
                    if(!skuAquafinalist.contains("1500 ml")){
                        skuAquafinalist.add(selectedaquafina1500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1500ml",true,SKUActivity.this);
                }else {
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1500ml",false,SKUActivity.this);
                    skuAquafinalist.clear();

                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check grb
                if (grbPepsi.isChecked()) {
                    selectedgrbPepsi = grbPepsi.getText().toString();
                    if(!skuGRBlist.contains("Pepsi")){
                        skuGRBlist.add(selectedgrbPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_pepsi",true,SKUActivity.this);
                }

                if (grb7UP.isChecked()) {
                    selectedgrb7UP = grb7UP.getText().toString();
                    if(!skuGRBlist.contains("7up")){
                        skuGRBlist.add(selectedgrb7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_7up",true,SKUActivity.this);
                }
                if (grbMirinda.isChecked()) {
                    selectedgrbMirinda = grbMirinda.getText().toString();
                    if(!skuGRBlist.contains("Mirinda")){
                        skuGRBlist.add(selectedgrbMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mirinda",true,SKUActivity.this);
                }

                if (grbMDew.isChecked()) {
                    selectedgrbDew = grbMDew.getText().toString();
                    if(!skuGRBlist.contains("MDEW")){
                        skuGRBlist.add(selectedgrbDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mdew",true,SKUActivity.this);
                }
                if (grbSlice.isChecked()) {
                    selectedgrbSlice = grbSlice.getText().toString();
                    if(!skuGRBlist.contains("Slice")){
                        skuGRBlist.add(selectedgrbSlice);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_slice",true,SKUActivity.this);
                }
                //check can

                if (canPepsi.isChecked()) {
                    selectedcanPepsi = canPepsi.getText().toString();
                    if(!skuCanlist.contains("Pepsi")){
                        skuCanlist.add(selectedcanPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi",true,SKUActivity.this);
                }

                if (can7UP.isChecked()) {
                    selectedcan7UP = can7UP.getText().toString();
                    if(!skuCanlist.contains("7up")){
                        skuCanlist.add(selectedcan7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_7up",true,SKUActivity.this);
                }
                if (canMirinda.isChecked()) {
                    selectedcanMirinda = canMirinda.getText().toString();
                    if(!skuCanlist.contains("Mirinda")){
                        skuCanlist.add(selectedcanMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mirinda",true,SKUActivity.this);
                }

                if (canMDew.isChecked()) {
                    selectedcanDew = canMDew.getText().toString();
                    if(!skuCanlist.contains("MDEW")){
                        skuCanlist.add(selectedcanDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mdew",true,SKUActivity.this);
                }

                if (canPepsiDiet.isChecked()) {
                    selectedCanPepsiDiet = canPepsiDiet.getText().toString();
                    if(!skuCanlist.contains("Pepsi Diet")){
                        skuCanlist.add(selectedCanPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_diet",true,SKUActivity.this);
                }

                if (canPepsiBlack.isChecked()) {
                    selectedCanPepsiBlack = canPepsiBlack.getText().toString();
                    if(!skuCanlist.contains("Pepsi Black")){
                        skuCanlist.add(selectedCanPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_black",true,SKUActivity.this);
                }
                //check go pack
                if (goPackPepsi.isChecked()) {
                    selectedgoPackPepsi = goPackPepsi.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi")){
                        skuGoPacklist.add(selectedgoPackPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi",true,SKUActivity.this);
                }
                if (goPack7UP.isChecked()) {
                    selectedgoPack7UP = goPack7UP.getText().toString();
                    if(!skuGoPacklist.contains("7up")){
                        skuGoPacklist.add(selectedgoPack7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_7up",true,SKUActivity.this);
                }
                if (goPackMirinda.isChecked()) {
                    selectedgoPackMirinda = goPackMirinda.getText().toString();
                    if(!skuGoPacklist.contains("Mirinda")){
                        skuGoPacklist.add(selectedgoPackMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mirinda",true,SKUActivity.this);
                }

                if (goPackMDew.isChecked()) {
                    selectedgoPackDew = goPackMDew.getText().toString();
                    if(!skuGoPacklist.contains("MDEW")){
                        skuGoPacklist.add(selectedgoPackDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mdew",true,SKUActivity.this);
                }
                if (goPackPepsiBlack.isChecked()) {
                    selectedgoPackPepsiBlack = goPackPepsiBlack.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi Black")){
                        skuGoPacklist.add(selectedgoPackPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi_black",true,SKUActivity.this);
                }
                //check 400 ml

                if (ml400Pepsi.isChecked()) {
                    selected400mlPepsi = ml400Pepsi.getText().toString();
                    if(!sku400mllist.contains("Pepsi")){
                        sku400mllist.add(selected400mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_pepsi",true,SKUActivity.this);
                }

                if (ml400MDew.isChecked()) {
                    selected400mlDew = ml400MDew.getText().toString();
                    if(!sku400mllist.contains("MDEW")){
                        sku400mllist.add(selected400mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_mdew",true,SKUActivity.this);
                }

                //check 500 ml

                if (ml500Pepsi.isChecked()) {
                    selected500mlPepsi = ml500Pepsi.getText().toString();
                    if(!sku500mllist.contains("Pepsi")){
                        sku500mllist.add(selected500mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi",true,SKUActivity.this);
                }

                if (ml5007UP.isChecked()) {
                    selected500ml7UP = ml5007UP.getText().toString();
                    if(!sku500mllist.contains("7up")){
                        sku500mllist.add(selected500ml7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up",true,SKUActivity.this);
                }
                if (ml500Mirinda.isChecked()) {
                    selected500mlMirinda = ml500Mirinda.getText().toString();
                    if(!sku500mllist.contains("Mirinda")){
                        sku500mllist.add(selected500mlMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mirinda",true,SKUActivity.this);
                }

                if (ml500MDew.isChecked()) {
                    selected500mlDew = ml500MDew.getText().toString();
                    if(!sku500mllist.contains("MDEW")){
                        sku500mllist.add(selected500mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mdew",true,SKUActivity.this);
                }

                if (ml500PepsiDiet.isChecked()) {
                    selected500mlPepsiDiet = ml500PepsiDiet.getText().toString();
                    if(!sku500mllist.contains("Pepsi Diet")){
                        sku500mllist.add(selected500mlPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi_diet",true,SKUActivity.this);
                }
                if (ml5007UPLight.isChecked()) {
                    selected500ml7UPLight = ml5007UPLight.getText().toString();
                    if(!sku500mllist.contains("7up Light")){
                        sku500mllist.add(selected500ml7UPLight);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up_light",true,SKUActivity.this);
                }

                //check 1 Liter ml
                if (oneLiterPepsi.isChecked()) {
                    selectedOneLiterPepsi = oneLiterPepsi.getText().toString();
                    if(!sku1Literlist.contains("Pepsi")){
                        sku1Literlist.add(selectedOneLiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_pepsi",true,SKUActivity.this);
                }

                if (oneLiter7UP.isChecked()) {
                    selectedOneLiter7UP = oneLiter7UP.getText().toString();
                    if(!sku1Literlist.contains("7up")){
                        sku1Literlist.add(selectedOneLiter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_7up",true,SKUActivity.this);
                }
                if (oneLiterMirinda.isChecked()) {
                    selectedOneLiterMirinda = oneLiterMirinda.getText().toString();
                    if(!sku1Literlist.contains("Mirinda")){
                        sku1Literlist.add(selectedOneLiterMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mirinda",true,SKUActivity.this);
                }

                if (oneLiterMDew.isChecked()) {
                    selectedOneLiterMDew = oneLiterMDew.getText().toString();
                    if(!sku1Literlist.contains("MDEW")){
                        sku1Literlist.add(selectedOneLiterMDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mdew",true,SKUActivity.this);
                }
                //2 ltr
                if (twoLiterPepsi.isChecked()) {
                    selected2LiterPepsi = twoLiterPepsi.getText().toString();
                    if(!sku2Literlist.contains("Pepsi")){
                        sku2Literlist.add(selected2LiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_pepsi",true,SKUActivity.this);
                }
                if (twoLiter7UP.isChecked()) {
                    selected2Liter7UP = twoLiter7UP.getText().toString();
                    if(!sku2Literlist.contains("7up")){
                        sku2Literlist.add(selected2Liter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_7up",true,SKUActivity.this);
                }


                //aquafina
                if (aquafina500ml.isChecked()) {
                    selectedaquafina500ml = aquafina500ml.getText().toString();
                    if(!skuAquafinalist.contains("500ml")){
                        skuAquafinalist.add(selectedaquafina500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_500ml",true,SKUActivity.this);
                }
                if (aquafina1000ml.isChecked()) {
                    selectedaquafina1000ml = aquafina1000ml.getText().toString();
                    if(!skuAquafinalist.contains("1000ml")){
                        skuAquafinalist.add(selectedaquafina1000ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1000ml",true,SKUActivity.this);
                }

                if (aquafina1500ml.isChecked()) {
                    selectedaquafina1500ml = aquafina1500ml.getText().toString();
                    if(!skuAquafinalist.contains("1500ml")){
                        skuAquafinalist.add(selectedaquafina1500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1500ml",true,SKUActivity.this);
                }

                Intent intent = new Intent(SKUActivity.this,ReconfirmedPage.class);
                startActivity(intent);
               // ActivityCompat.finishAffinity(SKUActivity.this);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check grb
                if (grbPepsi.isChecked()) {
                    selectedgrbPepsi = grbPepsi.getText().toString();
                    if(!skuGRBlist.contains("Pepsi")){
                        skuGRBlist.add(selectedgrbPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_pepsi",true,SKUActivity.this);
                }

                if (grb7UP.isChecked()) {
                    selectedgrb7UP = grb7UP.getText().toString();
                    if(!skuGRBlist.contains("7up")){
                        skuGRBlist.add(selectedgrb7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_7up",true,SKUActivity.this);
                }
                if (grbMirinda.isChecked()) {
                    selectedgrbMirinda = grbMirinda.getText().toString();
                    if(!skuGRBlist.contains("Mirinda")){
                        skuGRBlist.add(selectedgrbMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mirinda",true,SKUActivity.this);
                }

                if (grbMDew.isChecked()) {
                    selectedgrbDew = grbMDew.getText().toString();
                    if(!skuGRBlist.contains("MDEW")){
                        skuGRBlist.add(selectedgrbDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_mdew",true,SKUActivity.this);
                }
                if (grbSlice.isChecked()) {
                    selectedgrbSlice = grbSlice.getText().toString();
                    if(!skuGRBlist.contains("Slice")){
                        skuGRBlist.add(selectedgrbSlice);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("grb_slice",true,SKUActivity.this);
                }
                //check can

                if (canPepsi.isChecked()) {
                    selectedcanPepsi = canPepsi.getText().toString();
                    if(!skuCanlist.contains("Pepsi")){
                        skuCanlist.add(selectedcanPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi",true,SKUActivity.this);
                }

                if (can7UP.isChecked()) {
                    selectedcan7UP = can7UP.getText().toString();
                    if(!skuCanlist.contains("7up")){
                        skuCanlist.add(selectedcan7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_7up",true,SKUActivity.this);
                }
                if (canMirinda.isChecked()) {
                    selectedcanMirinda = canMirinda.getText().toString();
                    if(!skuCanlist.contains("Mirinda")){
                        skuCanlist.add(selectedcanMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mirinda",true,SKUActivity.this);
                }

                if (canMDew.isChecked()) {
                    selectedcanDew = canMDew.getText().toString();
                    if(!skuCanlist.contains("MDEW")){
                        skuCanlist.add(selectedcanDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_mdew",true,SKUActivity.this);
                }

                if (canPepsiDiet.isChecked()) {
                    selectedCanPepsiDiet = canPepsiDiet.getText().toString();
                    if(!skuCanlist.contains("Pepsi Diet")){
                        skuCanlist.add(selectedCanPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_diet",true,SKUActivity.this);
                }
                if (canPepsiBlack.isChecked()) {
                    selectedCanPepsiBlack = canPepsiBlack.getText().toString();
                    if(!skuCanlist.contains("Pepsi Black")){
                        skuCanlist.add(selectedCanPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("can_pepsi_black",true,SKUActivity.this);
                }
               //check go pack
                if (goPackPepsi.isChecked()) {
                    selectedgoPackPepsi = goPackPepsi.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi")){
                        skuGoPacklist.add(selectedgoPackPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi",true,SKUActivity.this);
                }
                if (goPack7UP.isChecked()) {
                    selectedgoPack7UP = goPack7UP.getText().toString();
                    if(!skuGoPacklist.contains("7up")){
                        skuGoPacklist.add(selectedgoPack7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_7up",true,SKUActivity.this);
                }
                if (goPackMirinda.isChecked()) {
                    selectedgoPackMirinda = goPackMirinda.getText().toString();
                    if(!skuGoPacklist.contains("Mirinda")){
                        skuGoPacklist.add(selectedgoPackMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mirinda",true,SKUActivity.this);
                }

                if (goPackMDew.isChecked()) {
                    selectedgoPackDew = goPackMDew.getText().toString();
                    if(!skuGoPacklist.contains("MDEW")){
                        skuGoPacklist.add(selectedgoPackDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_mdew",true,SKUActivity.this);
                }
                if (goPackPepsiBlack.isChecked()) {
                    selectedgoPackPepsiBlack = goPackPepsiBlack.getText().toString();
                    if(!skuGoPacklist.contains("Pepsi Black")){
                        skuGoPacklist.add(selectedgoPackPepsiBlack);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("go_pack_pepsi_black",true,SKUActivity.this);
                }
                //check 400 ml

                if (ml400Pepsi.isChecked()) {
                    selected400mlPepsi = ml400Pepsi.getText().toString();
                    if(!sku400mllist.contains("Pepsi")){
                        sku400mllist.add(selected400mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_pepsi",true,SKUActivity.this);
                }

                if (ml400MDew.isChecked()) {
                    selected400mlDew = ml400MDew.getText().toString();
                    if(!sku400mllist.contains("MDEW")){
                        sku400mllist.add(selected400mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("400ml_mdew",true,SKUActivity.this);
                }

                //check 500 ml

                if (ml500Pepsi.isChecked()) {
                    selected500mlPepsi = ml500Pepsi.getText().toString();
                    if(!sku500mllist.contains("Pepsi")){
                        sku500mllist.add(selected500mlPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi",true,SKUActivity.this);
                }

                if (ml5007UP.isChecked()) {
                    selected500ml7UP = ml5007UP.getText().toString();
                    if(!sku500mllist.contains("7up")){
                        sku500mllist.add(selected500ml7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up",true,SKUActivity.this);
                }
                if (ml500Mirinda.isChecked()) {
                    selected500mlMirinda = ml500Mirinda.getText().toString();
                    if(!sku500mllist.contains("Mirinda")){
                        sku500mllist.add(selected500mlMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mirinda",true,SKUActivity.this);
                }

                if (ml500MDew.isChecked()) {
                    selected500mlDew = ml500MDew.getText().toString();
                    if(!sku500mllist.contains("MDEW")){
                        sku500mllist.add(selected500mlDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_mdew",true,SKUActivity.this);
                }

                if (ml500PepsiDiet.isChecked()) {
                    selected500mlPepsiDiet = ml500PepsiDiet.getText().toString();
                    if(!sku500mllist.contains("Pepsi Diet")){
                        sku500mllist.add(selected500mlPepsiDiet);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_pepsi_diet",true,SKUActivity.this);
                }
                if (ml5007UPLight.isChecked()) {
                    selected500ml7UPLight = ml5007UPLight.getText().toString();
                    if(!sku500mllist.contains("7up Light")){
                        sku500mllist.add(selected500ml7UPLight);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("500ml_7up_light",true,SKUActivity.this);
                }

                //check 1 Liter ml
                if (oneLiterPepsi.isChecked()) {
                    selectedOneLiterPepsi = oneLiterPepsi.getText().toString();
                    if(!sku1Literlist.contains("Pepsi")){
                        sku1Literlist.add(selectedOneLiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_pepsi",true,SKUActivity.this);
                }

                if (oneLiter7UP.isChecked()) {
                    selectedOneLiter7UP = oneLiter7UP.getText().toString();
                    if(!sku1Literlist.contains("7up")){
                        sku1Literlist.add(selectedOneLiter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_7up",true,SKUActivity.this);
                }
                if (oneLiterMirinda.isChecked()) {
                    selectedOneLiterMirinda = oneLiterMirinda.getText().toString();
                    if(!sku1Literlist.contains("Mirinda")){
                        sku1Literlist.add(selectedOneLiterMirinda);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mirinda",true,SKUActivity.this);
                }
                if (oneLiterMDew.isChecked()) {
                    selectedOneLiterMDew = oneLiterMDew.getText().toString();
                    if(!sku1Literlist.contains("MDEW")){
                        sku1Literlist.add(selectedOneLiterMDew);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("1ltr_mdew",true,SKUActivity.this);
                }

            // 2 Liter
                if (twoLiterPepsi.isChecked()) {
                    selected2LiterPepsi = twoLiterPepsi.getText().toString();
                    if(!sku2Literlist.contains("Pepsi")){
                        sku2Literlist.add(selected2LiterPepsi);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_pepsi",true,SKUActivity.this);
                }

                if (twoLiter7UP.isChecked()) {
                    selected2Liter7UP = twoLiter7UP.getText().toString();
                    if(!sku2Literlist.contains("7up")){
                        sku2Literlist.add(selected2Liter7UP);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("2ltr_7up",true,SKUActivity.this);
                }

                //aquafina
                if (aquafina500ml.isChecked()) {
                    selectedaquafina500ml = aquafina500ml.getText().toString();
                    if(!skuAquafinalist.contains("500ml")){
                        skuAquafinalist.add(selectedaquafina500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_500ml",true,SKUActivity.this);
                }

                if (aquafina1000ml.isChecked()) {
                    selectedaquafina1000ml = aquafina1000ml.getText().toString();
                    if(!skuAquafinalist.contains("1000ml")){
                        skuAquafinalist.add(selectedaquafina1000ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1000ml",true,SKUActivity.this);
                }

                if (aquafina1500ml.isChecked()) {
                    selectedaquafina1500ml = aquafina1500ml.getText().toString();
                    if(!skuAquafinalist.contains("1500ml")){
                        skuAquafinalist.add(selectedaquafina1500ml);
                    }
                    SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences("aquafina_1500ml",true,SKUActivity.this);
                }

                Intent visiCoolerIntent = new Intent(SKUActivity.this,VISICoolerStatus.class);
                startActivity(visiCoolerIntent);
               // ActivityCompat.finishAffinity(SKUActivity.this);

            }



        });
    }

    public void initializeUI(){
        expandableBtnGRB = findViewById(R.id.expandableButtonGRB);
        //GRB Check box
        grbPepsi = (CheckBox)findViewById(R.id.checkbox_grb_pepsi);
        grb7UP = (CheckBox)findViewById(R.id.checkbox_grb_7up);
        grbMirinda = (CheckBox)findViewById(R.id.checkbox_grb_mirinda);
        grbMDew = (CheckBox)findViewById(R.id.checkbox_grb_mdew);
        grbSlice = (CheckBox)findViewById(R.id.checkbox_grb_slice);

        //CAN Checkbox
        canPepsi = (CheckBox)findViewById(R.id.checkbox_can_pepsi);
        can7UP = (CheckBox)findViewById(R.id.checkbox_can_7up);
        canMirinda = (CheckBox)findViewById(R.id.checkbox_can_mirinda);
        canMDew = (CheckBox)findViewById(R.id.checkbox_can_mdew);
        canPepsiDiet = (CheckBox)findViewById(R.id.checkbox_can_pepsi_diet) ;
        canPepsiBlack = (CheckBox)findViewById(R.id.checkbox_can_pepsi_black) ;

        //GO Pack Checkbox
        goPackPepsi = (CheckBox)findViewById(R.id.checkbox_go_pack_pepsi);
        goPack7UP = (CheckBox)findViewById(R.id.checkbox_go_pack_7up);
        goPackMirinda = (CheckBox)findViewById(R.id.checkbox_go_pack_mirinda);
        goPackMDew = (CheckBox)findViewById(R.id.checkbox_go_pack_mdew);
        goPackPepsiBlack = (CheckBox)findViewById(R.id.checkbox_go_pack_pepsi_black) ;

        //400 ml Checkbox
        ml400Pepsi = (CheckBox)findViewById(R.id.checkbox_400ml_pepsi);
        ml400MDew = (CheckBox)findViewById(R.id.checkbox_400ml_mdew);

        //500 ml Checkbox
        ml500Pepsi = (CheckBox)findViewById(R.id.checkbox_500ml_pepsi);
        ml5007UP = (CheckBox)findViewById(R.id.checkbox_500ml_7up);
        ml500Mirinda = (CheckBox)findViewById(R.id.checkbox_500ml_mirinda);
        ml500MDew = (CheckBox)findViewById(R.id.checkbox_500ml_mdew);
        ml500PepsiDiet = (CheckBox)findViewById(R.id.checkbox_500ml_pepsi_diet);
        ml5007UPLight = (CheckBox)findViewById(R.id.checkbox_500ml_7up_light);

        // 1 liter checkbox

        oneLiterPepsi = (CheckBox)findViewById(R.id.checkbox_1_liter_pepsi);
        oneLiter7UP = (CheckBox)findViewById(R.id.checkbox_1_liter_7up);
        oneLiterMirinda = (CheckBox)findViewById(R.id.checkbox_1_liter_mirinda);
        oneLiterMDew = (CheckBox)findViewById(R.id.checkbox_1_liter_mdew);


        // 2 liter checkbox
        twoLiterPepsi = (CheckBox)findViewById(R.id.checkbox_2_liter_pepsi);
        twoLiter7UP = (CheckBox)findViewById(R.id.checkbox_2_liter_7up);

        // aquafina checkbox

        aquafina500ml = (CheckBox)findViewById(R.id.checkbox_aquafina_500ml);
        aquafina1000ml = (CheckBox)findViewById(R.id.checkbox_aquafina_1000ml);
        aquafina1500ml = (CheckBox)findViewById(R.id.checkbox_aquafina_1500ml);



        btnNext = (Button) findViewById(R.id.btn_next);
        btnBack = (Button)findViewById(R.id.btn_back);
        btnUpdate =(Button)findViewById(R.id.btn_sku_update);
        btnNext.setTransformationMethod(null);
        btnBack.setTransformationMethod(null);
        btnUpdate.setTransformationMethod(null);

        logoutMenu = new LogoutMenu(SKUActivity.this);

        //To use font customization
        fontCustomization = new FontCustomization(SKUActivity.this);
        //grb
        grbPepsi.setTypeface(fontCustomization.getTexgyreHerosRegular());
        grb7UP.setTypeface(fontCustomization.getTexgyreHerosRegular());
        grbMirinda.setTypeface(fontCustomization.getTexgyreHerosRegular());
        grbMDew.setTypeface(fontCustomization.getTexgyreHerosRegular());
        grbSlice.setTypeface(fontCustomization.getTexgyreHerosRegular());

        //can
        canPepsi.setTypeface(fontCustomization.getTexgyreHerosRegular());
        can7UP.setTypeface(fontCustomization.getTexgyreHerosRegular());
        canMirinda.setTypeface(fontCustomization.getTexgyreHerosRegular());
        canMDew.setTypeface(fontCustomization.getTexgyreHerosRegular());
        canPepsiDiet.setTypeface(fontCustomization.getTexgyreHerosRegular());
        canPepsiBlack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        //GO Pack Checkbox
        goPackPepsi.setTypeface(fontCustomization.getTexgyreHerosRegular());
        goPack7UP.setTypeface(fontCustomization.getTexgyreHerosRegular());
        goPackMirinda.setTypeface(fontCustomization.getTexgyreHerosRegular());
        goPackMDew.setTypeface(fontCustomization.getTexgyreHerosRegular());
        goPackPepsiBlack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        //400 ml Checkbox
        ml400Pepsi.setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml400MDew.setTypeface(fontCustomization.getTexgyreHerosRegular());

        //500 ml Checkbox
        ml500Pepsi.setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml5007UP.setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml500Mirinda .setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml500MDew .setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml500PepsiDiet.setTypeface(fontCustomization.getTexgyreHerosRegular());
        ml5007UPLight .setTypeface(fontCustomization.getTexgyreHerosRegular());

        // 1 liter checkbox

        oneLiterPepsi .setTypeface(fontCustomization.getTexgyreHerosRegular());
        oneLiter7UP .setTypeface(fontCustomization.getTexgyreHerosRegular());
        oneLiterMirinda.setTypeface(fontCustomization.getTexgyreHerosRegular());

        // 2 liter checkbox
        twoLiterPepsi .setTypeface(fontCustomization.getTexgyreHerosRegular());
        twoLiter7UP .setTypeface(fontCustomization.getTexgyreHerosRegular());

        // aquafina checkbox

        aquafina500ml.setTypeface(fontCustomization.getTexgyreHerosRegular());
        aquafina1500ml .setTypeface(fontCustomization.getTexgyreHerosRegular());

        btnNext .setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnBack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnUpdate.setTypeface(fontCustomization.getTexgyreHerosRegular());


    }
    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sku, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_sku_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(SKUActivity.this);
                break;


            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
         grbPepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("grb_pepsi",SKUActivity.this));
         grb7UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("grb_7up",SKUActivity.this));
         grbMirinda.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("grb_mirinda",SKUActivity.this));
         grbMDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("grb_mdew",SKUActivity.this));
         grbSlice.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("grb_slice",SKUActivity.this));

        canPepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_pepsi",SKUActivity.this));
        can7UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_7up",SKUActivity.this));
        canMirinda.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_mirinda",SKUActivity.this));
        canMDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_mdew",SKUActivity.this));
        canPepsiDiet.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_pepsi_diet",SKUActivity.this));
        canPepsiBlack.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("can_pepsi_black",SKUActivity.this));

        goPackPepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("go_pack_pepsi",SKUActivity.this));
        goPack7UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("go_pack_7up",SKUActivity.this));
         goPackMirinda.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("go_pack_mirinda",SKUActivity.this));
         goPackMDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("go_pack_mdew",SKUActivity.this));
        goPackPepsiBlack.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("go_pack_pepsi_black",SKUActivity.this));

        ml400Pepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("400ml_pepsi",SKUActivity.this));
        ml400MDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("400ml_mdew",SKUActivity.this));

        ml500Pepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_pepsi",SKUActivity.this));
        ml5007UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_7up",SKUActivity.this));
        ml500Mirinda.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_mirinda",SKUActivity.this));
        ml500MDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_mdew",SKUActivity.this));
        ml500PepsiDiet.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_pepsi_diet",SKUActivity.this));
        ml5007UPLight.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("500ml_7up_light",SKUActivity.this));

        oneLiterPepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("1ltr_pepsi",SKUActivity.this));
        oneLiter7UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("1ltr_7up",SKUActivity.this));
        oneLiterMirinda.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("1ltr_mirinda",SKUActivity.this));
        oneLiterMDew.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("1ltr_mdew",SKUActivity.this));


        twoLiterPepsi.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("2ltr_pepsi",SKUActivity.this));
        twoLiter7UP.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("2ltr_7up",SKUActivity.this));


        aquafina500ml.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("aquafina_500ml",SKUActivity.this));
        aquafina1000ml.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("aquafina_1000ml",SKUActivity.this));
        aquafina1500ml.setChecked(SharedPreferenceLocalMemory.getBoleanValueSharedPreferences("aquafina_1500ml",SKUActivity.this));

    }

}
