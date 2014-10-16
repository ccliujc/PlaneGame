package com.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Player {
	// 主角的血量与血量位图
	// 默认3血
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	// 主角的坐标以及位图
	public int x, y;
	private Bitmap bmpPlayer;
	// 主角移动速度
	private int speed = 5;
	// 主角的移动标识
	private Boolean isUp, isDown, isLeft, isRight;

	// 主角的构造函数
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight();
	}

	// 主角的绘图函数
	public void draw(Canvas canvas, Paint paint) {
		// 绘制主角
		canvas.drawBitmap(bmpPlayer, x, y, paint);
		// 绘制主角血量
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(),
					MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);

		}
	}

	public void onTouchEvent(MotionEvent event) {
		int pointX = (int)event.getX();
		int pointY = (int)event.getY();
		
		if (event.getAction() == MotionEvent.ACTION_MOVE
				|| event.getAction() == MotionEvent.ACTION_DOWN) {
			if ((int) event.getX() < x) {
				isLeft = true;
			} else if ((int) event.getX() > x) {
				isRight = true;
			}
			if ((int) event.getY() < y) {
				isUp = true;
			} else if ((int) event.getY() < y) {
				isDown = true;
			}
		}else if(event.getAction() == MotionEvent.ACTION_UP) {
			isLeft = false;
			isRight = false;
			isUp = false;
			isDown= false;
		}
		
		
	}

	public void logic() {
		// 处理主角移动
		if (isLeft) {
			x -= speed;
		}
		if (isRight) {
			x += speed;
		}
		if (isUp) {
			y -= speed;
		}
		if (isDown) {
			y += speed;
		}
		// 判断屏幕X的边界
		if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
			x = MySurfaceView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		// 判断屏幕的Y边界
		if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
			y = MySurfaceView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}

	}

	// 设置主角血量
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}
	//获取主角血量
	public int getPlayerHp() {
		return playerHp;
	}

}
