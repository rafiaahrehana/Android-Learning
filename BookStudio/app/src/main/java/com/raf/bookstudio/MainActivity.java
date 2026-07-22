package com.raf.bookstudio;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 2200;
    private static final long ANIM_DURATION_MS = 550;
    private static final long ANIM_START_DELAY_MS = 150;

    private ImageView logo;
    private TextView appName;
    private Runnable navigateToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.bslogosquare);
        appName = findViewById(R.id.tvAppName);

        animateSplashIn();

        navigateToLogin = () -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        };

        logo.postDelayed(navigateToLogin, SPLASH_DELAY_MS);
    }

    private void animateSplashIn() {
        logo.setScaleX(0f);
        logo.setScaleY(0f);
        logo.setAlpha(0f);

        logo.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setStartDelay(ANIM_START_DELAY_MS)
                .setDuration(ANIM_DURATION_MS)
                .setInterpolator(new OvershootInterpolator(1.1f))
                .start();

        if (appName != null) {
            appName.setAlpha(0f);
            appName.animate()
                    .alpha(1f)
                    .setStartDelay(ANIM_START_DELAY_MS + ANIM_DURATION_MS / 2)
                    .setDuration(ANIM_DURATION_MS)
                    .start();
        }
    }

    @Override
    protected void onDestroy() {
        if (logo != null && navigateToLogin != null) {
            logo.removeCallbacks(navigateToLogin);
        }
        super.onDestroy();
    }
}