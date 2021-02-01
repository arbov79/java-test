package com.arbov.ipcount;

import java.util.stream.Stream;

/**
 * IP addresses parser from text interface
 */
public interface TextIPParser {

    /**
     * Clear parse results
     */
    void clear();


    /**
     * Parses IP addressed from text
     * @param text Stream of strings to parse
     */
    void parseText( Stream<String> text );


    /**
     * Returns the number number of lines with invalid IP addresses
     * @return the umber of lines with invalid IP addresses
     */
    long invalidIPCount();


    /**
     * Returns the number of unique IP addresses found
     * @return the number of unique IP addresses found
     */
    long uniqueIPCount();


    /**
     * Returns the number of parsed lines
     * @return the number of parsed lines
     */
    long linesParsed();

}
