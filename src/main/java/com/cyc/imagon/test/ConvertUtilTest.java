package com.cyc.imagon.test;

import com.cyc.imagon.common.DeepCopy;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 该类实现图片的一些基本转换
 *
 * @author 申雄全
 * Date 2023/12/24 1:50
 */
public class ConvertUtilTest {
    public static Image ConvertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public static ImageView ConvertToFxImageView(BufferedImage image) {
        return new ImageView(ConvertToFxImage(image));
    }

    public static BufferedImage convertToBufferedImage(Image fxImage, int BufferedImageType) {
        // 获取Image的宽度和高度
        int width = (int) fxImage.getWidth();
        int height = (int) fxImage.getHeight();

        // 创建一个ARGB类型的BufferedImage对象


        // 将fxImage绘制到BufferedImage中
        return SwingFXUtils.fromFXImage(fxImage, null);


    }

    public static BufferedImage resetSize(BufferedImage srcImg, double targetWidth, double targetHeight, boolean higherQuality) {
        if (srcImg == null)
            return srcImg;

        int type = srcImg.getType();
        BufferedImage ret;
        int w, h;
        long stime = System.currentTimeMillis();
        if (targetHeight > srcImg.getHeight() || targetWidth > srcImg.getWidth()) {

            return DeepCopy.cpyBufferedImage(srcImg);
        }

        if (higherQuality) {
            //双线性压缩
            w = srcImg.getWidth();
            h = srcImg.getHeight();
            double scaleX = targetWidth / w;
            double scaleY = targetHeight / h;
            BufferedImage tmp = new BufferedImage((int) targetWidth, (int) targetHeight, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(srcImg, 0, 0, (int) targetWidth, (int) targetHeight, null);
            g2.dispose();
            ret = tmp;

        } else {
            //粗糙压缩，适用于滑块拖动过程
            w = (int) targetWidth;
            h = (int) targetHeight;
            do {
                BufferedImage tmp = new BufferedImage(w, h, type);
                Graphics2D g2 = tmp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.drawImage(tmp, 0, 0, w, h, null);
                g2.dispose();
                ret = tmp;
            } while (w != (int) targetWidth || h != (int) targetHeight);
        }
        long etime = System.currentTimeMillis();
        System.out.printf("压缩执行时长：%d 毫秒\n", (etime - stime));

        return ret;


    }
}
