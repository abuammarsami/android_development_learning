package com.example.lib;

/**
 * Write a program to count the
 * letters, spaces, numbers and
 * other characters of an input
 * string.
 */
public class Problem_4 {
    public static void main(String[] args) {
        String str = "Hello World! 123";
        int letters = 0;
        int spaces = 0;
        int numbers = 0;
        int others = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                letters++;
            } else if (ch == ' ') {
                spaces++;
            } else if (ch >= '0' && ch <= '9') {
                numbers++;
            } else {
                others++;
            }
        }
        System.out.println("Letters: " + letters);
        System.out.println("Spaces: " + spaces);
        System.out.println("Numbers: " + numbers);
        System.out.println("Others: " + others);
    }

}
