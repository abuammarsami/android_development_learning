package com.example.lib;

import static java.lang.Math.pow;

import java.util.Scanner;
/**
 * Problem_2: Write a program that gets
 * from the user the radius and
 * print the area and perimeter
 * of a circle.
 */
public class Problem_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the radius of the Circle: ");
        int radius = input.nextInt();


        double area = Math.PI * pow(radius,2);
        double perimeter = 2 * Math.PI  * radius;
        System.out.println("The area of the circle is: " + area);
        System.out.println("The perimeter of the circle is: " + perimeter);
}
}
