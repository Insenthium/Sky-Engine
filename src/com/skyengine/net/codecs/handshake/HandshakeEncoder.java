package com.skyengine.net.codecs.handshake;

import java.util.List;

import com.skyengine.net.packet.Packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class HandshakeEncoder extends MessageToMessageEncoder<Packet>
{

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) throws Exception
    {
	System.out.println("Encoded Message: " + packet.toString());
	ctx.writeAndFlush(packet);
    }

}
