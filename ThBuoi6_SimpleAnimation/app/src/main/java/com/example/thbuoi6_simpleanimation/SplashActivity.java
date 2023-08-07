package com.example.thbuoi6_simpleanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {
    ImageView ivTop, ivBottom, ivMoon, ivBeat;
    TextView txtView;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AnhXa();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animationTop = AnimationUtils.loadAnimation(this,
                R.anim.top_wave);

        ivTop.setAnimation(animationTop);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivMoon,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        );

        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();

        Glide.with(this).load("https://i.giphy.com/lo90HBZzaM7W8Lg5L0.gif")
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivBeat);

        Animation animationBottom = AnimationUtils.loadAnimation(this,
                R.anim.bot_wave);
        ivBottom.setAnimation(animationBottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 4000);
    }


    private void AnhXa() {
        ivTop = findViewById(R.id.ivTop);
        ivBottom = findViewById(R.id.ivBottom);
        ivMoon = findViewById(R.id.ivMoon);
        txtView = findViewById(R.id.txtView);
        ivBeat = findViewById(R.id.ivBeat);
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            txtView.setText(charSequence.subSequence(0, index++));
            if(index <= charSequence.length()) {
                handler.postDelayed(runnable, delay);
            }
        }
    };

    public void animaText(CharSequence cs) {
        charSequence = cs;
        index = 0;
        txtView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }
}