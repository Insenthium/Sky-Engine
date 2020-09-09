package com.skyengine.net.packet;

import com.skyengine.util.StringUtils;

import io.netty.buffer.ByteBuf;

/**
 * The Packet handles the containment of streamed data and operation codes to
 * execute upon.
 * 
 * @author Graham Edgecombe
 * @author Anthony (Pokemon)
 *
 */
public class Packet
{

    /**
     * The type of packet.
     * 
     * @author Graham Edgecombe
     *
     */
    public enum Type
    {

	/**
	 * A fixed size packet where the size never changes.
	 */
	FIXED,

	/**
	 * A variable packet where the size is described by a byte.
	 */
	VARIABLE,

	/**
	 * A variable packet where the size is described by a word.
	 */
	VARIABLE_SHORT;

    }

    /**
     * The operation code indicating a specific task
     */
    private int operationCode;

    /**
     * The packet type
     */
    private Type type;

    /**
     * The packets buffer containing the stream data.
     */
    private ByteBuf buffer;

    /**
     * Creates a game packet
     * 
     * @param operationCode the task code indicating its operation
     * @param buffer        the payload of the packet containing the streamed data
     */
    public Packet(int operationCode, Type type, ByteBuf buffer)
    {
	this.operationCode = operationCode;
	this.type = type;
	this.buffer = buffer;
    }

    /**
     * Checks if the packet is raw, packets that are raw do not contain operation
     * codes nor headers.
     * 
     * @return <code>True</code> if the packet is raw <code>false</code> if
     *         otherwise.
     */
    public boolean isRaw()
    {
	return operationCode == -1;
    }

    /**
     * Gets the packets operation code
     * 
     * @return operationCode
     */
    public int getOperationCode()
    {
	return operationCode;
    }

    /**
     * Gets the packets buffer (payload)
     * 
     * @return buffer
     */
    public ByteBuf getBuffer()
    {
	return buffer;
    }

    /**
     * Gets the current size of the buffer
     * 
     * @return
     */
    public int getLength()
    {
	return buffer.capacity();
    }

    /**
     * Reads a single signed byte and returns it from the buffer
     * 
     * @return single byte
     */
    public byte getByte()
    {
	return buffer.readByte();
    }

    /**
     * Reads a single unsigned byte and returns it from the buffer
     * 
     * @return single unsigned byte
     */
    public int getUnsignedByte()
    {
	return buffer.readUnsignedByte();
    }

    /**
     * Reads a single signed short and returns it from the buffer
     * 
     * @return single short
     */
    public short getShort()
    {
	return buffer.readShort();
    }

    /**
     * Reads a single unsigned short and returns it from the buffer
     * 
     * @return return single unsigned short
     */
    public int getUnsignedShort()
    {
	return buffer.readUnsignedShort();
    }

    /**
     * Reads a single signed integer and returns it from the buffer
     * 
     * @return return single signed integer
     */
    public int getInt()
    {
	return buffer.readInt();
    }

    /**
     * Reads a single signed long and returns it from the buffer
     * 
     * @return return single signed integer
     */
    public long getLong()
    {
	return buffer.readLong();
    }

    /**
     * Gets the packet type
     * 
     * @return the packet type
     */
    public Type getType()
    {
	return type;
    }

    /**
     * Reads a type C byte.
     * 
     * @return A type C byte.
     */
    public byte getByteC()
    {
	return (byte) (-getByte());
    }

    /**
     * Gets a type S byte.
     * 
     * @return A type S byte.
     */
    public byte getByteS()
    {
	return (byte) (128 - getByte());
    }

    /**
     * Reads a little-endian type A short.
     * 
     * @return A little-endian type A short.
     */
    public short getLEShortA()
    {
	int i = (buffer.readByte() - 128 & 0xFF) | ((buffer.readByte() & 0xFF) << 8);
	if (i > 32767)
	    i -= 0x10000;
	return (short) i;
    }

    /**
     * Reads a little-endian short.
     * 
     * @return A little-endian short.
     */
    public short getLEShort()
    {
	int i = (buffer.readByte() & 0xFF) | ((buffer.readByte() & 0xFF) << 8);
	if (i > 32767)
	    i -= 0x10000;
	return (short) i;
    }

    /**
     * Reads a V1 integer.
     * 
     * @return A V1 integer.
     */
    public int getInt1()
    {
	byte b1 = buffer.readByte();
	byte b2 = buffer.readByte();
	byte b3 = buffer.readByte();
	byte b4 = buffer.readByte();
	return ((b3 << 24) & 0xFF) | ((b4 << 16) & 0xFF) | ((b1 << 8) & 0xFF) | (b2 & 0xFF);
    }

    /**
     * Reads a V2 integer.
     * 
     * @return A V2 integer.
     */
    public int getInt2()
    {
	int b1 = buffer.readByte() & 0xFF;
	int b2 = buffer.readByte() & 0xFF;
	int b3 = buffer.readByte() & 0xFF;
	int b4 = buffer.readByte() & 0xFF;
	return ((b2 << 24) & 0xFF) | ((b1 << 16) & 0xFF) | ((b4 << 8) & 0xFF) | (b3 & 0xFF);
    }

    /**
     * Gets a 3-byte integer.
     * 
     * @return The 3-byte integer.
     */
    public int getTriByte()
    {
	return ((buffer.readByte() << 16) & 0xFF) | ((buffer.readByte() << 8) & 0xFF) | (buffer.readByte() & 0xFF);
    }

    /**
     * Reads a type A byte.
     * 
     * @return A type A byte.
     */
    public byte getByteA()
    {
	return (byte) (getByte() - 128);
    }

    /**
     * Reads a RuneScape string.
     * 
     * @return The string.
     */
    public String getRS2String()
    {
	return StringUtils.getRS2String(buffer);
    }

    /**
     * Reads a type A short.
     * 
     * @return A type A short.
     */
    public short getShortA()
    {
	int i = ((buffer.readByte() & 0xFF) << 8) | (buffer.readByte() - 128 & 0xFF);
	if (i > 32767)
	    i -= 0x10000;
	return (short) i;
    }

    /**
     * Reads a series of bytes in reverse.
     * 
     * @param is     The target byte array.
     * @param offset The offset.
     * @param length The length.
     */
    public void getReverse(byte[] is, int offset, int length)
    {
	for (int i = (offset + length - 1); i >= offset; i--)
	{
	    is[i] = buffer.readByte();
	}
    }

    /**
     * Reads a series of type A bytes in reverse.
     * 
     * @param is     The target byte array.
     * @param offset The offset.
     * @param length The length.
     */
    public void getReverseA(byte[] is, int offset, int length)
    {
	for (int i = (offset + length - 1); i >= offset; i--)
	{
	    is[i] = getByteA();
	}
    }

    /**
     * Reads a series of bytes.
     * 
     * @param is     The target byte array.
     * @param offset The offset.
     * @param length The length.
     */
    public void get(byte[] is, int offset, int length)
    {
	for (int i = 0; i < length; i++)
	{
	    is[offset + i] = buffer.readByte();
	}
    }

    /**
     * Gets a smart.
     * 
     * @return The smart.
     */
    public int getSmart()
    {
	int peek = buffer.writerIndex();
	if (peek < 128)
	{
	    return (getByte() & 0xFF);
	}
	else
	{
	    return (getShort() & 0xFFFF) - 32768;
	}
    }

    /**
     * Gets a signed smart.
     * 
     * @return The signed smart.
     */
    public int getSignedSmart()
    {
	int peek = buffer.writerIndex();
	if (peek < 128)
	{
	    return ((getByte() & 0xFF) - 64);
	}
	else
	{
	    return ((getShort() & 0xFFFF) - 49152);
	}
    }

}
