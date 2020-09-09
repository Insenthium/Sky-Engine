package com.skyengine.net.codecs.handshake;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class HandshakeHandler implements ChannelInboundHandler
{

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
	System.out.println("Channel connected on: " + ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
	// TODO Auto-generated method stub
	System.out.println("Channel disconnected on: " + ctx.channel());
	
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
	// TODO Auto-generated method stub
	if (!ctx.channel().isActive())
	{
	    System.out.println("Channel inactive");
	    return;
	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext arg0) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void channelRegistered(ChannelHandlerContext arg0) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext arg0) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext arg0) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext arg0, Object arg1) throws Exception
    {
	// TODO Auto-generated method stub

    }

}
