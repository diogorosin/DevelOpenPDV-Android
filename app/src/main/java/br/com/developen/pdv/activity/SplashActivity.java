package br.com.developen.pdv.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;

import br.com.developen.pdv.R;
import br.com.developen.pdv.utils.Constants;


public class SplashActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ProgressBar spinner = findViewById(R.id.activity_splash_progressbar);

        spinner.getIndeterminateDrawable().setColorFilter(R.color.colorWhite, PorterDuff.Mode.DST);

        Handler handle = new Handler();

        handle.postDelayed(new Runnable() {

            public void run() {

                Intent intent;

                SharedPreferences preferences = getSharedPreferences(
                        Constants.SHARED_PREFERENCES_NAME, 0);

                if (preferences.getBoolean(Constants.DEVICE_CONFIGURED_PROPERTY, false)) {

                    if (preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0) == 0) {

                        intent = new Intent(SplashActivity.this, LoginActivity.class);

                    } else {

                        intent = new Intent(SplashActivity.this, MainActivity.class);

                    }

                } else {

                    intent = new Intent(SplashActivity.this, AccountActivity.class);

                }

                startActivity(intent);

                finish();

            }

        }, 2000);

    }

}