package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xpeppers.goosegame.response.PlayerStatus;
import com.xpeppers.goosegame.response.Response;
import static com.xpeppers.goosegame.response.Response.Status.*;

import org.junit.jupiter.api.Test;

public class UserAddTest {

    @Test 
    public void addNewUserTest(){   
        GooseGame game = new GooseGame();

        Response<PlayerStatus> response = game.addPlayer("pippo");
        assertTrue(response.status.equals(PLAYER_CREATED));
        assertTrue(response.payload.name.equals("pippo"));
    }

    
    @Test 
    public void addOneMoreUserTest(){   
        GooseGame game = new GooseGame();

        Response<PlayerStatus> response = game.addPlayer("pippo");
        assertTrue(response.status.equals(PLAYER_CREATED));
        assertTrue(response.payload.name.equals("pippo"));

        Response<PlayerStatus> response2 = game.addPlayer("pluto");
        assertTrue(response2.status.equals(PLAYER_CREATED));
        assertTrue(response2.payload.name.equals("pippo, pluto"));
    }

    @Test
    public void addDuplicateUserErrrorTest() {
        GooseGame game = new GooseGame();
        game.addPlayer("pippo");
        Response<PlayerStatus> response = game.addPlayer("pippo");

        assertTrue(response.status.equals(DUPLICATE_PLAYER));
        assertTrue(response.payload.name.equals("pippo"));

    }
}