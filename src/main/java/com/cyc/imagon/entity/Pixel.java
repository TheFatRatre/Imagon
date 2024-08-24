package com.cyc.imagon.entity;

import lombok.Data;

/**
 * ClassName: Pixel
 * Package: com.cyc.imagon.common
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:09
 * @Version 1.0
 */
@Data
public class Pixel {
    int x;
    int y;
    short r;
    short g;
    short b;
    public int getRGB() {
        int rr = (int) r;
        int gg = (int) g;
        int bb = (int) b;
        // 将RGB值转换为int类型
        int rgb = (rr << 16) | (gg << 8) | bb;
        return rgb;
    }
}
