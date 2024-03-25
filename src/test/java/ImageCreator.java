import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCreator {
    public static void main(String[] args) {
        // 假设有一个点的列表，每个点包含坐标和RGB值
        Point[] points = {
                new Point(0, 0, 255, 0, 0), // 红色点
                new Point(1, 0, 0, 255, 0), // 绿色点
                new Point(0, 1, 0, 0, 255), // 蓝色点
                // ...更多点
        };

        // 图像的宽度和高度
        int width = 10;
        int height = 10;

        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 填充像素数据
        for (Point p : points) {
            int x = p.getX();
            int y = p.getY();
            int rgb = p.getRGB();
            image.setRGB(x, y, rgb);
        }

        // 输出图像文件
        try {
            File outputfile = new File("output.jpg");
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Point {
    private int x;
    private int y;
    private int r;
    private int g;
    private int b;

    public Point(int x, int y, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRGB() {
        // 将RGB值转换为int类型
        int rgb = (r << 16) | (g << 8) | b;
        return rgb;
    }
}
