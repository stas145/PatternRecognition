package com.github.stas145.mnk.function;
import com.github.stas145.mnk.function.OneVariableFunction;
import java.util.Random;

public class SinFun implements OneVariableFunction {
    
    public SinFun() {

    }

    @Override
    public double getValueAtPoint(double x) {
        return 2*x+1 + (new Random().nextGaussian())/100;
    }
}