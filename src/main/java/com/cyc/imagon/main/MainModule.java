package com.cyc.imagon.main;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.entity.Pixel;
import com.cyc.imagon.entity.PixelWithCount;
import com.cyc.imagon.service.CountTxt;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MainModule
 * Package: com.cyc.imagon.entity
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:20
 * @Version 1.0
 */

@Slf4j
public class MainModule {

    static final String SRC_MAIN_RESOURCES_FILE_MAIN_MODULE_IG = "src/main/resources/file/mainmodule.ig";

    private static List<PixelWithCount> pixelWithCounts = new ArrayList<PixelWithCount>();

    private static ImageIcon imageOut = new ImageIcon("src/main/resources/images/imgOut.png");

    private static CountTxt countTxt = new CountTxt();

    private static short count;

    private static int size = 4;

    public MainModule() {
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                pixelWithCounts.add(new PixelWithCount());
            }
        }
    }

    public boolean storeImageWithCount(Image imageWithCount) {
        //图片存一次，count++;
        count++;
        // 原始网格的大小
        int originalWidth = imageWithCount.getWidth();
        int originalHeight = imageWithCount.getHeight();
        // 目标网格的大小
        int targetWidth = size * 1000;
        int targetHeight = size * 1000;
        // 创建一个安全随机数生成器
        SecureRandom secureRandom = new SecureRandom();
        // 遍历原始网格的每个点
        List<Pixel> pixels = imageWithCount.getPixels();
        for (Pixel pixel : pixels) {
            int x = pixel.getX();
            int y = pixel.getY();
            short r = pixel.getR();
            short g = pixel.getG();
            short b = pixel.getB();
            //int imageCount = imageWithCount.getCount();
            // 将原始网格上的点投影到目标网格上
            int targetX = secureRandom.nextInt(targetWidth);
            targetX = targetX % targetWidth;
            int targetY = secureRandom.nextInt(targetHeight);
            targetY = targetY % targetHeight;
            //运算出目标点在一维list下的位置
            int target = targetX + (targetY - 1) * targetWidth - 1;
            if (target < 0) {
                target = -target;
            }
            target = target % (targetWidth * targetHeight);
            pixelWithCounts.get(target).setX(x);
            pixelWithCounts.get(target).setY(y);
            pixelWithCounts.get(target).setR(r);
            pixelWithCounts.get(target).setG(g);
            pixelWithCounts.get(target).setB(b);
            pixelWithCounts.get(target).setCount(count);
        }
        log.info("total-count={}", count);
        return true;
    }

    public BufferedImage getImageByCount(int count) {
        if (count == 0) {
            return null;
        }
        // 图像的宽度和高度
        int width = 1200;
        int height = 700;
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < size * 1000; i++) {
            for (int j = 0; j < size * 1000; j++) {
                int index = j + i * size * 1000;
                PixelWithCount pixelWithCount = pixelWithCounts.get(index);
                if (pixelWithCount.getCount() == count) {
                    //System.out.println("获得count为"+count+"的像素点"+index);
                    int x = pixelWithCount.getX();
                    int y = pixelWithCount.getY();
                    if (x >= width || y >= height) {
                        continue;
                    }
                    int rgb = pixelWithCount.getRGB();
                    try {
                        image.setRGB(x, y, rgb);
                    } catch (Exception e) {
                        log.error("x=" + x + " y=" + y);
                    }
                }
            }
        }
        log.info("cur - count = {}",count);
        //首先创建一个BufferedImage变量，因为ImageIO写图片用到了BufferedImage变量。
        //再创建一个Graphics变量，用来画出来要保持的图片，及上面传递过来的Image变量
        Graphics g = image.getGraphics();
        try {
            g.drawImage(image, 0, 0, null);
            //将BufferedImage变量写入文件中。
            ImageIO.write(image, "jpg", new File("src/main/resources/images/img.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return image;
    }


    public static void loadFromHardDrive() throws IOException {
        count = (short) countTxt.readTxt();
        File mig = new File(SRC_MAIN_RESOURCES_FILE_MAIN_MODULE_IG);
        if (!mig.exists()) {
            try {
                mig.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        FileReader fileReader = new FileReader(mig);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String pixel;
        while ((pixel = bufferedReader.readLine()) != null) {
            int index = 0;
            int x = 0;
            int y = 0;
            int r = 0;
            int g = 0;
            int b = 0;
            short count = 0;
            int numberfornature = 0;
            int cur = 0;
            for (int i = 0; i < pixel.length(); i++) {
                if (pixel.charAt(i) == ' ') {
                    if (numberfornature == 0) {
                        index = cur;
                    } else if (numberfornature == 1) {
                        x = cur;
                    } else if (numberfornature == 2) {
                        y = cur;
                    } else if (numberfornature == 3) {
                        r = cur;
                    } else if (numberfornature == 4) {
                        g = cur;
                    } else if (numberfornature == 5) {
                        b = cur;
                    } else if (numberfornature == 6) {
                        count = (short) cur;
                    }
                    cur = 0;
                    numberfornature++;
                } else {
                    cur = cur * 10 + pixel.charAt(i) - '0';
                }
            }
            if (count == 0) {
                continue;
            }
            PixelWithCount pixelWithCount = pixelWithCounts.get(index);
            pixelWithCount.setX(x);
            pixelWithCount.setY(y);
            pixelWithCount.setR((short) r);
            pixelWithCount.setG((short) g);
            pixelWithCount.setB((short) b);
            pixelWithCount.setCount(count);
        }
    }

    public void storeToHardDrive() {
        countTxt.writeTxt(count);
        try {
            File writeName = new File(SRC_MAIN_RESOURCES_FILE_MAIN_MODULE_IG); // 相对路径，如果没有则要建立一个新的output.txt文件
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
    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        MainModule.size = size;
    }

    public static List<PixelWithCount> getPixelWithCounts() {
        return pixelWithCounts;
    }

    public static void setPixelWithCounts(List<PixelWithCount> pixelWithCounts) {
        MainModule.pixelWithCounts = pixelWithCounts;
    }

    public static ImageIcon getImageOut() {
        return imageOut;
    }

    public static void setImageOut(ImageIcon imageOut) {
        MainModule.imageOut = imageOut;
    }

    public static CountTxt getCountTxt() {
        return countTxt;
    }

    public static void setCountTxt(CountTxt countTxt) {
        MainModule.countTxt = countTxt;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(short count) {
        MainModule.count = count;
    }
}
