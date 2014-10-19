package com.example.planegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	public static MainActivity instance;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		
		//因为背景图片尺寸有点小的问题 为了美观 这里先把全屏设置取消了 想一下其实可以把背景图片 纵向拉伸一些的
		//去掉全屏实在是太丑了
		//设置竖屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//设置上部的信息导航条
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new MySurfaceView(this));
	}
}
