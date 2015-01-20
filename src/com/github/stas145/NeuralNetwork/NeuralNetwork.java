package com.github.stas145.NeuralNetwork;
import com.github.stas145.common.function.ExperimentalData;
import com.github.stas145.common.function.OneVariableFunction;

/**
 * Created by stas on 24.12.14.
 */
public class NeuralNetwork implements OneVariableFunction {

    private int m; //число узлов в сети
    private double[] zeroLayerX;
    private double[] zeroLayerConst;
    private double[] firstLayer;

    public NeuralNetwork() {
        m = 0;
        zeroLayerX = new double[0];
        zeroLayerConst = new double[0];
        firstLayer = new double[0];
    }

    public NeuralNetwork (int _m) {
        if(_m > 0) {
            this.m = _m;
            zeroLayerX = new double[m + 1];
            zeroLayerConst = new double[m + 1];
            firstLayer = new double[m + 1];
            for(int i = 0; i < this.m + 1; i++) {
                zeroLayerX[i] = 0;
                zeroLayerConst[i] = 0;
                firstLayer[i] = 0;
            }
        }
        else {
            m = 0;
            zeroLayerX = new double[0];
            zeroLayerConst = new double[0];
            firstLayer = new double[0];
        }
    }

    public void builtNeuralNetwork(ExperimentalData _data, double alfa) {
        for(int i = 0; i < this.m + 1; i++) {
            zeroLayerX[i] = Math.random()*100 - 50;
            zeroLayerConst[i] = Math.random()*100 - 50;
            firstLayer[i] = Math.random()*100 - 50;
        }
    }

    @Override
    public double getValueAtPoint(double _x) {
        return this.getValueAtPointIst(_x);
    }

    @Override
    public double getValueAtPointIst(double _x) {
        double res = 0;
        for(int i = 1; i < this.m; i ++) {
            double buf = 0;
            buf += zeroLayerX[i]*_x + zeroLayerConst[i];
            res += nonLinearFunction(buf)*firstLayer[i];
        }
        res += firstLayer[0];
        return res;
    }

    private double nonLinearFunction(double _x) {
        return 1/(1 + Math.exp(_x));
    }

    public void print() {
        System.out.print("Is network" + this.m); //todo
    }
}
