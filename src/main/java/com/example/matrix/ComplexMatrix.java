package com.example.matrix;

public class ComplexMatrix {
    private final Complex[][] data;
    private final int rows;
    private final int cols;
    // main constructor
    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Incorrect matrix size");
        }
        else {
            this.rows = rows;
            this.cols = cols;
            this.data = new Complex[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    data[i][j] = new Complex(0, 0);
        }
    }
    // second constructor for a fast multiplication
    public ComplexMatrix(Complex[][] values) {
        if (values == null || values.length == 0 || values[0].length == 0) {
            throw new IllegalArgumentException("Incorrect matrix data");
        } else {
            this.rows = values.length;
            this.cols = values[0].length;
            this.data = new Complex[rows][cols];
            for (int i = 0; i < rows; i++) {
                if (values[i].length != cols) {
                    throw new IllegalArgumentException("Matrix rows should have a same length ");
                } else {
                    for (int j = 0; j < cols; j++)
                        data[i][j] = values[i][j];
                }
            }
        }
    }
    public int getRows() {return rows;}
    public int getCols() {return cols;}

    public Complex get(int i, int j) {return data[i][j]; }
    public void set(int i, int j, Complex value) {data[i][j] = value; }

    //Matrix addition
    public ComplexMatrix matrixAddition(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols)
            throw new IllegalArgumentException("Incompatible matrix dimensions");
        ComplexMatrix result = new ComplexMatrix(rows, cols); // maybe using second constructor is better?
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
        return result;
    }

    //Matrix multiplication
    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows)
            throw new IllegalArgumentException("Matrix dimensions are incompatible for multiplication");
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][j].mul(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        return result;
    }

    //Matrix Transposition
    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.data[j][i] = this.data[i][j];
        return result;
    }
    //Determinant
    //Gauss method
    public Complex determinant() {
        if (rows != cols)
            throw new IllegalArgumentException("Determinant is defined only for square matrices");
        int matrixSize = rows;
        Complex[][] localCopy = new Complex[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++)
            for (int j = 0; j < matrixSize; j++)
                localCopy[i][j] = data[i][j];
        Complex det = new Complex(1,0);
        for (int i = 0; i < matrixSize; i++) {
            // Search for the pivot element
            int pivot = -1;
            double maxAbs = 0;
            for (int k = i; k < matrixSize; k++) {
                double abs = localCopy[k][i].abs();
                if (abs > maxAbs) {
                    maxAbs = abs;
                    pivot = k;
                }
            }
            if (pivot == -1 || maxAbs < 1e-12)
                return new Complex(0,0);
            if (pivot != i) { // if current row is not pivot, change rows
                Complex[] tmp = localCopy[i]; localCopy[i] = localCopy[pivot]; localCopy[pivot] = tmp;
                det = det.mul(new Complex(-1,0)); //
            }
            det = det.mul(localCopy[i][i]);
            for (int k = i +1; k < matrixSize; k++) {
                Complex factor = localCopy[k][i].div(localCopy[i][i]);
                for (int j = i; j < matrixSize; j++) {
                    localCopy[k][j] = localCopy[k][j].sub(factor.mul(localCopy[i][j]));
                }
            }

        }
        return det;
    }

    // Calculate the cofactor for element a[i][j]
    private Complex cofactor(int row, int col) {
        int n = rows;
        Complex[][] minor = new Complex[n - 1][n - 1];
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int mj = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[mi][mj] = data[i][j];
                mj++;
            }
            mi++;
        }
        Complex minorDet = new ComplexMatrix(minor).determinant();
        int sign = ((row + col) % 2 == 0) ? 1 : -1;
        return new Complex(sign, 0).mul(minorDet);
    }

    //Inverse matrix for division
    public ComplexMatrix inverse() {
        if (rows != cols)
            throw new IllegalArgumentException("Inverse matrix is defined only for square matrices");
        int n = rows;
        Complex det = determinant();
        if (det.abs() < 1e-12)
            throw new ArithmeticException("Matrix is singular - inverse matrix is not defined");

        ComplexMatrix result = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Complex cofactor = cofactor(i, j);
                result.data[j][i] = cofactor.div(det);
            }
        }
        return result;
    }

    //Matrix division A / B = A * B^(-1)
    public ComplexMatrix divide(ComplexMatrix other) {
        return this.multiply(other.inverse());
    }

    //override for table output
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[ ");
            for (int j = 0; j < cols; j++) {                                 //align the columns
                sb.append(String.format("%-22s", data[i][j].toString()));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }





}