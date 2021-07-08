package android.com.jumpco.io.breathapp;

import android.com.jumpco.io.breathapp.utils.Prefs;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

        private ImageView imageView;
        private TextView breathsTxt,timeTxt,sessionTxt,guideTxt;
        private Button startButton;
        private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        breathsTxt = findViewById(R.id.breathYakenTxt);
        timeTxt = findViewById(R.id.lessBreathTxt);
        sessionTxt = findViewById(R.id.todayMinutesTxt);
        guideTxt = findViewById(R.id.guideTxt);
        prefs = new Prefs(this);

        startIntroAnimation();

        //#CHALANGE
        //1.Make it so that each time users complete a session, the app plays a sound.
        //2. Use the quote API from previous section and display quotes between breathing
        // sessions. Each time, shpw a different quite and its author.
        //a. Share your new improved app with us in the discussion forums.
        sessionTxt.setText(MessageFormat.format("{0} min today", prefs.getSessions()));

        breathsTxt.setText((MessageFormat.format("{0} Breaths", prefs.getBreaths())));

        timeTxt.setText(prefs.getDate());



       startButton = findViewById(R.id.startButton);
       startButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startAnimation();

           }
       });

    }

    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breath");
                    }
                })
                .start();

    }

    private void startAnimation(){

        ViewAnimator
                .animate(imageView)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhlale...Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
             //   .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions()+1);
                        prefs.setBreaths(prefs.getBreaths()+1);
                        prefs.setDate(System.currentTimeMillis());

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long l) {
                                //put code to show ticking...1, 2, 3

                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
                .start();

    }
}
