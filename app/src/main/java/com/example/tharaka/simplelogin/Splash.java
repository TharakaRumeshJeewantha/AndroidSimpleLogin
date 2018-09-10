package com.example.tharaka.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
//private TextView tv;
private ImageView iv;

@Override
    protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);

    iv = (ImageView) findViewById(R.id.iv);
    Animation myanim = AnimationUtils.loadAnimation(this,R.anim.transition);

    iv.startAnimation(myanim);
    final Intent i = new Intent(this,MainActivity.class);
    Thread timer = new Thread() {
        public void run() {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                startActivity(i);
                finish();
            }
        }
    };
    timer.start();
}
}
