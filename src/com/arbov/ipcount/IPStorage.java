package com.arbov.ipcount;


/**
 * IPv4 simple set interface
 */
public interface IPStorage {

    /**
     * Adds the IPv4 address to this set
     * @param rawIP raw IPv4 address value
     */
    void addIP( byte[] rawIP );


    /**
     * Removes all of the elements from this set
     */
    void clear();


    /**
     * Returns the number of elements in this set
     * @return The number of elements in this set
     */
    long count();

}
