package com.xpeppers.goosegame;

import java.util.List;

import com.xpeppers.goosegame.response.PlayerStatus;
import com.xpeppers.goosegame.response.Response;

public  abstract class Decoder {

    public static final String parseResponse(Response<PlayerStatus> response) {
        String parsed = "";
        PlayerStatus playerdata = response.payload;
        switch (response.status) {
            case OK:
                parsed = parseHeader(playerdata)+ playerdata.lastPosition();
                break;
            case PLAYER_CREATED:
                parsed = "players: "+ playerdata.name;
                break;    
            case DUPLICATE_PLAYER:
                parsed = playerdata.name + ": already existing player";
                break;
            case PLAYER_NOT_EXISTS:
                parsed = playerdata.name + ": player does not exists";
                break;
            case INVALID_ROLL:
                parsed = playerdata.name + ": invalid dice roll. Dice can roll 1 to 6 only";
                break;
            case WIN:
                parsed = parseHeader(playerdata)+ playerdata.lastPosition() +". "+ playerdata.name +" Wins!!";
                break;
            case BOUNCE:
                parsed = parseHeader(playerdata)+ GooseGame.END +". "+ playerdata.name + " bounces! "+ playerdata.name +" returns to " + playerdata.lastPosition();
                break;
            case BRIDGE:
                parsed = playerdata.name + " rolls "+ playerdata.roll1+", "+playerdata.roll2+". "+ playerdata.name + " moves from "+ playerdata.startPosition()+" to The Bridge. "+ playerdata.name +" jumps to "+playerdata.lastPosition();
                break;
            case GOOSE:
                parsed = parseGooseResponse(playerdata);
                break;
            default: 
             parsed = "";
             break;
        }
        if (playerdata.isPranked()){
            for (String player : playerdata.pranked){
               parsed += ". On "+playerdata.lastPosition()+" there is "+player+", who returns to "+ playerdata.startPosition();
            }
        }
        return parsed;
    }

    private static String parseGooseResponse(PlayerStatus playerdata) {
        StringBuilder builder = new StringBuilder();
        List<Integer> moves = playerdata.moves;
        /*
        "Pippo rolls 2, 2. Pippo moves from 10 ro <-start
        14, The Goose. <- index 1
        Pippo moves again and goes to 18, The Goose. <- from 2 to size-1
        Pippo moves again and goes to 22" <-last */

        builder.append(parseHeader(playerdata));
        builder.append(moves.get(1)+", The Goose. ");
        for (Integer move : moves.subList(2, moves.size()-1)){
            builder.append(playerdata.name +" moves again and goes to "+move+", The Goose. ");
        }
        builder.append(playerdata.name +" moves again and goes to "+playerdata.lastPosition());
        return builder.toString();
    }

    private static final String parseHeader(PlayerStatus playerdata) {
        return playerdata.name + " rolls "+ playerdata.roll1+", "+playerdata.roll2+". "+ playerdata.name + " moves from "+ playerdata.startPosition() +" to ";
    }
    
}