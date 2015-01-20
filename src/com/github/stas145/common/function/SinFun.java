package com.github.stas145.common.function;
import java.util.Random;

public class SinFun implements OneVariableFunction {
    
    public SinFun() {

    }

    @Override
    public double getValueAtPoint(double x) {
        return getValueAtPointIst(x) + (new Random().nextGaussian()) / 5;
    }

    @Override
    public double getValueAtPointIst(double x) {
        //return 4 * Math.pow(x, 4) - 10 * Math.pow(x, 2) + x;
        //return Math.sin(1/x)*x;
        //return 0;
        return x*(x-2)*(x+2)*(x-1)*(x+1);
    }

}