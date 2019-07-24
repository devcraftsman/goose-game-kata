package com.xpeppers.goosegame.command;

public class AddPlayerCommand extends PlayerCommand implements Command<Void> {

    public AddPlayerCommand(String playerName){
        this.playerName = playerName;
    }
    
    @Override
    public Action Action() {
        return Action.ADD_PLAYER;
    }


    
}