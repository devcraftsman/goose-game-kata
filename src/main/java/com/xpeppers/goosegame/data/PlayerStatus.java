package com.xpeppers.goosegame.data;

import java.util.List;

/**
 * Player Status Value Object
 */
public class PlayerStatus{

    public final String name;
    public final int roll1;
    public final int roll2;
    public final List<Integer> moves;

    public PlayerStatus(String name, int roll1, int roll2, List<Integer> moves){
        this.name = name;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.moves = moves;
    }

    public Integer startPosition(){
        return moves.size() > 0 ? moves.get(0) : -1;
    }

    public Integer lastPosition(){
        return moves.size() > 0 ? moves.get(moves.size()-1) : -1;
    }

}
