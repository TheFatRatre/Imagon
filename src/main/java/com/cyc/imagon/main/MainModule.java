package com.cyc.imagon.main;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyc.imagon.entity.Image;
import com.cyc.imagon.entity.Pixel;
import com.cyc.imagon.entity.PixelWithCount;
import com.cyc.imagon.model.MainModuleData;
import com.cyc.imagon.service.Counter;
import com.cyc.imagon.service.MainModuleDataService;
import com.cyc.imagon.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static List<PixelWithCount> pixelWithCounts = new ArrayList<PixelWithCount>();

    private static ImageIcon imageOut = new ImageIcon("src/main/resources/images/imgOut.png");

    private static Counter counter = new Counter();

    private static final AtomicInteger COUNT = new AtomicInteger();

    private static final int SIZE = 4 * 1000;

    private final MainModuleDataService mainModuleDataService = SpringUtil.getBean(MainModuleDataService.class);


    public boolean storeImage(Image image) {
        COUNT.incrementAndGet();
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        long targetWidth = SIZE;
        long targetHeight = SIZE;
        SecureRandom secureRandom = new SecureRandom();
        List<Pixel> pixels = image.getPixels();
        for (Pixel pixel : pixels) {
            val x = pixel.getX();
            val y = pixel.getY();
            val r = pixel.getR();
            val g = pixel.getG();
            val b = pixel.getB();

            long targetX = secureRandom.nextLong();
            targetX = targetX % targetWidth;
            long targetY = secureRandom.nextLong();
            targetY = targetY % targetHeight;

            long target = targetX + (targetY - 1) * targetWidth - 1;
            if (target < 0) {
                target = -target;
            }
            target = target % ((long) targetWidth * targetHeight);
            mainModuleDataService.saveOrUpdate(new MainModuleData(target, r, g, b, COUNT.get(), x, y));
        }
        log.info("total-count = {}", COUNT.get());
        return true;
    }

    public BufferedImage getImageByCount(int count) {
        if (count == 0) {
            return null;
        }
        // 图像的宽度和高度
        AtomicInteger width = new AtomicInteger();
        AtomicInteger height = new AtomicInteger();
        LambdaQueryWrapper<MainModuleData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(MainModuleData::getCount);
        List<MainModuleData> listByCount = mainModuleDataService.list(queryWrapper);
        listByCount.forEach(p -> {
            width.set(Math.max(width.get(), p.getX()));
            height.set(Math.max(height.get(), p.getY()));
        });
        width.set(width.get() + 10);
        height.set(height.get() + 10);
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width.get(), height.get(), BufferedImage.TYPE_INT_RGB);
        listByCount.forEach(p -> {
            val x = p.getX();
            val y = p.getY();
            if (x >= width.get() || y >= height.get()) {
                return;
            }
            int rgb = p.getRGB();
            try {
                image.setRGB(x, y, rgb);
            } catch (Exception e) {
                log.error("x =" + x + " y =" + y);
            }
        });
        log.info("cur-count = {}", count);

        Graphics g = image.getGraphics();
        try {
            g.drawImage(image, 0, 0, null);
            ImageIO.write(image, "jpg", new File("src/main/resources/images/newestImg.jpg"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return image;
    }

    //    public static void loadFromHardDrive() throws IOException {
//        count = (short) counter.readTxt();
//        File mig = new File(SRC_MAIN_RESOURCES_FILE_MAIN_MODULE_IG);
//        if (!mig.exists()) {
//            try {
//                mig.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        FileReader fileReader = new FileReader(mig);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String pixel;
//        while ((pixel = bufferedReader.readLine()) != null) {
//            int index = 0;
//            int x = 0;
//            int y = 0;
//            int r = 0;
//            int g = 0;
//            int b = 0;
//            short count = 0;
//            int numberfornature = 0;
//            int cur = 0;
//            for (int i = 0; i < pixel.length(); i++) {
//                if (pixel.charAt(i) == ' ') {
//                    if (numberfornature == 0) {
//                        index = cur;
//                    } else if (numberfornature == 1) {
//                        x = cur;
//                    } else if (numberfornature == 2) {
//                        y = cur;
//                    } else if (numberfornature == 3) {
//                        r = cur;
//                    } else if (numberfornature == 4) {
//                        g = cur;
//                    } else if (numberfornature == 5) {
//                        b = cur;
//                    } else if (numberfornature == 6) {
//                        count = (short) cur;
//                    }
//                    cur = 0;
//                    numberfornature++;
//                } else {
//                    cur = cur * 10 + pixel.charAt(i) - '0';
//                }
//            }
//            if (count == 0) {
//                continue;
//            }
//            PixelWithCount pixelWithCount = pixelWithCounts.get(index);
//            pixelWithCount.setX(x);
//            pixelWithCount.setY(y);
//            pixelWithCount.setR((short) r);
//            pixelWithCount.setG((short) g);
//            pixelWithCount.setB((short) b);
//            pixelWithCount.setCount(count);
//        }
//    }
//
//    public void storeToHardDrive() {
//        counter.writeTxt(count);
//        try {
//            File writeName = new File(SRC_MAIN_RESOURCES_FILE_MAIN_MODULE_IG); // 相对路径，如果没有则要建立一个新的output.txt文件
//            //writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
//            try (FileWriter writer = new FileWriter(writeName);
//                 BufferedWriter out = new BufferedWriter(writer)
//            ) {
//                for (int i = 0; i < size * 1000; i++) {
//                    for (int j = 0; j < size * 1000; j++) {
//                        int index = j + i * size * 1000;
//                        PixelWithCount pixelWithCount = pixelWithCounts.get(index);
//                        int x = pixelWithCount.getX();
//                        int y = pixelWithCount.getY();
//                        int r = pixelWithCount.getR();
//                        int g = pixelWithCount.getG();
//                        int b = pixelWithCount.getB();
//                        int curcount = pixelWithCount.getCount();
//                        out.write(index + " " + x + " " + y
//                                + " " + r + " " + g + " " + b + " " + curcount + " "
//                                + "\r\n"); // \r\n即为换行
//                    }
//                }
//                out.flush(); // 把缓存区内容压入文件
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static int getSize() {
        return SIZE;
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

    public static Counter getCounter() {
        return counter;
    }

    public static void setCounter(Counter counter) {
        MainModule.counter = counter;
    }

    public static int getCount() {
        return COUNT.get();
    }

    public static void setCount(short count) {
        MainModule.COUNT.set(count);
    }
}
