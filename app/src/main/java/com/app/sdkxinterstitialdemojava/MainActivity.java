package com.app.sdkxinterstitialdemojava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.greedygame.core.AppConfig;
import com.greedygame.core.GreedyGameAds;
import com.greedygame.core.adview.modals.AdRequestErrors;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    private GGInterstitialAd mAd;
    private Boolean isAdShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppConfig appConfig = new AppConfig.Builder(this)
                .withAppId("62908905")  //Replace the app ID with your app's ID
                .enableDebug(false)
                .enableAdmobAds(true)
                .enableCrashReporting(true)
                .build();
        //In case you want callback for initialisation,
        //you can provide an greedyGameAdsEventsListener
        //for the same or assign *null* value
        GreedyGameAds.initWith(appConfig,null);

        mAd = new GGInterstitialAd(this, "float-5702");

        mAd.loadAd(new GGInterstitialEventsListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG,"Ad Loaded");
                if(mAd.isAdLoaded() && !isAdShown) {
                    mAd.show(); //Displays the ad in a full screen activity
                    isAdShown = true;
                }
            }
            @Override
            public void onAdLeftApplication() {
                Log.d(TAG,"Ad Left Application");
            }
            @Override
            public void onAdClosed() {
                Log.d(TAG,"Ad Closed");
                //Interstitial ad will be automatically refreshed by SDKX when     closed
            }
            @Override
            public void onAdOpened() {
                Log.d(TAG,"Ad Opened");
            }
            @Override
            public void onAdLoadFailed (AdRequestErrors cause) {
                Log.d(TAG,"Ad Load Failed "+cause);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAd != null) {
            mAd.destroy();
        }
    }
}