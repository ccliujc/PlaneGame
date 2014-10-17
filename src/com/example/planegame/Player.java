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
	private boolean isUp, isDown, isLeft, isRight;
	
	//
	private int pointX, pointY;
	
	//碰撞后处于无敌时间
	//计时器
	private int noCollisionCount = 0;
	//因为无敌时间
	private int noCollisionTime = 60;
	//是否批碰撞的标识位
	private boolean isCollision;

	// 主角的构造函数
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MySurfaceView.screenH - bmpPlayer.getHeight();
		
		pointX = x;
		pointY = y;
		
		//加了下面这句 解决了 运行程序时 程序崩溃的问题
		isUp = isDown = isLeft = isRight = false;
	}

	// 主角的绘图函数
	public void draw(Canvas canvas, Paint paint) {
		// 绘制主角
		//当处于无敌状态时，让主角闪烁
		if(isCollision) {
			//每2次游戏循环，绘制一次主角
			if(noCollisionCount % 2 == 0) {
				canvas.drawBitmap(bmpPlayer, x, y, paint);
			}
		} else {
		canvas.drawBitmap(bmpPlayer, x, y, paint);
		}
		// 绘制主角血量
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(),
					MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);

		}
		
		/*//测试用
		canvas.drawText("x = " + x + "	y = " + y, 20, 100, paint);
		canvas.drawText("pointX = " + pointX + "	pointY = " + pointY, 20, 200, paint);*/
	}

	public void onTouchEvent(MotionEvent event) {
		pointX = (int) event.getX() - bmpPlayer.getWidth() / 2;
		pointY = (int) event.getY() - bmpPlayer.getHeight() ;

		if (pointX < x ) {
			isLeft = true;

		} else if (pointX > x ) {
			isRight = true;
		}
		if (pointY < y ) {
			isUp = true;
		} else if (pointY > y ) {
			isDown = true;
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			speed = 9;
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			speed = 5;
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			isLeft = false;
			isRight = false;
			isUp = false;
			isDown = false;
		}

	}

	public void logic() {
		// 处理主角移动
		if (isLeft || isRight || isUp || isDown) {
			if (x > pointX && x + speed > pointX)
				x -= speed;

			if (x < pointX && x - speed < pointX)
				x += speed;

			if (y < pointY && y - speed < pointY)
				y += speed;

			if (y > pointY && y + speed > pointY)
				y -= speed;
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
		
		//处理无敌状态
		if(isCollision) {
			//计数器开始计时
			noCollisionCount ++;
			if(noCollisionCount >= noCollisionTime) {
				//无敌时间过后，解除无敌状态及初始化计数器
				isCollision = false;
				noCollisionCount = 0;
			}
			
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
	
	// 判断碰撞（主角与敌机）
	public boolean isCollsionWith(Enemy en) {
		// 是否处于无敌时间
		if (isCollision == false) {
			int x2 = en.x;
			int y2 = en.y;
			int w2 = en.frameW;
			int h2 = en.frameH;
			if (x > x2 && x >= x2 + w2) {
				return false;
			} else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
				return false;
			} else if (y >= y2 && y >= y2 + h2) {
				return false;
			} else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
				return false;
			}
			// 碰撞及进入无敌状态
			isCollision = true;
			return true;
			// 处于无敌状态，无视碰撞
		} else {
			return false;
		}
	}
}
