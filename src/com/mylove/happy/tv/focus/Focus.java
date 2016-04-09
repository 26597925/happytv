/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark kd邻近算法，通过焦点获取点位值
 */
package com.mylove.happy.tv.focus;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Focus
{
    private double x;

    private double y;
    
    private Actor actor;

    public Focus(double x, double y, Actor actor)
    {
        this.x = x;
        this.y = y;
        this.actor = actor;
    }
    public double getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setActor(Actor actor){
    	this.actor = actor;
    }
    
    public Actor getActor(){
    	return actor;
    }
    
    public void refresh(){
    	this.x = actor.getX();
    	this.y = actor.getY();
    }
    
    @Override public String toString()
    {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
