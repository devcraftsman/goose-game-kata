package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xpeppers.goosegame.data.Response;
import static com.xpeppers.goosegame.data.Response.Status.*;

import org.junit.jupiter.api.Test;

public class UserAddTest {

    @Test 
    public void addNewUserTest(){   
        GooseGame game = new GooseGame();

        Response<String> response = game.addPlayer("pippo");
        assertTrue(response.status.equals(OK));
        assertTrue(response.payload.equals("pippo"));
    }

    @Test
    public void addDuplicateUserErrrorTest() {
        GooseGame game = new GooseGame();
        game.addPlayer("pippo");
        Response<String> response2 = game.addPlayer("pippo");

        assertTrue(response2.status.equals(DUPLICATE_PLAYER));
        assertTrue(response2.payload.equals("pippo"));


    }
}