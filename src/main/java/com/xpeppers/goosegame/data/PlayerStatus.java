package com.xpeppers.goosegame.data;

import java.util.List;

/**
 * Player Status Value Object
 */
public class PlayerStatus{

    public final String name;
    public final List<Integer> moves;

    public PlayerStatus(String name, List<Integer> moves){
        this.name = name;
        this.moves = moves;
    }

    public int startPosition(){
        return moves.size() > 0 ? moves.get(0) : -1;
    }

    public int lastPosition(){
        return moves.size() > 0 ? moves.get(moves.size()-1) : -1;
    }

}
