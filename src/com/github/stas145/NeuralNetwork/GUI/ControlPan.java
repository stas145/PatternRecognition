package com.github.stas145.NeuralNetwork.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by stas on 06.11.14.
 */
public class ControlPan extends JPanel implements ActionListener {
    private JButton
            generateData,
            buildNeuralNetwork,
            buildNeuralNetworkBackpropagation,
            repaint,
            clear;
    private JCheckBox
            expData,
            network,
            originFun,
            oxy;
    private JTextField
            dataSize,
            numNetworkNodes,
            derivativeStep;
    NeuralNetworkGraph pg;

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(generateData)) {
            pg.setSizeExpData(Integer.parseInt(dataSize.getText()));
            pg.generateExpData();
        }
        if (e.getSource().equals(buildNeuralNetwork)) {
            pg.setNumNetworkNodes(Integer.parseInt(numNetworkNodes.getText()));
            pg.setDerivativeStep(Double.parseDouble(derivativeStep.getText()));
            pg.buildNeuralNetwork();
        }
        if (e.getSource().equals(buildNeuralNetworkBackpropagation)) {
            pg.setNumNetworkNodes(Integer.parseInt(numNetworkNodes.getText()));
            pg.setDerivativeStep(Double.parseDouble(derivativeStep.getText()));
            pg.buildNeuralNetworkBackpropagation();
        }
        if (e.getSource().equals(repaint)) {
            pg.repaint();
        }
        if (e.getSource().equals(clear)) {
            pg.clear(pg.getGraphics());
        }

        if (e.getSource().equals(expData)) {
            pg.setPaintExpData(expData.isSelected());
        }
        if (e.getSource().equals(network)) {
            pg.setPaintNetwork(network.isSelected());
        }
        if (e.getSource().equals(originFun)) {
            pg.setPaintOriginFun(originFun.isSelected());
        }
        if (e.getSource().equals(oxy)) {
            pg.setPaintOxy(oxy.isSelected());
        }
        if (e.getSource().equals(dataSize)) {
            System.out.println("!!!!" + dataSize.getText() + "!!!!");
        }
    }
    public void propertyChange(ActionEvent e) {
        if (e.getSource().equals(dataSize)) {
            System.out.println("!!!!" + dataSize.getText() + "!!!!");
        }
    }
    public ControlPan(NeuralNetworkGraph p)
    {
        setLayout(new GridLayout(0, 6));// Установка табличного менеджера размещения
        pg = p;

        generateData = new JButton("Generate exp data");
        generateData.addActionListener(this);

        buildNeuralNetwork = new JButton("network");
        buildNeuralNetwork.addActionListener(this);

        buildNeuralNetworkBackpropagation = new JButton("Backpropagation");
        buildNeuralNetworkBackpropagation.addActionListener(this);

        repaint = new JButton("repaint");
        repaint.addActionListener(this);

        clear = new JButton("clear");
        clear.addActionListener(this);

        expData = new JCheckBox("experimental data");
        expData.setSelected(true);
        expData.addActionListener(this);

        network = new JCheckBox("network");
        network.setSelected(true);
        network.addActionListener(this);

        originFun = new JCheckBox("origin function");
        originFun.setSelected(false);
        originFun.addActionListener(this);

        oxy = new JCheckBox("Oxy");
        oxy.setSelected(true);
        oxy.addActionListener(this);

        dataSize = new JTextField("100");
        dataSize.addActionListener(this);

        numNetworkNodes = new JTextField("2");
        numNetworkNodes.addActionListener(this);

        derivativeStep = new JTextField("0.1");
        derivativeStep.addActionListener(this);

        setLayout(new GridLayout(10, 1));

        this.add(generateData);
        this.add(dataSize);
        this.add(buildNeuralNetwork);
        this.add(numNetworkNodes);
        this.add(buildNeuralNetworkBackpropagation);
        this.add(derivativeStep);
        this.add(repaint);
        this.add(clear);
        this.add(expData);
        this.add(network);
        this.add(originFun);
        this.add(oxy);
    }
}
