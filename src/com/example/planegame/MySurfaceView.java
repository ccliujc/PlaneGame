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
	public static final int GAME_MENU = 0;//��Ϸ�˵� ��ʼ����
	public static final int GAMEING = 1;//��Ϸ��
	public static final int GAME_WIN = 2;//��Ϸʤ��
	public static final int GAME_LOSE = 3;//��Ϸʧ��
	public static final int GAME_PAUSE = -1;//��Ϸ�˵� ��Ϸ��ͣʱ�˵�
	//��ǰ��Ϸ״̬��Ĭ�ϳ�ʼ����Ϸ�˵����棩
	public static final int gameState = GAME_MENU;
	

	public MySurfaceView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setTextSize(40);
		
		//ʵ����canvas ��֪���費��ҪҪ �����ж�û�м�
		canvas = new Canvas();
		
		//��д���˵
		sfh = this.getHolder();
		sfh.addCallback(this);
		
		//���ý���  ������һ�� ��ȻҲ��֪�� ��û��Ӱ��
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
		//������û��this����run()��ˢ�£�����
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
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
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

				// �����������õ���WHITE�ɲ�֪��Ϊʲô��ʾ������ȱ��BLACK �����ѽ����������thread����
				canvas.drawColor(Color.WHITE);
				
				//��ͼ����������Ϸ״̬��ͬ���в�ͬ����
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
				
				
				
				

				// ���ʹ��strings�е����־�Ӧ����ô��Ӧ��
				// ��ȻӦ��ǰҪ��import��R.id�ĳ�import com.planegame.R;��Ȼ������
				// ��������� �ҾͲ�֪���� ��������֪������Ӧ����ô��
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
		//���������¼�����������Ϸ״̬��ͬ���в�ͬ����
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
