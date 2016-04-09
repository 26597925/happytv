 package com.mylove.happy.tv.core;
 
 import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.mylove.happy.tv.InGame;
 
 public class PixmapFactory
 {
   public static Pixmap newPixmap(InGame game, int width, int height, Pixmap.Format format)
   {
	 Pixmap pixmap = new Pixmap(width, height, format);
	 game.registerPixmap(pixmap);
	 return pixmap;
   }
 
   public static Pixmap newPixmap(InGame game, byte[] encodedData, int offset, int len)
   {
     Pixmap pixmap = null;
     pixmap = new Pixmap(encodedData, offset, len);
     game.registerPixmap(pixmap);
     return pixmap;
   }
 
   public static Pixmap newPixmap(InGame game, FileHandle file)
   {
     Pixmap pixmap = new Pixmap(file);
     game.registerPixmap(pixmap);
     return pixmap;
   }
 
   public static Pixmap newPixmap(InGame game, Gdx2DPixmap pixmap)
   {
     Pixmap p = newPixmap(game, pixmap);
     game.registerPixmap(p);
     return p;
   }
 }