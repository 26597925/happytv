/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 滚动角色
 */
package com.mylove.happy.tv.actor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mylove.happy.tv.actor.ScreenActor.ScreenStyle;
import com.mylove.happy.tv.animation.ActorAccessor;

public class ScrollActor extends WidgetGroup{
	
	Tween tw;
	
	float moveX;
	
	float moveY;
	
	ScrollStyle scrsty;
	
	boolean transform = true;
	
	public ScrollActor(){
		initialize ();
	}
	
	public ScrollActor(ScrollStyle scrsty){
		this.scrsty = scrsty;
		initialize ();
	}
	
	private void initialize (){
		Tween.registerAccessor(Actor.class, new ActorAccessor());
	}

	//要移动的距离
	public float scrollToX(Actor actor, float space){
		float absoluteX = actor.getX();
		float absolute = 0;
		if(absoluteX > 0){
			float width = getWidth();
			absolute = absoluteX + actor.getWidth();
			if(absolute>width){
				moveX = absolute - width + space;
			}else{
				moveX = 0;
				return moveX;
			}
		}else{
			moveX = absoluteX-space;
		}
		return moveX;
	}
	
	public float getMoveX(){
		return moveX - getX();
	}
	
	public float scrollToY(Actor actor, float space){
		float absoluteY = actor.getY();
		float absolute = 0;
		if(absoluteY > 0){
			float height = getHeight();
			absolute = absoluteY + actor.getHeight();
			if(absolute > height){
				moveY = absolute - height + space;
			}else{
				moveY = 0;
				return moveY;
			}
		}else{
			moveY = absoluteY-space;
		}
		return moveY;
	}
	
	public float getMoveY(){
		return moveY - getY();
	}
	
	public void draw (Batch batch, float parentAlpha) {
		validate();
		if (isTransform()) {
			applyTransform(batch, computeTransform());
			batch.flush();
			if (clipBegin(0, 0, getWidth(),
				getHeight())) {
				drawChildren(batch, parentAlpha);
				batch.flush();
				clipEnd();
				resetTransform(batch);
			}
		} else {
			super.draw(batch, parentAlpha);
		}
	}
	
	public void scrollTo(Actor actor, float space, TweenManager tweenManager){
		scrollToX(actor, space);
		scrollToY(actor, space);
		start(tweenManager);
	}
	
	public void start(TweenManager tweenManager){
		SnapshotArray<Actor> children = getChildren();
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			Tween.to(child, ActorAccessor.POS_XY, 0.6f).ease(Cubic.OUT)
			.target(child.getX()-moveX, child.getY()-moveY).start(tweenManager);
		}
	}
	
	public boolean hasChildren(Actor actor){
		SnapshotArray<Actor> children = getChildren();
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			if(child == actor){
				return true;
			}
		}
		return false;
	}
	
	static public class ScrollStyle {
		public float width;
		public float height;
		public ScrollStyle (float width, float height){
			this.width = width;
			this.height = height;
		}
		public ScrollStyle (ScreenStyle style) {
			this.width = style.width;
			this.height = style.height;
		}
	}
}
