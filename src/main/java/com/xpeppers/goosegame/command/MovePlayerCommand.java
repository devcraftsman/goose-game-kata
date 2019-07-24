package com.xpeppers.goosegame.command;

import java.util.List;

public class MovePlayerCommand extends PlayerCommand implements Command<List<Integer>> {

    private List<Integer> moves;

    public MovePlayerCommand(String playerName, Integer roll1, Integer roll2){
        this.playerName = playerName;
        this.moves = List.of(roll1,roll2);
    }

    public List<Integer> moves() {
        return this.moves;
    }

    @Override
    public Action Action() {
        return Action.MOVE;
    }
    
}