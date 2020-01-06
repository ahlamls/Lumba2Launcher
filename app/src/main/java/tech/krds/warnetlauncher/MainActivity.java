package tech.krds.warnetlauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.tbruyelle.rxpermissions2.RxPermissions;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements IMethodCaller , GLSurfaceView.Renderer {
Button start_btn,recent_btn,f5;
ConstraintLayout startmenu_cl;
    private List<AppInfo> appsList;
    private GLSurfaceView glSurfaceView;
    private StringBuilder sb;
LinearLayout startmenuBottomx,timer_ll;
    private DatabaseHandler databaseHandler;
RecyclerView rv,rv2;
TextView battery_tv,timer_tv;
ProgressBar progressBar,progressBar2;
Boolean AppLoaded = false;
ImageView batterylogo_iv;
    private FavoriteAdapter adapter;
    private ArrayList<FavoriteModel> catatanArrayList;
    private List<catatanModels> catatanDb;
    String name;
//    private AdView mAdView;
//    private InterstitialAd mInterstitialAd;
Boolean kadalwafat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            69);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }

        if (PrefItils.getLocked(getApplicationContext())) {
            Intent i = new Intent(MainActivity.this,LockActivity.class);
//                        i.putExtra("name", name.getText().toString());
            startActivity(i);
            finish();
        }
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        setContentView(R.layout.activity_main);
initAd();
        start_btn = (Button) findViewById(R.id.start_btn);

        final ActivityManager activityManager =  (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        this.glSurfaceView = new GLSurfaceView(this);
        this.glSurfaceView.setRenderer(this);
        ((ViewGroup)start_btn.getParent()).addView(this.glSurfaceView);


        databaseHandler = new DatabaseHandler(this);


        f5 =  (Button) findViewById(R.id.f5);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        batterylogo_iv = (ImageView) findViewById(R.id.batterylogo_iv);

        battery_tv = (TextView) findViewById(R.id.battery_tv);
        timer_tv = findViewById(R.id.timer_tv);
        timer = null;
        warnetTimeHandler();
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        startmenu_cl = (ConstraintLayout) findViewById(R.id.startmenu_cl);
        startmenuBottomx = (LinearLayout) findViewById(R.id.startmenuBottomx);
        timer_ll = (LinearLayout) findViewById(R.id.timer_ll);


        rv = (RecyclerView) findViewById(R.id.rv);
        rv2 = (RecyclerView) findViewById(R.id.rv2);
        //initDrawer();
        initDrawer();
        initSidebar();

        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                if (mInterstitialAd.isLoaded()) {
//                    Random r = new Random();
//                    int i1 = r.nextInt(10 - 1 + 1) + 1;
//                    if (i1 <= 1) {
//                        mInterstitialAd.show();
//                    }
//
//                } else {
//                    Log.d("TAG", "The interstitial wasn't loaded yet.");
//                }
               if (startmenu_cl.getVisibility() == View.GONE) {
                   startmenu_cl.setVisibility(View.VISIBLE);
                   start_btn.setBackground(getResources().getDrawable(R.drawable.startbuttonbgpressed));

                   if (!AppLoaded) {

                    }

               } else {
                   start_btn.setBackground(getResources().getDrawable(R.drawable.startbuttonbg));

                   startmenu_cl.setVisibility(View.GONE);
               }
            }
        });

        f5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initDrawer();
//                if (mInterstitialAd.isLoaded()) {
//                    Random r = new Random();
//                    int i1 = r.nextInt(10 - 1 + 1) + 1;
//                    if (i1 <= 3) {
//                        mInterstitialAd.show();
//                    }
//
//                } else {
//                    Log.d("TAG", "The interstitial wasn't loaded yet.");
//                }
            }
        });




        startmenuBottomx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= 21){
                    if(isAccessibilityServiceEnabled(getApplicationContext(),PowerMenuService.class)) {
                        Intent intent = new Intent("tech.krds.warnetlauncher.ACCESSIBILITY_ACTION");
                        intent.putExtra("action", AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        ComponentName component = new ComponentName(getApplicationContext(), PowerMenuService.class);
                        getApplicationContext().getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                PackageManager.DONT_KILL_APP);
                    } else {
                        Toast.makeText(getApplicationContext(),"Silahkan Aktifkan Accessibility Lumba2Launcher untuk menggunakan fitur ini",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                } else{
                    Toast.makeText(getApplicationContext(),"Maaf . Fitur ini hanya bisa dijalankan di Android Versi 5.1 / Lolipop Keatas",Toast.LENGTH_SHORT).show();
                }

            }
        });

        timer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mInterstitialAd.isLoaded()) {
//                    Random r = new Random();
//                    int i1 = r.nextInt(10 - 1 + 1) + 1;
//                    if (i1 <= 3) {
//                        mInterstitialAd.show();
//                    }
//
//                } else {
//                    Log.d("TAG", "The interstitial wasn't loaded yet.");
//                }
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.billingdialog_layout);
                TextView textView4 = dialog.findViewById(R.id.textView4);
                String startTime = PrefItils.getStart(getApplicationContext());
                int rawDuit = 0;
                rawDuit = rawDuit + (Integer.valueOf(PrefItils.getJam(getApplicationContext())) * 3000);
                int menit = Integer.valueOf(PrefItils.getMenit(getApplicationContext()));
                if (menit  >= 50 ) {

                    rawDuit = rawDuit + 3000;

                } else if (menit >= 40) {
                    rawDuit = rawDuit + 2500;
                } else if (menit >= 30) {
                    rawDuit = rawDuit + 2000;
                } else if (menit >= 20) {
                    rawDuit = rawDuit + 1500;
                } else if (menit >= 10) {
                    rawDuit = rawDuit + 1000;
                } else {
                    rawDuit = rawDuit + 500;
                }
                 String duit = String.valueOf(rawDuit);
                String nama = PrefItils.getNama(getApplicationContext());
                String billingText = nama + "\n" + startTime + "\n" + timertext + "\n" + duit+ ".-\n" + duit+ ".-\n0";

                textView4.setText(billingText);
                Button stopBtn = (Button) dialog.findViewById(R.id.button9);

                stopBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (mInterstitialAd.isLoaded()) {
//                            Random r = new Random();
//                            int i1 = r.nextInt(10 - 1 + 1) + 1;
//                            if (i1 <= 8) {
//                                mInterstitialAd.show();
//                            }
//
//                        } else {
//                            Log.d("TAG", "The interstitial wasn't loaded yet.");
//                        }
                        PrefItils.setLocked(true,getApplicationContext());
                        Intent i = new Intent(MainActivity.this,LockActivity.class);
//                        i.putExtra("name", name.getText().toString());
                        startActivity(i);
                        dialog.cancel();
                        finish();



                    }
                });
                Button infoBtn = (Button) dialog.findViewById(R.id.button5);
                infoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (mInterstitialAd.isLoaded()) {
//                            Random r = new Random();
//                            int i1 = r.nextInt(10 - 1 + 1) + 1;
//                            if (i1 <= 8) {
//                                mInterstitialAd.show();
//                            }
//
//                        } else {
//                            Log.d("TAG", "The interstitial wasn't loaded yet.");
//                        }
                        Intent i = new Intent(MainActivity.this,AboutActivity.class);
//                        i.putExtra("name", name.getText().toString());
                        startActivity(i);
                        dialog.cancel();


                    }
                });
                Button settingBtn = dialog.findViewById(R.id.button7);
                settingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (mInterstitialAd.isLoaded()) {
//                            Random r = new Random();
//                            int i1 = r.nextInt(10 - 1 + 1) + 1;
//                            if (i1 <= 8) {
//                                mInterstitialAd.show();
//                            }
//
//                        } else {
//                            Log.d("TAG", "The interstitial wasn't loaded yet.");
//                        }
                        Intent i = new Intent(MainActivity.this,SettingActivity.class);
//                        i.putExtra("name", name.getText().toString());
                        startActivity(i);
                        dialog.cancel();


                    }
                });
                dialog.show();


            }
        });



    }
    Timer timer;
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
String timertext = "";



    void warnetTimeHandler() {
       if (timer != null) {
           timer.cancel();
       }

        if(1 == 2) {
            timer.cancel();
            timer = null;
        } else {
            //Log.e("Timer", "Timer Started");
            timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {


                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    int rawJam = Integer.valueOf(PrefItils.getJam(getApplicationContext()));
                    int rawMenit = Integer.valueOf(PrefItils.getMenit(getApplicationContext()));
                    int rawDetik = Integer.valueOf(PrefItils.getDetik(getApplicationContext()));
                    String jam = adjustLength(String.valueOf(rawJam), 3);
                    String menit = adjustLength(String.valueOf(rawMenit), 2);
                    String detik = adjustLength(String.valueOf(rawDetik), 2);

                    timer_tv.setText(jam + ":" + menit + ":" + detik);
                    timertext = jam + ":" + menit + ":" + detik;
                 //   Log.e("Timer", timertext);
                //    Log.e("Timer", String.valueOf(rawJam) + String.valueOf(rawMenit) + String.valueOf(rawDetik));

                    rawDetik = rawDetik + 1;

                    if (rawDetik >= 60) {
                        rawMenit = rawMenit + 1;
                        rawDetik = 0;

                    }

                    if (rawMenit >= 60) {
                        rawJam = rawJam + 1;
                        rawMenit = 0;

                    }
                    timer_tv.setText(jam + ":" + menit + ":" + detik);
                    timertext = jam + ":" + menit + ":" + detik;
                  //  Log.e("Timer", String.valueOf(rawJam) + String.valueOf(rawMenit) + String.valueOf(rawDetik));
                    PrefItils.setJam(String.valueOf(rawJam), getApplicationContext());
                    PrefItils.setMenit(String.valueOf(rawMenit), getApplicationContext());
                    PrefItils.setDetik(String.valueOf(rawDetik), getApplicationContext());

                        }
                    });
                }

            }, 0, 1000);


        }
    }

    String adjustLength(String string , int length) {
        String adjustedString = string;
        if (string.length() < length) {

            if (length == 2) {
                adjustedString = "0" + string;
            } else {
                if (string.contains("00")) {
                    adjustedString = "0" + string;
                } else {
                    adjustedString = "00" + string;
                }

            }


        }
        return adjustedString;
    }

    @Override
    public void onPause() {
        this.unregisterReceiver(this.mBatInfoReceiver);
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        warnetTimeHandler();
        super.onResume();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
final String gpuName = gl.glGetString(GL10.GL_RENDERER);
//        sb.append("VENDOR").append( gl.glGetString(GL10.GL_VENDOR)).append("\n");

//        sb.append("VERSION").append(gl.glGetString(GL10.GL_VERSION)).append("\n");
//        sb.append("EXTENSIONS").append(gl.glGetString(GL10.GL_EXTENSIONS));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView gpu_tv = findViewById(R.id.gpu_tv);
                gpu_tv.setText(gpuName);
                glSurfaceView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    }



    void initSidebar() {
        TextView manuBrand_tv = findViewById(R.id.manuBrand_tv);
        TextView model_tv = findViewById(R.id.model_tv);
        TextView soc_tv = findViewById(R.id.soc_tv);
        TextView version_tv = findViewById(R.id.version_tv);
        TextView reso_tv = findViewById(R.id.reso_tv);
        final TextView freeram_tv = findViewById(R.id.freeram_tv);
        TextView storage_tv = findViewById(R.id.storage_tv);

        manuBrand_tv.setText(getMerek());
        model_tv.setText(Build.MODEL);
        soc_tv.setText(getSoc());
        version_tv.setText(Build.VERSION.RELEASE);
        reso_tv.setText(getResol());
        getFreeRam(freeram_tv);
        storage_tv.setText(getStorage() + "M");


    }
    String ram = "0";
    void getFreeRam(final TextView textView){
        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

                try {
                    activityManager.getMemoryInfo(mi);
                    long totalMegs = mi.totalMem / 1048576L;
                    long availableMegs = mi.availMem / 1048576L;
                    ram = String.valueOf(availableMegs) + "/" + String.valueOf(totalMegs) + "M";
                } catch (Exception e) {

                }
                textView.setText(ram);

                // handler.postDelayed(this, delay);
            }
        }, delay);


    }

String getStorage() {
    String strg = "na";
    try {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        long megAvailable = bytesAvailable / 1048576;
        strg = String.valueOf(megAvailable);
    } catch (Exception e) {

    }
    return strg;
}
    String getResol() {
        String resol = "na";
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        Method mGetRawH = null,mGetRawW = null;
        int width = 0;
        int height = 0;
        try {
            display.getRealMetrics(metrics);
            width = metrics.widthPixels;
            height = metrics.heightPixels;
            resol = String.valueOf(height) + "x" + String.valueOf(width);
        } catch ( Exception e) {

        }
        return resol;
    }
    String getMerek() {
        if (Build.MANUFACTURER.equalsIgnoreCase(Build.BRAND)) {
            return Build.MANUFACTURER;
        } else {
            return Build.MANUFACTURER + " " + Build.BRAND;
        }

    }
    String getSoc() {
        Process p = null;
        String profesor = "Unknown";
        try {
            p = new ProcessBuilder("/system/bin/getprop", "ro.board.platform").redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line=br.readLine()) != null){
                profesor = line;
            }
            p.destroy();
        } catch (IOException e) {
            profesor = "Unknown";
// TODO Auto-generated catch block

        }

        String parsedProfesor = profesor;

        switch (parsedProfesor) {
            case "msm8956":
                parsedProfesor = "Snapdragon 650";
                break;

            case "msm8610":
                parsedProfesor = "Snapdragon 200";
                break;
            case "msm8609":
            case "msm8909":
                parsedProfesor = "Snapdragon 210";
                break;
            case "apq8908":
                parsedProfesor = "Snapdragon FPI";
                break;

            case "msm8953":
                parsedProfesor = "Snapdragon 450/625";
                break;
            case "msm8917":
                parsedProfesor = "Snapdragon 425";
                break;
            case "msm8937":
                parsedProfesor = "Snapdragon 430";
                break;
            case "msm8940":
                parsedProfesor = "Snapdragon 435";
                break;
            case "msm8216":
            case "msm8916":
                parsedProfesor = "Snapdragon 410";
                break;


            case "msm8952":
                parsedProfesor = "Snapdragon 650";
                break;
            case "msm8939":
                parsedProfesor = "Snapdragon 615/6";
                break;
            case "sm6150":
                parsedProfesor = "Snapdragon 675";
                break;
            case "trinket":
                parsedProfesor = "Snapdragon 665";
                break;

            case "sdmmagpie":
                parsedProfesor = "Snapdragon 730";
                break;
            case "sdmnobelium":
                parsedProfesor = "Snapdragon 712";
                break;

            case "msm8974":
                parsedProfesor = "Snapdragon 800/1";
                break;
            case "msm8992":
                parsedProfesor = "Snapdragon 808";
                break;
            case "msm8994":
                parsedProfesor = "Snapdragon 810";
                break;
            case "msm8996":
                parsedProfesor = "Snapdragon 820/1";
                break;
            case "msm8998":
                parsedProfesor = "Snapdragon 835";
                break;
            case "sm8150":
                parsedProfesor = "Snapdragon 855";
                break;


            case "mt6755":
            case "mt6755m":
                parsedProfesor = "Helio P10";
                break;
            case "mt6757":
                parsedProfesor = "Helio P20";
                break;
            case "mt6762":
            case "mt6762r":
                parsedProfesor = "Helio P22";
                break;
            case "mt6763":
            case "mt6763t":
                parsedProfesor = "Helio P23";
                break;
            case "mt6757cd":
                parsedProfesor = "Helio P25";
                break;
            case "mt6758":
                parsedProfesor = "Helio P30";
                break;
            case "mt6765":
                parsedProfesor = "Helio P35";
                break;
            case "mt6771":
                parsedProfesor = "Helio P60";
                break;
            case "mt6779":
                parsedProfesor = "Helio P90";
                break;

            case "mt6795":
                parsedProfesor = "Helio X10";
                break;
            case "mt6797":
                parsedProfesor = "Helio X20";
                break;
            case "mt6797d":
                parsedProfesor = "Helio X23";
                break;
            case "mt6797t":
                parsedProfesor = "Helio X25";
                break;
            case "mt6797x":
                parsedProfesor = "Helio X27";
                break;
            case "mt6799":
                parsedProfesor = "Helio X30";
                break;

        }
        if (parsedProfesor.contains("sdm")) {
            parsedProfesor = parsedProfesor.replace("sdm","Snapdragon ");
        }
        if (parsedProfesor.contains("mt")) {
            parsedProfesor = parsedProfesor.replace("mt","Mediatek ");
        }
        if (parsedProfesor.contains("sc")) {
            parsedProfesor = parsedProfesor.replace("sc","Spreadtrum ");
        }
        if (parsedProfesor.contains("sp")) {
            parsedProfesor = parsedProfesor.replace("sp","Spreadtrum ");
        }

        return parsedProfesor;
    }

    String getRam() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        String ram = "0";
        try {
            activityManager.getMemoryInfo(mi);
            long availableMegs = mi.totalMem / 1048576L;
            ram = String.valueOf(availableMegs);
        } catch (Exception e) {

        }
        return ram;
    }

    public boolean isAccessibilityServiceEnabled(Context context, Class accessibilityServiceClass) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        return prefString!= null && prefString.contains(context.getPackageName() + "/" + accessibilityServiceClass.getName());

    }
    public void initFavorite() {


        ESLThread thread = new ESLThread();
        thread.setListener(new ESLThreadListener() {
            @Override
            public List onBackground() {
                catatanArrayList = new ArrayList<>();
                catatanDb = databaseHandler.getAllRecord();
                final Intent in = new Intent(Intent.ACTION_MAIN, null);
                in.addCategory(Intent.CATEGORY_LAUNCHER);
                final PackageManager pm = getApplicationContext().getPackageManager();
                List<ResolveInfo> allApps = pm.queryIntentActivities(in, 0);
                // catatanArrayList.add(new Catatan("Contoh", "waktu disini"));
                for (int i = 0; i < catatanDb.size(); i++) {
//           Log.d("Hasil",catatanDb.get(i).);

                        String packagename = catatanDb.get(i).packageName;
                String aidi = String.valueOf(catatanDb.get(i).id);
                    ApplicationInfo ai;
                    try {
                        ai = pm.getApplicationInfo( packagename, 0);
                    } catch (final PackageManager.NameNotFoundException e) {
                        databaseHandler.deleteModel(catatanDb.get(i).id);
                        e.printStackTrace();
                        return appsList;
                    }
                    final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");

                        String label = applicationName;
                        Drawable icon = null;
                    try {
                        icon = getPackageManager().getApplicationIcon(packagename);

                        catatanArrayList.add(new FavoriteModel(aidi,label,packagename,icon));

                    } catch (PackageManager.NameNotFoundException e) {
                        databaseHandler.deleteModel(catatanDb.get(i).id);
                        e.printStackTrace();
                        return appsList;
                    }



                }

               return appsList; //dummy
            }

            @Override
            public void onPostExecute(List t) {
                rv2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.GONE);

                rv2 = (RecyclerView) findViewById(R.id.rv2);
                IMethodCaller iMethodCaller = new IMethodCaller() {
                    @Override
                    public void initFavorite() {
                        initFavoritex();
                    }
                };

                adapter = new FavoriteAdapter(catatanArrayList,iMethodCaller);

//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,3);





                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5 ,GridLayoutManager.HORIZONTAL, false);

                rv2.setLayoutManager(gridLayoutManager);

                rv2.setHasFixedSize(true);
                rv2.setAdapter(adapter);
            }
        });

        thread.execute();
    }

    void initDrawer() {
        rv2.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);

        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        final PackageManager pm = getApplicationContext().getPackageManager();
        appsList = new ArrayList<AppInfo>();

        final Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        ESLThread thread = new ESLThread();
        thread.setListener(new ESLThreadListener() {


            @Override
            public List onBackground() {

                List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
                for(ResolveInfo ri:allApps) {
                    AppInfo app = new AppInfo();
                    app.label = ri.loadLabel(pm);
                    app.packageName = ri.activityInfo.packageName;
                    app.icon = ri.activityInfo.loadIcon(pm);
                    if (filter(app.label.toString(),app.packageName.toString())) {
                        appsList.add(app);
                    }

                }



                return appsList;
            }

            @Override
            public void onPostExecute(List t) {
                IMethodCaller iMethodCaller = new IMethodCaller() {
                    @Override
                    public void initFavorite() {
                        initFavoritex();
                    }
                };
                RAdapter radapter = new RAdapter(getApplicationContext(),iMethodCaller);
                rv.setAdapter(radapter);
                rv.setLayoutManager(new GridLayoutManager(getApplicationContext() , 2));
                AppLoaded = true;


                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
//                if (kadalwafat) {
//                   // Toast.makeText(getApplicationContext(),"Aplikasi Kadal tidak diperlihatkan",Toast.LENGTH_SHORT).show();
//                }
//                if (!kadalwafat && Build.BRAND.equalsIgnoreCase("docomo")) {
//                 //   Toast.makeText(getApplicationContext(),"Selamat Kadalmu sudah di debloat!",Toast.LENGTH_SHORT).show();
//
//                }
                initFavorite();
            }
        });

        thread.execute();





    }

    void initFavoritex() {
        initFavorite();
    }

    boolean filter (String label,String packagename) {

        if (packagename.contains("nttdocomo") || packagename.contains("docomo") || packagename.contains("jp.co")) {
            //ANTI KADAL
            kadalwafat = true;
            return false;
        }
        return true;

    }

void initAd() {
//    MobileAds.initialize(this, new OnInitializationCompleteListener() {
//        @Override
//        public void onInitializationComplete(InitializationStatus initializationStatus) {
//        }
//    });
//    mAdView = findViewById(R.id.adView);
//
//    AdRequest adRequest = new AdRequest.Builder().addTestDevice("<SENSOR>").build();
//
//
//    mAdView.loadAd(adRequest);
//    mInterstitialAd = new InterstitialAd(this);
//    mInterstitialAd.setAdUnitId("ca-app-pub-<SENSOR>");
//    mInterstitialAd.loadAd(adRequest);
//
//    mAdView.setAdListener(new AdListener() {
//        @Override
//        public void onAdLoaded() {
//            // Code to be executed when an ad finishes loading.
//        }
//
//        @Override
//        public void onAdFailedToLoad(int errorCode) {
//            // Code to be executed when an ad request fails.
//        }
//
//        @Override
//        public void onAdOpened() {
//            // Code to be executed when an ad opens an overlay that
//            // covers the screen.
//        }
//
//        @Override
//        public void onAdClicked() {
//            // Code to be executed when the user clicks on an ad.
//        }
//
//        @Override
//        public void onAdLeftApplication() {
//            // Code to be executed when the user has left the app.
//        }
//
//        @Override
//        public void onAdClosed() {
//
//            // Code to be executed when the user is about to return
//            // to the app after tapping on an ad.
//        }
//    });
//    mInterstitialAd.setAdListener(new AdListener() {
//        @Override
//        public void onAdLoaded() {
//
//            // Code to be executed when an ad finishes loading.
//        }
//
//        @Override
//        public void onAdFailedToLoad(int errorCode) {
//            // Code to be executed when an ad request fails.
//        }
//
//        @Override
//        public void onAdOpened() {
//            // Code to be executed when the ad is displayed.
//timer.cancel();
//            timer = null;
//        }
//
//        @Override
//        public void onAdClicked() {
//            // Code to be executed when the user clicks on an ad.
//        }
//
//        @Override
//        public void onAdLeftApplication() {
//            // Code to be executed when the user has left the app.
//        }
//
//        @Override
//        public void onAdClosed() {
//warnetTimeHandler();
//            mInterstitialAd.loadAd(adRequest);
//        }
//    });
}
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int cas = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            battery_tv.setText(String.valueOf(level) + "%");
            if (cas == 0 ) {
                batterylogo_iv.setBackground(getResources().getDrawable(R.drawable.ic_battery_full_black_24dp));
            } else {
                batterylogo_iv.setBackground(getResources().getDrawable(R.drawable.ic_battery_charging_full_black_24dp));
            }
        }
    };
}
