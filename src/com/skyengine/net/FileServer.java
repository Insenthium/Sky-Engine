package com.skyengine.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.logging.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FileServer
{

    /**
     * The logger for the {@docRoot FileServer} class.
     */
    private static final Logger logger = Logger.getLogger(FileServer.class.getName());
    
    public FileServer() {
	
    }
    

    /**
     * Starts the specified service.
     * 
     * @param service            The name of the service.
     * @param channelInitializer The channel initializer.
     * @param port            The port.
     */
    private void start(String service, ChannelInitializer<?> channelInitializer, int port)
    {
	SocketAddress address = new InetSocketAddress(port);

	logger.info("Binding " + service + " service to " + address + "...");

	ServerBootstrap bootstrap = new ServerBootstrap();
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	bootstrap.group(bossGroup, workerGroup)
	.channel(NioServerSocketChannel.class)
	.childHandler(channelInitializer)
	.bind(address);
    }
}
