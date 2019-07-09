package com.foodmile.livraison.Livraison.Activitieslogin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodmile.livraison.R;

public class ScreenActivity extends AppCompatActivity {
    ImageView moto;
    TextView slog;
    Animation motoanim,sloganim;
    private static int SPLASH_TIME = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Typeface mycusto =Typeface.createFromAsset(getAssets(),"fonts/text.otf");
        moto = (ImageView) findViewById(R.id.test);
        slog = (TextView) findViewById(R.id.slogon);
        slog.setTypeface(mycusto);

        motoanim = AnimationUtils.loadAnimation(this,R.anim.livreuranim);
        sloganim = AnimationUtils.loadAnimation(this,R.anim.motoanim);
        moto.setAnimation(motoanim);
        slog.setAnimation(sloganim);


        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(ScreenActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                 *  from Home page which is ActivityHome
                 */
                finish();
            }
        }, SPLASH_TIME);
    }
}
