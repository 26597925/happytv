/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 游戏入口界面
 */
package com.mylove.happy.tv;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mylove.happy.tv.screen.CategoryScreen;
import com.mylove.happy.tv.screen.MainScreen;
import com.mylove.happy.tv.screen.RecommendScreen;
import com.mylove.happy.tv.screen.StartScreen;
import com.mylove.happy.tv.screen.UpdateScreen;
import com.mylove.happy.tv.util.LogUtil;

public class InGame extends Game {
	
	public float GAMESTAGE_WIDTH;
	public float GAMESTAGE_HEIGHT;
	public OrthographicCamera camera;
	public StartScreen startscreen;
	public MainScreen  mainscreen;
	public UpdateScreen updatescreen;
	public CategoryScreen categoryscreen;
	public RecommendScreen recommendscreen;
	public MemoryManage memorymanage;
	FreeTypeFontGenerator generator;
	
    //初始化ui界面
	@Override
	public void create() {
		// TODO Auto-generated method stub
		LogUtil.ENABLED = true;
		GAMESTAGE_WIDTH = Gdx.graphics.getWidth();
		GAMESTAGE_HEIGHT = Gdx.graphics.getHeight();
		generator = new FreeTypeFontGenerator(Gdx.files.internal(HappySettings.TTF));
		memorymanage = new MemoryManage();
		//初始化照相机
		camera = new OrthographicCamera(GAMESTAGE_WIDTH, GAMESTAGE_HEIGHT);
		startscreen = new StartScreen(this);
		mainscreen = new MainScreen(this);
		updatescreen = new UpdateScreen(this);
		categoryscreen = new CategoryScreen(this);
		recommendscreen = new RecommendScreen(this);
		//切换游戏界面的主要方法。
		setScreen(startscreen);
	}
	
	//注册Pixmap
	public void registerPixmap(Pixmap pixmap){
		memorymanage.registerPixmap(pixmap);
	}
	
	//注册texture
	public void registerTexture(Texture texture) {
		memorymanage.registerTexture(texture);
	}
	
	public BitmapFont getBitmapFont(int size, String title){
		BitmapFont bitmapFont = generator.generateFont(size, title, false);
		memorymanage.registerBitmapFont(bitmapFont);
		return bitmapFont;
	}

	//释放所有资源
	@Override
	public void dispose() {
		startscreen.dispose();
		mainscreen.dispose();
		updatescreen.dispose();
		categoryscreen.dispose();
		recommendscreen.dispose();
		memorymanage.dispose();
		generator.dispose();
	}

}
