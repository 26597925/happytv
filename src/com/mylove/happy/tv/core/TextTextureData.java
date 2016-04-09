 package com.mylove.happy.tv.core;
 
 import android.graphics.Bitmap;
import android.opengl.GLUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;
 
 public class TextTextureData
   implements TextureData
 {
   private String mText;
   private int width;
   private int height;
   private int preWidth;
   private int preHeight;
   private int maxLine = 1;
   private int textColor = -1;
   private float textSize = 26.0F;
 
   private boolean isPrepared = false;
   private float shadowRadius;
   private float shadowDx;
   private float shadowDy;
   private int shadowColor = -1;
 
   private int contentWidth = 0;
   private int contentHeight = 0;
   private Bitmap mBtimap;
 
   public void setShadowLayer(float radius, float dx, float dy, int color)
   {
     shadowRadius = radius;
     shadowDx = dx;
     shadowDy = dy;
     shadowColor = color;
   }
 
   public String getmText()
   {
     return mText;
   }
 
   public void setText(String mText) {
     this.mText = mText;
   }
 
   public int getMaxLine()
   {
     return maxLine;
   }
 
   public void setMaxLine(int maxLine) {
	   this.maxLine = maxLine;
   }
 
   public int getTextColor() {
     return textColor;
   }
 
   public void setTextColor(int textColor) {
	   this.textColor = textColor;
   }
 
   public float getTextSize() {
     return textSize;
   }
 
   public void setTextSize(float textSize) {
	   this.textSize = textSize;
   }
 
   public void setPreSize(int width, int height) {
     preWidth = width;
     preHeight = height;
   }
 
   public int getContentWidth() {
     return contentWidth;
   }
 
   public int getContentHeight() {
     return contentHeight;
   }
 
   public TextureData.TextureDataType getType()
   {
     return TextureData.TextureDataType.Custom;
   }
 
   public boolean isPrepared()
   {
     return isPrepared;
   }
 
   public void prepare()
   {
     mBtimap = getBitmap();
     if (mBtimap != null) {
       contentWidth = mBtimap.getWidth();
       contentHeight = mBtimap.getHeight();
       width = mBtimap.getWidth();
       height = mBtimap.getHeight();
     }
 
     isPrepared = (mBtimap != null);
   }
 
    @Override
	public Pixmap consumePixmap () {
		throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
	}

	@Override
	public boolean disposePixmap () {
		throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
	}
 
   public void update()
   {
     Bitmap bm = mBtimap;
     if ((bm != null) && (!bm.isRecycled())) {
       isPrepared = true;
 
       width = bm.getWidth();
       contentWidth = bm.getWidth();
 
       height = bm.getHeight();
       contentHeight = bm.getHeight();
       int format = GLUtils.getInternalFormat(bm);
       int type = GLUtils.getType(bm);
       Gdx.gl.glDisable(3042);
       Gdx.gl.glTexImage2D(3553, 0, format, bm.getWidth(), bm.getHeight(), 0, format, type, null);
       GLUtils.texSubImage2D(3553, 0, 0, 0, bm, format, type);
       Gdx.gl.glBindTexture(3553, 0);
       bm.recycle();
     }
   }
 
   private Bitmap getBitmap() {
     Bitmap bm = null;
     if (maxLine > 1) {
       if ((preWidth > 0) && (preHeight > 0)) {
         if ((shadowRadius > 0.0F) && (shadowDx > 0.0F) && (shadowDy > 0.0F))
           bm = StringTextureUtil.newMultilineStringBitmap(mText, preWidth, preHeight, maxLine, textSize, textColor, shadowRadius, shadowDx, shadowDy, shadowColor);
         else
           bm = StringTextureUtil.newMultilineStringBitmap(mText, preWidth, preHeight, maxLine, textSize, textColor);
       }
     }
     else if ((shadowRadius > 0.0F) && (shadowDx > 0.0F) && (shadowDy > 0.0F))
       bm = StringTextureUtil.newStringBitmap(mText, textSize, textColor, shadowRadius, shadowDx, shadowDy, shadowColor);
     else {
       bm = StringTextureUtil.newStringBitmap(mText, textSize, textColor);
     }
 
     return bm;
   }
 
   public int getWidth()
   {
     return width;
   }
 
   public int getHeight()
   {
     return height;
   }
 
   public Pixmap.Format getFormat()
   {
     return Pixmap.Format.RGBA8888;
   }
 
   public boolean useMipMaps()
   {
     return false;
   }
 
  public boolean isManaged()
  {
    return false;
  }

  public boolean dispose() {
    if (mBtimap != null) {
      mBtimap.recycle();
    }
    return false;
  }

	@Override
	public void consumeCustomData(int target) {
		// TODO Auto-generated method stub
		Bitmap bm = mBtimap;
	    if ((bm != null) && (!bm.isRecycled()))
	    {
	       GLUtils.texImage2D(3553, 0, bm, 0);
	       width = bm.getWidth();
	       height = bm.getHeight();
	 
	       bm.recycle();
	    }
	}
}
