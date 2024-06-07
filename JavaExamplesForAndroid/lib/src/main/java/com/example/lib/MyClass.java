package com.example.lib;

import java.util.Scanner;
//Write a program that gets
//from the user 2 numbers and
//displays their division and
//remainder.
public class MyClass {
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the first number: ");
    int num1 = input.nextInt();
    System.out.println("Enter the second number: ");
    int num2 = input.nextInt();

    double division = (double)num1 / num2; // integer over integer will give an integer so we need to cast it to float or double
    double remainder = (double)num1 % num2;
    System.out.println("The division of the two numbers is: " + division);
    System.out.println("The remainder of the two numbers is: " + remainder);


}
}