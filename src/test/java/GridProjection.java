import lombok.extern.java.Log;

import java.security.SecureRandom;

public class GridProjection {
    public static void main(String[] args) {
        long st=System.currentTimeMillis();
        // 原始网格的大小
        int originalWidth = 800;
        int originalHeight = 900;

        // 目标网格的大小
        int targetWidth = 16000;
        int targetHeight = 16000;

        // 创建一个安全随机数生成器
        SecureRandom secureRandom = new SecureRandom();

        // 遍历原始网格的每个点
        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                // 将原始网格上的点投影到目标网格上
                int targetX = secureRandom.nextInt(targetWidth);
                int targetY = secureRandom.nextInt(targetHeight);

                // 输出投影结果

                System.out.println("原始点 (" + x + ", " + y + ") 被投影到目标点 (" + targetX + ", " + targetY + ")");
            }
        }
        long ed=System.currentTimeMillis();
        System.out.println(ed-st);
    }
}
