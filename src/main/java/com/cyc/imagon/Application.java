package com.cyc.imagon;

import com.cyc.imagon.main.MainModule;

import javax.swing.*;

/**
 * ClassName: Application
 * Package: com.cyc.imagon
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:21
 * @Version 1.0
 */
public class Application {
    public static MainModule mainModule = new MainModule();
    public static ImageIcon initImage=new ImageIcon("src/main/resources/image/init.png");
    public static JFrame frame = new JFrame("Imagon");
    public static void main(String[] args) {
        GUI gui = new GUI(mainModule);
        frame.setContentPane(gui.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setVisible(true);
        //初始图像
        JLabel label=new JLabel(initImage);
        frame.setLayout(null);
        frame.add(label);
        label.setBounds(50,10,900,720);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
