package com.lc.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;

public class ColorPickPoint {

	private int center_x;
	private int center_y;
	private int id;
	private int Text_Color;
	private int Main_Color;
	private int BK_color;
	private int Main_Radius;
	private int BK_Radius;
	private Paint Main_Paint;
	private Paint BK_Paint;
	private Paint Text_Paint;
	private Rect main_rect;
	private int left;
	private int top;
	private int right;
	private int bottom;
	private boolean isTouch = false;
	public static int SIZE=50;

	public ColorPickPoint() {
		center_x = 0;
		center_y = 0;
		id = 0;
		Text_Color = Color.GREEN;
		Main_Color = Color.RED;
		BK_color = Color.BLACK;
		Main_Radius = 35;
		BK_Radius = SIZE;
		init();
	}

	private void init() {

		left = center_x - BK_Radius;
		right = center_x + BK_Radius;

		top = center_y - BK_Radius;
		bottom = center_y + BK_Radius;

		main_rect = new Rect(left, top, right, bottom);

		Main_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Main_Paint.setColor(Main_Color);
//		Main_Paint.setAlpha(0x50);

		BK_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		BK_Paint.setColor(BK_color);
		BK_Paint.setAlpha(0x50);

		Text_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Text_Paint.setShadowLayer(Main_Radius, center_x, center_y, Text_Color);
		Text_Paint.setTextAlign(Align.CENTER);
		Text_Paint.setTextSize(Main_Radius);
		Text_Paint.setColor(Text_Color);
	}

	private void initRect() {
		main_rect.left = center_x - BK_Radius;
		main_rect.right = center_x + BK_Radius;

		main_rect.top = center_y - BK_Radius;
		main_rect.bottom = center_y + BK_Radius;
	}

	public void DrawPoint(Canvas canvas) {
	//	canvas.translate(center_x, center_y);

		canvas.drawCircle(center_x, center_y, BK_Radius, BK_Paint);

		canvas.drawCircle(center_x, center_y, Main_Radius, Main_Paint);

	//	canvas.drawText(String.valueOf(id), center_x, center_y, Text_Paint);
	}

	public int getCenter_x() {
		return center_x;
	}

	public void setCenter_x(int center_x) {
		this.center_x = center_x;
		initRect();
	}

	public int getCenter_y() {
		return center_y;
	}

	public void setCenter_y(int center_y) {
		this.center_y = center_y;
		initRect();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getText_Color() {
		return Text_Color;
	}

	public void setText_Color(int text_Color) {
		Text_Color = text_Color;
		Text_Paint.setColor(Text_Color);
	}

	public int getMain_Color() {
		return Main_Color;
	}

	public void setMain_Color(int main_Color) {
		Main_Color = main_Color;
		Main_Paint.setColor(Main_Color);
	}

	public int getBK_color() {
		return BK_color;
	}

	public void setBK_color(int bK_color) {
		BK_color = bK_color;
		BK_Paint.setColor(bK_color);
	}

	public int getMain_Radius() {
		return Main_Radius;
	}

	public void setMain_Radius(int main_Radius) {
		Main_Radius = main_Radius;
		initRect();
	}

	public int getBK_Radius() {
		return BK_Radius;
	}

	public void setBK_Radius(int bK_Radius) {
		BK_Radius = bK_Radius;
		initRect();
	}

	public Paint getMain_Paint() {
		return Main_Paint;
	}

	public void setMain_Paint(Paint main_Paint) {
		Main_Paint = main_Paint;
	}

	public Paint getBK_Paint() {
		return BK_Paint;
	}

	public void setBK_Paint(Paint bK_Paint) {
		BK_Paint = bK_Paint;
	}

	public Paint getText_Paint() {
		return Text_Paint;
	}

	public void setText_Paint(Paint text_Paint) {
		Text_Paint = text_Paint;
	}

	public Rect getMain_rect() {
		return main_rect;
	}

	public boolean isTouch() {
		return isTouch;
	}

	public void setTouch(boolean isTouch) {
		this.isTouch = isTouch;
	}

}
