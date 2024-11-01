package com.cyc.imagon.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
@Data
public class Image {

    //static int count=0;

    private int width;

    private int height;

    private List<Pixel> pixels = new ArrayList<Pixel>();

    public void setPixels(Pixel pixels) {
        this.pixels.add(pixels);
    }

    public Image loadImage(File imageFile) {
        Image imageWithCount = new Image();
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setWidth(image.getWidth());
        this.setHeight(image.getHeight());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel1 = new Pixel();
                int pixel = image.getRGB(x, y);
                short red = (short) ((pixel >> 16) & 0xff);
                short green = (short) ((pixel >> 8) & 0xff);
                short blue = (short) ((pixel) & 0xff);
                if (red < 0) {
                    red = (short) ((pixel >> 16) & 0xff);
                }
                pixel1.setX(x);
                pixel1.setY(y);
                pixel1.setR(red);
                pixel1.setG(green);
                pixel1.setB(blue);
                imageWithCount.setPixels(pixel1);
            }
        }
        return imageWithCount;
    }
}
