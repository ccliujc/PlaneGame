package com.example.planegame;


import java.util.Random;
import java.util.Vector;

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
	public static final int GAME_LOST = 3;//游戏失败
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
	
	//声明一个敌机容器
	private Vector<Enemy> vcEnemy;
	//每次生成敌机的时间（毫秒）
	private int createEnemyTime = 50;
	private int count;//计数器
	//敌人数组：1和2代表敌机的种类，-1表示Boss
	//二维数组的每一维都是一组怪物
	private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 },
			{ 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 },
			{ 1, 3, 1, 1 }, { 2, 1 }, { 1, 3 }, { 2, 1 }, { -1 } };
	
	//当前取出一维数组的下标
	private int enemyArrayIndex;
	//是否出现Boss标识位
	private boolean  isBoss;
	//随机库，为创建的敌机赋予随机坐标
	private Random random;
	
	
	//敌机子弹容器
	private Vector<Bullet> vcBullet = new Vector<Bullet>();
	//添加子弹的计数器
	private int countEnemyBullet;
	//主角子弹容器
	private Vector<Bullet> vcBulletPlayer = new Vector<Bullet>();
	//添加子弹的计数器
	private int countPlayerBullet;
	

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
			
			//实例敌机容器
			vcEnemy = new Vector<Enemy>();
			//实例随机库
			random = new Random();
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
		// 逻辑处理函数根据游戏状态不同进行不同处理
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMEING:
			// 背景逻辑
			backGround.logic();
			// 主角的逻辑
			player.logic();
			
			//处理敌机与主角的碰撞
			for(int i = 0; i < vcEnemy.size(); i ++) {
				if(player.isCollsionWith(vcEnemy.elementAt(i))) {
					//发生碰撞，主角血量-1
					player.setPlayerHp(player.getPlayerHp() - 1);
				}
			}
			
			
			//处理敌机子弹与主角碰撞
			for(int i = 0; i < vcBullet.size(); i++) {
				if(player.isCollisionWith(vcBullet.elementAt(i))){
					//发生碰撞，主角血量-1
					player.setPlayerHp(player.getPlayerHp() - 1);
					//当主角血量小于0时，判断游戏失败
					if(player.getPlayerHp() <= -1) {
						gameState = GAME_LOST;
					}
				}
			}
			
			//处理主角子弹与敌机碰撞
			for(int i = 0; i < vcBulletPlayer.size(); i++ ) {
				//取出主角子弹容器德尔每个元素
				Bullet blPlayer = vcBulletPlayer.elementAt(i);
				for(int j = 0; j < vcEnemy.size(); j++) {
					//取出敌机容器的每个元与主角子弹遍历判断
					if(vcEnemy.elementAt(j).isCollisionWith(blPlayer)){
						//添加爆咋效果
					}
				}
			}

			// 敌机逻辑
			if (isBoss == false) {
				// 敌机逻辑
				for (int i = 0; i < vcEnemy.size(); i++) {
					Enemy en = vcEnemy.elementAt(i);
					// 因为容器不断添加敌机，那么对敌机的isDead判定，
					// 如果已死亡那么就从容器中删除，对容器起到了优化作用；
					if (en.isDead) {
						vcEnemy.removeElementAt(i);

					} else {
						en.logic();
					}
				}
				// 生成敌机
				count++;
				if (count % createEnemyTime == 0) {
					for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
						// 苍蝇
						if (enemyArray[enemyArrayIndex][i] == 1) {
							int x = random.nextInt(screenW - 100) + 50;
							vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
							// 鸭子左
						} else if (enemyArray[enemyArrayIndex][i] == 2) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50,
									y));
							// 鸭子右
						} else if (enemyArray[enemyArrayIndex][i] == 3) {
							int y = random.nextInt(20);
							vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3,
									screenW + 50, y));
						}
					}
					// 这里判断下一组是否为最后一组（Boss）
					if (enemyArrayIndex == enemyArray.length - 1) {
						isBoss = true;
					} else {
						enemyArrayIndex++;
					}
				}
				
			
				//每2秒添加一个敌机子弹
				countEnemyBullet ++;
				if(countEnemyBullet % 40 == 0) {
					for(int i = 0; i < vcEnemy.size(); i++) {
						Enemy en = vcEnemy.elementAt(i);
						//不同类型的敌机不同的子弹运动轨迹
						int bulletType = 0;
						switch(en.type) {
						//苍蝇
						case Enemy.TYPE_FLY:
							bulletType = Bullet.BULLET_FLY;
							break;
						//鸭子
						case Enemy.TYPE_DUCKL:
						case Enemy.TYPE_DUCKR:
							bulletType = Bullet.BULLET_DUCK;
							break;
						}
						vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
					}
				}
				//处理子弹的逻辑
				for(int i = 0; i < vcBullet.size(); i++) {
					Bullet b = vcBullet.elementAt(i);
					if(b.isDead){
						vcBullet.removeElement(b);
					} else {
						b.logic();
					}
				}
				
				
			} else {
				// Boss逻辑

			}
			
			//每秒添加一个主角子弹
			countPlayerBullet++;
			if(countPlayerBullet % 20 == 0) {
				vcBulletPlayer.add(new Bullet(bmpBullet, player.x + bmpPlayer.getWidth()/2 - bmpBullet.getWidth()/2,
						player.y - 20, Bullet.BULLET_PLAYER));
			}
			//处理主角子弹逻辑
			for(int i = 0; i < vcBulletPlayer.size(); i++) {
				Bullet b = vcBulletPlayer.elementAt(i);
				if(b.isDead){
					vcBulletPlayer.removeElement(b);
				}else{
					b.logic();
				}
			}
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
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
					//怪物绘图函数
					if(isBoss == false) {
						//敌机绘制
						for(int i = 0; i < vcEnemy.size(); i ++){
							vcEnemy.elementAt(i).draw(canvas, paint);
						}
						
						//敌机子弹绘制
						for(int i = 0; i < vcBullet.size(); i ++){
							vcBullet.elementAt(i).draw(canvas, paint);
						}
						
					}else {
						//Boss绘制
					}

					
					//处理主角子弹的绘制
					for(int i = 0; i < vcBulletPlayer.size(); i++) {
						vcBulletPlayer.elementAt(i).draw(canvas, paint);
					}
					
					break;
				case GAME_WIN:
					break;
				case GAME_LOST:
					canvas.drawBitmap(bmpGameLost, 0, 0,paint);
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
		case GAME_LOST:
			break;
		}
		return true;
	}

}
