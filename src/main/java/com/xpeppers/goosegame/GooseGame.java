package com.xpeppers.goosegame;

import static com.xpeppers.goosegame.data.Response.Status.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xpeppers.goosegame.data.PlayerStatus;
import com.xpeppers.goosegame.data.Response;

public class GooseGame {

    /**
     * Goose game engine
     */
    
    public static final int START = 0;
    public static final int END = 63;
    public static final int THE_BRIDGE = 6;
    public static final Set<Integer> gooses = Set.of(5, 9, 14, 18, 23, 27);

    private Map<String,Integer> players= new HashMap<String,Integer>();

	public Response<String> addPlayer(String name) {
        if (players.containsKey(name)){
            return new Response<String>(DUPLICATE_PLAYER, name);
        }
        
        players.put(name,START);
        return new Response<String>(OK,name);  
        

	}

	public Response<PlayerStatus> move(String name, int roll1, int roll2) {
        if (!players.containsKey(name)){
            return new Response<PlayerStatus>(PLAYER_NOT_EXISTS, new PlayerStatus(name, List.of(-1,-1)));
        }

        if (roll1 > 6 || roll1 < 1  || roll2 > 6 || roll2 < 1)  {
            Integer currentPos = players.get(name);
            return new Response<PlayerStatus>(INVALID_ROLL, new PlayerStatus(name,  List.of(currentPos)));
        }
        
        return manageMove( name,  roll1,  roll2);
        
	}

    private Response<PlayerStatus> manageMove(String  name,int roll1, int roll2  ) {
        Integer lastPos = players.get(name);
        int rolled = roll1 + roll2;
        Integer nextPos = lastPos + rolled;
        

        if (nextPos.equals(THE_BRIDGE)){
            // bridge
            nextPos = THE_BRIDGE * 2;
            players.put(name, nextPos);
            return new Response<PlayerStatus>(BRIDGE, new PlayerStatus(name, List.of(lastPos,nextPos)));
        }else if (gooses.contains(nextPos)){
            //goose
            int gooseJump = nextPos + rolled;
            players.put(name, gooseJump);
            return new Response<PlayerStatus>(GOOSE, new PlayerStatus(name, List.of(lastPos,gooseJump)));
        }else if (nextPos.equals(END)){
            // WIN
            players.put(name, END);
            return new Response<PlayerStatus>(WIN, new PlayerStatus(name, List.of(lastPos,END)));            
        }else if (nextPos > END){
            // bounce
            nextPos = END - (nextPos - END);
            players.put(name, nextPos);
            return new Response<PlayerStatus>(BOUNCE, new PlayerStatus(name, List.of(lastPos,nextPos)));
        }else{
            // otherwise
            players.put(name, nextPos);
            return new Response<PlayerStatus>(OK, new PlayerStatus(name, List.of(lastPos,nextPos)));
        }

        
    }

}