package com.arbov.ipcount;

import java.util.Arrays;


/**
 * IPv4 simple set storage based on long array
 */
public class IPLongArrayStorage implements IPStorage {

    private final long[] bitsArray; // fixed long array
    private long itemCount;         // number of elements

    {
        bitsArray = new long[65536 * 1024];
        itemCount = 0;
    }


    /**
     * Adds the IPv4 address to this set
     * @param rawIP raw IPv4 address value
     */
    @Override
    public void addIP( byte[] rawIP )
    {
        // convert to unsigned values
        int b1 = ((int) rawIP[ 0 ]) & 0xff;
        int b2 = ((int) rawIP[ 1 ]) & 0xff;
        int b3 = ((int) rawIP[ 2 ]) & 0xff;
        int b4 = ((int) rawIP[ 3 ]) & 0xff;

        // index in array
        int index = (b1 << 18) | (b2 << 10) | (b3 << 2) | (( b4 & 0xc0) >> 6);

        //  index of required bit
        int bitIndex = b4 & 0x3f;

        // bitmask for required bit
        long mask = 1L << bitIndex;

        if( ( bitsArray[ index ] & mask ) == 0 ) {
            // ip is unique
            // set required bit
            bitsArray[ index ] |= mask;
            itemCount++;
        }
    }


    /**
     * Removes all of the elements from this set
     */
    @Override
    public void clear() {
        itemCount = 0;
        Arrays.fill( bitsArray, 0L);
    }


    /**
     * Returns the number of elements in this set
     * @return The number of elements in this set
     */
    @Override
    public long count() {
        return itemCount;
    }

}
