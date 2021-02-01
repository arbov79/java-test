package com.arbov.ipcount;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.Stream;

/**
 * Simple version of IPv4 text parser
 */
public class SimpleIPv4Parser implements TextIPParser {

    private final IPStorage ipStorage; // set of IPv4 addresses
    protected long invalidLines = 0;   // number of invalid IPv4 addresses founds
    protected long totalLines = 0;     // number of parsed lines

    /**
     * Create new simple IPv4 text parser with specified set of IP addresses
     * @param ipStorage set of IP addresses to use
     */
    public SimpleIPv4Parser( IPStorage ipStorage )
    {
        this.ipStorage = ipStorage;
    }


    /**
     * Clear parse results
     */
    @Override
    public void clear() {
        totalLines = 0;
        invalidLines = 0;
        ipStorage.clear();
    }


    /**
     * Returns the number number of lines with invalid IP addresses
     * @return the umber of lines with invalid IP addresses
     */
    public long invalidIPCount() {
        return invalidLines;
    }


    /**
     * Returns the number of unique IP addresses found
     * @return the number of unique IP addresses found
     */
    public long uniqueIPCount() {
        return ipStorage != null ?  ipStorage.count() : 0;
    }


    /**
     * Returns the number of parsed lines
     * @return the number of parsed lines
     */
    public long linesParsed() {
        return totalLines;
    }


    /**
     * Parses IPv4 addressed from text
     * @param text Stream of strings to parse
     */
    public void parseText( Stream<String> text ) {
        text.forEach( str -> checkIPFromString( str ) );
    }


    /**
     * Parse IPv4 address from string
     * @param stringIP string to parse
     */
    protected void checkIPFromString( String stringIP ) {

        totalLines++;

        try {
            // parse ip from string
            byte[] rawIP = InetAddress.getByName( stringIP ).getAddress();

            // only ipv4 is ok
            if( rawIP.length == 4 ) addIP( rawIP ); else invalidLines++;

        } catch ( UnknownHostException e )   {
            // wrong ip
            invalidLines++;
        }
    }


    /**
     * Adds the IPv4 address to set
     * @param rawIP raw IPv4 address value
     */
    protected void addIP( byte[] rawIP )
    {
        ipStorage.addIP( rawIP );
    }

}
