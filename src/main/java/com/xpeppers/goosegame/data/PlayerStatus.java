package com.xpeppers.goosegame.data;

/**
 * Player Status Value Object
 */
public class PlayerStatus{

    public final String name;
    public final int lastPos;
    public final int currentPos;

    public PlayerStatus(String name, int lastPos,  int currentPos){
        this.name = name;
        this.lastPos = lastPos;
        this.currentPos = currentPos;
    }

}
