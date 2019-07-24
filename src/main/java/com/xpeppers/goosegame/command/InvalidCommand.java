package com.xpeppers.goosegame.command;

public class InvalidCommand implements Command<Void> {

    @Override
    public Action Action() {
        return Action.INVALID;
    }


    
}