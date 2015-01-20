package com.github.stas145.common.function;
import com.github.stas145.common.Point2D;

public class ExperimentalData {
    //n sets of Data {x, y} are experimentaly got
    private int n;
    private double[] x;
    private double[] y;

    public int getSize() {
    	return n;
    }

    public Point2D getPoint(int _n) {
    	if(_n >= n)
    		return new Point2D();
    	return new Point2D(x[_n], y[_n]);
    }

	public ExperimentalData() {
		n = 0;
		x = new double[0];
		y = new double[0];
	}

	public ExperimentalData(int _n, double[] _x, double[] _y) {
		n = _n;
        x = new double[n];
        y = new double[n];
		System.arraycopy(_x, 0, x, 0, n);
		System.arraycopy(_y, 0, y, 0, n);
	}

	public void randomCreate(int _n, double left_x, double right_x, OneVariableFunction f) {
		n = _n;
		x = new double[n];
		y = new double[n];
		for(int i = 0; i < n; i++) {
			x[i] = Math.random()*(right_x - left_x) + left_x;
			y[i] = f.getValueAtPoint(x[i]);	
		}
	}

	public void print() {
		System.out.print("x: ");
		for(int i = 0; i < n; i++) {
			System.out.printf("%.2f ", x[i]);
		}
		System.out.printf("\ny: ");
		for(int i = 0; i < n; i++) {
			System.out.printf("%.2f ", y[i]);
		}
		System.out.print("\n");
	}
}
