package com.xpeppers.goosegame.data;

    /**
     * Parametric default response value object. It expose a status type and a
     * generic payload of type T
     * @
     * @param <T> payloadType
     */
    public class Response<T> {

        public final Status status;
		public final T payload;

		public Response(Status status, T payload){
            this.status=status;
            this.payload=payload;
        }

    // Here because is used just to construct responses
    public enum Status {
        OK,
        DUPLICATE_PLAYER,
        PLAYER_NOT_EXISTS,
        INVALID_ROLL,
        KO;
    }    

    }