package com.github.stas145.mnk.function;
import com.github.stas145.mnk.function.OneVariableFunction;
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
        //return 2*x+1 + (new Random().nextGaussian())/100;
        //return Math.sin(x) + (new Random().nextGaussian()) / 5;
        //return 4 * Math.pow(x, 4) - 10 * Math.pow(x, 2) + x;
        return Math.sin(1/x);
    }

}