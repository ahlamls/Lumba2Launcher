package tech.krds.warnetlauncher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class LockActivity extends AppCompatActivity {
    private Button btnUnlock,btnPersonal,btnCancel,btnPanduan;
    TextView tv1,tv2,tv3;
    private LinearLayout ll3;
//    private AdView mAdView;
//    private InterstitialAd mInterstitialAd;
    private EditText editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initAd();
        merkHpHandler();
        init();

        PrefItils.setJam(String.valueOf(0),getApplicationContext());
        PrefItils.setMenit(String.valueOf(0),getApplicationContext());
        PrefItils.setDetik(String.valueOf(0),getApplicationContext());

    }

    void merkHpHandler(){
        TextView merkHp_tv = (TextView) findViewById(R.id.merekHp_tv);
        String merek = "1";
        if (Build.BRAND.equalsIgnoreCase("docomo")) {
            merek = "Kadal";
        } else if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            merek = "Siomay";
        } else if (Build.BRAND.equalsIgnoreCase("advan")) {
            merek = "Kentang";
        }
        merkHp_tv.setText("HP - " + merek + "\nTime Warning");
    }

    void init(){
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

        tv1.setText(PrefItils.getWarnetName(getApplicationContext()));
        tv2.setText(PrefItils.getWarnetAddress(getApplicationContext()));
        tv3.setText(PrefItils.getWarnetEmail(getApplicationContext()));

       // mLockscreenUtils = new LockscreenUtils();
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        btnUnlock = (Button) findViewById(R.id.button5);
        editText1 = (EditText) findViewById(R.id.editText);
        btnUnlock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                String nama = editText1.getText().toString();
                if (!nama.equals("")) {
                    Toast.makeText(getApplicationContext(),"Selamat Datang " + nama , Toast.LENGTH_SHORT).show();
                }
                unlockDevice();
            }
        });

        btnPersonal = (Button) findViewById(R.id.button2);
        btnPersonal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                ll3.setVisibility(View.VISIBLE);
            }
        });

        btnCancel = (Button) findViewById(R.id.button6);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                ll3.setVisibility(View.GONE);
            }
        });

        btnPanduan = (Button) findViewById(R.id.button4);
        btnPanduan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        LockActivity.this);

                // set title dialog
                alertDialogBuilder.setTitle("Panduan");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Untuk Mulai menggunakan HP cukup masukan nama anda di menu personal lalu sentuh tombol OK untuk mulai menggunakan HP")
                        .setIcon(R.drawable.lumba2logo)
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // jika tombol diklik, maka akan menutup activity ini
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        });
    }

    void unlockDevice() {
//        if (mInterstitialAd.isLoaded()) {
//            Random r = new Random();
//            int i1 = r.nextInt(10 - 1 + 1) + 1;
//            if (i1 <= 8) {
//                mInterstitialAd.show();
//            }
//
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
        PrefItils.setJam(String.valueOf(0),getApplicationContext());
        PrefItils.setMenit(String.valueOf(0),getApplicationContext());
        PrefItils.setDetik(String.valueOf(0),getApplicationContext());
        PrefItils.setLocked(false,getApplicationContext());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        PrefItils.setStart(date,getApplicationContext());
        PrefItils.setNama(editText1.getText().toString(),getApplicationContext());
        Intent i = new Intent(LockActivity.this,MainActivity.class);
                       i.putExtra("name", editText1.getText().toString());
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    void initAd() {
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("<SENSOR>").build();
//
//        mAdView.loadAd(adRequest);
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-<SENSOR>");
//        mInterstitialAd.loadAd(adRequest);
//
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//        });
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                mInterstitialAd.loadAd(adRequest);
//            }
//        });
    }
}
