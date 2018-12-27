package limited.it.planet.visicoolertracking.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.activities.BasicInformationActivity;
import limited.it.planet.visicoolertracking.activities.ShowNotificationMessage;
import limited.it.planet.visicoolertracking.util.Constants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by interspeed.com.bd on 21-Aug-16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();
        String  message = (String) data.get("message");

        List<String> list = new ArrayList<>(data.values());
        String title = list.get(3);
        String subTitle = list.get(0);

       showNotification(message,title,subTitle);
        //Calling method to generate notification

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void showNotification(String message,String title,String subTitle) {

        Intent i = new Intent(this,ShowNotificationMessage.class);
        i.putExtra("title",title);
        i.putExtra("sub_title",subTitle);
        i.putExtra("message",message);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }


}
