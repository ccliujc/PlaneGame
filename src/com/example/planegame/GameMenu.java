package com.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {
	//菜单背景
	private Bitmap bmpMenu;
	private Bitmap bmpButton, bmpButtonPress;
	//按钮的坐标
	private int btnX,btnY;
	//按钮是否按下标识位
	private Boolean isPress;
	//菜单初始化
	public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress) {
		this.bmpMenu = bmpMenu;
		this.bmpButton = bmpButton;
		this.bmpButtonPress = bmpButtonPress;
		//X剧中，Y紧接屏幕底部
		btnX = MySurfaceView.screenW/2 - bmpButton.getWidth()/2;
		btnY = MySurfaceView.screenH - bmpButton.getHeight();
		isPress = false;
	}
	public void draw(Canvas canvas, Paint paint) {
		//这里的canvas不用this.canvas = canvas;吗？
		
		/*//因为图片不够大所以放大一些  但是放大好像并不能解决这个问题啊
		canvas.save();
		canvas.scale(1f, 1f, MySurfaceView.screenW/2 + bmpMenu.getWidth()/2,  MySurfaceView.screenH/2 + bmpMenu.getHeight()/2);
		//y方向放大1.2倍绘制背景图片
		canvas.drawBitmap(bmpMenu,0, 0, paint);
		canvas.restore();*/
		
		//绘制背景图片
				canvas.drawBitmap(bmpMenu,0, 0, paint);
		
		//绘制为按下按钮图
		if(isPress) {//根据是否按下绘制不同状态的按钮图
			canvas.drawBitmap(bmpButtonPress,btnX,btnY,paint);
		} else if(!isPress) {
			canvas.drawBitmap(bmpButton, btnX, btnY, paint);
		}
	}
	//菜单触屏实践函数，主要用于处理按钮事件
	public void onTouchEvent(MotionEvent event) {
		//获取用户当前触屏位置
		int pointX = (int)event.getX();
		int pointY = (int)event.getY();
		//当用户按下动作或移动动作
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			//判断用户是否点击了按钮
			if(pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
				if(pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
					isPress = true;
					
				}else {
					isPress = false;
				}
			} else {
				isPress = false;
			}
			//当前用户是抬起动作
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
			//抬起判断是否点击按钮，防止用户移动到别处
			if(pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
				if(pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
					//还原Button状态为为按下状态
					isPress = false;
					//改变当前游戏状态为开始游戏
					MySurfaceView.gameState = MySurfaceView.GAMEING;
				}
			}
		}
		
	}

}
