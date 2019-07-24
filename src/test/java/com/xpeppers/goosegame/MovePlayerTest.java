package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static com.xpeppers.goosegame.response.Response.Status.*;


import org.junit.jupiter.api.Test;
import com.xpeppers.goosegame.response.*;

public class MovePlayerTest {

    @Test
    public void moveNotExistingPlayer(){
        GooseGame goosegame = new GooseGame();
        Response<PlayerStatus> response = goosegame.move("pippo",4,2);
        assertTrue(response.status.equals(PLAYER_NOT_EXISTS));
    }

    @Test
    public void moveByInvalidRoll1(){
        GooseGame goosegame = new GooseGame();
        goosegame.addPlayer("pippo");
        Response<PlayerStatus> response = goosegame.move("pippo",8,2);
        assertTrue(response.status.equals(INVALID_ROLL));
    }

    @Test
    public void moveByInvalidRoll2(){
        GooseGame goosegame = new GooseGame();
        goosegame.addPlayer("pippo");
        Response<PlayerStatus> response = goosegame.move("pippo",2,10);
        assertTrue(response.status.equals(INVALID_ROLL));

    }

    @Test
    public void moveByInvalidNegativeRoll(){
        GooseGame goosegame = new GooseGame();
        goosegame.addPlayer("pippo");
        Response<PlayerStatus> response = goosegame.move("pippo",2,-1);
        assertTrue(response.status.equals(INVALID_ROLL));

    }

    @Test
    public void moveByInvalidZeroRoll(){
        GooseGame goosegame = new GooseGame();
        goosegame.addPlayer("pippo");
        Response<PlayerStatus> response = goosegame.move("pippo",0,5);
        assertTrue(response.status.equals(INVALID_ROLL));

    }

    @Test
    public void movePlayer(){
        GooseGame goosegame = new GooseGame();
        goosegame.addPlayer("pippo");
        Response<PlayerStatus> response = goosegame.move("pippo",4,3);
        assertTrue(response.status.equals(OK));
        PlayerStatus player = response.payload;
        assertTrue(player.startPosition().equals("Start"));
        assertTrue(player.lastPosition().equals("7"));


    }

    @Test
    public void simpleGameSimulation(){
        /*
            If there are two participants "Pippo" and "Pluto" on space "Start"
        */
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");
        gooseGame.addPlayer("Pluto");
        
        /*
            the user writes: "move Pippo 4, 3"
            the system responds: "Pippo rolls 4, 3. Pippo moves from Start to 7"
        */
        Response<PlayerStatus> rsp1 = gooseGame.move("Pippo", 4, 3);
        assertTrue(rsp1.status.equals(OK));
        PlayerStatus st1 = rsp1.payload;
        assertTrue(st1.lastPosition().equals("7"));
        
    
        /*

            the user writes: "move Pluto 2, 2"
            the system responds: "Pluto rolls 2, 2. Pluto moves from Start to 4"
        */

        Response<PlayerStatus> rsp2 = gooseGame.move("Pluto", 2, 2);
        assertTrue(rsp2.status.equals(OK));
        PlayerStatus st2 = rsp2.payload;
        assertTrue(st2.lastPosition().equals("4"));
        
        /*
            the user writes: "move Pippo 2, 3"
            the system responds: "Pippo rolls 2, 3. Pippo moves from 7 to 12"
        */

        Response<PlayerStatus> rsp3 = gooseGame.move("Pippo", 2, 3);
        assertTrue(rsp3.status.equals(OK));
        PlayerStatus st3 = rsp3.payload;
        assertTrue(st3.lastPosition().equals("12"));

    }


    @Test
    public void playerWin() {
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");

        // go to 60
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        Response<PlayerStatus> to60 = gooseGame.move("Pippo", 6, 6);
        assertTrue(to60.payload.lastPosition().equals("60"));
        

        Response<PlayerStatus> win =gooseGame.move("Pippo",1,2);
        assertTrue(win.status.equals(WIN));
        assertTrue(win.payload.lastPosition().equals(String.valueOf(GooseGame.END)));
    }

    @Test
    public void playerBounce() {
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");

        // go to 60
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        gooseGame.move("Pippo", 6, 6);
        Response<PlayerStatus> to60 = gooseGame.move("Pippo", 6, 6);
        assertTrue(to60.payload.lastPosition().equals("60"));

        Response<PlayerStatus> bounce =gooseGame.move("Pippo",3,2);
        assertTrue(bounce.status.equals(BOUNCE));
        assertTrue(bounce.payload.lastPosition().equals("61"));
    }

    @Test
    public void bridgeJump() {
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");

        // go to 60
        gooseGame.move("Pippo", 2,2);
        Response<PlayerStatus> bridged = gooseGame.move("Pippo", 1,1);
    
        assertTrue(bridged.status.equals(BRIDGE));
        assertTrue(bridged.payload.lastPosition().equals("12"));
    }

    /*
    If there is one participant "Pippo" on space "3"
        assuming that the dice get 1 and 1
        when the user writes: "move Pippo"
        the system responds: "Pippo rolls 1, 1. Pippo moves from 3 to 5, The Goose. Pippo moves again and goes to 7"
        */
    @Test
    public void gooseMove(){

        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");

        gooseGame.move("Pippo", 1, 2);
        Response<PlayerStatus> gooseJump = gooseGame.move("Pippo",1,1);
        assertTrue(gooseJump.status.equals(GOOSE));
        assertTrue(gooseJump.payload.lastPosition().equals("7"));

    }

    @Test
    public void multipleGooseJumsp(){
        /*
        If there is one participant "Pippo" on space "10"
        assuming that the dice get 2 and 2
        when the user writes: "move Pippo"
        the system responds: "Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22"
        */
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");

        gooseGame.move("Pippo", 5, 5);
        Response<PlayerStatus> gooseJump = gooseGame.move("Pippo", 2, 2);
        assertTrue(gooseJump.status.equals(GOOSE));
        List<Integer> jumps = gooseJump.payload.moves;
        assertTrue(jumps.size() == 4);
        assertTrue(jumps.equals(List.of(10,14,18,22)));
    }

    @Test
    public void pranked(){
        GooseGame gooseGame = new GooseGame();
        gooseGame.addPlayer("Pippo");
        gooseGame.addPlayer("Pluto");
        gooseGame.move("Pippo", 2, 2);
        gooseGame.move("Pluto", 6, 5);

        Response<PlayerStatus> response = gooseGame.move("Pippo", 4, 3);
        PlayerStatus status = response.payload;
        assertTrue(status.pranked.size() == 1);
        assertTrue(status.pranked.contains("Pluto"));

        Response<PlayerStatus> plutoResponse = gooseGame.move("Pluto", 2, 2);
        assertTrue(plutoResponse.payload.startPosition().equals("4"));
        assertTrue(plutoResponse.payload.lastPosition().equals("8"));
    }
}