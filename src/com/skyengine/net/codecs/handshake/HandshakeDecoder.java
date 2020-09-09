package com.skyengine.net.codecs.handshake;

import java.util.List;

import com.skyengine.Settings;
import com.skyengine.cipher.ISAACCipher;
import com.skyengine.net.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class HandshakeDecoder extends MessageToMessageDecoder<ByteBuf>
{

    public HandshakeDecoder(ISAACCipher cipher)
    {
	this.cipher = cipher;
    }

    /**
     * The cipher.
     */
    private final ISAACCipher cipher;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception
    {
	if (buffer.readableBytes() > 0)
	{
	    int opcode = (buffer.readUnsignedByte() - cipher.getNextValue()) & 0xFF;
	    int size = Settings.PACKET_SIZES[opcode];
	    if (size == -1)
		size = buffer.readUnsignedByte();
	    if (buffer.readableBytes() >= size)
	    {
		final byte[] data = new byte[size];
		buffer.readBytes(data);
		final ByteBuf payload = Unpooled.buffer(size);
		payload.writeBytes(data);

		Packet packet = new Packet(opcode, Packet.Type.VARIABLE, payload);

		System.out.println("[HandshakeDecoder] - " + packet.toString());
	    }
	}
    }

}
