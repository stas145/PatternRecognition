package com.github.stas145.NeuralNetwork.GUI;

/**
 * Created by stas on 05.11.14.
 */

import com.github.stas145.NeuralNetwork.NeuralNetwork;
import com.github.stas145.common.function.ExperimentalData;
import com.github.stas145.common.function.OneVariableFunction;
import com.github.stas145.common.function.SinFun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NeuralNetworkGraph extends JPanel implements ActionListener {
    private int
            scale,// цена деления  по шкалам
            ly,// длина оси у
            lx,// длина оси х
            //центер системы координат
            ox,
            oy,

            hx;//шаг табуляции в пикселях

    private double left = -2;
    private double right = 2;

    //переменные данных

    private ExperimentalData data= new ExperimentalData();
    private int sizeExpData;
    private NeuralNetwork network;
    private int numNetworkNodes;
    private double derivativeStep;
    private OneVariableFunction originFun;
    private boolean
            paintOxy,
            paintExpData,
            paintNetwork,
            paintOriginFun;

    public void actionPerformed(ActionEvent e) {

    }

    public NeuralNetworkGraph() {
        this.setBackground(new Color(200, 200, 200));

        this.scale = 10;

        this.ox = 300;
        this.oy = 200;

        this.lx = 600;
        this.ly = 400;

        this.hx = 1;

        this.sizeExpData = 10;
        this.numNetworkNodes = 1;
        this.derivativeStep = 0.1;

        this.paintExpData = true;
        this.paintNetwork = false;
        paintOriginFun = false;
        paintOxy = true;

        originFun = new SinFun();
        data = new ExperimentalData();
        network = new NeuralNetwork(); //todo add parameter numNetworkNodes
    }

    @Override
    public void paint(Graphics g) {
        clear(g);
        if (paintOxy)
            paintOxy(g);
        if(paintOriginFun)
            paintOriginFun(g);
        if(paintExpData)
            paintExperimentalData(g);
        if(this.paintNetwork)
            paintNeuralNetwork(g);
        paintInfo(g, 0, 12);
    }
    public void paintInfo(Graphics g, int x, int y) {
        g.drawString("data size: " + sizeExpData, x, y);
        g.drawString("Network nodes: " + numNetworkNodes, x, y + 15);
        g.drawString("center: (" + ox + ", " + oy + ")", x, y + 30);
        NeuralNetwork.print();

    }
    public void generateExpData() {
       data.randomCreate(sizeExpData, left, right, originFun);
    }

    public void buildNeuralNetwork() {
        network = new NeuralNetwork(numNetworkNodes);
        network.builtNeuralNetwork(data, derivativeStep);

    }

    public void buildNeuralNetworkBackpropagation() {
        //todo
    }

    public void paintExperimentalData(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        for(int i = 0; i < data.getSize(); i++) {
            paintCircle(g, data.getPoint(i).getX(), data.getPoint(i).getY(), 6);
        }
        g.setColor(new Color(0, 0, 0));
    }
    public void paintNeuralNetwork(Graphics g) {
        g.setColor(new Color(0, 255, 0));
        paintFunction(g, network);
        g.setColor(new Color(0, 0, 0));
    }
    public void paintOriginFun(Graphics g) {
        g.setColor(new Color(0, 0, 255));
        paintFunction(g, originFun);
        g.setColor(new Color(0, 0, 0));
    }
    public void paintFunction(Graphics g, OneVariableFunction f) {
        for(int x = 0; x < lx; x += hx) {
            int x1 = x;
            int x2 = x + hx;
            if(x2 > lx)
                x2 = lx;
            paintLine(g, physToLogicCoordX(x1),f.getValueAtPointIst(physToLogicCoordX(x1)),
                    physToLogicCoordX(x2),f.getValueAtPointIst(physToLogicCoordX(x2)));

            paintCircle(g, physToLogicCoordX(x1), f.getValueAtPointIst(physToLogicCoordX(x1)), 1);
        }
    }
    public void paintOxy(Graphics g) {
        //рамка
        g.setColor(new Color(255, 255, 255));
        g.drawLine(0, 0, 0, ly);
        g.drawLine(0, ly, lx, ly);
        g.drawLine(lx, ly, lx, 0);
        g.drawLine(lx, 0, 0, 0);
        g.setColor(new Color(0, 0, 0));


        // Ось OY
        if(ox >= 0 && ox <= lx) {
            //Ось
            g.drawLine(logicToPhysCoordX(0), 0, logicToPhysCoordX(0), ly);
            //Стрелки
            g.drawLine(logicToPhysCoordX(0), 0, logicToPhysCoordX(0) - 3, 10);
            g.drawLine(logicToPhysCoordX(0), 0, logicToPhysCoordX(0) + 3, 10);
            //Надпись
            g.drawString("Y", logicToPhysCoordX(0) + 10, 10);
            for(int iy = 0; logicToPhysCoordY(iy) > 0 && logicToPhysCoordY(iy) < ly; iy++) {
                g.drawLine(logicToPhysCoordX(0), logicToPhysCoordY(iy),
                        logicToPhysCoordX(0) - 3, logicToPhysCoordY(iy));
            }
            for(int iy = 0; logicToPhysCoordY(iy) > 0 && logicToPhysCoordY(iy) < ly; iy--) {
                g.drawLine(logicToPhysCoordX(0), logicToPhysCoordY(iy),
                        logicToPhysCoordX(0) - 3, logicToPhysCoordY(iy));
            }
        }
        // Ось OX
        if(oy >= 0 && oy <= ly) {
            //Ось
            g.drawLine(0, logicToPhysCoordY(0), lx, logicToPhysCoordY(0));
            //Стрелки
            g.drawLine(lx, logicToPhysCoordY(0), lx - 10, logicToPhysCoordY(0) - 3);
            g.drawLine(lx, logicToPhysCoordY(0), lx - 10, logicToPhysCoordY(0) + 3);
            //Надпись
            g.drawString("X", lx - 10, logicToPhysCoordY(0) + 15);
            for(int ix = 0; logicToPhysCoordX(ix) < lx && logicToPhysCoordX(ix) > 0; ix--) {
                g.drawLine(logicToPhysCoordX(ix), logicToPhysCoordY(0),
                        logicToPhysCoordX(ix), logicToPhysCoordY(0) + 3);
            }
            for(int ix = 0; logicToPhysCoordX(ix) < lx && logicToPhysCoordX(ix) > 0; ix++) {
                g.drawLine(logicToPhysCoordX(ix), logicToPhysCoordY(0),
                        logicToPhysCoordX(ix), logicToPhysCoordY(0) + 3);
            }
        }
        if(oy >= 0 && oy <= ly && ox >= 0 && ox <= lx) {
            g.drawString("0", logicToPhysCoordX(0) - 15, logicToPhysCoordY(0) + 15);
            g.drawString("1", logicToPhysCoordX(1), logicToPhysCoordY(0) + 15);
        }

    }
    public void clear(Graphics g) {
        super.paint(g);
    }

    private void paintCircle(Graphics g, double x, double y, int r) {
        if(logicToPhysCoordX(x) >= 0 && logicToPhysCoordX(x) <= lx &&
                logicToPhysCoordY(y) >= 0 && logicToPhysCoordY(y) <= ly) {
            g.drawArc(logicToPhysCoordX(x) - r / 2, logicToPhysCoordY(y) - r / 2, r, r, 0, 360);
        }
    }
    private void paintLine(Graphics g, double x1, double y1, double x2, double y2) {//сделать ограничения
        g.drawLine(logicToPhysCoordX(x1),logicToPhysCoordY(y1),logicToPhysCoordX(x2),logicToPhysCoordY(y2));
    }
    private int logicToPhysCoordX(double x) {
        int physX = (int) (ox + x*scale);
        return physX;
    }
    private int logicToPhysCoordY(double y) {
        int physY = (int) (oy - y*scale);
        return physY;
    }
    private double physToLogicCoordX(int x) {
        double logicX =  ((double)x - (double)ox)/scale;
        return logicX;
    }
    private double physToLogicCoordY(int y) {
        double logicY = ((double)oy - (double)y)/scale;
        return logicY;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
        if(this.scale < 1) {
            this.scale = 1;
        }
    }

    public int getOx() {
        return ox;
    }

    public void setOx(int ox) {
        this.ox = ox;
    }

    public int getOy() {
        return oy;
    }

    public void setOy(int oy) {
        this.oy = oy;
    }

    public int getHx() {
        return hx;
    }

    public void setHx(int hx) {
        this.hx = hx;
        if(this.hx < 1) {
            this.hx = 1;
        }
    }

    public int getLx() {
        return lx;
    }

    public void setLx(int lx) {
        this.lx = lx;
    }

    public int getLy() {
        return ly;
    }

    public void setLy(int ly) {
        this.ly = ly;
    }
    public void setPaintExpData(boolean flag) {
        paintExpData = flag;

    }
    public boolean getPaintExpData() {
        return paintExpData;
    }

    public void setPaintNetwork(boolean flag) {
        paintNetwork = flag;
    }
    public boolean getPaintNetwork() {
        return paintNetwork;
    }
    public void setPaintOriginFun(boolean flag) {
        paintOriginFun = flag;
    }
    public boolean getPaintOriginFun() {
        return paintOriginFun;
    }

    public void setSizeExpData(int size) {
        sizeExpData = size;
        if(sizeExpData < 1)
            sizeExpData = 10;
    }
    public int getSizeExpData(){
        return sizeExpData;
    }
    public void setNumNetworkNodes(int m) {
        numNetworkNodes = m;
        if(numNetworkNodes < 1)
            numNetworkNodes = 1;
    }
    public double getDerivativeStep(){
        return derivativeStep;
    }
    public void setDerivativeStep(double step) {
        derivativeStep = step;
        if(derivativeStep < 0)
            derivativeStep = 0.1;
    }

    public int getNumNetworkNodes(){
        return numNetworkNodes;
    }

    public void setPaintOxy(boolean flag) {
        paintOxy = flag;
    }
    public boolean getPaintOxy(){
        return paintOxy;
    }

    public void setExperementalData(ExperimentalData _data) {
        this.data = _data;
    }
}