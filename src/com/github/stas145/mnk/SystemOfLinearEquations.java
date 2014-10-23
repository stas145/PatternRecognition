package com.github.stas145.mnk;

public class SystemOfLinearEquations {
    //Ax = b, find x
    private int n;
	private double[][] A;
	private double[] b;

	public SystemOfLinearEquations() {
		n = 0;
		A = new double[0][0];
		b = new double[0];
	}

	public SystemOfLinearEquations(int _n, double[][] _A, double[] _b) {
		n = _n;
		A = new double[n][n];
		b = new double[n];
		for(int i = 0; i < n; i++) {
			System.arraycopy(_A[i], 0, A[i], 0, n);
			System.arraycopy(_b, 0, b, 0, n);
		}
	}

    public void print() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.print(" | " + b[i] + "\n");
		}
	}

	public double[] gauss() {
		double[] x = new double[n];
		for(int i = 0; i < n; i++) {
			int main_i = i;
			for(int j = i; j < n; j++) {
				if(A[j][i]*A[j][i] > A[main_i][i]*A[main_i][i]) {
					main_i = j;
				}
			}
			if(A[main_i][i] == 0)
				return new double[0];

			if(main_i != i) {
				for(int j = 0; j < A[i].length; j++) {
					double buf_A = A[i][j];
					A[i][j] = A[main_i][j];
					A[main_i][j] = buf_A;
				}
				double buf_b = 0;
				buf_b = b[i];
				b[i] = b[main_i];
				b[main_i] = buf_b;
			}
	
			for(int j = i + 1; j < n; j++) {
				if(A[j][i] !=0)
				{
					double coef = -A[j][i]/A[i][i];
				
					for(int k = i; k < n; k++) {
						A[j][k] += coef*A[i][k];
					}
					b[j] +=coef*b[i];
				}
			}
		}
		if(A[n - 1][n - 1] == 0)
			return new double[0];
		for(int i = n - 1; i >= 0; i--) {
			x[i] = 0;
			x[i] += b[i];
			for(int j = i + 1; j < n; j ++) {
				x[i] -= A[i][j]*x[j];
			}
			x[i] /=A[i][i];
		}
		return x;
	}
}
