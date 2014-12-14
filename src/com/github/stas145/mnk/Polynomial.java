package com.github.stas145.mnk;
import com.github.stas145.mnk.*;
import com.github.stas145.mnk.function.OneVariableFunction;

public class Polynomial implements OneVariableFunction {
    private int m;
    private double[] monomial;

    public Polynomial() {
        m = 0;
        monomial = new double[0];
    }

    public Polynomial(int _m) {
        if(_m > 0) {
            m = _m;
            monomial = new double[m];
        } 
        else {
            m = 0;
            monomial = new double[0];
        }
    }

    public Polynomial(int _m, double[] _monomial) {
        if(_m > 0) {
            m = _m;
            monomial = new double[m];
            for(int i = 0; i < m; i++)
                monomial[i] = _monomial[i];
        } 
        else {
            m = 0;
            monomial = new double[0];
        }
    }

    public void buildPolynomialMNK(ExperimentalData _data) {
        
        double[][] A = new double[m][m];
        double[] b = new double[m];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                A[i][j] = 0;
                for(int k = 0; k < _data.getSize(); k++){
                    A[i][j] += Math.pow(_data.getPoint(k).getX(), i + j);
                }
            }    
        }
        for(int i = 0; i < m; i++) {
            b[i] = 0;
            for(int k = 0; k < _data.getSize(); k++){
                b[i] += Math.pow(_data.getPoint(k).getX(), i)*_data.getPoint(k).getY();
            }
        }

        
        SystemOfLinearEquations aSLE = new SystemOfLinearEquations(m, A, b);
        monomial = aSLE.gauss();
    }

    @Override
    public double getValueAtPoint(double x){
        double res = 0;
        for(int i = 0; i < m; i++){
            res += Math.pow(x, i)*monomial[i];
        }
        return res;
    }
    @Override
    public double getValueAtPointIst(double x){
        double res = 0;
        for(int i = 0; i < m; i++){
            res += Math.pow(x, i)*monomial[i];
        }
        return res;
    }

    public void print() {
         for(int i = 0; i < m; i++) {
            System.out.println("m[" + i + "] = " + monomial[i]);
         }
    }
    
}
