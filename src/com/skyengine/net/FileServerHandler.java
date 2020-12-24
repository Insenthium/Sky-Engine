package com.skyengine.net;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;

public class FileServerHandler extends IdleStateHandler
{

    public FileServerHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime,
	    TimeUnit unit)
    {
	super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
	System.out.println("msg to string: " + msg.toString());

	//TODO check the instance of the message and handle it appropriately.
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
    {
	e.printStackTrace();
    }

}
