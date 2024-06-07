package com.example.lib;

/**
 * Write a program to make such
 * a pattern like a pyramid with a
 * number which will repeat the
 * number in the same row.
 */
public class Problem_8 {
    public static void main(String[] args) {
        int n = 5;
        for (int i = 1; i <= n; i++) { // outer loop for number of rows(n)
            for (int j = 1; j <= n - i; j++) { // inner loop for spaces
                System.out.print(" "); // here we use j <= n - i because at the end of each row we have i-1 spaces
            }
            for (int j = 1; j <= i; j++) { // inner loop for number of columns
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
