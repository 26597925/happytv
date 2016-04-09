package com.mylove.happy.tv;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MemoryManage {
	List<Pixmap> pixmaps = new LinkedList<Pixmap>();
	List<Texture> textures = new LinkedList<Texture>();
	List<BitmapFont> bitmapFonts = new LinkedList<BitmapFont>();
	boolean isLose = false;
	 
	public boolean isLose() {
		return isLose;
	}
 
	private void clearPixmaps() {
		for (Pixmap pixmap : pixmaps) {
			pixmap.dispose();
		}
		pixmaps.clear();
	}
 
	private void clearTextures() {
		for (Texture tx : textures) {
			tx.dispose();
		}
		textures.clear();
	}
	
	private void clearBitmapFont(){
		for (BitmapFont bitmapFont : bitmapFonts) {
			bitmapFont.dispose();
		}
		bitmapFonts.clear();
	}
 
	public void registerPixmap(Pixmap pixmap) {
		if (isLose) {
			pixmap.dispose();
			return;
		}
		pixmaps.add(pixmap);
   }
 
	public void registerTexture(Texture texture) {
		if (isLose) {
			texture.dispose();
			return;
		}
		textures.add(texture);
   }
	
   public void registerBitmapFont(BitmapFont bitmapFont){
	   if (isLose) {
		   bitmapFont.dispose();
			return;
	   }
	   bitmapFonts.add(bitmapFont);
   }
	
	public void dispose() {
		isLose = true;
		clearTextures();
		clearPixmaps();
		clearBitmapFont();
	}
}
