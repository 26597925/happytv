/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark ¸Ë½ÇÉ«
 */
package com.mylove.happy.tv.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pools;
import com.mylove.happy.tv.InGame;

public class BarActor extends WidgetGroup{
	FreeTypeFontGenerator generator;
	BitmapFont bf;
	InGame game;
	boolean isSelected;
	BarStyle style;
	Image ico;
	Label title;
	public BarActor(InGame game){
		this.game = game;
		initialize ();
	}
	public BarActor(InGame game, BarStyle style){
		this.game = game;
		setStyle(style);
		initialize ();
	}
	public void setStyle(BarStyle style){
		this.style = style;
	}
	private void initialize () {
		addActor(ico = new Image());
		setWidth(style.width);
		setHeight(style.height);
		if(style.ori != BarStyle.Orientation.NONE){
			ico.setWidth(style.background.getMinWidth());
			ico.setHeight(style.background.getMinHeight());
			ico.setDrawable(style.background);
		}
	}
	public void setText(String text){
		BitmapFont bf = game.getBitmapFont(20, text);
		LabelStyle lsty = new LabelStyle(bf, Color.WHITE);
		addActor(title = new Label(text, lsty));
		title.setWidth(getWidth());
		if(style.ori == BarStyle.Orientation.TOP){
			title.setAlignment(Align.center);
			ico.setPosition((getWidth()-ico.getWidth())/2, getHeight()/2-5);
		}else if(style.ori == BarStyle.Orientation.LEFT){
			title.setPosition(getWidth()/2, (getHeight()-title.getHeight())/2);
			ico.setPosition(title.getX()-ico.getWidth(),(getHeight()-ico.getHeight())/2);
		}else if(style.ori == BarStyle.Orientation.DOWN){
			title.setAlignment(Align.center);
			title.setY(getHeight()-title.getHeight());
			ico.setPosition((getWidth()-ico.getWidth())/2, 5);
		}else if(style.ori == BarStyle.Orientation.RIGHT){
			ico.setPosition(getWidth()/2+ico.getWidth(),(getHeight()-ico.getHeight())/2);
			title.setPosition(ico.getX()-title.getWidth()/2, (getHeight()-title.getHeight())/2);
		}else{
			title.setAlignment(Align.center);
			title.setY((getHeight()-title.getHeight())/2);
		}
	}
	public void change(){
		if(isSelected){
			if(style.ori != BarStyle.Orientation.NONE) ico.setDrawable(style.selectBackground);
			title.setColor(Color.YELLOW);
		}else{
			if(style.ori != BarStyle.Orientation.NONE) ico.setDrawable(style.background);
			title.setColor(Color.WHITE);
		}
	}
	public void setSelect(boolean isSelected){
		this.isSelected = isSelected;
		ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
		fire(changeEvent);
		Pools.free(changeEvent);
	}
	static public class BarStyle {
		public enum Orientation { NONE, LEFT, RIGHT, TOP, DOWN };
		public Drawable background;
		public Drawable selectBackground;
		public Orientation ori;
		public float width;
		public float height;
		public BarStyle (Drawable background, Drawable selectBackground, Orientation ori, float width, float height){
			this.background = background;
			this.selectBackground = selectBackground;
			this.ori = ori;
			this.width = width;
			this.height = height;
		}
		public BarStyle (BarStyle style) {
			this.background = style.background;
			this.ori = style.ori;
			this.width = style.width;
			this.height = style.height;
		}
	}
}
