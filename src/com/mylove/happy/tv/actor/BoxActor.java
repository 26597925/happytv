/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 盒子角色,显示盒子的内容，当焦点角色处于他的未知的时候进行状态
 */
package com.mylove.happy.tv.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.mylove.happy.tv.HappySettings;
import com.mylove.happy.tv.InGame;
import com.mylove.happy.tv.core.PixmapFactory;
import com.mylove.happy.tv.core.TextureFactory;

public class BoxActor extends WidgetGroup{
	InGame game;
	BoxStyle style;
	Label label;
	public BoxActor(InGame game){this.game = game;}
	public BoxActor(InGame game, BoxStyle style){
		this(game);
		setStyle(style);
	}
	public void setStyle(BoxStyle style){
		this.style = style;
		float cHeight = Gdx.graphics.getHeight();
		setWidth(style.width);
		setHeight(style.height);
		setX(style.x);
		setY(cHeight-style.Y-style.height);
	}
	public void setText(String title){
		float height = 37;
		float margin = 10;
		BitmapFont bf = game.getBitmapFont(20, title);
		LabelStyle lsty = new LabelStyle(bf, Color.WHITE);
		Pixmap map = PixmapFactory.newPixmap(game, 0, 0, Format.RGBA8888);
		Texture	 texture = TextureFactory.newTexture(game, map);
		Sprite sprite = new Sprite(texture);
		sprite.setColor(0, 0, 0, 180);
		lsty.background = new SpriteDrawable(sprite);
		label = new Label(title, lsty);
		label.setWidth(style.width-margin);
		label.setAlignment(Align.center);
		label.setHeight(height);
		label.setX(margin/2);
		label.setY(margin/2);
		addActor(label);
	}
	
	public void textShow(){
		label.setVisible(true);
	}
	
	public void textHide(){
		label.setVisible(false);
	}
	
	public void draw (Batch batch, float parentAlpha) {
		validate();
		if (isTransform()) {
			applyTransform(batch, computeTransform());
			drawBackground(batch, parentAlpha, 0, 0);
			drawChildren(batch, parentAlpha);
			resetTransform(batch);
		}else{
			drawBackground(batch, parentAlpha, getX(), getY());
			super.draw(batch, parentAlpha);
		}
	}
	
	protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
		if (style.background == null) return;
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		style.background.draw(batch, x, y, getWidth(), getHeight());
	}
	
	static public class BoxStyle {
		public Drawable background;
		public float width;
		public float height;
		public float x;
		public float Y;
		public BoxStyle (Drawable background, float width, float height, float x, float Y){
			this.background = background;
			this.width = width;
			this.height = height;
			this.x = x;
			this.Y = Y;
		}
		public BoxStyle (BoxStyle style) {
			this.background = style.background;
			this.width = style.width;
			this.height = style.height;
			this.x = style.x;
			this.Y = style.Y;
		}
	}
}
