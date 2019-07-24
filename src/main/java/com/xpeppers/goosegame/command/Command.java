package com.xpeppers.goosegame.command;

public interface Command<T> {

     public Action Action();


    enum Action {
        ADD_PLAYER,
        MOVE,
        INVALID;
    }
    
}