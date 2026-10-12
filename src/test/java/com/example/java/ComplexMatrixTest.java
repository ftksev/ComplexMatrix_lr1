package com.example.java;

import com.example.matrix.Complex;
import com.example.matrix.ComplexMatrix;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexMatrixTest {

    @Test
    void testAddition() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][]{
                { new Complex(1, 2), new Complex(3, -1) },
                { new Complex(0, 1), new Complex(-2, 0) }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][]{
                { new Complex(2, -2), new Complex(1, 1) },
                { new Complex(5, 0),  new Complex(0, 3) }
        });
        ComplexMatrix sum = a.matrixAddition(b);
        assertEquals(new Complex(3, 0), sum.get(0, 0));
        assertEquals(new Complex(4, 0), sum.get(0, 1));
        assertEquals(new Complex(5, 1), sum.get(1, 0));
        assertEquals(new Complex(-2, 3), sum.get(1, 1));
    }

    @Test
    void testDeterminantComplex() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][]{
                { new Complex(1, 1), new Complex(1, -1) },
                { new Complex(1, -1), new Complex(1, 1) }
        });
        Complex det = a.determinant();
        assertEquals(0.0, det.getRe(), 1e-9);
        assertEquals(4.0, det.getIm(), 1e-9);
    }

    @Test
    void testDivideSingular() {
        ComplexMatrix a = new ComplexMatrix(new Complex[][]{
                { new Complex(1, 0), new Complex(2, 0) },
                { new Complex(3, 0), new Complex(4, 0) }
        });
        ComplexMatrix b = new ComplexMatrix(new Complex[][]{
                { new Complex(1, 0), new Complex(2, 0) },
                { new Complex(2, 0), new Complex(4, 0) }
        });
        assertThrows(ArithmeticException.class, () -> a.divide(b));
    }
}