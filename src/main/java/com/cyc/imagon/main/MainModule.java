package com.cyc.imagon.main;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.entity.Pixel;
import com.cyc.imagon.entity.PixelWithCount;
import com.cyc.imagon.service.CountTxt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.cyc.imagon.Application.frame;
import static com.cyc.imagon.Application.label;
import static java.lang.Math.max;

/**
 * ClassName: MainModule
 * Package: com.cyc.imagon.entity
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:20
 * @Version 1.0
 */
public class MainModule {
    private static List<PixelWithCount> pixelWithCounts=new ArrayList<PixelWithCount>();
    public static ImageIcon imageOut=new ImageIcon("src/main/resources/image/imgIcon.png");
    private static int count=0;
    private static int size=3;

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        MainModule.size = size;
    }

    public MainModule() {
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                pixelWithCounts.add(new PixelWithCount());
            }
        }
    }
    public boolean storeImageWithCount(Image imageWithCount){
        //图片存一次，count++;
        count++;
        // 原始网格的大小
        int originalWidth = imageWithCount.getWidth();
        int originalHeight = imageWithCount.getHeight();

        // 目标网格的大小
        int targetWidth = size*1000;
        int targetHeight = size*1000;

        // 创建一个安全随机数生成器
        SecureRandom secureRandom = new SecureRandom();

        // 遍历原始网格的每个点
        List<Pixel> pixels = imageWithCount.getPixels();
        for (int i = 0; i < pixels.size(); i++) {
            Pixel pixel = pixels.get(i);
            int x=pixel.getX();
            int y=pixel.getY();
            int r=pixel.getR();
            int g=pixel.getG();
            int b=pixel.getB();
            //int imageCount = imageWithCount.getCount();
            // 将原始网格上的点投影到目标网格上
            int targetX = secureRandom.nextInt(targetWidth);
            targetX=targetX%targetWidth;
            int targetY = secureRandom.nextInt(targetHeight);
            targetY=targetY%targetHeight;
            //运算出目标点在一维list下的位置
            int target =targetX+(targetY-1)*targetWidth-1;
            if(target<0) target=-target;
            target=target%(targetWidth*targetHeight);
            pixelWithCounts.get(target).setX(x);
            pixelWithCounts.get(target).setY(y);
            pixelWithCounts.get(target).setR(r);
            pixelWithCounts.get(target).setG(g);
            pixelWithCounts.get(target).setB(b);
            pixelWithCounts.get(target).setCount(count);
        }
//        for (int i = 0; i < size * 1000000*size; i++) {
//            if(pixelWithCounts.get(i).getCount()!=0){
//                System.out.println("存入");
//            }
//        }
        synchronizedCount(count);
        System.out.println(count);
        return true;
    }
    public BufferedImage getImageByCount(int count){
        if(count==0) return null;
        // 图像的宽度和高度
        int width=2000;
        int height=2000;
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                int index=i+j*size*1000;
                PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                if(pixelWithCount.getCount() == count){
                    //System.out.println("获得count为"+count+"的像素点"+index);
                    int x=pixelWithCount.getX();
                    int y=pixelWithCount.getY();
                    int rgb=pixelWithCount.getRGB();
                    image.setRGB(x, y, rgb);
                }
            }
        }
        imageOut.setImage(image);
        //首先创建一个BufferedImage变量，因为ImageIO写图片用到了BufferedImage变量。
//再创建一个Graphics变量，用来画出来要保持的图片，及上面传递过来的Image变量
        Graphics g = image.getGraphics();
        try {
            g.drawImage(image, 0, 0, null);

//将BufferedImage变量写入文件中。
            ImageIO.write(image,"jpg",new File("src/main/resources/image/img.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //JLabel label=new JLabel(imageOut);
        label.setIcon(imageOut);
        frame.setLayout(null);
        frame.add(label);
        label.setBounds(50,10,900,720);
        frame.setVisible(true);
        frame.setResizable(false);
        //showImage();
//        for (int i = 0; i < size * 1000000*size; i++) {
//            if(pixelWithCounts.get(i).getCount()==count){
//                System.out.println("读出"+count);
//            }
//        }
        return image;
    }
    private void showImage(){

        JLabel label=new JLabel(imageOut);
        frame.setLayout(null);
        frame.add(label);
        label.setBounds(50,10,900,720);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public boolean synchronizedCount(int count){
        CountTxt countTxt = new CountTxt();
        int countintxt=0;
        countintxt=countTxt.readTxt();
        if(count!=countintxt) {
            countTxt.writeTxt(count);
        }
        return true;
    }
}
