package com.cyc.imagon.main;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.entity.Pixel;
import com.cyc.imagon.entity.PixelWithCount;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.List;

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
    private static List<PixelWithCount> pixelWithCounts;
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
            int targetY = secureRandom.nextInt(targetHeight);
            //运算出目标点在一维list下的位置
            int target =targetX+(targetY-1)*targetWidth-1;
            pixelWithCounts.get(target).setX(x);
            pixelWithCounts.get(target).setY(y);
            pixelWithCounts.get(target).setR(r);
            pixelWithCounts.get(target).setG(g);
            pixelWithCounts.get(target).setB(b);
            pixelWithCounts.get(target).setCount(count);
        }
        return true;
    }
    public BufferedImage getImageWithCount(int count){
        // 图像的宽度和高度
        int width=0;
        int height=0;
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                int index=i+j*size*1000;
                PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                if(pixelWithCount.getCount() == count){
                    width=max(width,pixelWithCount.getX());
                    height=max(height,pixelWithCount.getY());
                }
            }
        }
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                int index=i+j*size*1000;
                PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                if(pixelWithCount.getCount() == count){
                    int x=pixelWithCount.getX();
                    int y=pixelWithCount.getY();
                    int rgb=pixelWithCount.getRGB();
                    image.setRGB(x, y, rgb);
                }
            }
        }
        return image;
    }
}
