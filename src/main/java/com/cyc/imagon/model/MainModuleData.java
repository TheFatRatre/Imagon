package com.cyc.imagon.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: MainModuleData
 * Package: com.cyc.imagon.model
 * Description:
 *
 * @Author CYC
 * @Create 2024/10/31 11:19
 * @Version 1.0
 */
@Data
@TableName("main_module")
public class MainModuleData {
    @TableId
    private Long id;

    @TableField
    private short r;

    @TableField
    private short g;

    @TableField
    private short b;

    @TableField
    private int count;

    @TableField
    private int x;

    @TableField
    private int y;

    public MainModuleData(short r, short g, short b, int count, int x, int y) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.count = count;
        this.x = x;
        this.y = y;
    }

    public MainModuleData(Long id, short r, short g, short b, int count, int x, int y) {
        this.id = id;
        this.r = r;
        this.g = g;
        this.b = b;
        this.count = count;
        this.x = x;
        this.y = y;
    }
    public int getRGB() {
        return ((int) r << 16) | ((int) g << 8) | b;
    }
}
