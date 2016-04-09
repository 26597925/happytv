/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 焦点组，查找焦点
 */
package com.mylove.happy.tv.focus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.view.KeyEvent;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;

public class FocusGroup {
	Stage stage;
	List<Focus> focuses;//查找的焦点
	List<Focus> filters;
	FocusFinder finder;
	Focus focusActor;

	public FocusGroup(Stage stage){
		this.stage = stage;
		focuses = new ArrayList<Focus>();
		filters = new ArrayList<Focus>(); 
	}
	
	public List<Focus> getGroup(){
		return focuses;
	}
	
	public List<Focus> getfilter(){
		return filters;
	}
	
	public void AddGroup(Focus focus){
		if(!focuses.contains(focus))
			focuses.add(focus);
	}
	
	public void AddGroup(Actor actor){
		Focus focusActor = new Focus(actor.getX(), actor.getY(), actor);
		AddGroup(focusActor);
	}
	
	public Focus getFocus(Actor actor){
		Iterator<Focus> iter = focuses.iterator();
		while(iter.hasNext())  
        {  
			Focus focus = iter.next();
			if(focus.getActor() == actor){
				return focus;
			}
        }
		return null;
	}
	
	public Focus getCurrentFocus(){
		return focusActor;
	}
	
	//查找所有子元素的焦点
	public void findChildrenFocus(Group group){
		clear();
		SnapshotArray<Actor> children = group.getChildren();
		for(int i = 0, n = children.size; i < n; i++){
			AddGroup(children.get(i));
		}
	}
	
	public void removeGroup(Focus focus){
		if(focuses.contains(focus))
			focuses.remove(focus);
	}
	
	public void requestFocus(Actor actor){
		stage.setKeyboardFocus(actor);
	}
	
	public Focus findNextFocus(Group group, Actor actor){
		findChildrenFocus(group);
		return findNextFocus(actor, 0);
	}
	
	public Focus findNextFocus(Actor actor, int direction){
		if(direction == 0){
			focusActor = new Focus(actor.getX(), actor.getY(), actor);
			filters.addAll(focuses);
		}else{
			focusActor = getFocus(actor);
			if(focusActor == null) return focusActor;
			filterFocus(actor, direction);
		}
		finder = new FocusFinder(getfilter());
		if(finder.getRoot() == null) return focusActor;
		Focus nextFocusActor = finder.findNextFocus(focusActor, finder.getRoot());
		requestFocus(nextFocusActor.getActor());
		filters.clear();
		return nextFocusActor;
	}
	
	public void clear(){
		focuses.clear();
		filters.clear();
	}
	
	void filterFocus(Actor actor, int direction){
		float x = actor.getX();
		float y = actor.getY();
		Iterator<Focus> iter = focuses.iterator();
		while(iter.hasNext())
        {  
			Focus focus = iter.next();
			focus.refresh();
			Actor factor = focus.getActor();
			if(direction == KeyEvent.KEYCODE_DPAD_UP){
				if(factor.getY()>y){
					filters.add(focus);
				}
			}else if(direction == KeyEvent.KEYCODE_DPAD_DOWN){
				if(factor.getY()<y){
					filters.add(focus);
				}
			}else if(direction == KeyEvent.KEYCODE_DPAD_LEFT){
				if(factor.getX()<x){
					filters.add(focus);
				}
			}else{
				if(factor.getX()>x){
					filters.add(focus);
				}
			}
        }
	}
}
