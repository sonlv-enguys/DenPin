package com.game.behoctoan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;

import java.util.Random;


public class MainActivity extends Activity {
    Animation animRightToCenter, animCenterToLeft;
    LinearLayout layoutBieuThuc;
    ProgressBar progressBar;
    TextView txt0, txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txtNumber1, txtNumber2, txtMath, txtValue, txtScore, txtStart;
    int number1 = 0;
    int number2 = 0;
    Random random;
    int value = 0;
    int firstValue = 0;
    int max = 20;
    int score = 0;
    int lever = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        animRightToCenter = new TranslateAnimation(size.x, -layoutBieuThuc.getX(), 0, 0);
        animRightToCenter.setDuration(1000);
        animRightToCenter.setFillAfter(false);

        animCenterToLeft = new TranslateAnimation(layoutBieuThuc.getX(), -size.x, 0, 0);
        animCenterToLeft.setDuration(1000);
        animCenterToLeft.setFillAfter(false);
        AnimationListener rightToCenterListener = new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                playMediaNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == animRightToCenter) {
                    startTime();
                }

            }
        };
        AnimationListener centerToLeftListener = new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == animCenterToLeft) {
                    genNumber();
                }

            }
        };
        animRightToCenter.setAnimationListener(rightToCenterListener);
        animCenterToLeft.setAnimationListener(centerToLeftListener);
        random = new Random();
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        genNumber();

    }

    CountDownTimer countDownTimer;
    int totalTime = 3000;

    public void startTime() {
        next = false;
        if (score > 0) {
            progressBar.setProgress(100);
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            countDownTimer = new CountDownTimer(totalTime, 1) {
                public void onTick(long millisUntilFinished) {
                    progressBar.setProgress(Math.round(100.0f
                            * millisUntilFinished / totalTime));
                }

                public void onFinish() {
                    progressBar.setProgress(0);
                    if (!next) {
                        playMediaError();
                    }

                }
            }.start();
        }
    }

    public void setupView() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = 0;
                switch (v.getId()) {
                    case R.id.txt0:
                        newValue = 0;
                        break;
                    case R.id.txt1:
                        newValue = 1;
                        break;
                    case R.id.txt2:
                        newValue = 2;
                        break;
                    case R.id.txt3:
                        newValue = 3;
                        break;
                    case R.id.txt4:
                        newValue = 4;
                        break;
                    case R.id.txt5:
                        newValue = 5;
                        break;
                    case R.id.txt6:
                        newValue = 6;
                        break;
                    case R.id.txt7:
                        newValue = 7;
                        break;
                    case R.id.txt8:
                        newValue = 8;
                        break;
                    case R.id.txt9:
                        newValue = 9;
                        break;
                }
                if (mValue >= value) {
                    mValue = 0;
                }
                if (mValue == 0) {
                    mValue = newValue;
                } else {
                    mValue = mValue * 10 + newValue;
                }
                checkIngame(newValue);

            }
        };
        layoutBieuThuc = (LinearLayout) findViewById(R.id.layout_bieu_thuc);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        txt0 = (TextView) findViewById(R.id.txt0);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);


        txtNumber1 = (TextView) findViewById(R.id.txt_number_1);
        txtNumber2 = (TextView) findViewById(R.id.txt_number_2);
        txtValue = (TextView) findViewById(R.id.txt_value);
        txtMath = (TextView) findViewById(R.id.txt_math);
        txtScore = (TextView) findViewById(R.id.txt_score);
        txtStart = (TextView) findViewById(R.id.txt_start);

        txt0.setOnClickListener(onClickListener);
        txt1.setOnClickListener(onClickListener);
        txt2.setOnClickListener(onClickListener);
        txt3.setOnClickListener(onClickListener);
        txt4.setOnClickListener(onClickListener);
        txt5.setOnClickListener(onClickListener);
        txt6.setOnClickListener(onClickListener);
        txt7.setOnClickListener(onClickListener);
        txt8.setOnClickListener(onClickListener);
        txt9.setOnClickListener(onClickListener);


    }

    Boolean next = false;

    public void checkIngame(int newValue) {
        if (value == newValue) {
            next = true;
            txtValue.setText(newValue + "");
        }
        if (value == mValue) {
            next = true;
            txtValue.setText(mValue + "");
        }
        Log.d("genNumber", "" + mValue);
        if (next) {
            score = score + 1;
            txtScore.setText(score + "");

            layoutBieuThuc.startAnimation(animCenterToLeft);
            if (score % 10 == 0) {
                lever++;
            }
        }

    }

    int mValue = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void genNumber() {

        if (max < 100) {
            max = lever * 10;
        }
        value = randInt(max - 9, max);
        while (value < 2 || value == firstValue) {
            value = randInt(max - 9, max);
        }
        Log.d("genNumber", "" + value);
        int number1 = randInt(max - 9, value);
        while (number1 == value) {
            number1 = randInt(max - 9, max);
        }
        int number2 = value - number1;
        txtMath.setText("+");
        if (number1 > value) {
            txtMath.setText("-");
            number2 = number1 - value;
        }
        if (lever > 5 && random.nextBoolean()) {
            number1 = randInt(1, 9);
            number2 = randInt(1, 9);
            value = number1 * number2;
            txtMath.setText("x");
            if (lever > 10 && random.nextBoolean()) {
                int temp1 = number1;
                number1 = value;
                value = temp1;
                txtMath.setText(":");
            }
        }
        txtNumber1.setText("" + number1);
        txtNumber2.setText("" + number2);
        txtValue.setText("=?");
        firstValue = value;
        mValue = 0;
        layoutBieuThuc.startAnimation(animRightToCenter);
    }

    public void playMediaError() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.error);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    public void playMediaNext() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.next);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }

    AudioManager audio;

    public void updateVolume() {
        audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                audio.getStreamVolume(AudioManager.STREAM_RING),
                AudioManager.FLAG_VIBRATE);
    }


}
