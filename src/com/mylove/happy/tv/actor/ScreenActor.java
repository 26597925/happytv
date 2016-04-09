/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 分屏角色
 */
package com.mylove.happy.tv.actor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mylove.happy.tv.animation.ActorAccessor;

public class ScreenActor extends WidgetGroup{
	int index = 0;
	ScreenStyle style;
	public enum Orientation { LEFT, RIGHT };
	TweenManager tweenManager;
	ScrollActor scroll;
	public ScreenActor(ScreenStyle style){
		this.style = style;
		initialize();
	}
	public void setStyle (ScreenStyle style) {
		if (style == null) throw new IllegalArgumentException("style cannot be null.");
		this.style = style;
	}
	private void initialize (){
		Tween.registerAccessor(Actor.class, new ActorAccessor());
	}
	public void setAnimation(TweenManager tweenManager){
		this.tweenManager = tweenManager;
	}
	//切换屏幕
	public void setScreen(int index){
		SnapshotArray<Actor> children = getChildren();
		for(int i = 0, n = children.size; i < n; i++){
			if(this.index>i){
				children.get(i).setX(-children.get(i).getWidth());
			}else if(this.index == i){
				children.get(i).setX(0);
			}else{
				children.get(i).setX(children.get(i).getWidth());
			}
		}
		if (this.index == index) return;
		if(children.size >index){
			scroll = getActor(this.index);
			float width = scroll.getWidth();
			if(this.index>index){
				width = scroll.getWidth();
			}else{
				width = -scroll.getWidth();
			}
			Tween tween = Tween.to(scroll, ActorAccessor.POS_XY, 0.3f).ease(Cubic.OUT).target(width, scroll.getY());
			this.index = index;
			scroll = getActor(this.index);
			Tween cur_tween = Tween.to(scroll, ActorAccessor.POS_XY, 0.3f).ease(Cubic.OUT).target(0, scroll.getY());
			Timeline.createParallel()
			.beginParallel()
			.push(tween).push(cur_tween).start(tweenManager);
		}
	}
	public void switchScreen(Orientation ori){
		int index = this.index;
		if(ori == Orientation.LEFT){
			if(index > 0 ){
				index--;
			}else{
				index = getChildren().size -1;
			}
		}else{
			if(getChildren().size-1>index){
				index++;
			}else{
				index = 0;
			}
		}
		setScreen(index);
	}
	//获取屏幕索引
	public int getScreenIndex(){
		return this.index;
	}
	//获取当前屏幕
	public ScrollActor getScreen(){
		SnapshotArray<Actor> children = getChildren();
		if(index >=children.size) return null;
		return (ScrollActor) children.get(index);
	}
	//获取当前屏幕
	public ScrollActor getActor(int index){
		int count = getChildren().size;
		if(count >index){
			return (ScrollActor) getChildren().get(index);
		}
		return null;
	}
	//添加scroll角色到分屏
	public void addScroll(ScrollActor scroll){
		addActor(scroll);
	}
	//移除scroll角色
	public void removeScroll(ScrollActor actor){
		super.removeActor(actor);
	}
	
	//屏幕样式
	static public class ScreenStyle {
		public float width;
		public float height;
		public ScreenStyle (float width, float height){
			this.width = width;
			this.height = height;
		}
		public ScreenStyle (ScreenStyle style) {
			this.width = style.width;
			this.height = style.height;
		}
	}
}
