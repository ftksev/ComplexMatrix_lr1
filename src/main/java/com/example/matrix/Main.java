package com.example.matrix;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Матрицы комплексных чисел ===");

        ComplexMatrix a = readMatrix("A");
        ComplexMatrix b = readMatrix("B");

        System.out.println("\nMatrix A:");
        System.out.print(a);
        System.out.println("\nMatrix B:");
        System.out.print(b);

        boolean running = true;
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
            try {
                switch (choice) {
                    case "1": printResult("A + B", a.matrixAddition(b)); break;
                    case "2": printResult("A × B", a.multiply(b)); break;
                    case "3": printResult("A / B", a.divide(b)); break;
                    case "4": printResult("Aᵀ", a.transpose()); break;
                    case "5": printResult("Bᵀ", b.transpose()); break;
                    case "6": System.out.println("det(A) = " + a.determinant()); break;
                    case "7": System.out.println("det(B) = " + b.determinant()); break;
                    case "0": running = false; break;
                    default:  System.out.println("Unknown command");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Program is finished");
    }

    private static ComplexMatrix readMatrix(String name) {
        System.out.println("\nEnter matrix " + name + ":");
        int rows = readInt("Number of rows: ");
        int cols = readInt("Number of cols: ");
        ComplexMatrix m = new ComplexMatrix(rows, cols);
        System.out.println("Enter the elements in the format 're im' (for example, '3 -2' for 3-2i):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    System.out.printf("  %s[%d][%d] = ", name, i, j);
                    String line = scanner.nextLine().trim().replace(',', '.');
                    String[] parts = line.split("\\s+");
                    try {
                        double re = Double.parseDouble(parts[0]);
                        double im = parts.length > 1 ? Double.parseDouble(parts[1]) : 0;
                        m.set(i, j, new Complex(re, im));
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect input, try again");
                    }
                }
            }
        }
        return m;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter integer");
            }
        }
    }

    private static void printResult(String title, ComplexMatrix m) {
        System.out.println("\nResult " + title + ":");
        System.out.print(m);
    }
}