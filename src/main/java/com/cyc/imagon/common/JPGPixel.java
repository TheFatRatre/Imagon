package com.cyc.imagon.common;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * ClassName: JPGPixel
 * Package: com.cyc.imagon.common
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/20 15:07
 * @Version 1.0
 */
public class JPGPixel {
    public void JPGPixelExtractor(){
        try {
            // 读取JPG图像文件
            //File imageFile = new File("E:\\source\\JAVA\\Imagon\\src\\main\\resources\\image\\img.png");
            File imageFile = new File("src/main/resources/image/img.png");
            BufferedImage image = ImageIO.read(imageFile);
            // 获取图像的宽度和高度
            int width = image.getWidth();
            int height = image.getHeight();
            // 遍历图像中的每个像素
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // 获取(x, y)处的像素值
                    int pixel = image.getRGB(x, y);
                    // 提取RGB值
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    // 打印像素的RGB值
                    System.out.println("Pixel at (" + x + "," + y + "): (" + red + "," + green + "," + blue + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
