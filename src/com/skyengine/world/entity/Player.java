package com.skyengine.world.entity;

import com.skyengine.cipher.ISAACCipher;
import com.skyengine.net.packet.Packet;
import com.skyengine.net.packet.PacketSender;

import io.netty.channel.Channel;

public class Player
{

    /**
     * The players username
     */
    private String username;

    /**
     * The players password
     */
    private String password;

    private Channel channel;

    /**
     * The packet sender of the player.
     */
    private final PacketSender packetSender = new PacketSender(this);

    /**
     * The inCipher of the player.
     */
    private ISAACCipher inCipher;

    /**
     * The outCipher of the player.
     */
    private ISAACCipher outCipher;

    /**
     * Finishes a player login.
     */
    public void finishLogin()
    {
	// World.getWorld().register(this);
	packetSender.sendLogin();
	System.out.println("Registered player [" + username + ", " + password + "]");
    }

    /**
     * Sets the inCipher.
     * 
     * @param inCipher The inCipher.
     */
    public void setInCipher(ISAACCipher inCipher)
    {
	this.inCipher = inCipher;
    }

    /**
     * Sets the outCipher.
     * 
     * @param outCipher The outCipher.
     */
    public void setOutCipher(ISAACCipher outCipher)
    {
	this.outCipher = outCipher;
    }

    /**
     * Gets the players username
     * 
     * @return the username
     */
    public String getUsername()
    {
	return username;
    }

    /**
     * Sets the name of the player.
     * 
     * @param name The name of the player.
     */
    public void setUsername(String name)
    {
	this.username = name;
    }

    /**
     * Gets the players password
     * 
     * @return password
     */
    public String getPassword()
    {
	return password;
    }

    /**
     * Set the pass of the player.
     * 
     * @param pass The pass of the player.
     */
    public void setPassword(String password)
    {
	this.password = password;
    }

    public Player(Channel channel)
    {
	this.channel = channel;
    }

    public void send(Packet packet)
    {
	channel.writeAndFlush(packet);
    }

    public ISAACCipher getInCipher()
    {
	return inCipher;
    }

    public ISAACCipher getOutCipher()
    {
	return outCipher;
    }

    /**
     * Gets the packet sender.
     * 
     * @return The packet sender of the player.
     */
    public PacketSender getPacketSender()
    {
	return packetSender;
    }

    public int getIndex()
    {
	// TODO Auto-generated method stub
	return 0;
    }
}
