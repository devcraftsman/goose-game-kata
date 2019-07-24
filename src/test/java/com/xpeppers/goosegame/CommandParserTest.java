package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.xpeppers.goosegame.command.Command.Action.*;

import com.xpeppers.goosegame.command.*;

import org.junit.jupiter.api.Test;

public class CommandParserTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void parseInvalidCommand() {
        String command = "Hello World";
        Command cmd = CommandParser.parse(command);
        assertEquals(INVALID, cmd.Action());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseVoidStringToInvalidCommand() {
        String command = "";
        Command cmd = CommandParser.parse(command);
        assertEquals(INVALID, cmd.Action());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseAddPlayer() {
        String command = "add player pippo";
        Command cmd = CommandParser.parse(command);
        assertEquals(ADD_PLAYER, cmd.Action());
        AddPlayerCommand addCmd = (AddPlayerCommand) cmd;
        assertEquals("pippo", addCmd.playerName());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseFullMovePlayer() {
        String command = "move pippo 3, 2";
        Command cmd = CommandParser.parse(command);
        assertEquals(MOVE, cmd.Action());
        MovePlayerCommand mvCmd = (MovePlayerCommand) cmd;
        assertEquals(2, mvCmd.moves().size());
        assertEquals(3, mvCmd.moves().get(0));
        assertEquals(2, mvCmd.moves().get(1));
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseRandomMovePlayer() {
        String command = "move pippo";
        Command cmd = CommandParser.parse(command);
        assertEquals(MOVE, cmd.Action());
        MovePlayerCommand mvCmd = (MovePlayerCommand) cmd;
        assertEquals(2, mvCmd.moves().size());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseInvalidFirstRoll() {
        String command = "move pippo 7, 2";
        Command cmd = CommandParser.parse(command);
        assertEquals(INVALID, cmd.Action());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void parseInvalidSecondRoll() {
        String command = "move pippo 1, 10";
        Command cmd = CommandParser.parse(command);
        assertEquals(INVALID, cmd.Action());
    }

}