package com.hungle.findingcolor;

import java.util.Random;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mView {
	FindColor findcolor;
	private int n, index;
	private int color1, color2, color3;
	private static ImageView[] array_icon;
	private TextView score, highscore;
	public static SoundPool sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
	private int soundIds[] = new int[3];

	public mView(FindColor findcolor) {
		this.findcolor = findcolor;
	}

	public void Show() {
		init();
		createGamePlay();

	}

	private void init() {
		Data.check_pause = false;
		n = random(5, 4);
		index = random(n * n, 0);
		array_icon = new ImageView[n * n];
		color1 = random(255, 0);
		color2 = random(255, 0);
		color3 = random(255, 0);

		soundIds[0] = sp.load(findcolor, R.raw.click, 1);

		score = (TextView) findcolor.findViewById(R.id.tv_score);
		String s = String.format("%02d", Data._score);
		score.setText("" + s);

		highscore = (TextView) findcolor.findViewById(R.id.tv_hscore);
	}

	@SuppressWarnings("deprecation")
	private void DrawBoard(LinearLayout l) {
		int size = 400;
		Bitmap view = Bitmap.createBitmap(400, 400, Config.ARGB_8888);
		Canvas c = new Canvas(view);
		Paint p = new Paint();
		p.setAntiAlias(true);

		p.setColor(Color.parseColor("#dddddd"));

		RectF r1 = new RectF(0, 0, size, size);
		c.drawRoundRect(r1, 7, 7, p);
		l.setBackgroundDrawable(new BitmapDrawable(findcolor.getResources(),
				view));
	}

	private void DrawIcon(ImageView image) {
		int size = 400 / n - 10;
		Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas c = new Canvas(bitmap);
		Paint p = new Paint();
		p.setAntiAlias(true);

		p.setColor(Color.rgb(color1, color2, color3));
		RectF r = new RectF(0, 0, size, size);
		c.drawRoundRect(r, 5, 5, p);
		image.setImageBitmap(bitmap);
	}

	private void Draw_Icon(ImageView image) {
		int size = 400 / n;
		Bitmap view = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas c = new Canvas(view);
		Paint p = new Paint();
		p.setAntiAlias(true);

		p.setColor(Color.rgb(color1 + 20, color2 + random(10, 10), color3));

		RectF r1 = new RectF(0, 0, size, size);
		c.drawRoundRect(r1, 5, 5, p);
		image.setImageBitmap(view);
	}

	private void createGamePlay() {
		final LinearLayout show_view = (LinearLayout) findcolor
				.findViewById(R.id.show_view);
		final LinearLayout show_board = (LinearLayout) findcolor
				.findViewById(R.id.show_board);
		DrawBoard(show_board);
		LinearLayout[] layouts = new LinearLayout[n];
		LinearLayout.LayoutParams layout_param = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT, 1);
		layout_param.setMargins(2, 2, 2, 2);
		for (int i = 0; i < n; i++) {
			layouts[i] = new LinearLayout(findcolor);
			layouts[i].setGravity(Gravity.CENTER_HORIZONTAL);
			layouts[i].setOrientation(LinearLayout.HORIZONTAL);
			layouts[i].setLayoutParams(layout_param);
			show_view.addView(layouts[i]);
		}

		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int myId = v.getId();

				if (myId == index && !Data.check_pause) {
					sp.play(soundIds[0], 1.0f, 1.0f, 1, 0, 1.0f);

					for (int i = 0; i < n * n; i++) {
						if (i == index)
							startAnimationthis(array_icon[i]);
						else
							startAnimation1(array_icon[i]);
					}

					Data._score++;
					String s = String.format("%02d", Data._score);
					score.setText("" + s);
					SaveHighScore();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							show_view.removeAllViews();
							Show();
						}
					}, 500);

				} else {
					startAnimationthis(array_icon[myId]);
					// sp.play(soundIds[0], 1.0f, 1.0f, 1, 0, 1.0f);
				}
			}
		};

		for (int j = 0; j < n * n; j++) {
			int y = j / n;
			array_icon[j] = new ImageView(findcolor);
			array_icon[j].setLayoutParams(layout_param);
			array_icon[j].setId(j);
			array_icon[j].setOnClickListener(ocl);
			if (j == index) {
				Draw_Icon(array_icon[j]);
			} else {
				DrawIcon(array_icon[j]);
			}
			layouts[y].addView(array_icon[j]);

		}
	}

	private void startAnimation1(ImageView iv) {
		Animation anime;
		int u = random(6, 1);
		if (u == 1) {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.slideup);
		} else if (u == 2) {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.slidedown);
		} else if (u == 3) {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.slideleft);
		} else if (u == 4) {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.slideright);
		} else if (u == 5) {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.fadeout);
		} else {
			anime = AnimationUtils.loadAnimation(findcolor, R.anim.rotate);
		}
		iv.startAnimation(anime);
	}

	private void startAnimationthis(ImageView iv) {
		Animation anime = AnimationUtils.loadAnimation(findcolor, R.anim.scale);
		iv.startAnimation(anime);
	}

	private int random(int u, int v) {
		Random r = new Random();
		return (int) (Math.abs(r.nextInt()) % u + v);
	}

	public void SaveHighScore() {
		SharedPreferences sharedPref = findcolor.getSharedPreferences(
				"HighScore", Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		if (Data._score > Data._highscore) {
			editor.putInt("highscore", Data._score);
			String s = String.format("%02d", Data._score);
			highscore.setText("" + s);
		}
		editor.commit();
	}
}
