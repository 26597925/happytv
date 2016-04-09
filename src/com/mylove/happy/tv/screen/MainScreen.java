/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 应用主界面
 */
package com.mylove.happy.tv.screen;

import java.util.ArrayList;
import java.util.Arrays;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Expo;
import aurelienribon.tweenengine.equations.Quint;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.mylove.happy.tv.actor.ActionBarActor;
import com.mylove.happy.tv.actor.ActionBarActor.ActionBarStyle;
import com.mylove.happy.tv.actor.BarActor;
import com.mylove.happy.tv.actor.BarActor.BarStyle;
import com.mylove.happy.tv.actor.BoxActor;
import com.mylove.happy.tv.actor.BoxActor.BoxStyle;
import com.mylove.happy.tv.actor.FocusActor;
import com.mylove.happy.tv.actor.FocusActor.FocusStyle;
import com.mylove.happy.tv.actor.ScreenActor;
import com.mylove.happy.tv.actor.ScreenActor.ScreenStyle;
import com.mylove.happy.tv.actor.ScrollActor;
import com.mylove.happy.tv.HappySettings;
import com.mylove.happy.tv.InGame;
import com.mylove.happy.tv.core.TextureFactory;
import com.mylove.happy.tv.event.KeypadDetector;
import com.mylove.happy.tv.focus.Focus;
import com.mylove.happy.tv.focus.FocusGroup;
import com.mylove.happy.tv.util.ImageUtil;

public class MainScreen implements Screen{
	InGame game;
	SpriteBatch batch;
	Stage stage;
	Texture background;
	FocusActor focus;
	ActionBarActor action;
	boolean isshow = false;
	ArrayList<ScrollActor> screenList;
	ArrayList<BoxActor> boxList;
	TweenManager tweenManager;
	FocusGroup group;
	ScreenActor screen;
	public MainScreen(InGame game) {
		this.game = game;
		screenList = new ArrayList<ScrollActor>();
		boxList = new ArrayList<BoxActor>();
		tweenManager = new TweenManager();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage=new Stage();
		group = new FocusGroup(stage);
		batch = new SpriteBatch();
		background = TextureFactory.newTexture(game, Gdx.files.internal(HappySettings.MAINBG));
		
		
		Texture	 texture = TextureFactory.newTexture(game, Gdx.files.internal("data/focus.9.png"));
		NinePatch patch = new NinePatch(texture, 15, 15, 15, 15);
		FocusStyle focussty = new FocusStyle(patch, 15, 15, 15, 15);
		focus = new FocusActor(focussty);
		focus.setName("focus");
		focus.setArg(300, 400);
		focus.setAnimation(tweenManager);
		stage.addActor(focus);
		
		ScreenStyle screensty = new ScreenStyle(game.GAMESTAGE_WIDTH, 480);
		screen = new ScreenActor(screensty);
		screen.setName("screen");
		screen.setAnimation(tweenManager);
		
		ScrollActor scroll0 = new ScrollActor();
		scroll0.setSize(game.GAMESTAGE_WIDTH, 480);
		scroll0.setPosition(0, 150);
		
		Drawable background = ImageUtil.toDrawable(game, Gdx.files.internal("data/movie.png"));
		BoxStyle boxsty = new BoxStyle(background, 300, 400, 200, 100);
		BoxActor box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				Focus cus = null;
				if(Arrays.binarySearch(keys, keycode) >= 0){
					cus = group.findNextFocus(actor, keycode);
				}
				if(cus != null){
					if(keycode == KeyEvent.KEYCODE_DPAD_DOWN&&actor == cus.getActor()){
						group.requestFocus(action);
					}
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("电影");
		scroll0.addActor(box);
		group.requestFocus(box);
		group.AddGroup(box);
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/tv.png"));
		boxsty = new BoxStyle(background, 420, 280, 510, 100);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				if(Arrays.binarySearch(keys, keycode) >= 0){
					group.findNextFocus(actor, keycode);
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("电视剧");
		scroll0.addActor(box);
		
		group.AddGroup(box);
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/rr.png"));
		boxsty = new BoxStyle(background, 420, 280, 510, 390);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				if(Arrays.binarySearch(keys, keycode) >= 0){
					group.findNextFocus(actor, keycode);
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("综艺");
		scroll0.addActor(box);
		
		group.AddGroup(box);
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/rr.png"));
		boxsty = new BoxStyle(background, 420, 280, 510, 680);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				Focus cus = null;
				if(Arrays.binarySearch(keys, keycode) >= 0){
					cus = group.findNextFocus(actor, keycode);
				}
				if(cus != null){
					if(keycode == KeyEvent.KEYCODE_DPAD_DOWN&&actor == cus.getActor()){
						BarActor bar = action.getBar();
						focus.moveFocus(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
						group.requestFocus(action);
					}
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
					if(focused){
						focus.setVisible(true);
						ScrollActor scroll = (ScrollActor) actor.getParent();
						scroll.scrollTo(actor, 25, tweenManager);
						focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
					}else{
						focus.setVisible(false);
					}
			}			
		});
		box.setText("搞笑");
		scroll0.addActor(box);
		
		group.AddGroup(box);
		
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/rr.png"));
		boxsty = new BoxStyle(background, 420, 280, 940, 390);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				if(Arrays.binarySearch(keys, keycode) >= 0){
					group.findNextFocus(actor, keycode);
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("生活");
		scroll0.addActor(box);
		
		group.AddGroup(box);
		
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/rr.png"));
		boxsty = new BoxStyle(background, 420, 280, 1370, 390);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				if(Arrays.binarySearch(keys, keycode) >= 0){
					group.findNextFocus(actor, keycode);
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("记录");
		scroll0.addActor(box);
		
		group.AddGroup(box);
		screen.addScroll(scroll0);
		
		ScrollActor scroll1 = new ScrollActor();
		scroll1.setSize(game.GAMESTAGE_WIDTH, 480);
		scroll1.setPosition(0, 150);
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/movie.png"));
		boxsty = new BoxStyle(background, 300, 400, 200, 100);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				Focus cus = null;
				if(Arrays.binarySearch(keys, keycode) >= 0){
					cus = group.findNextFocus(actor, keycode);
				}
				if(cus != null){
					if(keycode == KeyEvent.KEYCODE_DPAD_DOWN&&actor == cus.getActor()){
						group.requestFocus(action);
					}
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("电影");
		scroll1.addActor(box);
		screen.addScroll(scroll1);
		
		
		ScrollActor scroll2 = new ScrollActor();
		scroll2.setSize(game.GAMESTAGE_WIDTH, 480);
		scroll2.setPosition(0, 150);
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/movie.png"));
		boxsty = new BoxStyle(background, 300, 400, 200, 100);
		box = new BoxActor(game, boxsty);
		box.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				Actor actor = event.getTarget();
				int [] keys = {KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT};
				Arrays.sort(keys);
				Focus cus = null;
				if(Arrays.binarySearch(keys, keycode) >= 0){
					cus = group.findNextFocus(actor, keycode);
				}
				if(cus != null){
					if(keycode == KeyEvent.KEYCODE_DPAD_DOWN&&actor == cus.getActor()){
						group.requestFocus(action);
					}
				}
				return false;
			}
		});
		box.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				if(focused){
					focus.setVisible(true);
					ScrollActor scroll = (ScrollActor) actor.getParent();
					scroll.scrollTo(actor, 25, tweenManager);
					focus.moveFocus(actor.getX()-scroll.getMoveX(), actor.getY()-scroll.getMoveY(), actor.getWidth(), actor.getHeight());
				}else{
					focus.setVisible(false);
				}
			}			
		});
		box.setText("电影");
		scroll2.addActor(box);
		screen.addScroll(scroll2);
		
		
		stage.addActor(screen);
		
		Sprite sprite = new Sprite();
		sprite.setColor(0, 0, 0, 180);
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/bottom_bar_bg.png"));
		Drawable selectBackground = ImageUtil.toDrawable(game, Gdx.files.internal("data/simple_tab_selected.png"));
		ActionBarStyle actionsty = new ActionBarStyle(background, selectBackground, game.GAMESTAGE_WIDTH, 78);
		action = new ActionBarActor(actionsty);
		action.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				if(keycode == KeyEvent.KEYCODE_DPAD_UP || keycode == KeyEvent.KEYCODE_DPAD_DOWN){
					ScrollActor scroll = screen.getScreen();
					Actor actor = group.getCurrentFocus().getActor();
					if(scroll.hasChildren(actor)){
						group.requestFocus(actor);
					}else{
						group.findNextFocus(scroll, action.getBar());
					}
					
				}
				return false;
			}
		});
		action.addListener(new FocusListener(){
			public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
				
			}
		});
		action.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				if(actor instanceof ActionBarActor){
					ActionBarActor abar = (ActionBarActor) actor;
					screen.setScreen(abar.getSelect());
				}
			}
			
		});
		action.setName("action");
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video.png"));
		selectBackground = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video_select.png"));
		BarStyle barsty = new BarStyle(background, selectBackground, BarStyle.Orientation.TOP, 250, 78);
		BarActor bar = new BarActor(game, barsty);
		bar.setText("影视");
		bar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				if(stage.getKeyboardFocus() == action){
					BarActor bar = action.getBar();
					focus.moveFocus(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
				}
				BarActor bar = (BarActor) actor;
				bar.change();
			}
			
		});
		action.addBar(bar);
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video.png"));
		selectBackground = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video_select.png"));
		barsty = new BarStyle(background, selectBackground, BarStyle.Orientation.TOP, 250, 78);
		bar = new BarActor(game, barsty);
		bar.setText("直播");
		bar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				if(stage.getKeyboardFocus() == action){
					BarActor bar = action.getBar();
					focus.moveFocus(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
				}
				BarActor bar = (BarActor) actor;
				bar.change();
			}
			
		});
		action.addBar(bar);
		
		background = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video.png"));
		selectBackground = ImageUtil.toDrawable(game, Gdx.files.internal("data/icon_video_select.png"));
		barsty = new BarStyle(background, selectBackground, BarStyle.Orientation.TOP, 250, 78);
		bar = new BarActor(game, barsty);
		bar.setText("综艺");
		bar.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				if(stage.getKeyboardFocus() == action){
					BarActor bar = action.getBar();
					focus.moveFocus(bar.getX(), bar.getY(), bar.getWidth(), bar.getHeight());
				}
				BarActor bar = (BarActor) actor;
				bar.change();
			}
			
		});
		action.addBar(bar);
		
		action.setAnimation(tweenManager);
		action.selectBar(0);
		stage.addActor(action);
		
		InputMultiplexer multiplexer=new InputMultiplexer();// 多输入接收器
		multiplexer.addProcessor(new KeypadDetector(stage));//添加按键事件
		multiplexer.addProcessor(stage); // 添加舞台
		Gdx.input.setInputProcessor(multiplexer);// 设置多输入接收器为接收器
		isshow = true;
	
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		tweenManager.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//添加背景
		batch.begin();
		batch.draw(background, 0, 0, game.GAMESTAGE_WIDTH, game.GAMESTAGE_HEIGHT);
		batch.end();
		//添加舞台
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//在切换过程中，show还没完全执行完毕，会导致空指针
		if(isshow){
			stage.dispose();
			batch.dispose();
			background.dispose();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		isshow = false;
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
