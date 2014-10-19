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
	
	//Boss疯狂状态下子弹相关成员变量
	private int dir;//当前Boss子弹方向
	//8方向常量
	public static final int DIR_UP = -1;
	public static final int DIR_DOWN = 2;
	public static final int DIR_LEFT = 3;
	public static final int DIR_RIGHT = 4;
	public static final int DIR_UP_RIGHT = 5;
	public static final int DIR_UP_LEFT = 6;
	public static final int DIR_DOWN_LEFT = 7;
	public static final int DIR_DOWN_RIGHT = 8;
	
	
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
	
	/**
	 * 专用于处理Boss疯狂状态下创建的子弹
	*@param bmpBullet
	*@param bulletX
	*@param bulletY
	*@param bulletType
	*@param Dir
	*/
	
	public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType, int dir) {
		this.bmpBullet = bmpBullet;
		this.bulletX = bulletX;
		this.bulletY = bulletY;
		this.bulletType = bulletType;
		speed = 5;
		this.dir = dir;
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
			//Boss疯狂状态下的子弹逻辑待实现
			switch(dir) {
			//各方向上的子弹
			case DIR_UP:
				bulletY -= speed;
				break;
			case DIR_DOWN:
				bulletY += speed;
				break;
			case DIR_LEFT:
				bulletX -= speed;
				break;
			case DIR_RIGHT:
				bulletX += speed;
				break;
			case DIR_UP_LEFT:
				bulletY -= speed;
				bulletX -= speed;
				break;
			case DIR_UP_RIGHT:
				bulletY -= speed;
				bulletX += speed;
				break;
			case DIR_DOWN_LEFT:
				bulletY += speed;
				bulletX -= speed;
				break;
			case DIR_DOWN_RIGHT:
				bulletY += speed;
				bulletX += speed;
				break;
			}
			break;
		}
	}
}

