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
    short r;
    short g;
    short b;
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

    public short getR() {
        return r;
    }

    public void setR(short r) {
        this.r = r;
    }

    public short getG() {
        return g;
    }

    public void setG(short g) {
        this.g = g;
    }

    public short getB() {
        return b;
    }

    public void setB(short b) {
        this.b = b;
    }
    public int getRGB() {
        int rr=(int) r;
        int gg=(int) g;
        int bb=(int) b;
        // 将RGB值转换为int类型
        int rgb = (rr << 16) | (gg << 8) | bb;
        return rgb;
    }



}
