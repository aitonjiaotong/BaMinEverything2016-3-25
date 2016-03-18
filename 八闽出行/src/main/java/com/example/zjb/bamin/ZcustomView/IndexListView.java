package com.example.zjb.bamin.ZcustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class IndexListView extends View
{

	private Paint paint;
	private boolean isPressed;
	private int index = -1;
	private final int LETTER_LENGTH = 26;
	private final String PRIME_TEXTCOLOR = "#666666";
	private final String CHOOSE_TEXTCOLOR = "#FFCCCC";
//	private final String CHOOSE_VIEWBACKGROUD = "#7F6699CC";
	private final String CHOOSE_VIEWBACKGROUD = "#7F199989";
	private GetLetterListener listener;//回调接口

	public IndexListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor(PRIME_TEXTCOLOR));
		paint.setTextSize(20);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		int ViewHeight = getHeight();
		int ViewWidth = getWidth();
		int textHeight = ViewHeight / LETTER_LENGTH;
		canvas.drawColor(Color.parseColor("#00000000"));//背景色
		if (isPressed)
		{
			canvas.drawColor(Color.parseColor(CHOOSE_VIEWBACKGROUD));
		}
		char mChar = 'A';
		for (int i = 0; i < LETTER_LENGTH; i++)
		{
			if (index == i)
			{
				paint.setColor(Color.parseColor(CHOOSE_TEXTCOLOR));
			} else
			{
				paint.setColor(Color.parseColor(PRIME_TEXTCOLOR));
			}
			String TextContent = "" + (char) (mChar + i);
			int textWidth = (int) paint.measureText(TextContent);
			int x_draw = ViewWidth / 2 - textWidth / 2;
			int y_draw = textHeight * (i + 1);
			canvas.drawText(TextContent, x_draw, y_draw, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float y2 = event.getY();
		index = (int) (y2 * LETTER_LENGTH / getHeight());
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:

			isPressed = true;
			String letter_down = "" + (char) ('A' + index);
			this.listener.onLetterChanged(letter_down);
			Log.e("--->>Event", "按下");
			break;

		case MotionEvent.ACTION_MOVE:

			isPressed = true;
			String letter_move = "" + (char) ('A' + index);
			this.listener.onLetterChanged(letter_move);
			Log.e("--->>Event", "移动");
			break;

		case MotionEvent.ACTION_UP:

			isPressed = false;
			index = -1;
			this.listener.onActionUp();
			Log.e("--->>Event", "抬起");
			break;

		default:
			break;
		}
		invalidate();

		return true;
	}
	
	
	
	public interface GetLetterListener
	{
		//抽象方法为Activity中实现操作相关控件
		void onLetterChanged(String letter);
		void onActionUp();
	}
	
	public void setOnGetLetterListener(GetLetterListener listener)
	{
		this.listener = listener;
	}


}


