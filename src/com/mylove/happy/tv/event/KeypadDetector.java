package com.mylove.happy.tv.event;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mylove.happy.tv.actor.ScreenActor;
import com.mylove.happy.tv.actor.ScreenActor.Orientation;
import com.mylove.happy.tv.util.LogUtil;

public class KeypadDetector extends InputAdapter{
	Stage stage;
	public KeypadDetector(Stage stage){
		this.stage = stage;
	}
	public boolean keyDown (int keycode) {
		if(Input.Keys.BACK == keycode){
			LogUtil.e(this, String.valueOf(keycode));
			return true;
		}
		if(Input.Keys.LEFT == keycode){
			ScreenActor screen = (ScreenActor) stage.getRoot().findActor("screen");
		//	screen.switchScreen(Orientation.LEFT);
		}
		if(Input.Keys.RIGHT == keycode){
			ScreenActor screen = (ScreenActor) stage.getRoot().findActor("screen");
		//	screen.switchScreen(Orientation.RIGHT);
		}
		if(Input.Keys.DOWN == keycode){
			
		}
		if(Input.Keys.UP == keycode){
			
		}
		return false;
	}
}
