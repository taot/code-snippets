package com.taot;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws ClassNotFoundException {
        System.out.println( "Hello World!" );
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        System.out.println(clazz.toString());
    }
}
