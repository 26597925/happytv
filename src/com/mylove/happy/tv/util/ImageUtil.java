package com.mylove.happy.tv.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mylove.happy.tv.InGame;
import com.mylove.happy.tv.core.TextureFactory;

public class ImageUtil {
	public static Drawable toDrawable(InGame game, FileHandle file){
		Texture	 texture = TextureFactory.newTexture(game, file);
	    Sprite sprite = new Sprite(texture);
	   // texture.dispose();
	    return new SpriteDrawable(sprite);
	}
}
