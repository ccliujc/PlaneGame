package com.example.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Boom {
	//爆炸效果资源图
	private Bitmap bmpBoom;
	//爆炸效果的位置坐标
	private int boomX, boomY;
	//爆炸动画播放当前的帧下标
	private int currentFrameIndex;
	//爆炸效果的总帧数
	private int totleFrame;
	//每帧的宽高
	private int frameW, frameH;
	//是否播放完毕，优化处理
	public boolean playEnd;
	
	//爆炸效果的构造函数
	public Boom(Bitmap bmpBoom, int x, int y, int totleFrame) {
		this.bmpBoom = bmpBoom;
		this.boomX = x;
		this.boomY = y;
		this.totleFrame = totleFrame;
		frameW = bmpBoom.getWidth() / totleFrame;
		frameH = bmpBoom.getWidth();
	}
	//爆炸效果绘制
	public void draw(Canvas canvas, Paint paint) {
		canvas.save();
		canvas.clipRect(boomX, boomY, boomX + frameW, boomY + frameH);
		canvas.drawBitmap(bmpBoom, boomX - currentFrameIndex * frameW, boomY, paint);
		canvas.restore();
	}
	//爆炸的效果逻辑
	public void logic() {
		if(currentFrameIndex < totleFrame) {
			currentFrameIndex ++;
			
		}else {
			playEnd = true;
		}
	}

}
