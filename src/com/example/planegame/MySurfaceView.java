package com.example.planegame;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.planegame.R;




public class MySurfaceView extends SurfaceView implements Runnable, Callback {
	//貌似想要在别的类里面 通过“类名.常量”用变量，就得将常量定义为public static 类型 常量；
	public static int screenW;
	public static int screenH;
	private boolean flag;
	
	private Paint paint;
	private Canvas canvas;
	private SurfaceHolder sfh;
	
	private Thread th;
	
	
	//定义游戏状态常量
	public static final int GAME_MENU = 0;//游戏菜单 开始
	public static final int GAMEING = 1;//游戏中
	public static final int GAME_WIN = 2;//游戏胜利
	public static final int GAME_LOSE = 3;//游戏失败
	public static final int GAME_PAUSE = -1;//游戏菜单 暂停
	//当前游戏状态（默认初始在游戏菜单界面）
	public static int gameState = GAME_MENU;
	
	
	//声明一个Resources实例便于加载图片
	private Resources res = this.getResources();
	//声明游戏需要用到的图片资源（图片声明）
	private Bitmap bmpBackGround;//游戏背景
	private Bitmap bmpBoom;//爆咋效果
	private Bitmap bmpBoosBoom;//Boos爆咋效果
	private Bitmap bmpButton;//游戏开始按钮
	private Bitmap bmpButtonPress;//游戏开始按钮被点击
	private Bitmap bmpEnemyDuck;//怪物鸭子
	private Bitmap bmpEnemyFly;//怪物苍蝇
	private Bitmap bmpEnemyBoos;//怪物猪头Boos
	private Bitmap bmpGameWin;//游戏胜利背景
	private Bitmap bmpGameLost;//游戏失败背景
	private Bitmap bmpPlayer;//游戏主角飞机
	private Bitmap bmpPlayerHp;//宗教飞机血量
	private Bitmap bmpMenu;//菜单背景
	public static Bitmap bmpBullet;//子弹
	public static Bitmap bmpEnemyBullet;//敌机子弹
	public static Bitmap bmpBossBullet;//Boss子弹
	
	//声明一个菜单对象
	private GameMenu gameMenu;
	
	//声明一个滚动游戏背景对象
	private GameBg backGround;
	
	//声明主角对象
	private Player player;
	
	

	public MySurfaceView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.YELLOW);
		
		
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
		
		//一开始把initGame();放到MySurfaceView的构造函数中了 
		//就导致 GameMenu中的背景图正常显示 Button第一次打开程序时不显示  需要按一次“返回键”重新进入游戏后才能正常显示
		initGame();//自定义初始化函数 便于初始化游戏
		
		flag = true;
		//写了Thread()没有给括号里加this run()就不会一直run
		th = new Thread(this);
		th.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		flag = false;
	}
	
	private void initGame() {
		//放置游戏切入后台重新进入游戏时，游戏被重置！
		//当游戏状态处于菜单是，才会重置游戏
		if(gameState == GAME_MENU) {
			//加载游戏资源
			bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
			bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
			bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
			bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
			bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
			bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
			bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
			bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
			bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
			bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
			bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
			bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
			bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
			bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
			bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
			bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
			
			//菜单类实例
			gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);
			
			//实例游戏背景
			backGround = new GameBg(bmpBackGround);
			
			//实例主角
			player = new Player(bmpPlayer, bmpPlayerHp);
		}
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
			//背景逻辑
			backGround.logic();
			//主角的逻辑
			player.logic();
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
					//菜单的绘图函数
					gameMenu.draw(canvas, paint);
					break;
				case GAMEING:
					//游戏背景
					backGround.draw(canvas, paint);
					//主角绘图函数
					player.draw(canvas, paint);
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
			//菜单的触屏处理事件
			gameMenu.onTouchEvent(event);
			break;
		case GAMEING:
			//主角的触屏处理事件
			player.onTouchEvent(event);
			break;
		case GAME_WIN:
			break;
		case GAME_LOSE:
			break;
		}
		return true;
	}

}
