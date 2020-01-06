package tech.krds.warnetlauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AboutActivity extends AppCompatActivity {
TextView textView2,textView3,textView4;
String easterEggOnClick = "";
ImageView imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init();
    }

    void init(){
        textView2= (TextView) findViewById(R.id.textView2);
        textView3= (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        textView2.setText("Lumba2 \nLauncher\nVersion 081219\nAndroid " + Build.VERSION.RELEASE );
        textView4.setText(getMerek() + "\n" + Build.MODEL + "\n" + getSoc() + "\n" + getRam() + "MB of RAM");
        if (Build.BRAND.equalsIgnoreCase("docomo")) {
            imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.brand_docomo));
            easterEggOnClick = "docomo = komodo = kadal";
        } else if (Build.BRAND.equalsIgnoreCase("advan")) {
            imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.brand_advan));
            easterEggOnClick = "advan = HP kentang = kentang";

        } else if (Build.BRAND.equalsIgnoreCase("asus")) {
            imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.brand_asus));
            easterEggOnClick = "asus s nya dihilangkan = asu = anjing = yoga";
        } else if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
            imageView4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.brand_xiaomi));
            easterEggOnClick = "xiaomi = siomay ghoib";
        }

        textView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press

                    Toast.makeText(getApplicationContext(), "Asede !@#$%^ sama lo semua !@#$%^& sama lo semua . nama cuma placeholder di versi selanjutnya bisa diganti di setting", Toast.LENGTH_LONG).show();

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // unlock home button and then screen on button press
                if (!easterEggOnClick.equalsIgnoreCase("")) {


                    Toast.makeText(getApplicationContext(), easterEggOnClick, Toast.LENGTH_LONG).show();
                }
            }
        });
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
        return profesor;
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
}
