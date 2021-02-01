package com.arbov.ipcount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class Main {


    public static void main(String[] args) throws RuntimeException {

        // filename as first param is required
        var fileName = args.length > 0 ? args[ 0 ] : null;
        if( fileName == null ) {
            System.out.println( "Filename param is missed!" );
            return;
        }

        // ok, we got filename param
        var startTime = System.nanoTime();
        System.out.printf( "Parsing file: '%s'%nPlease wait", fileName );

        // create parser and storage
        TextIPParser parser = new FastIPv4Parser( new IPLongArrayStorage() );

        // parse text from file
        try( Stream<String> text = Files.lines( Path.of( fileName ) ) ) {

            // separate thread for parsing
            Thread thread = new Thread( () -> { parser.parseText(text); } );
            thread.start();

            try {
                // print progress while waiting
                String[] blink = { ".", "|", "\b/", "\b-", "\b\\", "\b" };
                var i = 0;
                while( thread.isAlive() ) {
                    System.out.print( blink[ i++ ] );
                    if( i == blink.length )  i = 0; else Thread.sleep(1000 );
                }

                // print results
                System.out.printf( "\b%nDone!%n" );
                System.out.printf( "Total lines checked: %,d%n", parser.linesParsed() ) ;
                System.out.printf( "Invalid IPv4 addresses found: %,d%n", parser.invalidIPCount() );
                System.out.printf( "Unique IPv4 addresses found: %,d%n", parser.uniqueIPCount() );
                System.out.printf( "Memory used: %,d bytes%n", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() );

                var seconds = TimeUnit.SECONDS.convert( System.nanoTime() - startTime, TimeUnit.NANOSECONDS );
                System.out.printf( "Total elapsed time: %d minutes %d seconds%n", seconds / 60, seconds % 60 );

            } catch ( InterruptedException e ) {
                // main thread was interrupted, something's gone wrong
                throw new RuntimeException( e );
            }

        } catch ( IOException e ) {
            // IO problems
            e.printStackTrace();
        }
    }

}