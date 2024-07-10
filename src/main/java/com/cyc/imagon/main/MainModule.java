package com.cyc.imagon.main;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.entity.Pixel;
import com.cyc.imagon.entity.PixelWithCount;
import com.cyc.imagon.service.CountTxt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    CountTxt countTxt = new CountTxt();
    private static int count;
    private static int size=3;

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        MainModule.size = size;
    }

    public MainModule() {
        count=countTxt.readTxt();
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                pixelWithCounts.add(new PixelWithCount());
            }
        }
    }
    public boolean storeImageWithCount(Image imageWithCount){
        //图片存一次，count++;
        count++;
        if(count==size*size+1){
            count=1;
        }
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
            short r=pixel.getR();
            short g=pixel.getG();
            short b=pixel.getB();
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
        synchronizedCount(count);
        System.out.println("count="+count);
        return true;
    }
    public BufferedImage getImageByCount(int count){
        if(count==0) return null;
        // 图像的宽度和高度
        int width=1200;
        int height=1200;
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                int index=j+i*size*1000;
                PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                if(pixelWithCount.getCount() == count){
                    //System.out.println("获得count为"+count+"的像素点"+index);
                    int x=pixelWithCount.getX();
                    int y=pixelWithCount.getY();
                    if(x>=width||y>=height) continue;
                    int rgb=pixelWithCount.getRGB();
                    try {
                        image.setRGB(x, y, rgb);
                    } catch (Exception e) {
                        System.out.println("x="+x+" y="+y);
                    }
                }
            }
        }
        System.out.println("count="+count);
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
    static String mainmoduletxtpathname = "src/main/resources/file/mainmodule.ig";
    public static void loadFromHardDrive() throws IOException  {
        FileReader fileReader = new FileReader(new File(mainmoduletxtpathname));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String pixel;
        while ((pixel = bufferedReader.readLine()) != null) {
            int index = 0;
            int x = 0;
            int y = 0;
            int r = 0;
            int g = 0;
            int b = 0;
            int count = 0;
            int numberfornature=0;
            int cur=0;
            for (int i = 0; i < pixel.length(); i++) {
                if(pixel.charAt(i)==' '){
                    if (numberfornature==0){
                        index=cur;
                    }
                    else if(numberfornature==1){
                        x=cur;
                    }
                    else if(numberfornature==2){
                        y=cur;
                    }
                    else if(numberfornature==3){
                        r=cur;
                    }
                    else if(numberfornature==4){
                        g=cur;
                    }
                    else if(numberfornature==5){
                        b=cur;
                    }
                    else if(numberfornature==6){
                        count=cur;
                    }
                    cur=0;
                    numberfornature++;
                }
                else{
                    cur=cur*10+pixel.charAt(i)-'0';
                }
            }
            if(count==0) continue;
            PixelWithCount pixelWithCount = pixelWithCounts.get(index);
            pixelWithCount.setX(x);
            pixelWithCount.setY(y);
            pixelWithCount.setR((short) r);
            pixelWithCount.setG((short) g);
            pixelWithCount.setB((short) b);
            pixelWithCount.setCount(count);
        }
    }
    public void storeToHardDrive(){
        countTxt.writeTxt(count);
        try {
            File writeName = new File(mainmoduletxtpathname); // 相对路径，如果没有则要建立一个新的output.txt文件
            //writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for (int i = 0; i < size * 1000; i++) {
                    for (int j = 0; j < size * 1000; j++) {
                        int index = j + i * size * 1000;
                        PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                        int x = pixelWithCount.getX();
                        int y = pixelWithCount.getY();
                        int r = pixelWithCount.getR();
                        int g = pixelWithCount.getG();
                        int b = pixelWithCount.getB();
                        int curcount = pixelWithCount.getCount();
                        out.write(index + " " + x + " " + y
                                + " " + r + " " + g + " " + b + " " + curcount + " "
                                + "\r\n"); // \r\n即为换行
                    }
                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
