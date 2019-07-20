package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.xpeppers.goosegame.data.Response.Status.*;


import org.junit.jupiter.api.Test;
import com.xpeppers.goosegame.data.*;

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

        Response<PlayerStatus> response = goosegame.move("pippo",4,2);
        assertTrue(response.status.equals(OK));
        PlayerStatus player = response.payload;
        assertTrue(player.lastPos == 0);
        assertTrue(player.currentPos == 6);


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
            the user writes: "move Pippo 4, 2"
            the system responds: "Pippo rolls 4, 2. Pippo moves from Start to 6"
        */
        Response<PlayerStatus> rsp1 = gooseGame.move("Pippo", 4, 2);
        assertTrue(rsp1.status.equals(OK));
        PlayerStatus st1 = rsp1.payload;
        assertTrue(st1.currentPos == 6);
        
        
        /*

            the user writes: "move Pluto 2, 2"
            the system responds: "Pluto rolls 2, 2. Pluto moves from Start to 4"
        */

        Response<PlayerStatus> rsp2 = gooseGame.move("Pluto", 2, 2);
        assertTrue(rsp2.status.equals(OK));
        PlayerStatus st2 = rsp2.payload;
        assertTrue(st2.currentPos == 4);
        
        /*
            the user writes: "move Pippo 2, 3"
            the system responds: "Pippo rolls 2, 3. Pippo moves from 6 to 11"
        */

        Response<PlayerStatus> rsp3 = gooseGame.move("Pippo", 2, 3);
        assertTrue(rsp3.status.equals(OK));
        PlayerStatus st3 = rsp3.payload;
        assertTrue(st3.currentPos == 11);

    }
}