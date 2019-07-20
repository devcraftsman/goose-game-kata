package com.xpeppers.goosegame;

import static com.xpeppers.goosegame.data.Response.Status.*;

import java.util.HashMap;
import java.util.Map;

import com.xpeppers.goosegame.data.PlayerStatus;
import com.xpeppers.goosegame.data.Response;

public class GooseGame {

    private Map<String,Integer> players= new HashMap<String,Integer>();

	public Response<String> addPlayer(String name) {
        if (players.containsKey(name)){
            return new Response<String>(DUPLICATE_PLAYER, name);
        }
        
        players.put(name,0);
        return new Response<String>(OK,name);  
        

	}

	public Response<PlayerStatus> move(String name, int roll1, int roll2) {
        if (!players.containsKey(name)){
            return new Response<PlayerStatus>(PLAYER_NOT_EXISTS, new PlayerStatus(name, -1, -1));
        }

        if (roll1 > 6 || roll1 < 1  || roll2 > 6 || roll2 < 1)  {
            Integer currentPos = players.get(name);
            return new Response<PlayerStatus>(INVALID_ROLL, new PlayerStatus(name, currentPos,currentPos));
        }
        
        Integer lastPos = players.get(name);
        Integer nextPos = lastPos + roll1 + roll2;
        players.put(name, nextPos);

        return new Response<PlayerStatus>(OK, new PlayerStatus(name, lastPos, nextPos));
	}

}