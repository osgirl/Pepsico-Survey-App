package limited.it.planet.visicoolertracking.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.services.TrackGPS;
import limited.it.planet.visicoolertracking.util.Constants;
import limited.it.planet.visicoolertracking.util.FontCustomization;
import limited.it.planet.visicoolertracking.util.ImageCompressor;
import limited.it.planet.visicoolertracking.util.LogoutMenu;
import limited.it.planet.visicoolertracking.util.ServeyModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.getValueFromSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveBoleanValueSharedPreferences;
import static limited.it.planet.visicoolertracking.util.SharedPreferenceLocalMemory.saveToSharedPreferences;

public class ImagesAndLast extends AppCompatActivity {
    Button btnNext,btnBack,btnTakeCoolerPic,btnTakeOutletPic,btnUpdate;
    CheckBox chkShopSinage,chkPOSM,chkTableTop,chkDangler;
    EditText edtInputRemarks;
    String photoUploadAPI = Constants.photoUploadAPI;

   public static ArrayList<String> availabiltyList = new ArrayList<String>();

    String selectedShopSignage = "";
    String selectedPOSM = "";
    String selectedTableTop = "";
    String selectedDangler = "";
    String inputRemarks = "";

    //take pic
    Uri uri;
    String resizeCoolerImagePath = "";
    String resizeOutletImagePath = "";

    private final int CAPTURE_COOLER_PHOTO = 104;
    private final int CAPTURE_OUTLET_PHOTO = 103;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;

    public static final int REQUEST_COOLER_IMAGE = 100;
    public static final int REQUEST_OUTLET_IMAGE = 105;
    Bitmap resizeImage ;

    //location
    private TrackGPS gps;
    double longitude;
    double latitude;
    ServeyModel serveyModel;
    private ProgressBar progressBar;
    String coolerPicCode = "";
    String outletPicCode = "";
    TextView txvCoolerPicPath ,txvOutletPicPath,txvAvailability,txvRemarks,txvCoolerPic,txvOutletPic;

    String coolerFname = "";
    String OutletFname = "";
    LogoutMenu logoutMenu;
    Toolbar toolbar ;
    boolean checkActiviyRunning ;
    FontCustomization fontCustomization;

    ImageCompressor imageCompressor;
    String coolerImageFilePath = "";
    String outletImageFilePath ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_and_last);
        toolbar = (Toolbar)findViewById(R.id.toolbar_image_last) ;
        setSupportActionBar(toolbar);


        initializeUI();
        ///checkActiviyRunning = ReconfirmedPage.isActive;
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",ImagesAndLast.this);


        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }



        serveyModel = new ServeyModel();
        //camera permission



        if(gps.canGetLocation()){

            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            String convertlat = String.valueOf(latitude);
            String convertLon = String.valueOf(longitude);

//            serveyModel.setLatitude(latitude);
//            serveyModel.setLongitude(longitude);

            saveToSharedPreferences("latitude",convertlat,ImagesAndLast.this);
            saveToSharedPreferences("longitude",convertLon,ImagesAndLast.this);

//            Toast.makeText(getApplicationContext(),
//                    "Your longitude: "+Double.toString(longitude)+"\nYour latitude: "+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else {
            gps.showSettingsAlert();
        }


        //  isStoragePermissionGranted();

        btnTakeCoolerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ImagesAndLast.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }

                }
				if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(ImagesAndLast.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);

                } else{
                     takeCoolerPhotoByCamera ();
                }

            }
        });

        btnTakeOutletPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(ImagesAndLast.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);

                } else{
                    takeOutletPhotoByCamera ();
                }

            }
        });
        chkShopSinage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkShopSinage.isChecked()){
                    selectedShopSignage = chkShopSinage.getText().toString();
                    if(!availabiltyList.contains("Shop Signage")){
                    availabiltyList.add(selectedShopSignage);
                       }

                    saveBoleanValueSharedPreferences("shop_signage",true,ImagesAndLast.this);

                }else {
                    saveBoleanValueSharedPreferences("shop_signage",false,ImagesAndLast.this);
                    availabiltyList.clear();
                }
            }
        });
        chkPOSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkPOSM.isChecked()) {
                    selectedPOSM = chkPOSM.getText().toString();
                    if(!availabiltyList.contains("POSM")){
                        availabiltyList.add(selectedPOSM);
                    }
                    saveBoleanValueSharedPreferences("posm",true,ImagesAndLast.this);
                }else {
                    saveBoleanValueSharedPreferences("posm",false,ImagesAndLast.this);
                    availabiltyList.clear();
                }
            }
        });
        chkTableTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkTableTop.isChecked()) {
                    selectedTableTop = chkTableTop.getText().toString();
                     if(!availabiltyList.contains("Table Top")){
                    availabiltyList.add(selectedTableTop);
                      }
                    saveBoleanValueSharedPreferences("table_top",true,ImagesAndLast.this);
                }else {
                    saveBoleanValueSharedPreferences("table_top",false,ImagesAndLast.this);
                    availabiltyList.clear();
                }
            }
        });
        chkDangler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkDangler.isChecked()) {
                    selectedDangler = chkDangler.getText().toString();
                    if(!availabiltyList.contains("Dangler")){
                        availabiltyList.add(selectedDangler);
                    }
                    saveBoleanValueSharedPreferences("dangler",true,ImagesAndLast.this);
                }else {
                    saveBoleanValueSharedPreferences("dangler",false,ImagesAndLast.this);
                    availabiltyList.clear();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkShopSinage.isChecked()) {
                    selectedShopSignage = chkShopSinage.getText().toString();
                    if(!availabiltyList.contains("Shop Signage")){
                        availabiltyList.add(selectedShopSignage);
                    }

                    saveBoleanValueSharedPreferences("shop_signage",true,ImagesAndLast.this);
                }

                if (chkPOSM.isChecked()) {
                    selectedPOSM = chkPOSM.getText().toString();
                    if(!availabiltyList.contains("POSM")){
                        availabiltyList.add(selectedPOSM);
                    }
                    saveBoleanValueSharedPreferences("posm",true,ImagesAndLast.this);
                }

                if (chkTableTop.isChecked()) {
                    selectedTableTop = chkTableTop.getText().toString();
                    if(!availabiltyList.contains("Table Top")){
                        availabiltyList.add(selectedTableTop);
                    }
                    saveBoleanValueSharedPreferences("table_top",true,ImagesAndLast.this);
                }
                if (chkDangler.isChecked()) {
                    selectedDangler = chkDangler.getText().toString();
                    if(!availabiltyList.contains("Dangler")){
                        availabiltyList.add(selectedDangler);
                    }
                    saveBoleanValueSharedPreferences("dangler",true,ImagesAndLast.this);
                }

                //remarks
                inputRemarks = edtInputRemarks.getText().toString();
                saveToSharedPreferences("input_remarks",inputRemarks,ImagesAndLast.this);

                Intent intent = new Intent(ImagesAndLast.this,ReconfirmedPage.class);
                startActivity(intent);
               // ActivityCompat.finishAffinity(ImagesAndLast.this);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //check availabilty

                if (chkShopSinage.isChecked()) {
                    selectedShopSignage = chkShopSinage.getText().toString();
                    if(!availabiltyList.contains("Shop Signage")){
                        availabiltyList.add(selectedShopSignage);
                    }

                    saveBoleanValueSharedPreferences("shop_signage",true,ImagesAndLast.this);
                }

                if (chkPOSM.isChecked()) {
                    selectedPOSM = chkPOSM.getText().toString();
                    if(!availabiltyList.contains("POSM")){
                        availabiltyList.add(selectedPOSM);
                    }
                    saveBoleanValueSharedPreferences("posm",true,ImagesAndLast.this);
                }

                if (chkTableTop.isChecked()) {
                    selectedTableTop = chkTableTop.getText().toString();
                    if(!availabiltyList.contains("Table Top")){
                        availabiltyList.add(selectedTableTop);
                    }
                    saveBoleanValueSharedPreferences("table_top",true,ImagesAndLast.this);
                }
                if (chkDangler.isChecked()) {
                    selectedDangler = chkDangler.getText().toString();
                    if(!availabiltyList.contains("Dangler")){
                        availabiltyList.add(selectedDangler);
                    }
                    saveBoleanValueSharedPreferences("dangler",true,ImagesAndLast.this);
                }

                //remarks
                inputRemarks = edtInputRemarks.getText().toString();
                saveToSharedPreferences("input_remarks",inputRemarks,ImagesAndLast.this);


                if(txvCoolerPicPath.getText().toString().length()>0 && txvOutletPicPath.getText().toString().length()>0){
                    if(coolerImageFilePath.length()>0 && outletImageFilePath.length()>0){
                        Intent intent = new Intent(ImagesAndLast.this,ReconfirmedPage.class);
                        startActivity(intent);
                    }

                   // ActivityCompat.finishAffinity(ImagesAndLast.this);
                }else {
                    Toast.makeText(ImagesAndLast.this,"Photo take Mandatory",Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImagesAndLast.this,VISICoolerStatus.class);
                startActivity(intent);
              //  ActivityCompat.finishAffinity(ImagesAndLast.this);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_COOLER_IMAGE:
                  imageCompressor.compressTempImage(coolerImageFilePath,coolerImageFilePath,ImagesAndLast.this);
                    String coolerImSavePath = imageCompressor.getCoolerFileName();
                    String coolerImageName = imageCompressor.getCoolerImageName();
                    saveToSharedPreferences("coolerImagePath",coolerImSavePath,ImagesAndLast.this);
                    saveToSharedPreferences("coolerImageName",coolerImageName,ImagesAndLast.this);
                    txvCoolerPicPath.setText(coolerImageName);

                    if(coolerImSavePath!=null && coolerImSavePath.length()>0){
                        new  UpladCoolerPicToServer(coolerImSavePath).execute(Uri.parse(coolerImSavePath));
                        Toast.makeText(ImagesAndLast.this,"Your Photo save and Resize Success",Toast.LENGTH_SHORT).show();
                    }

                    break;

                case REQUEST_OUTLET_IMAGE:
                    imageCompressor.compressOutletTempImage(outletImageFilePath,outletImageFilePath,ImagesAndLast.this);
                    String outletImSavePath = imageCompressor.getOutletFileName();
                    String outletImageName = imageCompressor.getOutletImageName();
                    saveToSharedPreferences("OutletImagePath",outletImSavePath,ImagesAndLast.this);
                    saveToSharedPreferences("outletImageName",outletImageName,ImagesAndLast.this);
                    txvOutletPicPath.setText(outletImageName);

                    if(outletImSavePath!=null && outletImSavePath.length()>0){
                        new  UpladOutletPicToServer(outletImSavePath).execute(Uri.parse(outletImSavePath));
                        Toast.makeText(ImagesAndLast.this,"Your Photo save and Resize Success",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case CAPTURE_COOLER_PHOTO:
                    Bitmap capturedCoolerBitmap = (Bitmap) returnIntent.getExtras().get("data");

                    int capturedCImageWidth = 1200;
                    int capturedCImageHeight = 800;
                    resizeImage =   resizeBitmap(capturedCoolerBitmap,capturedCImageWidth,capturedCImageHeight);
                    saveCoolerImage(resizeImage);

                    break;

                case CAPTURE_OUTLET_PHOTO:
                    Bitmap capturedOutletBitmap = (Bitmap) returnIntent.getExtras().get("data");
                    int capturedOImageWidth = 1200;
                    int capturedOImageHeight = 800;
                    resizeImage =   resizeBitmap(capturedOutletBitmap,capturedOImageWidth,capturedOImageHeight);

                    saveOutletImage(resizeImage);
                    break;

                default:
                    break;

            }

        }

    }
    public void takeCoolerPhotoByCamera (){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createCoolerImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_COOLER_IMAGE);
            }
        }else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_COOLER_PHOTO);
        }

    }

    public void takeOutletPhotoByCamera (){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createOutletImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_OUTLET_IMAGE);
            }
        }else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, CAPTURE_OUTLET_PHOTO);
        }

    }
    private File createCoolerImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        coolerImageFilePath = image.getAbsolutePath();
        return image;
    }
    private File createOutletImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        outletImageFilePath = image.getAbsolutePath();
        return image;
    }

    private static Bitmap resizeBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
    private void saveCoolerImage(Bitmap finalBitmap) {
         Bitmap  bmp = Bitmap.createScaledBitmap(finalBitmap, 800, 800 * finalBitmap.getHeight() / finalBitmap.getWidth(), false);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/coolerImages_new");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        coolerFname = "Image-"+ n +".jpg";
        File file = new File (myDir, coolerFname);
        if (file.exists ()) file.delete ();
        //To use Image Compress
        resizeCoolerImagePath = file.getAbsolutePath();

        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();
            saveToSharedPreferences("coolerImagePath",resizeCoolerImagePath,ImagesAndLast.this);
            saveToSharedPreferences("coolerImageName",coolerFname,ImagesAndLast.this);

            txvCoolerPicPath.setText(coolerFname);
            Toast.makeText(ImagesAndLast.this,"Your Photo save and Resize Success",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ImagesAndLast.this,"Exaception Throw",Toast.LENGTH_SHORT).show();
        }

        String imageUriAfterResize = resizeCoolerImagePath;
        if(imageUriAfterResize!=null && !imageUriAfterResize.isEmpty()){
            new  UpladCoolerPicToServer(imageUriAfterResize).execute(Uri.parse(imageUriAfterResize));
        }
    }
    private void saveOutletImage(Bitmap finalBitmap) {
        Bitmap  bmp = Bitmap.createScaledBitmap(finalBitmap, 800, 800 * finalBitmap.getHeight() / finalBitmap.getWidth(), false);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/outletImages_new");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        OutletFname = "Image-"+ n +".jpg";
        File file = new File (myDir, OutletFname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            resizeOutletImagePath = file.getAbsolutePath();
            out.flush();
            out.close();
            saveToSharedPreferences("OutletImagePath",resizeOutletImagePath,ImagesAndLast.this);


            txvOutletPicPath.setText(OutletFname);
            saveToSharedPreferences("outletImageName",OutletFname,ImagesAndLast.this);
            Toast.makeText(ImagesAndLast.this,"Your Photo save and Resize Success",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ImagesAndLast.this,"Exaception Throw",Toast.LENGTH_SHORT).show();
        }

        String imageUriAfterResize = resizeOutletImagePath;
        if(imageUriAfterResize!=null && !imageUriAfterResize.isEmpty()){
            new  UpladOutletPicToServer(imageUriAfterResize).execute(Uri.parse(imageUriAfterResize));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    public void initializeUI(){
        btnNext = (Button)findViewById(R.id.btn_next);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnUpdate = (Button)findViewById(R.id.btn_image_last_update);
        btnNext.setTransformationMethod(null);
        btnBack.setTransformationMethod(null);
        btnUpdate.setTransformationMethod(null);

        btnTakeCoolerPic = (Button)findViewById(R.id.btn_cooler_picture);
        btnTakeOutletPic = (Button)findViewById(R.id.btn_outlet_picture);
        btnTakeCoolerPic.setTransformationMethod(null);
        btnTakeOutletPic.setTransformationMethod(null);

        edtInputRemarks = (EditText)findViewById(R.id.edt_input_remarks);
        //disable auto keyboard in input remarks


        chkShopSinage = (CheckBox) findViewById(R.id.checkbox_shop_signage);
        chkPOSM = (CheckBox)findViewById(R.id.checkbox_posm);
        chkTableTop = (CheckBox)findViewById(R.id.checkbox_table_top);
        chkDangler =(CheckBox)findViewById(R.id.checkbox_dangler);
        txvCoolerPicPath =(TextView)findViewById(R.id.txvcooler_pic_info);
        txvOutletPicPath = (TextView)findViewById(R.id.txvoutlet_pic_info);


        gps = new TrackGPS(ImagesAndLast.this);
        progressBar = new ProgressBar(ImagesAndLast.this);

        logoutMenu = new LogoutMenu(ImagesAndLast.this);

        //To use font customization
        fontCustomization = new FontCustomization(ImagesAndLast.this);
        txvAvailability = (TextView)findViewById(R.id.txv_availability);
        txvRemarks = (TextView)findViewById(R.id.txv_remarks);
        txvCoolerPic = (TextView)findViewById(R.id.txv_cooler_pic);
        txvOutletPic = (TextView)findViewById(R.id.txv_outlet_pic);

        txvAvailability.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvRemarks.setTypeface(fontCustomization.getTexgyreHerosBold());
        txvCoolerPic .setTypeface(fontCustomization.getTexgyreHerosBold());
        txvOutletPic .setTypeface(fontCustomization.getTexgyreHerosBold());

        btnNext .setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnBack.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnUpdate .setTypeface(fontCustomization.getTexgyreHerosRegular());

        btnTakeCoolerPic.setTypeface(fontCustomization.getTexgyreHerosRegular());
        btnTakeOutletPic .setTypeface(fontCustomization.getTexgyreHerosRegular());
        edtInputRemarks .setTypeface(fontCustomization.getTexgyreHerosRegular());

        chkShopSinage .setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkPOSM .setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkTableTop.setTypeface(fontCustomization.getTexgyreHerosRegular());
        chkDangler .setTypeface(fontCustomization.getTexgyreHerosRegular());
        txvCoolerPicPath .setTypeface(fontCustomization.getTexgyreHerosRegular());
        txvOutletPicPath .setTypeface(fontCustomization.getTexgyreHerosRegular());

        imageCompressor = new ImageCompressor();
    }

    public class UpladCoolerPicToServer extends AsyncTask<Uri, Integer, String> {
        String mContentFilePath;

        UpladCoolerPicToServer(String contentFilePath){
            this.mContentFilePath = contentFilePath;
        }
        private String getRealPathFromURI(Uri contentURI) {
            String result;
            Cursor cursor = ImagesAndLast.this.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return  result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(Uri... params) {
            String responseBodyText = null;
            Uri uri = params[0];
            String mime = "image/jpg";

            okhttp3.MediaType MEDIA_TYPE_PNG = okhttp3.MediaType.parse(mime);
            File imageFile = new File(getRealPathFromURI(uri));
            String imageFileName = imageFile.getName();
            OkHttpClient client = new OkHttpClient();

            try {

                RequestBody req = new okhttp3.MultipartBody.Builder()
                        .setType(okhttp3.MultipartBody.FORM)
                        .addFormDataPart("imageUpload", imageFileName, RequestBody.create(MEDIA_TYPE_PNG, imageFile)).build();

                Request request = new Request.Builder()
                        .url(photoUploadAPI)
                        .post(req)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    responseBodyText = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseBodyText);
                        if(jsonObject!=null){
                            coolerPicCode = jsonObject.getString("image_id");
                            saveToSharedPreferences("cooler_pic_code",coolerPicCode,ImagesAndLast.this);
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ImagesAndLast.this,"Image send to server Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    saveToSharedPreferences("cooler_pic_code","0",ImagesAndLast.this);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImagesAndLast.this,"Image not send to server Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                }



            } catch (IOException e) {
                saveToSharedPreferences("cooler_pic_code","0",ImagesAndLast.this);
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);


        }
    }
    public class UpladOutletPicToServer extends AsyncTask<Uri, Integer, String> {
        String mContentFilePath;

        UpladOutletPicToServer(String contentFilePath){
            this.mContentFilePath = contentFilePath;
        }
        private String getRealPathFromURI(Uri contentURI) {
            String result;
            Cursor cursor = ImagesAndLast.this.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return  result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(Uri... params) {
            String responseBodyText = null;
            Uri uri = params[0];
            String mime = "image/jpg";

            okhttp3.MediaType MEDIA_TYPE_PNG = okhttp3.MediaType.parse(mime);
            File imageFile = new File(getRealPathFromURI(uri));
            String imageFileName = imageFile.getName();
            OkHttpClient client = new OkHttpClient();

            try {

                RequestBody req = new okhttp3.MultipartBody.Builder()
                        .setType(okhttp3.MultipartBody.FORM)
                       .addFormDataPart("imageUpload", imageFileName, RequestBody.create(MEDIA_TYPE_PNG, imageFile)).build();

                Request request = new Request.Builder()
                        .url(photoUploadAPI)
                        .post(req)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    responseBodyText = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseBodyText);
                        if(jsonObject!=null){
                            outletPicCode = jsonObject.getString("image_id");
                            saveToSharedPreferences("outlet_pic_code",outletPicCode,ImagesAndLast.this);
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ImagesAndLast.this,"Image send to server Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    saveToSharedPreferences("outlet_pic_code","0",ImagesAndLast.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImagesAndLast.this,"Image not send to server Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });


                    throw new IOException("Okhttp Error: " + response);


                }



            } catch (IOException e) {
                saveToSharedPreferences("outlet_pic_code","0",ImagesAndLast.this);
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(ImagesAndLast.this,"Image send to server Successfully",Toast.LENGTH_SHORT).show();

        }
    }
    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_and_last, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Sudip: 20170212
        switch (item.getItemId()) {

            case R.id.menu_image_last_logout:
                logoutMenu.logoutNavigation();
                ActivityCompat.finishAffinity(ImagesAndLast.this);
                break;


            default:
                return super.onOptionsItemSelected(item);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean isCheckShopSignage = getBoleanValueSharedPreferences("shop_signage",ImagesAndLast.this);
        chkShopSinage.setChecked(isCheckShopSignage);
        chkPOSM.setChecked(getBoleanValueSharedPreferences("posm",ImagesAndLast.this));
        chkTableTop.setChecked(getBoleanValueSharedPreferences("table_top",ImagesAndLast.this));
        chkDangler.setChecked(getBoleanValueSharedPreferences("dangler",ImagesAndLast.this));
        String remarks = getValueFromSharedPreferences("input_remarks",ImagesAndLast.this);
        if(remarks!=null && !remarks.isEmpty()){
            edtInputRemarks.setText(remarks);
        }

    String coolerPicPath = getValueFromSharedPreferences("coolerImageName",ImagesAndLast.this);
        String outletPicPath = getValueFromSharedPreferences("outletImageName",ImagesAndLast.this);


        if(coolerPicPath!=null && !coolerPicPath.isEmpty()){
            txvCoolerPicPath.setText(coolerPicPath);
        }
        if(outletPicPath!=null && !outletPicPath.isEmpty()){
            txvOutletPicPath.setText(outletPicPath);
        }
//        //check update or next
//        if(checkActiviyRunning){
//            btnUpdate.setVisibility(View.VISIBLE);
//            btnNext.setVisibility(View.GONE);
//        }else {
//            if(checkActiviyRunning){
//                btnUpdate.setVisibility(View.VISIBLE);
//                btnNext.setVisibility(View.GONE);
//            }else {
//                btnNext.setVisibility(View.VISIBLE);
//            }
//
//        }


    }
    @Override
    public void onPause() {
        super.onPause();
        boolean isCheckShopSignage = getBoleanValueSharedPreferences("shop_signage",ImagesAndLast.this);
        chkShopSinage.setChecked(isCheckShopSignage);
        chkPOSM.setChecked(getBoleanValueSharedPreferences("posm",ImagesAndLast.this));
        chkTableTop.setChecked(getBoleanValueSharedPreferences("table_top",ImagesAndLast.this));
        chkDangler.setChecked(getBoleanValueSharedPreferences("dangler",ImagesAndLast.this));
//remarks
        inputRemarks = edtInputRemarks.getText().toString();
        if(inputRemarks!=null && !inputRemarks.isEmpty()){
            saveToSharedPreferences("input_remarks",inputRemarks,ImagesAndLast.this);
        }




    }




}
