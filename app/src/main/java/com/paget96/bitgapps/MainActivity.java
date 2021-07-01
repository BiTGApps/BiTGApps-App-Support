package com.paget96.bitgapps;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    // Variables
    private final Utils utils = new Utils();
    private TextView gappsInstallState, gappsPackage, platform, sdk, version, buildDate, buildId, developer, appVersion;
    private MaterialCardView xda, telegram, gitHub;
    private ImageView expandArrow;
    private ImageButton about;
    private MaterialButton getBitGapps;
    private LinearLayout buttonHolder, moreInfo, theHitman, paget96;
    private FrameLayout aboutLayout;
    private MaterialCardView infoCard;

    private void initializeViews() {
        appVersion = findViewById(R.id.app_version);
        appVersion.setText(String.format("v%s", BuildConfig.VERSION_NAME));

        aboutLayout = findViewById(R.id.about_gapps);
        about = findViewById(R.id.about);
        theHitman = findViewById(R.id.the_hitman);
        paget96 = findViewById(R.id.paget96);
        infoCard = findViewById(R.id.info_card);
        gappsInstallState = findViewById(R.id.gapps_install_state);
        buttonHolder = findViewById(R.id.button_holder);
        expandArrow = findViewById(R.id.expand_arrow);
        moreInfo = findViewById(R.id.more_info);

        gappsPackage = findViewById(R.id.gapps_package);
        platform = findViewById(R.id.platform);
        sdk = findViewById(R.id.sdk);
        version = findViewById(R.id.version);
        buildDate = findViewById(R.id.build_date);
        buildId = findViewById(R.id.build_id);
        developer = findViewById(R.id.developer);

        getBitGapps = findViewById(R.id.get_bitgapps);
        xda = findViewById(R.id.xda);
        telegram = findViewById(R.id.telegram);
        gitHub = findViewById(R.id.github);
    }

    private void getText() {
        if (utils.fileExists("/system/etc/g.prop", false)) {
            infoCard.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.installed_bg_color));
            gappsInstallState.setText("BiTGApps Installed");
            infoCard.setClickable(true);
            buttonHolder.setVisibility(View.GONE);
            expandArrow.setVisibility(View.VISIBLE);

            gappsPackage.setText(utils.splitString(getLineContent(1), "=", 1));
            version.setText(utils.splitString(getLineContent(4), "=", 1));

            platform.setText(utils.splitString(getLineContent(2), "=", 1));
            sdk.setText(utils.splitString(getLineContent(3), "=", 1));
            buildDate.setText(utils.splitString(getLineContent(5), "=", 1));
            buildId.setText(utils.splitString(getLineContent(6), "=", 1));
            developer.setText(utils.splitString(getLineContent(7), "=", 1));
        } else {
            infoCard.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.not_installed_bg_color));
            gappsInstallState.setText("BiTGApps Not Installed");
            infoCard.setClickable(false);
            buttonHolder.setVisibility(View.VISIBLE);
            expandArrow.setVisibility(View.GONE);
            moreInfo.setVisibility(View.GONE);
        }
    }

    private void onClick() {
        about.setOnClickListener(v -> aboutLayout.setVisibility(View.VISIBLE));
        aboutLayout.setOnClickListener(v -> aboutLayout.setVisibility(View.GONE));
        paget96.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://play.google.com/store/apps/dev?id=6924549437581780390&hl=en"));
        theHitman.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://github.com/TheHitMan7"));

        infoCard.setOnClickListener(v -> utils.expandCollapseView(moreInfo, expandArrow));
        getBitGapps.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://bitgapps.github.io"));
        xda.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://forum.xda-developers.com/android/software/custom-bitgapps-android-t4012165"));
        telegram.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://t.me/bitgapps_official"));
        gitHub.setOnClickListener(v -> utils.openLink(MainActivity.this, "https://github.com/BiTGApps"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        onClick();
        getText();
    }

    public String getLineContent(int line) {
        return utils.runCommand("cat /system/etc/g.prop | sed -n " + line + "p", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.closeShell();
    }
}
