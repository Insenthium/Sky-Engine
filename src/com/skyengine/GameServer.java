package com.skyengine;

import com.skyengine.util.ConsoleStream;

/**
 * GameServer handles the intialization of the server
 * starting all the core and network tasks.
 * @author Anthony (Pokemon)
 *
 */
public class GameServer
{

    /**
     * Application entry-point.
     * @param args program parameters
     */
    public static void main(String[] args)
    {
	//Sets a custom console stream format
	System.setOut(new ConsoleStream(System.out));

    }


}
