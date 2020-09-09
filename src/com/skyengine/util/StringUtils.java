package com.skyengine.util;

import io.netty.buffer.ByteBuf;

public class StringUtils
{

    public static String getRS2String(final ByteBuf buffer)
    {
	final StringBuilder builder = new StringBuilder();
	byte b;
	while (buffer.isReadable() && (b = buffer.readByte()) != 10)
	{
	    builder.append((char) b);
	}
	return builder.toString();
    }

    public static String formatPlayerName(String name)
    {
	name = name.substring(0, 1).toUpperCase() + name.substring(1);
	name.replace("_", " ");
	return name;
    }

}
