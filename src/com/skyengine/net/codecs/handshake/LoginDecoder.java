package com.skyengine.net.codecs.handshake;

import java.security.SecureRandom;
import java.util.List;

import com.skyengine.Settings;
import com.skyengine.cipher.ISAACCipher;
import com.skyengine.net.StreamBuffer;
import com.skyengine.util.StringUtils;
import com.skyengine.world.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class LoginDecoder extends ReplayingDecoder<ByteBuf>
{

    /**
     * The stage of the decode.
     */
    private int stage;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception
    {
	System.out.println("[LoginDecoder] Decoding...");
	if (!ctx.channel().isActive())
	{
	    System.out.println("Connection inactive!");
	    return;
	}
	switch (stage)
	{
	case 0:
	    if (buffer.readableBytes() < 2)
	    {
		System.err.println("less than two bytes");
		return;
	    }
	    int request = buffer.readUnsignedByte();
	    System.out.println("request: " + request);
	    if (request != 14)
	    {
		System.err.println("Wrong log-in request! Request: " + request);
		ctx.close();
		return;
	    }
	    buffer.readUnsignedByte();

	    StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(17);
	    out.writeLong(0);
	    out.writeByte(0);
	    out.writeLong(new SecureRandom().nextLong());
	    ctx.writeAndFlush(out.getBuffer());

	    stage = 1;
	    break;
	case 1:
	    System.out.println("State 1");
	    if (buffer.readableBytes() < 2)
		return;
	    int type = buffer.readByte();
	    System.out.println("type: " + type);
	    if (type != 16 && type != 18)
	    {
		System.err.println("Wrong log-in type! Type: " + type);
		ctx.close();
		return;
	    }
	    int blockLength = buffer.readUnsignedByte();
	    if (buffer.readableBytes() < blockLength)
		return;
	    buffer.readByte();
	    int protocol = buffer.readShort();
	    if (protocol != Settings.PROTOCOL)
	    {
		System.err.println("Wrong protocol version! Protocol: " + protocol);
		ctx.close();
		return;
	    }
	    buffer.readByte();
	    for (int i = 0; i < 9; i++)
	    {
		buffer.readInt();
	    }
	    buffer.readByte();
	    int rsaCode = buffer.readByte();

	    System.out.println("RSA Code: " + rsaCode);
	    if (rsaCode != 10)
	    {
		System.err.println("Unable to decode RSA block!");
		ctx.close();
		return;
	    }
	    long clientKey = buffer.readLong();
	    long serverKey = buffer.readLong();

	    buffer.readInt();

	    String name = StringUtils.formatPlayerName(StringUtils.getRS2String(buffer));
	    String pass = StringUtils.getRS2String(buffer);

	    System.out.println("username: " + name + " password: " + pass);

	    /*
	     * And setup the ISAAC cipher which is used to encrypt and decrypt opcodes.
	     * 
	     * However, without RSA, this is rendered useless anyway.
	     */
	    int[] sessionKey = new int[4];
	    sessionKey[0] = (int) (clientKey >> 32);
	    sessionKey[1] = (int) clientKey;
	    sessionKey[2] = (int) (serverKey >> 32);
	    sessionKey[3] = (int) serverKey;

	    ISAACCipher inCipher = new ISAACCipher(sessionKey);
	    for (int i = 0; i < 4; i++)
	    {
		sessionKey[i] += 50;
	    }
	    ISAACCipher outCipher = new ISAACCipher(sessionKey);

	    finalCheck(ctx.channel(), inCipher, outCipher, name, pass);

	    break;
	}

    }

    /**
     * Does the final checking before we switch from log-in decoding to packet
     * decoding.
     * 
     * @param channel The channel.
     * @param in      The inCipher.
     * @param out     The outCipher.
     * @param name    The player name.
     * @param pass    The player pass.
     * @return The account manager.
     */
    private Player finalCheck(Channel channel, ISAACCipher in, ISAACCipher out, String name, String pass)
    {
	channel.pipeline().remove("decoder");
	channel.pipeline().addFirst("decoder", new HandshakeDecoder(in));
	Player player = new Player(channel);
	player.setInCipher(in);
	player.setOutCipher(out);
	player.setUsername(name);
	player.setPassword(pass);

	player.getPacketSender().sendLoginResponse(2);
	player.finishLogin();
	return player;
	// return AccountManager.manageLogin(player);
    }

}
