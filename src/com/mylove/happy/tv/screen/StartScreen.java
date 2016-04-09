/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 开机动画
 */
package com.mylove.happy.tv.screen;

import javax.microedition.khronos.opengles.GL10;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mylove.happy.tv.InGame;
import com.mylove.happy.tv.util.LogUtil;

public class StartScreen implements Screen{
	String TAG = "StartScreen";
	InGame game;
	SpriteBatch batch;
	Texture loading;
	public float statetime = 0;
	
	public StartScreen(InGame game) {
		this.game = game;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		loading =  new Texture(Gdx.files.internal("data/loading.png"));
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		statetime += Gdx.graphics.getDeltaTime();
		
		if (statetime > 1) {
			LogUtil.e(this, "启动主屏幕");
			game.setScreen(game.mainscreen);
		}

		batch.begin();
		batch.draw(loading, 0, 0, game.GAMESTAGE_WIDTH, game.GAMESTAGE_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		loading.dispose();
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}