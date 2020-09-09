package com.skyengine;

import com.skyengine.net.ServerChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * GameServer handles the intialization of the server
 * starting all the core and network tasks.
 * @author Anthony (Pokemon)
 *
 */
public class GameServer
{

    public static void main(String[] args)
    {
	try
	{
	    new GameServer().bind();
	}
	catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
    }

    private void bind() throws InterruptedException
    {
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();

	System.out.println("Starting " + Settings.FRAMEWORK + "...");
	try
	{
	    ServerBootstrap bootstrap = new ServerBootstrap();
	    bootstrap.group(bossGroup, workerGroup)
	    .channel(NioServerSocketChannel.class)
		    .childHandler(new ServerChannelInitializer())
		    .option(ChannelOption.SO_BACKLOG, 128)
		    .childOption(ChannelOption.SO_KEEPALIVE, true);

	    // Bind and start to accept incoming connections.
	    ChannelFuture future = bootstrap.bind(Settings.PORT).sync();

	    // Wait until the server socket is closed.
	    future.channel().closeFuture().sync();
	}
	finally
	{
	    workerGroup.shutdownGracefully();
	    bossGroup.shutdownGracefully();
	}
    }

}
