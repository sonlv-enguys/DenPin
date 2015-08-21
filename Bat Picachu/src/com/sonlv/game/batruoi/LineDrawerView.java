package com.sonlv.game.batruoi;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class LineDrawerView extends View {
	public LineDrawerView(Context context,AttributeSet attrs) {
		super(context,attrs);
	}

	ArrayList<Rect> lines = new ArrayList<Rect>();
	int x1, y1;
	int x2, y2;
	boolean drawing = false;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		canvas.drawColor(Color.YELLOW);

		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setDither(true);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeJoin(Paint.Join.ROUND);
		p.setStrokeCap(Paint.Cap.ROUND);

		p.setColor(Color.GRAY);
		p.setStrokeWidth(20);

		for (int i = 0; i < lines.size(); i++) {
			Rect currline = lines.get(i);
			canvas.drawLine(currline.left, currline.top, currline.right,
					currline.bottom, p);
		}
		canvas.drawLine(x1, y1, x2, y2, p);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x1 = x2 = (int) event.getX();
			y1 = y2 = (int) event.getY();
			drawing = true;
			result = true;
			break;
		case MotionEvent.ACTION_MOVE:
			x2 = (int) event.getX();
			y2 = (int) event.getY();
			result = true;
			break;
		case MotionEvent.ACTION_UP:
			x2 = (int) event.getX();
			y2 = (int) event.getY();
			lines.add(new Rect(x1, y1, x2, y2));
			drawing = false;
			result = true;
			break;
		}

		invalidate();
		return result;
	}
}
