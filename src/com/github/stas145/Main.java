package com.github.stas145;

import com.github.stas145.mnk.*;
import com.github.stas145.mnk.function.*;

public class Main {
	public static void main(String[] args){
		/*
		//gauss test
		int n = 3;
		//x = [1, 2, 3]
		double[][] A = 
		{
			{1, 2, 3},
			{7, 0, 5},
			{4, 1, 0},
		};
		double[] b = {14, 22, 6};
		SystemOfLinearEquations aSLE = new SystemOfLinearEquations(n, A, b);
		aSLE.print();
		double[] x = aSLE.gauss();
		System.out.print("\nx = {");
		for(double x_i: x) {
			System.out.print(x_i + " ");
		}
		System.out.print("\b}\n");
		*/

		OneVariableFunction sin = new SinFun();
		ExperimentalData data = new ExperimentalData();
		data.randomCreate(10, 0, 1, sin);
		data.print();
		Polynomial pol = new Polynomial(4);
		pol.buildPolynomialMNK(data);
		pol.print();
	}
}