package com.cyc.imagon.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: ImageWithCount
 * Package: com.cyc.imagon.common
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:03
 * @Version 1.0
 */
public class ImageWithCount {
    static int count=0;
    public List<Pixel> pixels;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Pixel> getPixels() {
        return pixels;
    }

    public void setPixels(Pixel pixels) {
        this.pixels.add(pixels);
    }
    public ImageWithCount loadImage(File imageFile){
        //创建对象
        ImageWithCount imageWithCount = new ImageWithCount();
        //count赋值,count传给对象ImageWithCount
        imageWithCount.setCount(++count);
        //从文件中读入图片
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 获取图像的宽度和高度
        int width = image.getWidth();
        int height = image.getHeight();
        // 遍历图像中的每个像素
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel1 = new Pixel();
                // 获取(x, y)处的像素值
                int pixel = image.getRGB(x, y);
                // 提取RGB值
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                pixel1.setX(x);
                pixel1.setY(y);
                pixel1.setR(red);
                pixel1.setG(green);
                pixel1.setB(blue);
                //将图片的各个像素传给对象ImageWithCount
                imageWithCount.setPixels(pixel1);
            }
        }
        //返回对象
        return imageWithCount;
    }
}
