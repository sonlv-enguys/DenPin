package com.sonlv.game.batruoi;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	CountDownTimer count_obj;
	ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,
			img11, img12, img13, img14, img15, img16, img17, img18, img19,
			img20, img21, img22, img23, img24;
	int x;
	int xtam_old;
	boolean flag, flag_end;
	TextView tv, scores;
	Button refresh;
	int value = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.textView1);
		scores = (TextView) findViewById(R.id.scores);
		refresh = (Button) findViewById(R.id.refresh);

		img1 = (ImageView) findViewById(R.id.imageView1);
		img2 = (ImageView) findViewById(R.id.imageView2);
		img3 = (ImageView) findViewById(R.id.imageView3);
		img4 = (ImageView) findViewById(R.id.imageView4);

		img5 = (ImageView) findViewById(R.id.imageView5);
		img6 = (ImageView) findViewById(R.id.imageView6);
		img7 = (ImageView) findViewById(R.id.imageView7);
		img8 = (ImageView) findViewById(R.id.imageView8);

		img9 = (ImageView) findViewById(R.id.imageView9);
		img10 = (ImageView) findViewById(R.id.imageView10);
		img11 = (ImageView) findViewById(R.id.imageView11);
		img12 = (ImageView) findViewById(R.id.imageView12);

		img13 = (ImageView) findViewById(R.id.imageView13);
		img14 = (ImageView) findViewById(R.id.imageView14);
		img15 = (ImageView) findViewById(R.id.imageView15);
		img16 = (ImageView) findViewById(R.id.imageView16);

		img17 = (ImageView) findViewById(R.id.imageView17);
		img18 = (ImageView) findViewById(R.id.imageView18);
		img19 = (ImageView) findViewById(R.id.imageView19);
		img20 = (ImageView) findViewById(R.id.imageView20);

		img21 = (ImageView) findViewById(R.id.imageView21);
		img22 = (ImageView) findViewById(R.id.imageView22);
		img23 = (ImageView) findViewById(R.id.imageView23);
		img24 = (ImageView) findViewById(R.id.imageView24);

		startGame();
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startGame();
			}
		});

	}

	public void startGame() {
		final ImageView mang[] = { img1, img2, img3, img4, img5, img6, img7,
				img8, img9, img10, img11, img12, img13, img14, img15, img16,
				img17, img18, img19, img20, img21, img22, img23, img24 };
		final int mang_hinh[] = { R.drawable.icon_03, R.drawable.icon_17,
				R.drawable.icon_06, R.drawable.icon_12, R.drawable.icon_26,
				R.drawable.icon_42, R.drawable.icon_09, R.drawable.icon_24,
				R.drawable.icon_38, R.drawable.icon_40, R.drawable.icon_115,
				R.drawable.icon_118, R.drawable.icon_114, R.drawable.icon_116,
				R.drawable.icon_119, R.drawable.icon_121, R.drawable.icon_123,
				R.drawable.icon_122, R.drawable.icon_120, R.drawable.icon_124,
				R.drawable.icon_127, R.drawable.icon_129, R.drawable.icon_125,
				R.drawable.icon_126 };

		for (int i = 0; i < mang.length; i++) {
			mang[i].setImageResource(mang_hinh[i]);
		}

		flag = false;
		flag_end = true;
		scores.setText("0");
		value = 0;
		scores.setVisibility(View.VISIBLE);
		new CountDownTimer(60000, 600) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if (flag_end == true) {
					if (flag == true) {
						mang[xtam_old].setImageResource(mang_hinh[xtam_old]);
						mang[xtam_old]
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub

									}
								});
					}

					Random ran = new Random();
					x = ran.nextInt(24);

					mang[x].setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							value = value + 10;
							scores.setText(value + "");

						}
					});

					mang[x].setImageResource(R.drawable.icon_25);
					xtam_old = x;
					flag = true;

					tv.setText("" + formatTime(millisUntilFinished));
				}

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if (flag_end == true && value < 50) {
					for (int i = 0; i < mang.length; i++) {
						mang[i].setImageResource(R.drawable.icon111);
					}
					tv.setText("Hết giờ");
				} else {
					tv.setText("Chiến thắng!!!");
					scores.setVisibility(View.GONE);
					flag_end = false;
				}
			}
		}.start();
	}

	public String formatTime(long millis) {
		String output = "00:00";
		long seconds = millis / 1000;
		long minutes = seconds / 60;

		seconds = seconds % 60;
		minutes = minutes % 60;

		String sec = String.valueOf(seconds);
		String min = String.valueOf(minutes);

		if (seconds < 10)
			sec = "0" + seconds;
		if (minutes < 10)
			min = "0" + minutes;

		output = min + " : " + sec;
		return output;
	}// formatTime

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
