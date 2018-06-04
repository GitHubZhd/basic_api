package com.toefl.basic.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventTest {

    public static void main(String[] args) {

        JButton jButton=new JButton("show");
//        jButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("1231233333");
//            }
//        });

        jButton.addActionListener(e -> System.out.println("1231233333"));
        jButton.addActionListener((e) -> System.out.println("1231233333"));
    }
}
