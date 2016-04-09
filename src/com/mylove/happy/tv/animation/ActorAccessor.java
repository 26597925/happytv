package com.mylove.happy.tv.animation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mylove.happy.tv.util.LogUtil;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Back;

public class ActorAccessor  implements TweenAccessor<Actor>{
	public static final int POS_XY = 1;
	public static final int CPOS_XY = 2;
	public static final int SCALE_XY = 3;
	public static final int ROTATION = 4;
	public static final int OPACITY = 5;
	public static final int TINT = 6;
	public static final int ACTOR_WH = 7;
	//Tween.to(focus, ActorAccessor.POS_XY, 1.0f).ease(Back.OUT)
	//.target(actor.getX(), actor.getY()).start(tweenManager);
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POS_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case CPOS_XY:
			returnValues[0] = target.getX() + target.getWidth() / 2;
			returnValues[1] = target.getY() + target.getHeight() / 2;
			return 2;
		case SCALE_XY:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;
		case ROTATION:
			returnValues[0] = target.getRotation();
			return 1;
		case OPACITY:
			returnValues[0] = target.getColor().a;
			return 1;
		case TINT:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case ACTOR_WH:
			returnValues[0] = target.getWidth();
			returnValues[1] = target.getHeight();
			return 2;
		default:
			assert false;
			return -1;
		}
	}
	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		Color c;
		switch (tweenType) {
		case POS_XY:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case CPOS_XY:
			target.setX(newValues[0] - target.getWidth() / 2);
			target.setY(newValues[1] - target.getHeight() / 2);
			break;
		case SCALE_XY:
			target.setScaleX(newValues[0]);
			target.setScaleY(newValues[1]);
			break;
		case ROTATION:
			target.setRotation(newValues[0]);
			break;
		case OPACITY:
			c = target.getColor();
			c.set(c.r, c.g, c.b, newValues[0]);
			target.setColor(c);
			break;
		case TINT:
			c = target.getColor();
			c.set(newValues[0], newValues[1], newValues[2], c.a);
			target.setColor(c);
			break;
		case ACTOR_WH:
			target.setWidth(newValues[0]);
			target.setHeight(newValues[1]);
			break;
		default:
			assert false;
		}
	}
}
