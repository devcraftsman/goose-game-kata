package com.xpeppers.goosegame;

import static com.xpeppers.goosegame.response.Response.Status.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.xpeppers.goosegame.response.PlayerStatus;
import com.xpeppers.goosegame.response.Response;


public class GooseGame {

    /**
     * Goose game engine
     */
    
    public static final int START = 0;
    public static final int END = 63;
    public static final int THE_BRIDGE = 6;
    public static final Set<Integer> gooses = Set.of(5, 9, 14, 18, 23, 27);

    private Map<String,Integer> players= new HashMap<String,Integer>();
    private boolean finished = false;

    public Integer playerCount() {
        return players.size();
    }

    public boolean finished(){
        return this.finished;
    }

	public Response<PlayerStatus> addPlayer(String name) {
        if (players.containsKey(name)){
            return new Response<PlayerStatus>(DUPLICATE_PLAYER, new PlayerStatus(name, -1,-1, Collections.EMPTY_LIST));
        }
        
        players.put(name,START);

        // because keysSet is ordered from most newer to older
        List<String> playersList= new ArrayList<>();
        // because default API can only reverse list
        playersList.addAll(players.keySet());
        Collections.reverse(playersList);
        
        String names = String.join(", ", playersList);
        return new Response<PlayerStatus>(PLAYER_CREATED, new PlayerStatus(names, -1,-1,Collections.EMPTY_LIST));

	}

	public Response<PlayerStatus> move(String name, int roll1, int roll2) {
        if (!players.containsKey(name)){
            return new Response<PlayerStatus>(PLAYER_NOT_EXISTS, new PlayerStatus(name, -1,-1,List.of(-1,-1)));
        }

        if (roll1 > 6 || roll1 < 1  || roll2 > 6 || roll2 < 1)  {
            Integer currentPos = players.get(name);
            return new Response<PlayerStatus>(INVALID_ROLL, new PlayerStatus(name, roll1, roll2, List.of(currentPos)));
        }
        
        return manageMove( name,  roll1,  roll2);
        
	}

    private Response<PlayerStatus> manageMove(String  name,int roll1, int roll2) {
        List<Integer> jumps = new ArrayList<>();
        Response.Status status = OK;

        final Integer lastPos = players.get(name);
        jumps.add(lastPos);

        int rolled = roll1 + roll2;
        Integer nextPos = lastPos + rolled;
        

        if (nextPos.equals(THE_BRIDGE)){
            // bridge
            nextPos = THE_BRIDGE * 2;
            players.put(name, nextPos);
            jumps.add(nextPos);
            status = BRIDGE;
        }else if (gooses.contains(nextPos)){
            //goose
            while (gooses.contains(nextPos)){
                jumps.add(nextPos);
                nextPos += rolled;
            }
            jumps.add(nextPos);
            players.put(name, nextPos);
            status = GOOSE;
        }else if (nextPos.equals(END)){
            // WIN
            players.put(name, END);
            jumps.add(END);
            status = WIN;
            this.finished = true;            
        }else if (nextPos > END){
            // bounce
            nextPos = END - (nextPos - END);
            players.put(name, nextPos);
            jumps.add(nextPos);
            status = BOUNCE;
        }else{
            // otherwise
            players.put(name, nextPos);
            jumps.add(nextPos);
        }

        //check pranked
        final Integer position = nextPos; // to acces nextPos from lambda copy to a final variable
        List<String> pranked = players.entrySet().stream()
            .filter((e) -> !e.getKey().equals(name) && e.getValue().equals(position))
            .map((e) -> e.getKey()).collect(Collectors.toList());
        if (pranked.isEmpty()){
            return new Response<PlayerStatus>(status, new PlayerStatus(name, roll1, roll2, jumps));
        }else{
            players.entrySet().stream()
            .filter((e) -> pranked.contains(e.getKey()))
            .forEach((e) -> e.setValue(lastPos));
            return new Response<PlayerStatus>(status, new PlayerStatus(name, roll1, roll2, jumps,pranked));
        }

        

        
    }

}