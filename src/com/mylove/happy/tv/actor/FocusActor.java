/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 焦点角色,用于知道角色处于的未知
 */
package com.mylove.happy.tv.actor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mylove.happy.tv.animation.ActorAccessor;

public class FocusActor extends Image{
	FocusStyle style;
	TweenManager tweenManager;
	
	public FocusActor(FocusStyle style){
		super(style.patch);
		this.style = style;
		Tween.registerAccessor(FocusActor.class, new ActorAccessor());
	}
	
	public void setStyle(FocusStyle style){
		this.style = style;
	}
	
	public void setArg(float width, float height){
		setWidth(width+style.left+style.right);
		setHeight(height+style.top+style.bottom);
	}
	
	public void setAnimation(TweenManager tweenManager){
		this.tweenManager = tweenManager;
	}
	
	public float getAbsoluteX(float x){
		return x-style.left;
	}
	
	public float getAbsoluteY(float y){
		return y-style.top;
	}
	
	public float getAbsoluteWidth(float width){
		return width+style.left+style.right;
	}
	
	public float getAbsoluteHeight(float height){
		return height+style.top+style.bottom;
	}
	
	public void moveFocus(float mx, float my, float mw, float mh){
		float x = getAbsoluteX(mx);
		float y = getAbsoluteY(my);
		float widht = getAbsoluteWidth(mw);
		float height = getAbsoluteHeight(mh);
		Timeline.createParallel()
		.beginParallel()
		.push(Tween.to(this, ActorAccessor.ACTOR_WH, 0.5f).ease(Cubic.OUT).target(widht,
				height))
		.push(Tween.to(this, ActorAccessor.POS_XY, 0.5f).ease(Cubic.OUT).target(x,
				y))
		.start(tweenManager);
	}
	
	static public class FocusStyle {
		//设置焦点的未知
		public float left,top,right,bottom;
		public NinePatch patch;
		public FocusStyle (NinePatch patch, float left, float top, float right, float bottom){
			this.patch = patch;
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}
		public FocusStyle (FocusStyle style) {
			this.patch = style.patch;
		}
	}
}