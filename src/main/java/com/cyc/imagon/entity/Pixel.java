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
        return ((int) r << 16) | ((int) g << 8) | b;
    }
}
