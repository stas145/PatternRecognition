package com.github.stas145.mnk;

public class Point2D {
    private double x;
    private double y;

	public Point2D() {
		this.x = 0;
        this.y = 0;
	}


	public Point2D(double _x, double _y) {
        this.x = _x;
        this.y = _y;
	}

    public Point2D(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
}
