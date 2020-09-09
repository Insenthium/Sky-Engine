package com.skyengine.net;

import com.skyengine.net.codecs.handshake.LoginDecoder;
import com.skyengine.net.codecs.handshake.HandshakeEncoder;
import com.skyengine.net.codecs.handshake.HandshakeHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * Server Channel Pipeline factory
 * adds all the codec configurations in
 * a specified order to the server
 * @author Anthony (Pokemon)
 *
 */
public class ServerChannelInitializer extends ChannelInitializer<Channel>
{

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
	ChannelPipeline pipeline = channel.pipeline();
	
	System.out.println("Setting up pipelinefactory...");
	//Add pipeline factories before
	pipeline.addLast("encoder", new HandshakeEncoder());
	pipeline.addLast("decoder", new LoginDecoder());
	pipeline.addLast("handler", new HandshakeHandler());
	
    }

}
