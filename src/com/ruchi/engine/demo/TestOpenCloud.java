package com.ruchi.engine.demo;


import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

public class TestOpenCloud {
    //String s="autralia is our country, australia that is our";
   

    protected void initUI(String sen) {
        String[] WORDS =sen.split("[\\s']");
        JFrame frame = new JFrame(TestOpenCloud.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        Cloud cloud = new Cloud();
        Random random = new Random();
        for (String s : WORDS) {
           /* for (int i = random.nextInt(50); i > 0; i--) {*/
                cloud.addTag(s);
            
        }
        for (Tag tag : cloud.tags()) {
            final JLabel label = new JLabel(tag.getName());
            label.setOpaque(false);
            if(tag.getName().equalsIgnoreCase("australia"))
            {
            label.setFont(label.getFont().deriveFont(72.0f));
            label.setForeground(Color.red);
            //label.getFont().deriveFont((float) tag.getWeight() * 10)
            }
            panel.add(label);
        }
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestOpenCloud().initUI("autralia is our country, australia that is our");
            }
        });
    }

}