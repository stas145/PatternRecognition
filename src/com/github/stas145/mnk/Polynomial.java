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

    public void  buildPolynomialMNKByCrossValidation(ExperimentalData _data, int numPartitions) {
        int intervalLength = _data.getSize()/numPartitions;
        for(int i = 0; i < numPartitions; i++) {
            double[] xTest = new double[intervalLength];
            double[] yTest = new double[intervalLength];
            double[] xTraining = new double[intervalLength*(numPartitions - 1)];
            double[] yTraining = new double[intervalLength*(numPartitions - 1)];

            int testDataIndex = 0;
            int trainingDataIndex = 0;
            for(int k = 0; k < intervalLength*numPartitions; k++) {
                if( (k >= i * intervalLength)&&(k < (i+1) * intervalLength) ) {
                    xTest[testDataIndex] = _data.getPoint(k).getX();
                    yTest[testDataIndex] = _data.getPoint(k).getY();
                    testDataIndex ++;
                } else {
                    xTraining[testDataIndex] = _data.getPoint(k).getX();
                    yTraining[testDataIndex] = _data.getPoint(k).getY();
                    trainingDataIndex ++;
                }
            }

            ExperimentalData testData = new ExperimentalData(intervalLength, xTest, yTest);
            ExperimentalData trainingData = new ExperimentalData(intervalLength*(numPartitions - 1), xTraining, yTraining);

            Polynomial crossValidationPolynomial = new Polynomial(this.m);
            crossValidationPolynomial.buildPolynomialMNK(trainingData);
            if(i == 0) {
                this.m = crossValidationPolynomial.m;
                System.arraycopy(crossValidationPolynomial.monomial, 0, this.monomial, 0, crossValidationPolynomial.monomial.length);
            } else {
                if (crossValidationPolynomial.testPolynomial(testData) < this.testPolynomial(testData)) {
                    this.m = crossValidationPolynomial.m;
                    System.arraycopy(crossValidationPolynomial.monomial, 0, this.monomial, 0, crossValidationPolynomial.monomial.length);
                }
            }

        }
    }

    public double testPolynomial(ExperimentalData testData) {
        double res = 0;
        for(int i = 0; i < testData.getSize(); i++) {
            res = Math.pow((this.getValueAtPoint(testData.getPoint(i).getX()) - testData.getPoint(i).getY()), 2);
        }
        return res;
    }
    
}
