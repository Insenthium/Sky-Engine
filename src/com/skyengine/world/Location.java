package com.skyengine.world;

/**
 * The {@code Location} represents a position in the game world
 * @author Anthony (Pokemon)
 *
 */
public class Location
{

    /**
     * The position represents the coordinates of the location
     */
    private Vector3f position;
    
    /**
     * Creates a location representing a position
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    protected Location(int x, int y, int z) {
	this.position = new Vector3f(x, y, z);
    }
    
    /**
     * Creates a location representing a position in the game world
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a new {@code Location}
     */
    public static Location struct(int x, int y, int z) {
	return new Location(x, y, z);
    }
    
    /**
     * Gets the x-coordinate of the location.
     * @return {@value x}
     */
    public int getX() {
	return position.x;
    }
    
    /**
     * Gets the y-coordinate of the location.
     * @return {@value y}
     */
    public int getY() {
	return position.y;
    }
    
    /**
     * Gets the z-coordinate of the location
     * @return {@value z}
     */
    public int getZ() {
	return position.z;
    }
    
    /**
     * The {@code Vector3f} immutable class represents a 3D point within the game world.
     * @author Anthony (Pokemon)
     *
     */
    class Vector3f
    {

        /**
         * The vectors x-coordinate.
         */
        private final int x;
        
        /**
         * The vectors y-coordinate.
         */
        private final int y;
        
        /**
         * The vectors z-coordinate.
         */
        private final int z;

        /**
         * Creates a 3 dimensional vector
         * @param x the vector x-coordinate
         * @param y the vector y-coordinate
         * @param z the vector z-coordinate
         */
        public Vector3f(int x, int y, int z)
        {
    	this.x = x;
    	this.y = y;
    	this.z = z;
        }

        /**
         * Adds two 3 dimensional vectors.
         * @param addend the vector added
         * @return new vector with the added values between {@value addend}
         */
        public Vector3f add(Vector3f addend)
        {
    	return new Vector3f(x + addend.x, y + addend.y, z + addend.z);
        }
        
        /**
         * Subtracts two 3 dimensional vectors.
         * @param difference the vector subtracted
         * @return new vector with the subtracted values between {@value difference}
         */
        public Vector3f subtract(Vector3f difference) {
    	return new Vector3f(x - difference.x, y - difference.y, z - difference.z);
        }

    }
    
}
