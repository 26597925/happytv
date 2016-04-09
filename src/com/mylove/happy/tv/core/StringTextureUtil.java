package com.mylove.happy.tv.core;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mylove.happy.tv.InGame;

public class StringTextureUtil
{
  public static Bitmap newStringBitmap(String text, int width, int height, TextPaint paint)
  {
    Bitmap.Config config = Bitmap.Config.ARGB_8888;

    Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
    width = width > 0 ? width : (int)Math.ceil(paint.measureText(text));
    height = height > 0 ? height : metrics.bottom - metrics.top;
    if ((width <= 0) || (height <= 0)) {
      return null;
    }
    Bitmap bitmap = Bitmap.createBitmap(width, height, config);
    Canvas canvas = new Canvas(bitmap);
    canvas.translate(0.0F, -metrics.ascent);
    canvas.drawText(text, 0.0F, 0.0F, paint);
    return bitmap;
  }

  public static Bitmap newMultilineStringBitmap(String text, int width, int height, int maxLines, TextPaint paint)
  {
    Layout layout = new StaticLayout(text, 0, text.length(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true, null, 0);

    Bitmap.Config config = Bitmap.Config.ARGB_8888;

    Bitmap bitmap = Bitmap.createBitmap(width, height, config);

    Canvas canvas = new Canvas(bitmap);

    int lineCount = layout.getLineCount();

    if ((maxLines >= 0) && (maxLines < lineCount)) {
      int clipBottom = layout.getLineBottom(maxLines);
      canvas.clipRect(0, 0, width, clipBottom);
    }
    layout.draw(canvas);

    return bitmap;
  }

  public static Bitmap newMultilineStringBitmap(String text, int width, int height, int maxLines, float textSize, int color)
  {
    return newMultilineStringBitmap(text, width, height, maxLines, getDefaultPaint(textSize, color));
  }

  public static Bitmap newMultilineStringBitmap(String text, int width, int height, int maxLines, float textSize, int color, float shadowRadius, float shadowDx, float shadowDy, int shadowColor)
  {
    TextPaint paint = getDefaultPaint(textSize, color);
    paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
    return newMultilineStringBitmap(text, width, height, maxLines, paint);
  }

  public static byte[] newMultilineStringBytes(String text, int width, int height, int maxLines, float textSize, int color)
  {
    return bitmapToByte(newMultilineStringBitmap(text, width, height, maxLines, getDefaultPaint(textSize, color)));
  }

  public static int getTextWidth(String text, float textSize) {
    TextPaint paint = getDefaultPaint(textSize, -1);
    return (int)Math.ceil(paint.measureText(text));
  }

  public static Bitmap newStringBitmap(String text, TextPaint paint) {
    return newStringBitmap(text, -1, -1, paint);
  }

  public static Bitmap newStringBitmap(String text, int width, int height, float textSize, int color)
  {
    return newStringBitmap(text, width, height, getDefaultPaint(textSize, color));
  }

  public static Bitmap newStringBitmap(String text, float textSize, int color)
  {
    return newStringBitmap(text, getDefaultPaint(textSize, color));
  }

  public static Bitmap newStringBitmap(String text, float textSize, int color, float shadowRadius, float shadowDx, float shadowDy, int shadowColor)
  {
    TextPaint paint = getDefaultPaint(textSize, color);
    paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
    return newStringBitmap(text, paint);
  }

  public static byte[] newStringBytes(String text, float textSize, int color)
  {
    return bitmapToByte(newStringBitmap(text, getDefaultPaint(textSize, color)));
  }

  public static TextPaint getDefaultPaint(float textSize, int color)
  {
    TextPaint paint = new TextPaint();
    paint.setTextSize(textSize);
    paint.setAntiAlias(true);
    paint.setColor(color);
    paint.density = 2.0F;
   /* if (App.font != null) {
      paint.setTypeface(App.font);
    }*/

    return paint;
  }

  public static Texture newStringTexture(InGame game, String text, int width, int height, float textSize, int color)
  {
    Bitmap bitmap = newStringBitmap(text, width, height, textSize, color);

    byte[] data = bitmapToByte(bitmap);
    Pixmap pixmap = PixmapFactory.newPixmap(game, data, 0, data.length);
    Texture texture = TextureFactory.newTexture(game, pixmap);
    pixmap.dispose();
    bitmap.recycle();

    return texture;
  }

  public static Pixmap newStringPixmap(InGame game, String text, int width, int height, float textSize, int color)
  {
    Bitmap bitmap = newStringBitmap(text, width, height, textSize, color);

    byte[] data = bitmapToByte(bitmap);
    Pixmap pixmap = PixmapFactory.newPixmap(game, data, 0, data.length);
    bitmap.recycle();

    return pixmap;
  }

  public static Texture newMultiLineStringTexture(InGame game, String text, int width, int height, int maxLines, float textSize, int color)
  {
    Bitmap bitmap = newMultilineStringBitmap(text, width, height, maxLines, getDefaultPaint(textSize, color));

    byte[] data = bitmapToByte(bitmap);
    Pixmap pixmap = PixmapFactory.newPixmap(game, data, 0, data.length);
    Texture texture = TextureFactory.newTexture(game, pixmap);
    pixmap.dispose();
    bitmap.recycle();

    return texture;
  }

  public static Pixmap newMultiLineStringPixmap(InGame game, String text, int width, int height, int maxLines, float textSize, int color)
  {
    Bitmap bitmap = newMultilineStringBitmap(text, width, height, maxLines, getDefaultPaint(textSize, color));

    byte[] data = bitmapToByte(bitmap);
    Pixmap pixmap = PixmapFactory.newPixmap(game, data, 0, data.length);
    bitmap.recycle();

    return pixmap;
  }

  public static Texture newStringTexture(InGame game, String text, float textSize, int color)
  {
     return newStringTexture(game, text, -1, -1, textSize, color);
   }
 
   public static Pixmap newStringPixmap(InGame game, String text, float textSize, int color) {
     return newStringPixmap(game, text, -1, -1, textSize, color);
   }
 
   public static byte[] bitmapToByte(Bitmap bitmap)
   {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
     byte[] r = baos.toByteArray();
 
     return r;
   }
 }