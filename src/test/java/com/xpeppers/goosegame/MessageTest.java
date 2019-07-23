package com.xpeppers.goosegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.xpeppers.goosegame.data.Response.Status.*;


import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.xpeppers.goosegame.data.*;


@SuppressWarnings({"unchecked"})
public class MessageTest{

// the system responds: "players: Pippo"
// the system responds: "players: Pippo, Pluto"

@Test
public void addingFirstplayer(){
    String expected = "players: Pippo";

    PlayerStatus status = new PlayerStatus("Pippo", -1, -1, Collections.EMPTY_LIST);
    Response<PlayerStatus> response = new Response(PLAYER_CREATED, status);

    assertEquals(expected, Decoder.parseResponse(response));

}

@Test
public void addingMoreFirstplayer(){
    String expected = "players: Pippo, Pluto";

    PlayerStatus status = new PlayerStatus("Pippo, Pluto", -1, -1, Collections.EMPTY_LIST);
    Response<PlayerStatus> response = new Response(PLAYER_CREATED, status);

    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void simpleMove() {
    String expected = "Pippo rolls 4, 2. Pippo moves from Start to 6";

    PlayerStatus status = new PlayerStatus("Pippo", 4, 2, List.of(0,6));
    Response<PlayerStatus> response = new Response(OK, status);

    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void winMove() {
    String expected = "Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!";

    PlayerStatus status = new PlayerStatus("Pippo", 1, 2, List.of(60,63));
    Response<PlayerStatus> response = new Response(WIN, status);
    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void bounceMove() {
    String expected = "Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61";

    PlayerStatus status = new PlayerStatus("Pippo", 3, 2, List.of(60,61));
    Response<PlayerStatus> response = new Response(BOUNCE, status);
    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void bridgeMove() {
    String expected = "Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12";

    PlayerStatus status = new PlayerStatus("Pippo", 1, 1, List.of(4,12));
    Response<PlayerStatus> response = new Response(BRIDGE, status);
    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void singleGoose() {
    String expected = "Pippo rolls 1, 1. Pippo moves from 3 to 5, The Goose. Pippo moves again and goes to 7";

    PlayerStatus status = new PlayerStatus("Pippo", 1, 1, List.of(3,5,7));
    Response<PlayerStatus> response = new Response(GOOSE, status);
    assertEquals(expected, Decoder.parseResponse(response));
}

@Test
public void multipleGoose() {
    String expected = "Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22";

    PlayerStatus status = new PlayerStatus("Pippo", 2, 2, List.of(10,14,18,22));
    Response<PlayerStatus> response = new Response(GOOSE, status);
    assertEquals(expected, Decoder.parseResponse(response));
}





}