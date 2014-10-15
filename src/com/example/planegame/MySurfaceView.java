package com.example.planegame;

import com.planegame.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;



public class MySurfaceView extends SurfaceView implements Runnable, Callback {
	private int screenW, screenH;
	private boolean flag;
	
	private Paint paint;
	private Canvas canvas;
	private SurfaceHolder sfh;
	
	private Thread th;
	
	//定义游戏状态常量
	public static final int GAME_MENU = 0;//游戏菜单 开始界面
	public static final int GAMEING = 1;//游戏中
	public static final int GAME_WIN = 2;//游戏胜利
	public static final int GAME_LOSE = 3;//游戏失败
	public static final int GAME_PAUSE = -1;//游戏菜单 游戏暂停时菜单
	//当前游戏状态（默认初始在游戏菜单界面）
	public static final int gameState = GAME_MENU;
	

	public MySurfaceView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setTextSize(40);
		
		//实例化canvas 不知道需不需要要 例子中都没有加
		canvas = new Canvas();
		
		//总写错的说
		sfh = this.getHolder();
		sfh.addCallback(this);
		
		//设置焦点  总忘这一句 虽然也不知道 有没有影响
		setFocusable(true);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		
		flag = true;
		//括号里没加this导致run()不刷新！！！
		th = new Thread(this);
		th.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;
	}

	@Override
	public void run() {
		while(flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			if((end - start) < 50)
				try {
					Thread.sleep(50 - (end - start));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private void logic() {
		//逻辑处理根据游戏状态不同进行不同处理
		switch(gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			break;
		case GAME_WIN:
			break;
		case GAME_LOSE:
			break;
		}
	}

	private void myDraw() {
		try {

			canvas = sfh.lockCanvas();
			if (canvas != null) {

				// 这里明明设置的是WHITE可不知道为什么显示出来的缺少BLACK 问题已解决！在上面thread那里
				canvas.drawColor(Color.WHITE);
				
				//绘图函数根据游戏状态不同进行不同绘制
				switch(gameState) {
				case GAME_MENU:
					break;
				case GAMEING:
					break;
				case GAME_WIN:
					break;
				case GAME_LOSE:
					break;
				}
				
				
				
				

				// 如果使用strings中的文字就应该这么来应用
				// 当然应用前要把import。R.id改成import com.planegame.R;不然会出错的
				// 麻袋换不对 我就不知道了 擦！！不知道到底应该怎么用
				// canvas.drawText(R.string.hello_world, 100, 300, paint);
			}
		} catch (Exception e) {
			// TODO Auto-generated method stub

		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//触屏监听事件函数根据游戏状态不同进行不同监听
		switch(gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			break;
		case GAME_WIN:
			break;
		case GAME_LOSE:
			break;
		}
		return true;
	}

}
