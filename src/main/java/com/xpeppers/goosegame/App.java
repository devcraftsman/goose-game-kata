package com.xpeppers.goosegame;

import java.io.Console;
import java.io.PrintWriter;

import com.xpeppers.goosegame.command.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();

    }

    public void run() {
        Console console;

        if ((console = System.console()) != null) {
            PrintWriter writer = console.writer();
            writer.append("!!Welcome to the Goose game!!\n\n Please choose 'p' for play or 'q' for quit \n");
            writer.flush();
            String choice = console.readLine();
            if (choice.equalsIgnoreCase("p")) {
                GooseGame game = new GooseGame();
                while (!game.finished()) {
                    move(console, writer, game);
                }
                writer.write("Game Over\n");
                exit(writer);
            } else {
                exit(writer);
            }
        }
    }

    private void move(Console console, PrintWriter writer, GooseGame game) {
        writer.append("Place your next move \n");
        writer.flush();
        String command = console.readLine();
        if (command.equalsIgnoreCase("q")) {
            exit(writer);
        } else {
            writer.write(play(command, game));
            writer.flush();
        }
    }

    private void exit(PrintWriter writer) {
        writer.write("Bye \n");
        writer.flush();
        System.exit(0);
    }

    private String play(String commandString, GooseGame game) {
        String response = "\n";

        var command = CommandParser.parse(commandString.trim());

        switch (command.Action()) {
            case ADD_PLAYER:
                AddPlayerCommand addCmd = (AddPlayerCommand) command;
                response = Decoder.parseResponse(game.addPlayer(addCmd.playerName()));
                break;
            case MOVE:
                MovePlayerCommand mvCmd = (MovePlayerCommand) command;
                response = Decoder.parseResponse(game.move(mvCmd.playerName(), mvCmd.moves().get(0), mvCmd.moves().get(1)));
                break;
            case INVALID:
                response = "Invalid command.\nAvailable commands are:\n '- add player <player>'\n '- move <player> <roll1> <roll2>'\n";
                break;
            default:
                break;
        }
        return response + "\n";
    }
}
