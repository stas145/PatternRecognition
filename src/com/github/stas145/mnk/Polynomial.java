package com.github.stas145.mnk;
import com.github.stas145.common.function.ExperimentalData;
import com.github.stas145.common.function.OneVariableFunction;

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
        return this.getValueAtPointIst(x);
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

    public int getOptPowerMNKByCrossValidation(ExperimentalData _data, int numPartitions, int minPower, int maxPower) {

        int optPower = 1;
        int[] powerIs = new int[numPartitions];
        for(int m = 0; m < powerIs.length; m++)
        {
            powerIs[m] = 0;
        }
        int intervalLength = _data.getSize()/numPartitions;
        double[] xTest = new double[intervalLength];
        double[] yTest = new double[intervalLength];
        double[] xTraining = new double[intervalLength*(numPartitions - 1)];
        double[] yTraining = new double[intervalLength*(numPartitions - 1)];

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

            int optPowerOnTestData = 0;
            double testError = Double.MAX_VALUE;
            for(int p = minPower; p <= maxPower; p++) {
                Polynomial crossValidationPolynomial = new Polynomial(p);
                crossValidationPolynomial.buildPolynomialMNK(trainingData);
                if(p == minPower) {
                    testError = crossValidationPolynomial.testPolynomial(testData);
                    optPowerOnTestData = p;
                } else {
                    if (crossValidationPolynomial.testPolynomial(testData) < testError) {
                        testError = crossValidationPolynomial.testPolynomial(testData);
                        optPowerOnTestData = p;
                    }
                }
            }
            powerIs[i] = optPowerOnTestData;
            System.out.print("\n");

        }
        for(int m = 0; m < powerIs.length; m++)
        {
            System.out.println("Power: " + powerIs[m]);
        }
        return 0;
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
