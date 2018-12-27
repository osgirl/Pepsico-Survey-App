package limited.it.planet.visicoolertracking.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by mmislam3 on 16/08/2016.
 */
public class ImageCompressor {
    String coolerFilename="";
    String outletFilename="";
    String coolerFname = "";
    String outletFname = "";
    private String lowQualityImageCompress(String filePath, String imgName, Context context) {
      //  String filename="";
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;

            Bitmap scaledBitmap = BitmapFactory.decodeFile(filePath, options);

            FileOutputStream out = null;

            File file =getCoolerFilePath();
            coolerFilename = file.getAbsolutePath();
            out = new FileOutputStream(file);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (Exception exception) {
            exception.printStackTrace();

        }
        return coolerFilename;
    }
    private String lowQualityOulletImageCompress(String filePath, String imgName, Context context) {
        //  String filename="";
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;

            Bitmap scaledBitmap = BitmapFactory.decodeFile(filePath, options);

            FileOutputStream out = null;

            File file =getOutletFilePath();
            outletFilename = file.getAbsolutePath();
            out = new FileOutputStream(file);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (Exception exception) {
            exception.printStackTrace();

        }
        return outletFilename;
    }
    public String compressTempImage(String imageUri, String imgName, Context context) {


        String filePath = getRealPathFromURI(imageUri, context);


        File mfile = new File(filePath);
        long length = mfile.length() / 1024;
        if (length<=700){
            coolerFilename=lowQualityImageCompress(filePath, imgName,context);
        }else {
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;


            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            Point size = new Point();
            display.getSize(size);
        /*int width = ((size.x) * 60) / 100;
        int height = ((size.y) * 70) / 100;*/


//            float maxHeight = 1920;
//            float maxWidth = 1080;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;

            float imgRatio = 0;
            float maxRatio = 0;

            try {
                imgRatio = actualWidth / actualHeight;
                maxRatio = maxWidth / maxHeight;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            //options.inTempStorage = new byte[20 * 1024];
            options.inTempStorage = new byte[16 * 1024];
            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
            }


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream out = null;

            File file = getCoolerFilePath();

            try {
                out = new FileOutputStream(file);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
                coolerFilename = file.getAbsolutePath();
                out.flush();
                out.close();
                if (canvas != null) {
                    canvas.setBitmap(null);
                    canvas = null;
                }

                if (scaledBitmap != null && !scaledBitmap.isRecycled()) {
                    scaledBitmap.recycle();
                    scaledBitmap = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return coolerFilename;

    }
    public String compressOutletTempImage(String imageUri, String imgName, Context context) {


        String filePath = getRealPathFromURI(imageUri, context);


        File mfile = new File(filePath);
        long length = mfile.length() / 1024;
        if (length<=700){
            outletFilename=lowQualityOulletImageCompress(filePath, imgName,context);
        }else {
            Bitmap scaledBitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

            int actualHeight = options.outHeight;
            int actualWidth = options.outWidth;


            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            Point size = new Point();
            display.getSize(size);
        /*int width = ((size.x) * 60) / 100;
        int height = ((size.y) * 70) / 100;*/


//            float maxHeight = 1920;
//            float maxWidth = 1080;
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;

            float imgRatio = 0;
            float maxRatio = 0;

            try {
                imgRatio = actualWidth / actualHeight;
                maxRatio = maxWidth / maxHeight;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;

                }
            }

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPurgeable = true;
            options.inInputShareable = true;
            //options.inTempStorage = new byte[20 * 1024];
            options.inTempStorage = new byte[16 * 1024];
            try {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();

            }
            try {
                scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError exception) {
                exception.printStackTrace();
            }

            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
            }


            ExifInterface exif;
            try {
                exif = new ExifInterface(filePath);

                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream out = null;

            File file = getOutletFilePath();

            try {
                out = new FileOutputStream(file);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
                outletFilename = file.getAbsolutePath();
                out.flush();
                out.close();
                if (canvas != null) {
                    canvas.setBitmap(null);
                    canvas = null;
                }

                if (scaledBitmap != null && !scaledBitmap.isRecycled()) {
                    scaledBitmap.recycle();
                    scaledBitmap = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return outletFilename;

    }
    public String getCoolerFileName(){
        return coolerFilename;
    }

    public String getCoolerImageName(){
        return coolerFname;
    }
    public String getOutletFileName(){
        return outletFilename;
    }

    public String getOutletImageName(){
        return outletFname;
    }

    public File getCoolerFilePath() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/coolerImages_new");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        coolerFname = "Image-"+ n +".jpg";
        File file = new File (myDir, coolerFname);
        if (file.exists ()) file.delete ();


        return file;
    }
    public File getOutletFilePath() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/outletImages_new");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        outletFname = "Image-"+ n +".jpg";
        File file = new File (myDir, outletFname);
        if (file.exists ()) file.delete ();


        return file;
    }
//    public String getFilename() {
//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images/compress");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
//        return uriSting;
//    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private String getRealPathFromURI(String contentURI, Context context) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    public static Bitmap decodeSampledBitmapFromByteArray(String byteString, int reqWidth, int reqHeight) {
        byte[] decodedBytes = Base64.decode(byteString, 0);


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

    }

//
//    public static String attachNewBitMapIntoCache(String imageName, String bitteArray, Context mContext) {
//        String status = "false";
//        try {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//
//           /* options.inSampleSize = 4;*/
//            Point point = DisplayUtils.screenSize(mContext);
//            options.inSampleSize = calculateInSampleSize(options, point.x, point.y);
//
//            byte[] decodedString = Base64.decode(bitteArray, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);
//
//
//
//            DiskLruImageCache mDiskLruImageCache = new DiskLruImageCache(mContext, NFP4PrefUtils.getCacheDiskName(mContext), DiskLruImageCache.DISK_CACHE_SIZE, DiskLruImageCache.mCompressFormat, DiskLruImageCache.mCompressQuality);
//            imageName = imageName.replace(".jpg", "").toLowerCase();
//            imageName = imageName.replace("_", "-");
//            if (!mDiskLruImageCache.containsKey(imageName)) {
//                mDiskLruImageCache.put(imageName, bitmap);
//            }
//           /* BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            byte[] decodedString = Base64.decode(bitteArray, Base64.DEFAULT);
//            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);
//
//
//            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//
//
//            int maxHeight = size.y;
//            int maxWidth = size.x;
//
//
//*//*
//            try {
//                imgRatio = actualWidth / actualHeight;
//                maxRatio = maxWidth / maxHeight;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }*//*
//
//         *//*   if (actualHeight > maxHeight || actualWidth > maxWidth) {
//                if (imgRatio < maxRatio) {
//                    imgRatio = maxHeight / actualHeight;
//                    actualWidth = (int) (imgRatio * actualWidth);
//                    actualHeight = (int) maxHeight;
//                } else if (imgRatio > maxRatio) {
//                    imgRatio = maxWidth / actualWidth;
//                    actualHeight = (int) (imgRatio * actualHeight);
//                    actualWidth = (int) maxWidth;
//                } else {
//                    actualHeight = (int) maxHeight;
//                    actualWidth = (int) maxWidth;
//
//                }
//            }*//*
//            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
//            options.inJustDecodeBounds = false;
//            options.inDither = false;
//            options.inPurgeable = true;
//            options.inInputShareable = true;
//            options.inTempStorage = new byte[20 * 1024];
//
//            DiskLruImageCache mDiskLruImageCache = new DiskLruImageCache(mContext, NFP4PrefUtils.getCacheDiskName(mContext), DiskLruImageCache.DISK_CACHE_SIZE, DiskLruImageCache.mCompressFormat, DiskLruImageCache.mCompressQuality);
//            imageName = imageName.replace(".jpg", "").toLowerCase();
//            imageName = imageName.replace("_", "-");
//            if (!mDiskLruImageCache.containsKey(imageName)) {
//                mDiskLruImageCache.put(imageName, bmp);
//                status="true";
//            }
//
//            if (bmp != null && !bmp.isRecycled()) {
//                bmp.recycle();
//                bmp = null;
//            }*/
//            status = "True";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return status;
//
//    }
//

}
