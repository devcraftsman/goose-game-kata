package com.xpeppers.goosegame.response;

import java.util.Collections;
import java.util.List;


/**
 * Player Status Value Object
 */

public class PlayerStatus{

    public final String name;
    public final int roll1;
    public final int roll2;
    public final List<Integer> moves;
    // because it is an op
    public final List<String> pranked;


    public PlayerStatus(String name, int roll1, int roll2, List<Integer> moves){
        this(name, roll1, roll2, moves,Collections.EMPTY_LIST);
    }

    public PlayerStatus(String name){
        this(name, -1, -1, Collections.EMPTY_LIST,Collections.EMPTY_LIST);

    }

    public PlayerStatus(String name, int roll1, int roll2, List<Integer> moves, List<String> pranked){
        this.name = name;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.moves = moves;
        this.pranked = pranked;

    }

    public String startPosition(){
        int move = moves.size() > 0 ? moves.get(0) : -1;
        return move == 0 ? "Start" : String.valueOf(move);
    }

    public String lastPosition(){
        int move = moves.size() > 0 ? moves.get(moves.size()-1) : -1;
        return move == 0 ? "Start" : String.valueOf(move);
    }

    public boolean isPranked(){
        return pranked.size() >0;
    }

}
