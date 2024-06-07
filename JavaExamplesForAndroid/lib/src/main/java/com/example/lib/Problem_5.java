package com.example.lib;

/**
 * Write a program to reverse a string.
 */
public class Problem_5 {
    public static void main(String[] args) {
        String str = "Hello World!";
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }
        System.out.println("Reversed String: " + reversed);
    }
}
