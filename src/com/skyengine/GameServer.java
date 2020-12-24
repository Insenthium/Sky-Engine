package com.skyengine;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.skyengine.util.ConsoleStream;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * GameServer handles the intialization of the server starting all the core and
 * network tasks.
 * 
 * @author Anthony (Pokemon)
 *
 */
public class GameServer
{

    /**
     * Logger for the {@docRoot GameServer} class
     */
    private static final Logger logger = Logger.getLogger(GameServer.class.getName());

    public GameServer()
    {
	// Sets the logger level to the appropriate setting for debugging purposes
	logger.setLevel(Level.SEVERE);
    }

    /**
     * Application entry-point.
     * 
     * @param args program parameters
     */
    public static void main(String[] args)
    {
	logger.info("Initializing Sky-Engine...");

	// Sets a custom console stream format
	System.setOut(new ConsoleStream(System.out));

	logger.info("Setting console stream format...");

    }

}
