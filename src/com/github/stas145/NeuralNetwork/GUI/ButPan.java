package com.github.stas145.NeuralNetwork.GUI;

/**
 * Created by stas on 05.11.14.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButPan extends JPanel implements ActionListener
{
    private JButton jbt1,jbt2,jbt3,jbt4,jbt5,jbt6,jbt7,jbt8;
    NeuralNetworkGraph pg;

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(jbt1)) {
            pg.setScale(pg.getScale() + 5);
            pg.repaint();
        }

        if (e.getSource().equals(jbt2)) {
            pg.setScale(pg.getScale() - 5);
            pg.repaint();
        }

        if (e.getSource().equals(jbt3)) {
            pg.setOy(pg.getOy() + pg.getScale());
            pg.repaint();
        }

        if (e.getSource().equals(jbt4)) {
            pg.setOy(pg.getOy() - pg.getScale());
            pg.repaint();
        }

        if (e.getSource().equals(jbt5)) {
            pg.setOx(pg.getOx() - pg.getScale());
            pg.repaint();
        }

        if (e.getSource().equals(jbt6)) {
            pg.setOx(pg.getOx() + pg.getScale());
            pg.repaint();
        }

        if (e.getSource().equals(jbt7)) {
            pg.setHx( pg.getHx() + 1);
            pg.repaint();
        }

        if (e.getSource().equals(jbt8)) {
            pg.setHx( pg.getHx() - 1);
            pg.repaint();
        }
    }
    public ButPan(NeuralNetworkGraph p)
    {
        setLayout(new GridLayout(8, 1));// Установка табличного менеджера размещения
        pg = p;
        jbt1 = new JButton("+");
        jbt2 = new JButton("-");
        jbt3 = new JButton("^");
        jbt4 = new JButton("v");
        jbt5 = new JButton("<");
        jbt6 = new JButton(">");
        jbt7 = new JButton("h+");
        jbt8 = new JButton("h-");
        jbt1.addActionListener(this);
        jbt2.addActionListener(this);
        jbt3.addActionListener(this);
        jbt4.addActionListener(this);
        jbt5.addActionListener(this);
        jbt6.addActionListener(this);
        jbt7.addActionListener(this);
        jbt8.addActionListener(this);
        setLayout(new GridLayout(1, 8));
        this.add(jbt1);
        this.add(jbt2);
        this.add(jbt3);
        this.add(jbt4);
        this.add(jbt5);
        this.add(jbt6);
        this.add(jbt7);
        this.add(jbt8);
    }
}