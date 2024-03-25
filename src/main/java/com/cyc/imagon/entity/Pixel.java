package com.cyc.imagon.entity;

/**
 * ClassName: Pixel
 * Package: com.cyc.imagon.common
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/22 17:09
 * @Version 1.0
 */
public class Pixel {
    int x;
    int y;
    int r;
    int g;
    int b;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
    public int getRGB() {
        // 将RGB值转换为int类型
        int rgb = (r << 16) | (g << 8) | b;
        return rgb;
    }



}
