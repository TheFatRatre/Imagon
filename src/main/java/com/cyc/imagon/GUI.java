package com.cyc.imagon;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.main.MainModule;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/**
 * ClassName: GUI
 * Package: com.cyc.imagon
 * Description:
 *
 * @Author CYC
 * @Create 2024/4/9 20:20
 * @Version 1.0
 */
public class GUI {
    private JButton 上一张图Button;
    private JButton 下一张图Button;
    private JButton 添加图片Button;
    public JPanel root;

    static File imageFile;

    public GUI(MainModule mainModule) {
        上一张图Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        下一张图Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        添加图片Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.showOpenDialog(null);
                File selectedFile = jFileChooser.getSelectedFile();
                imageFile = jFileChooser.getSelectedFile();
                System.out.println(imageFile.toPath());
                mainModule.storeImageWithCount(new Image().loadImage(imageFile));
            }
        });
    }

}
