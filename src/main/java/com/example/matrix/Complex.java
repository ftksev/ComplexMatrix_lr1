package com.example.matrix;

public class Complex {
    private final double re; // protects encapsulation
    private final double im; // immutable objects
    public Complex(double re, double im) { // main constructor
        this.re = re;
        this.im = im;
    }
    public Complex(double re) { //additional constructor
        this(re, 0);
    }
    public double getRe() { return re; }
    public double getIm() { return im; }
    public Complex add(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }
    public Complex sub(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }
    public Complex mul(Complex other) {
        return new Complex(this.re*other.re - this.im*other.im, this.re*other.im + this.im*other.re);
    }
    public Complex div(Complex other) {
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return new Complex(
                (this.re * other.re + this.im * other.im) / denom,
                (this.im * other.re - this.re * other.im) / denom
        );

    }
    public Complex conjugate() {
        return new Complex(re, -im);
    }
    public double abs() {
        return Math.hypot(re, im);
    }
    @Override   //override for convenient output
    public String toString(){
        if (im == 0.0000) return String.format("%.4f", re);                      //If the im = zero, output only the re
        if (re == 0.0000) return String.format("%.4fi", im);                     //If the re = zero, output only the im +i
        String sign = im >= 0 ? "+" : "-";
        return String.format("%.4f %s %.4fi", re, sign, Math.abs(im));           //abs(im), not to duplicate the sign
    }

    //It is correct to compare complex numbers
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Complex)) return false;
        Complex c = (Complex) object;
        return Double.compare(c.re, re) == 0 && Double.compare(c.im, im) == 0;
    }
    //equals redefined -> hashCode redefined
    @Override
    public int hashCode() {
        return Double.hashCode(re)*31 + Double.hashCode(im);
    }

}