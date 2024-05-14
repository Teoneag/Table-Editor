package com.teoneag;

import javax.swing.*;

class SwingExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JButton button = new JButton(" Click me!");

        button.addActionListener(e -> {
            System.out.println("Button clicked");
        });

        button.setBounds(150, 200, 220, 50);

        frame.add(button);

        frame.setSize(500, 600);

        // using no layout managers
        frame.setLayout(null);

        frame.setVisible(true);
    }
}
