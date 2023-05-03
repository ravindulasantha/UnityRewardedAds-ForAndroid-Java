package com.test.unityadsandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.ads.metadata.MetaData;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private String unityGameID = "5265618";
    private String REWARDED_ID = "Rewarded_Android";
    private boolean testMode = true;

    private boolean adPlayed = true;

    Button button;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.inter);
        linearLayout = findViewById(R.id.banner);

        UnityAds.initialize(this, unityGameID, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                MetaData gdprMetadata = new MetaData(MainActivity.this);
                gdprMetadata.set("gdpr.consent",false);
                gdprMetadata.commit();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adPlayed = false;
            }
        }, 20000); // 10 seconds delay



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DisplayRewardedAd();
                if (!adPlayed) {
                    DisplayRewardedAd();
                    adPlayed = true;
                }
            }
        });
    }
    IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {

        }

        @Override
        public void onUnityAdsShowStart(String s) {

        }

        @Override
        public void onUnityAdsShowClick(String s) {

        }

        @Override
        public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {
            if (unityAdsShowCompletionState.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED)){

                finish();
                Intent intentPenc = new Intent(MainActivity.this, MainActivity.class);
                intentPenc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentPenc);
            }else {

            }
        }
    };

    IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String s) {
            UnityAds.show(MainActivity.this,REWARDED_ID, new UnityAdsShowOptions(),showListener);
        }

        @Override
        public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

        }
    };
    public void DisplayRewardedAd () {
        UnityAds.load(REWARDED_ID, loadListener);

    }
}