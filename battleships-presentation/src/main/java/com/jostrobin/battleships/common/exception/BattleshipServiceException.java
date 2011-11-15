package com.jostrobin.battleships.common.exception;

public class BattleshipServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public BattleshipServiceException(String message)
    {
        super(message);
    }

    public BattleshipServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
