package com.example.matrix;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Матрицы комплексных чисел ===");

        ComplexMatrix a = readMatrix("A");  //input and check
        ComplexMatrix b = readMatrix("B");

        System.out.println("\nMatrix A:");
        System.out.print(a);
        System.out.println("\nMatrix B:");
        System.out.print(b);

        boolean running = true;       //move the exit condition into a variable
        while (running) {
            System.out.println("\nChoose the operation:");
            System.out.println("1 — Addition (A + B)");
            System.out.println("2 — Multiplication (A × B)");
            System.out.println("3 — Division (A / B = A × B⁻¹)");
            System.out.println("4 — Transposition A");
            System.out.println("5 — Transposition B");
            System.out.println("6 — Determinant A");
            System.out.println("7 — Determinant B");
            System.out.println("0 — Exit");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            try {  //
                switch (choice) {  //processing of input values
                    case "1":
                        printResult("A + B", a.matrixAddition(b));
                        break;
                    case "2":
                        printResult("A × B", a.multiply(b));
                        break;
                    case "3":
                        printResult("A / B", a.divide(b));
                        break;
                    case "4":
                        printResult("Aᵀ", a.transpose());
                        break;
                    case "5":
                        printResult("Bᵀ", b.transpose());
                        break;
                    case "6":
                        System.out.println("det(A) = " + a.determinant());
                        break;
                    case "7":
                        System.out.println("det(B) = " + b.determinant());
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {  //if the operation is impossible
                System.out.println("Error: " + e.getMessage());           //or the matrix is degenerate during division
            }
        }
        System.out.println("Program is finished");
    }

    //checking the correct(integer*integer) matrix size
    private static int readInt(String prompt) {
        while (true) { // infinite cycle
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim()); //trim for the successful parsing
            } catch (NumberFormatException e) {
                System.out.println("Enter integer");
            }
        }
    }

    //correct output
    private static void printResult(String title, ComplexMatrix m) {
        System.out.println("\nResult " + title + ":");
        System.out.print(m);
    }

    //creating a matrix
    private static ComplexMatrix readMatrix(String name) {
        System.out.println("\nEnter matrix " + name + ":");
        int rows = readInt("Number of rows: ");
        int cols = readInt("Number of cols: ");
        ComplexMatrix c = new ComplexMatrix(rows, cols);   //creating an empty matrix
        System.out.println("Enter the elements in the format 're im' (for example, '3 -2' for 3-2i):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {                                                                    //error protection
                    System.out.printf("  %s[%d][%d] = ", name, i, j);                             //neat input
                    String line = scanner.nextLine().trim();                                      //trim for the successful parsing
                    String[] parts = line.split("\\s+");                                    //split the string by whitespace
                    try {  //transformation
                        double re = Double.parseDouble(parts[0]);  //parsing the real part
                        double im = parts.length > 1 ? Double.parseDouble(parts[1]) : 0; //parsing the imaginary part; if only one element, the im = 0
                        c.set(i, j, new Complex(re, im));    //creating a complex number and writing it to a matrix
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect input, try again");
                    }
                }
            }
        }
        return c;
    }
}

