package com.xpeppers.goosegame;

import java.util.Random;
import java.util.regex.Pattern;
import com.xpeppers.goosegame.command.*;

public abstract class CommandParser {

    @SuppressWarnings("rawtypes")
    public static Command parse(String command) {
        Pattern addPlayerP = Pattern.compile("add\\s+player\\s+(\\w+)");
        Pattern movePlayerP = Pattern.compile("move\\s+(\\w+)(\\s+([1-6]),\\s*([1-6]))?");

        var addPlayerM = addPlayerP.matcher(command);
        var movePlayerM = movePlayerP.matcher(command);
        if (addPlayerM.matches()){
            String playerName = addPlayerM.group(1);
            return new AddPlayerCommand(playerName);
        }else if (movePlayerM.matches()){
            String playerName = movePlayerM.group(1);
            Random r = new Random();
            int roll1 = movePlayerM.group(3) == null ? (r.nextInt(6)+1) : Integer.parseInt(movePlayerM.group(3));
            int roll2 = movePlayerM.group(4) == null ? (r.nextInt(6)+1) : Integer.parseInt(movePlayerM.group(4));
            return new MovePlayerCommand(playerName,roll1, roll2);
        }else{
            return new InvalidCommand();
        }
    }
    
}