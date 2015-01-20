package com.github.stas145.NeuralNetwork.GUI;

/**
 * Created by stas on 05.11.14.
 */

import javax.swing.*;
import java.awt.*;

public class DirPan extends JFrame
{
    private NeuralNetworkGraph pg; // класс вывода графика функции
    private ButPan bp; // класс управляющих масштабом кнопок
    private ControlPan cp;

    public DirPan() {
        super("Построение графика функции");
        Container c = getContentPane();
        c.setLayout(new BorderLayout()); // установка менеджера размещения

        pg = new NeuralNetworkGraph(); // инициализация класса построения графика функции
        pg.setSize(400,400); // задание размеров
        c.add(pg,BorderLayout.CENTER); // задание размещения


        bp = new ButPan(pg); // инициализация класса кнопок масштаба
        bp.setSize(100, 10);
        bp.setBackground(new Color(100, 0, 0));
        c.add(bp,BorderLayout.SOUTH);

        cp = new ControlPan(pg);
        c.add(cp,BorderLayout.EAST);

        this.setSize(800,600); // задание размеров основного окна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // задание параметров главного окна при закрытии
        this.setVisible(true);
    }
}