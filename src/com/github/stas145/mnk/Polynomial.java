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
        if(monomial.length == 0) {
            throw new ArrayStoreException("bad data");
        }
    }

    public void buildPolynomialMNKbyTikhonov(ExperimentalData _data, double alfa) {

        double[][] A = new double[m][m];
        double[] b = new double[m];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                A[i][j] = 0;
                for(int k = 0; k < _data.getSize(); k++){
                    A[i][j] += Math.pow(_data.getPoint(k).getX(), i + j);
                    if(j == i) {
                        A[i][j] += alfa;
                    }
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
        if(monomial.length == 0) {
            throw new ArrayStoreException("bad data");
        }
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

    public ExperimentalData  buildPolynomialMNKByCrossValidation(ExperimentalData _data, int numPartitions) {
        int intervalLength = _data.getSize()/numPartitions;

        double testError = Double.MAX_VALUE;
        double[] xTest = new double[intervalLength];
        double[] yTest = new double[intervalLength];
        double[] xTraining = new double[intervalLength*(numPartitions - 1)];
        double[] yTraining = new double[intervalLength*(numPartitions - 1)];
        ExperimentalData optData= new ExperimentalData();

        for(int i = 0; i < numPartitions; i++) {
            int testDataIndex = 0;
            int trainingDataIndex = 0;
            for(int k = 0; k < intervalLength*numPartitions; k++) {
                if( (k >= i * intervalLength)&&(k < (i+1) * intervalLength) ) {
                    xTest[testDataIndex] = _data.getPoint(k).getX();
                    yTest[testDataIndex] = _data.getPoint(k).getY();
                    testDataIndex ++;
                } else {
                    xTraining[trainingDataIndex] = _data.getPoint(k).getX();
                    yTraining[trainingDataIndex] = _data.getPoint(k).getY();
                    trainingDataIndex ++;
                }
            }
            ExperimentalData testData = new ExperimentalData(intervalLength, xTest, yTest);
            ExperimentalData trainingData = new ExperimentalData(intervalLength*(numPartitions - 1), xTraining, yTraining);
            System.out.print("size:" + trainingData.getSize());

            Polynomial crossValidationPolynomial = new Polynomial(this.m);
            crossValidationPolynomial.buildPolynomialMNK(trainingData);
            System.out.print("m: "
                    + crossValidationPolynomial.m
                    + " Iterasion "
                    + i
                    + ". Error: "
                    + crossValidationPolynomial.testPolynomial(testData)
                    + " ? " + testError);
            if(i == 0) {
                testError = crossValidationPolynomial.testPolynomial(testData);
                this.m = crossValidationPolynomial.m;
                System.arraycopy(crossValidationPolynomial.monomial, 0, this.monomial, 0, crossValidationPolynomial.monomial.length);
                System.out.print(" <<--! ");
                optData = new ExperimentalData(intervalLength*(numPartitions - 1), xTraining, yTraining);
            } else {
                if (crossValidationPolynomial.testPolynomial(testData) < testError) {
                    testError = crossValidationPolynomial.testPolynomial(testData);
                    this.m = crossValidationPolynomial.m;
                    System.arraycopy(crossValidationPolynomial.monomial, 0, this.monomial, 0, crossValidationPolynomial.monomial.length);
                    System.out.print(" <<--! ");
                    optData = new ExperimentalData(intervalLength*(numPartitions - 1), xTraining, yTraining);
                }
            }
            System.out.print("\n");

        }
        return optData;
    }

    public double testPolynomial(ExperimentalData testData) {
        double res = 0;
        for(int i = 0; i < testData.getSize(); i++) {
            res += Math.pow(
                    (this.getValueAtPoint( testData.getPoint(i).getX() ) - testData.getPoint(i).getY()),
                    2);
        }
        return res;
    }
    
}
