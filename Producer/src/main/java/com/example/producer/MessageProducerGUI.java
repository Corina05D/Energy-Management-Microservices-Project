package com.example.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MessageProducerGUI {

    private MessageProducer messageProducer;

    public void createAndShowUI(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;

        JFrame frame = new JFrame("Message Producer UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JButton startButton = new JButton("Start Producing");
        JButton stopButton = new JButton("Stop Producing");
        JLabel deviceIdLabel = new JLabel("Device ID:");
        JTextField deviceIdTextField = new JTextField();
        JButton setDeviceIdButton = new JButton("Set Device ID");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    try {
                        messageProducer.produceMessage();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageProducer.stopProducing();
            }
        });

        setDeviceIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deviceId = deviceIdTextField.getText();
                messageProducer.setDeviceId(deviceId);
                JOptionPane.showMessageDialog(null, "Device ID set to: " + deviceId);
            }
        });

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(startButton)
                        .addComponent(stopButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(deviceIdLabel)
                        .addComponent(deviceIdTextField)
                        .addComponent(setDeviceIdButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(startButton)
                        .addComponent(deviceIdLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(stopButton)
                        .addComponent(deviceIdTextField))
                .addComponent(setDeviceIdButton)
        );

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}