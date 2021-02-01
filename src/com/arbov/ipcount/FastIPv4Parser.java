package com.arbov.ipcount;

/**
 * Fast version of IPv4 text parser
 */
public class FastIPv4Parser extends SimpleIPv4Parser implements TextIPParser {

    private final byte[] rawIP = new byte[4]; // storage for raw IPv4 values

    /**
     * Create new fast IPv4 text parser with specified set of IP addresses
     * @param ipStorage set of IP addresses to use
     */
    public FastIPv4Parser( IPStorage ipStorage ) {
        super( ipStorage );
    }


    /**
     * Parse IPv4 address from string
     * @param stringIP string to parse
     */
    @Override
    protected void checkIPFromString( String stringIP ) {

        // parse IPv4 from string
        if( stringToRawIPV4( stringIP ) ) addIP( rawIP ); else invalidLines++;
        totalLines++;
    }


    /**
     * Convert string to raw IPv4 value
     * @param src String to parse
     * @return true if IPv4 value is correct, false otherwise
     */
    private Boolean stringToRawIPV4( String src ) {
        long tmpValue = 0;
        int currByte = 0;
        boolean newOctet = true;

        // fast pre-check
        int len = src.length();
        if (len == 0 || len > 15) return false;

        // parse string char by char
        for (int i = 0; i < len; i++) {
            char c = src.charAt(i);

            if (c == '.') { // char is delimeter

                // value is incorrect
                if (newOctet || tmpValue < 0 || tmpValue > 0xff || currByte == 3) return false;

                rawIP[currByte++] = (byte) (tmpValue & 0xff);
                tmpValue = 0;
                newOctet = true;

            } else {
                // char must be positive digit
                int digit = Character.digit(c, 10);
                if (digit < 0) return false;

                // char is digit
                tmpValue *= 10;
                tmpValue += digit;
                newOctet = false;
            }
        }

        // value is incorrect
        if (newOctet || tmpValue < 0 || tmpValue >= (1L << ((4 - currByte) * 8)))  return false;

        // store value to byte array
        switch (currByte) {
            case 0:
                rawIP[0] = (byte) ((tmpValue >> 24) & 0xff);
            case 1:
                rawIP[1] = (byte) ((tmpValue >> 16) & 0xff);
            case 2:
                rawIP[2] = (byte) ((tmpValue >>  8) & 0xff);
            case 3:
                rawIP[3] = (byte) ((tmpValue >>  0) & 0xff);
        }

        return true;
    }

}
