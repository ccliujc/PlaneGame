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
	
	//������Ϸ״̬����
	public static final int GAME_MENU = 0;//游戏菜单 开始
	public static final int GAMEING = 1;//游戏中
	public static final int GAME_WIN = 2;//游戏胜利
	public static final int GAME_LOSE = 3;//游戏失败
	public static final int GAME_PAUSE = -1;//游戏菜单 暂停
	//当前游戏状态（默认初始在游戏菜单界面）
	public static final int gameState = GAME_MENU;
	

	public MySurfaceView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setTextSize(40);
		
		//*实例化一个Canvas 不知道有没有必要 模仿的例子里面没有写
		canvas = new Canvas();
		
		//*总写错诶
		sfh = this.getHolder();
		sfh.addCallback(this);
		
		//*总忘记写啊喂
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
		//写了Thread()没有给括号里加this run()就不会一直run
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
		//逻辑处理函数根据游戏状态不同进行不同处理
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

				//用白色刷屏
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
				
				
				
				

			
				//*想使用strings。xml中定义的字符串未果
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
		//触屏监听事件根据游戏状态的不同进行不同监听
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
