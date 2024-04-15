package com.cyc.imagon;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.main.MainModule;
import com.cyc.imagon.service.CountTxt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

import static com.cyc.imagon.Application.frame;

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
    private int totalCount=0;
    private int curCount=0;
    private JButton 上一张图Button;
    private JButton 下一张图Button;
    private JButton 添加图片Button;
    public JPanel root;

    static File imageFile;
    static CountTxt countTxt=new CountTxt();
    public GUI(MainModule mainModule) {
        上一张图Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                totalCount=countTxt.readTxt();
                if(curCount>0) curCount--;
               // BufferedImage imageByCount = mainModule.getImageByCount(curCount);
                mainModule.getImageByCount(curCount);
//                File outputImage = new File("src/main/resources/image/output.png");
//                try {
//                    ImageIO.write(imageByCount, "png", outputImage);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
            }
        });
        下一张图Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                totalCount=countTxt.readTxt();
                if(curCount<totalCount) curCount++;
                //BufferedImage imageByCount = mainModule.getImageByCount(curCount);
                mainModule.getImageByCount(curCount);

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
