package com.xpeppers.goosegame;

import java.io.Console;
import java.io.PrintWriter;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Console console;

        if ((console = System.console()) != null) {
            PrintWriter writer = console.writer();
            writer.append("!!Welcome to the Goose game!!\n\n Please choose 'p' for play or 'q' for quit \n");
            writer.flush();
            String choice = console.readLine();
            if (choice.equalsIgnoreCase("p")){
                GooseGame game = new GooseGame();
                while (!game.finished()){
                    writer.append("Place your next move \n");
                    writer.flush();
                    String command = console.readLine();
                    if (command.equalsIgnoreCase("q")) {
                        exit(writer);
                    }else{
                        writer.write(play(command, game));
                        writer.flush();
                    }
                }
                writer.write("Game Over\n");
                exit(writer);
               
            }else{
                exit(writer);
            }
        }

    }

    private static void exit(PrintWriter writer) {
        writer.write("Bye \n");
        writer.flush();
        System.exit(0);
    }

    private static String play(String command, GooseGame game) {
        String response = "";
        Pattern addPlayerP = Pattern.compile("add\\s+player\\s+(\\w+)");
        Pattern movePlayerP = Pattern.compile("move\\s+(\\w+)(\\s+([1-6]),\\s*([1-6]))?");

        var addPlayerM = addPlayerP.matcher(command);
        var movePlayerM = movePlayerP.matcher(command);
        if (addPlayerM.matches()){
            String playerName = addPlayerM.group(1);

            response = Decoder.parseResponse(game.addPlayer(playerName));

        }else if (movePlayerM.matches()){
            String playerName = movePlayerM.group(1);
            Random r = new Random();
            int roll1 = movePlayerM.group(3) == null ? (r.nextInt(6)+1) : Integer.parseInt(movePlayerM.group(3));
            int roll2 = movePlayerM.group(4) == null ? (r.nextInt(6)+1) : Integer.parseInt(movePlayerM.group(4));
            response = Decoder.parseResponse(game.move(playerName,roll1,roll2));
        }else{
            response = "Invalid command.Available commands are:\n '- add player <player>'\n '- move <player> <roll1> <roll2>'\n";
        }
        return response+"\n";
    }
}
