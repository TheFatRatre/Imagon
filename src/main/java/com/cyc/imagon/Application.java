package com.cyc.imagon;

import com.cyc.imagon.main.MainModule;
import com.cyc.imagon.service.CountTxt;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static com.cyc.imagon.main.MainModule.loadFromHardDrive;

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
    public static JLabel label=new JLabel(initImage);
    public static JFrame frame = new JFrame("Imagon");
    public static int lastcount= 0;
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI(mainModule);
        frame.setContentPane(gui.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //图标
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Image icon = toolkit.getImage("src/main/resources/image/icon.jpg");
        frame.setIconImage(icon);
        frame.setSize(1000,800);
        gui.root.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
        //从持久层加载历史记录
        loadFromHardDrive();
        //初始图像
        initImage();
        frame.setLayout(null);
        frame.add(label);
        label.setBounds(50,10,900,720);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private static void initImage() {
        CountTxt countTxt = new CountTxt();
        lastcount=countTxt.readTxt();
        mainModule.getImageByCount(lastcount);
    }
}
