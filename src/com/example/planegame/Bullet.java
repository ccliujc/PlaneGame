package com.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
	//子弹的图片资源
	public Bitmap bmpBullet;
	//子弹的坐标
	public int bulletX, bulletY;
	//子弹的速度
	public int speed;
	//子弹的种类以及常量
	public int bulletType;
	//主角的
	public static final int BULLET_PLAYER = -1;
	//鸭子的
	public static final int BULLET_DUCK = 1;
	//苍蝇的
	public static final int BULLET_FLY = 2;
	//Boss的
	public static final int BULLET_BOSS = 3;
	//子弹是否超屏，优化处理
	public boolean isDead;
	
	//子弹的构造函数
	public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType) {
		this.bmpBullet = bmpBullet;
		this.bulletX = bulletX;
		this.bulletY = bulletY;
		this.bulletType = bulletType;
		//不同的子弹类型速度不同
		switch(bulletType) {
		case BULLET_PLAYER:
			speed = 4;
			break;
		case BULLET_DUCK:
			speed = 3;
			break;
		case BULLET_FLY:
			speed = 4;
			break;
		case BULLET_BOSS:
			speed = 5;
			break;
		}
	}
	//子弹的绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpBullet, bulletX, bulletY,paint);
	}
	//子弹的逻辑
	public void logic() {
		//不同的子弹类型速度不同
		//主角的子弹垂直向上运动
		switch(bulletType) {
		case BULLET_PLAYER: 
			bulletY -= speed;
			if(bulletY <= -50) {
				isDead = true;
			}
			break;
		//鸭子河苍蝇的子弹都是垂直向下落运动
		case BULLET_DUCK:
		case BULLET_FLY:
			bulletY += speed;
			if(bulletY >= MySurfaceView.screenH) {
				isDead = true;
			}
			break;
		case BULLET_BOSS:
			//Boss的子弹逻辑待实现
			break;
		}
	}
}

