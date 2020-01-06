package tech.krds.warnetlauncher;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button button;
//    private AdView mAdView;
//    private InterstitialAd mInterstitialAd;
    EditText tv3,tv4,tv5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initAd();
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-4122406078134326/7193355275");
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice("859D3FE92631A341F9D24407DCC4F5C9").build();
//
//        mInterstitialAd.loadAd(adRequest);
//
//        mAdView = findViewById(R.id.adView);
//        mAdView.loadAd(adRequest);
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
//
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
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });

        tv3 = (EditText) findViewById(R.id.editText3);
        tv4 = (EditText) findViewById(R.id.editText4);
        tv5 = (EditText) findViewById(R.id.editText5);

        tv3.setText(PrefItils.getWarnetName(getApplicationContext()));
        tv4.setText(PrefItils.getWarnetAddress(getApplicationContext()));
        tv5.setText(PrefItils.getWarnetEmail(getApplicationContext()));

        button = (Button) findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    PrefItils.setWarnetName(tv3.getText().toString(),getApplicationContext());
                    PrefItils.setWarnetAddress(tv4.getText().toString(),getApplicationContext());
                    PrefItils.setWarnetEmail(tv5.getText().toString(),getApplicationContext());

                    Toast.makeText(getApplicationContext(),"Setting Disimpan",Toast.LENGTH_SHORT).show();

                    finish();


                // unlock home button and then screen on button press

            }
        });
    }

//    @Override
//    public void onRewarded(RewardItem reward) {
////        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
////                reward.getAmount(), Toast.LENGTH_SHORT).show();
//        // Reward the user.
//        PrefItils.setWarnetName(tv3.getText().toString(),getApplicationContext());
//        PrefItils.setWarnetAddress(tv4.getText().toString(),getApplicationContext());
//        PrefItils.setWarnetEmail(tv5.getText().toString(),getApplicationContext());
//
//        Toast.makeText(getApplicationContext(),"Setting Telah Disimpan",Toast.LENGTH_SHORT).show();
//
//        finish();
//
//    }
//
//    @Override
//    public void onRewardedVideoAdLeftApplication() {
//
//    }
//
//    @Override
//    public void onRewardedVideoAdClosed() {
//        Toast.makeText(this, "Gagal Menyimpan Setting karena iklan ditutup", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onRewardedVideoAdFailedToLoad(int errorCode) {
//        Toast.makeText(this, "Video gagal didapatkan . pastikan terkoneksi internet", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onRewardedVideoAdLoaded() {
//
//    }
//
//    @Override
//    public void onRewardedVideoAdOpened() {
//
//    }
//
//    @Override
//    public void onRewardedVideoStarted() {
//
//    }
//
//    @Override
//    public void onRewardedVideoCompleted() {
//
//    }
//
//    private RewardedVideoAd mRewardedVideoAd;
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
//        // Use an activity context to get the rewarded video instance.
//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        mRewardedVideoAd.setRewardedVideoAdListener(this);
//        mRewardedVideoAd.loadAd("ca-app-pub-<SENSOR>",
//                new AdRequest.Builder().build());
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

    }

}
