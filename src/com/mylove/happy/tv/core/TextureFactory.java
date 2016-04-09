package com.mylove.happy.tv.core;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.mylove.happy.tv.InGame;

public class TextureFactory
{
  public static Texture newTexture(InGame game, String internalPath)
  {
	  Texture texture = new Texture(internalPath);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, FileHandle file) {
	  Texture texture = new Texture(file);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, FileHandle file, boolean useMipMaps) {
	  Texture texture = new Texture(file, useMipMaps);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, FileHandle file, Pixmap.Format format, boolean useMipMaps) {
	  Texture texture = new Texture(file, format, useMipMaps);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, Pixmap pixmap) {
	  Texture texture = new Texture(pixmap);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, Pixmap pixmap, boolean useMipMaps) {
	  Texture texture = newTexture(game, pixmap, null, useMipMaps);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
	  Texture texture = new Texture(pixmap, format, useMipMaps);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, int width, int height, Pixmap.Format format) {
	  Texture texture = new Texture(width, height, format);
	  game.registerTexture(texture);
	  return texture;
  }

  public static Texture newTexture(InGame game, TextureData data) {
	  Texture texture = new Texture(data);
	  game.registerTexture(texture);
	  return texture;
  }
}