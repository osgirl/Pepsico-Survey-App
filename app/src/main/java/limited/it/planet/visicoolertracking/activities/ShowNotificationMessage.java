package limited.it.planet.visicoolertracking.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import limited.it.planet.visicoolertracking.R;

public class ShowNotificationMessage extends AppCompatActivity {
    String title = "";
    String subTitle = "";
    String message = "";

    TextView txvTitle ,txvSubTitle,txvMessage,txvUrl;
    Toolbar toolbar;
   boolean checkHttpsUrl ;
    boolean checkHttpUrl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification_message);
        toolbar = (Toolbar)findViewById(R.id.toolbar_show_notification_msg) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initializeUI();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             title = extras.getString("title");
             subTitle = extras.getString("sub_title");
             message = extras.getString("message");
            txvTitle.setText(title);
            txvSubTitle.setText(subTitle);

            if (URLUtil.isValidUrl(message)) {
                txvUrl.setText(Html.fromHtml(message));
            }else if(message.indexOf("https")>=0){
                String[] splitted = message.split("https://");
                String url = "https://" + splitted[1];
                String message = splitted[0];
                txvUrl.setText(Html.fromHtml(url));
                txvMessage.setText(message);

            }else if(message.indexOf("http")>=0){
                String[] splitted = message.split("http://");
                String urlText = splitted[1];
                String url = "http://" + urlText;
                String message = splitted[0];
                txvUrl.setText(Html.fromHtml(url));
                txvMessage.setText(message);

            }
            else if(message.indexOf("www")>=0){
                String[] splitted = message.split("www.");
                String urlText = splitted[1];
                String url = "www." + urlText;
                String message = splitted[0];
                txvUrl.setText(Html.fromHtml(url));
                txvMessage.setText(message);

            }
            else if(!message.contains("http")){
                txvMessage.setText(message);
            }


        }




        txvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getAPKLink = txvUrl.getText().toString();

                //String s = "download_recording true Package available http://abc.com/recDownload/635131586215948750.exe";
                if (URLUtil.isValidUrl(getAPKLink)) {
                    // URL is valid
                    if(getAPKLink!=null && getAPKLink.length()>0){
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getAPKLink));
                        startActivity(intent);
                    }

                }
            }
        });
    }

    public void initializeUI(){
        txvTitle = (TextView)findViewById(R.id.txv_title);
        txvSubTitle = (TextView)findViewById(R.id.txv_subtitle);
        txvMessage = (TextView)findViewById(R.id.txv_message);
        txvUrl = (TextView)findViewById(R.id.txv_url);

    }

    public void decideProtocol(URL url) throws IOException {
        if ("https".equals(url.getProtocol())) {
            checkHttpsUrl = true;
        } else if ("http".equals(url.getProtocol())) {
            checkHttpUrl = true;
        }
    }
}
