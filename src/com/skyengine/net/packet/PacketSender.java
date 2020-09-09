package com.skyengine.net.packet;

import com.skyengine.Settings;
import com.skyengine.net.StreamBuffer;
import com.skyengine.world.entity.Player;

public class PacketSender
{

    private Player player;

    public PacketSender(Player player)
    {
	this.player = player;
    }

    /**
     * Sends a response code.
     * 
     * @param code The response code.
     * @return The packet sender instance, for chaining.
     */
    public PacketSender sendLoginResponse(int code)
    {
	player.send(new PacketBuilder(code).putInt(0).putInt(0).toPacket());
	//StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(3);
	//out.writeByte(code);
	//out.writeByte(0); // TODO Staff rights
	//out.writeByte(0);
	//player.send(out.getBuffer());
	return this;
    }

    /**
     * Sends all the login packet's.
     * 
     * @return The packet sender instance, for chaining.
     */
    public PacketSender sendLogin()
    {
	sendDetails();
	System.out.println("Sent player details...");
	sendCameraReset();
	System.out.println("Sent camera reset...");
	//sendMapRegion();
	//sendSidebarInterfaces();
	//sendMessage("Welcome to " + Settings.FRAMEWORK + ".");
	//System.out.println("Sent map region");
	return this;
    }

    /**
     * Sends a player detail.
     * 
     * @return The packet sender instance, for chaining.
     */
    public PacketSender sendDetails()
    {

	
	player.send(new PacketBuilder(249).putByteA(1).putLEShortA(player.getIndex()).toPacket());
	return this;
	/*StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
	out.writeHeader(player.getOutCipher(), 249);
	out.writeByte(1, StreamBuffer.ValueType.A);
	out.writeShort(player.getIndex(), StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
	player.send(out.getBuffer());
	return this;*/
    }

    /**
     * Sends a camera reset.
     * 
     * @return The packet sender instance, for chaining.
     */
    public PacketSender sendCameraReset()
    {
	
	player.send(new PacketBuilder(107).toPacket());
	//StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(1);
	//out.writeHeader(player.getOutCipher(), 107);
	//player.send(out.getBuffer());
	return this;
    }

    /**
     * Sends a server message such as "Welcome to RuneScape.".
     * 
     * @param message The message to send.
     * @return The packet sender instance, for chaining.
     */
    public PacketSender sendMessage(String message)
    {
	StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(message.length() + 3);
	out.writeVariablePacketHeader(player.getOutCipher(), 253);
	out.writeString(message);
	out.finishVariablePacketHeader();
	//player.send(out.getBuffer());
	return this;
    }

    public PacketSender sendMapRegion()
    {
	StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
	out.writeHeader(player.getOutCipher(), 73);
	out.writeShort(150 + 6, StreamBuffer.ValueType.A);
	out.writeShort(20 + 6);
	//player.send(out.getBuffer());
	return this;
    }

    /**
     * Sends all the sidebar interfaces.
     * 
     * @return The action sender instance, for chaining.
     */
    public PacketSender sendSidebarInterfaces()
    {
	final int[] icons = Settings.SIDEBAR_INTERFACES[0];
	final int[] interfaces = Settings.SIDEBAR_INTERFACES[1];
	for (int i = 0; i < icons.length; i++)
	{
	    //sendSidebarInterface(icons[i], interfaces[i]);
	}
	return this;
    }

    /**
     * Sends a specific sidebar interface.
     * 
     * @param icon        The sidebar icon.
     * @param interfaceId The interface id.
     * @return The action sender instance, for chaining.
     */
    public PacketSender sendSidebarInterface(int icon, int interfaceId)
    {
	
	StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
	out.writeHeader(player.getOutCipher(), 71);
	out.writeShort(interfaceId);
	out.writeByte(icon);
	//player.send(out.getBuffer());
	return this;
    }

}
