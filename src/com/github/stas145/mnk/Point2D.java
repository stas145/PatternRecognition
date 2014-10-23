package com.github.stas145.mnk;

public class Point2D {
    private double x;
    private double y;

	public Point2D() {
		x = 0;
		y = 0;
	}
	public Point2D(double _x, double _y) {
		x = _x;
		y = _y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
}
