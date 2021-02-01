package com.arbov.ipcount;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;


/**
 * IPv4 simple set storage based on map of bitsets
 */
public class IPBitSetMapStorage implements IPStorage {

    private Map<Integer, BitSet> bitSetMap; // map of bitsets
    private long itemCount;                 // number of elements

    {
        bitSetMap = new HashMap<>();
        itemCount = 0;
    }


    /**
     * Adds the IPv4 address to this set
     * @param rawIP raw IPv4 address value
     */
    @Override
    public void addIP( byte[] rawIP ) {

        // convert to unsigned values
        int b1 = Byte.toUnsignedInt( rawIP[ 0 ] );
        int b2 = Byte.toUnsignedInt( rawIP[ 1 ] );
        int b3 = Byte.toUnsignedInt( rawIP[ 2 ] );
        int b4 = Byte.toUnsignedInt( rawIP[ 3 ] );

        int setIndex = (b1 << 8) + b2; // bitset index
        int bitIndex = (b3 << 8) + b4; // bit index

        BitSet bitSet = bitSetMap.get( setIndex );

        if( bitSet != null ) { // bitset already exists

            if( bitSet.get( bitIndex ) ) return; // ip isn't unique

        } else {
            // new bitset required
            bitSet = new BitSet();
            bitSetMap.put( setIndex, bitSet );
        }

        // ip is unique
        // set required bit
        bitSet.set( bitIndex );
        itemCount++;
    }


    /**
     * Removes all of the elements from this set
     */
    @Override
    public void clear() {
        bitSetMap = new HashMap<>();
        itemCount = 0;
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
