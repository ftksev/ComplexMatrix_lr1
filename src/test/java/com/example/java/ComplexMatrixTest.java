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

    @Test
    void testMultiply() {
        // A = | 1+2i   3-i  |   (2x2)
        //     |   i    -2    |
        ComplexMatrix a = new ComplexMatrix(new Complex[][]{
                { new Complex(1, 2), new Complex(3, -1) },
                { new Complex(0, 1), new Complex(-2, 0) }
        });

        // B = | 2-2i   1+i |   (2x2)
        //     |  5      3i |
        ComplexMatrix b = new ComplexMatrix(new Complex[][]{
                { new Complex(2, -2), new Complex(1, 1) },
                { new Complex(5, 0),  new Complex(0, 3) }
        });

        // Ожидаемый результат A*B:
        // C[0][0] = (1+2i)(2-2i) + (3-i)(5+0i)
        //         = (6+2i) + (15-5i) = 21 - 3i
        // C[0][1] = (1+2i)(1+i) + (3-i)(0+3i)
        //         = (-1+3i) + (3+9i) = 2 + 12i
        // C[1][0] = (0+i)(2-2i) + (-2)(5)
        //         = (2+2i) + (-10) = -8 + 2i
        // C[1][1] = (0+i)(1+i) + (-2)(0+3i)
        //         = (-1+i) + (0-6i) = -1 - 5i
        ComplexMatrix product = a.multiply(b);

        assertEquals(2, product.getRows());
        assertEquals(2, product.getCols());

        assertEquals(new Complex(21, -3), product.get(0, 0));
        assertEquals(new Complex(2, 12),  product.get(0, 1));
        assertEquals(new Complex(-8, 2),  product.get(1, 0));
        assertEquals(new Complex(-1, -5), product.get(1, 1));
    }
}