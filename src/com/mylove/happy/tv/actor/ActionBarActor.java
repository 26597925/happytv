/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 操作杆角色
 */
package com.mylove.happy.tv.actor;

import java.util.LinkedList;
import java.util.List;

import android.view.KeyEvent;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mylove.happy.tv.animation.ActorAccessor;
import com.mylove.happy.tv.util.LogUtil;

public class ActionBarActor extends WidgetGroup{
	int Selected = -1;
	ActionBarStyle style;
	Image selectImage;
	List<BarActor> bars;
	TweenManager tweenManager;
	
	public ActionBarActor(ActionBarStyle style){
		this.style = style;
		initialize ();
	}
	
	public void setStyle(ActionBarStyle style){
		this.style = style;
	}
	
	private void initialize () {
		bars = new LinkedList<BarActor>();
		Tween.registerAccessor(Image.class, new ActorAccessor());
		addActor(selectImage = new Image());
		selectImage.setDrawable(style.selectBackground);
		setWidth(style.width);
		setHeight(style.height);
		addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {
				int cur_sel = Selected;
				if(keycode == KeyEvent.KEYCODE_DPAD_LEFT){
					if(cur_sel>0){
						cur_sel--;
					}else{
						cur_sel = bars.size()-1;
					}
					selectBar(cur_sel);
				}else if(keycode == KeyEvent.KEYCODE_DPAD_RIGHT){
					if(cur_sel<bars.size()-1){
						cur_sel++;
					}else{
						cur_sel = 0;
					}
					selectBar(cur_sel);
				}
				return false;
			}
		});
	}
	
	public void setAnimation(TweenManager tweenManager){
		this.tweenManager = tweenManager;
	}
	
	public void addBar(BarActor bar){
		float x = 0;
		int size = getChildren().size;
		for(int i = 0; i < size; i++){
			x = i*getChildren().get(i).getWidth();
		}
		addActor(bar);
		bar.setX(x);
		bars.add(bar);
	}
	
	public boolean removeBar(BarActor bar){
		bars.remove(bar);
		return removeActor(bar);
	}
	
	public boolean removeBar(int index){
		SnapshotArray<Actor> children = getChildren();
		if(index >= children.size) return false;
		Actor item = getChildren().get(index);
		//选中角色不能被删除
		if(item instanceof Image) return false;
		bars.remove(item);
		return item.remove();
	}
	
	public int selectBar(int Selected){
		if (this.Selected == Selected) return Selected;
		if(Selected > bars.size()) return Selected;
		this.Selected = Selected;
		for(int i = 0, n = bars.size(); i < n; i++){
			BarActor bar = bars.get(i);
			if(i == Selected){
				bar.setSelect(true);
				if(selectImage.getWidth() != bar.getWidth())selectImage.setWidth(bar.getWidth());
				if(selectImage.getHeight() != bar.getHeight())selectImage.setHeight(bar.getHeight());
				if(tweenManager == null){
					selectImage.setX(bar.getX());	
				}else{
					Tween.to(selectImage, ActorAccessor.POS_XY, 0.8f).ease(Cubic.OUT)
					.target(bar.getX(), bar.getY()).start(tweenManager);
				}
			}else{
				bar.setSelect(false);
			}
		}
		ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
		fire(changeEvent);
		Pools.free(changeEvent);
		return Selected;
	}
	
	public int getSelect(){
		return Selected;
	}
	
	public BarActor getBar(){
		if(Selected > bars.size()) return null;
		return  bars.get(Selected);
	}
	
	public void draw (Batch batch, float parentAlpha) {
		validate();
		drawBackground(batch, parentAlpha, getX(), getY());
		super.draw(batch, parentAlpha);
	}
	
	protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
		if (style.background == null) return;
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		style.background.draw(batch, x, y, getWidth(), getHeight());
	}
	
	static public class ActionBarStyle {
		public float width;
		public float height;
		public Drawable background;
		public Drawable selectBackground;
		public ActionBarStyle (Drawable background, Drawable selectBackground, float width, float height){
			this.selectBackground = selectBackground;
			this.background = background;
			this.width = width;
			this.height = height;
		}
		public ActionBarStyle (ActionBarStyle style) {
			this.selectBackground = style.selectBackground;
			this.background = style.background;
			this.width = style.width;
			this.height = style.height;
		}
	}
}
