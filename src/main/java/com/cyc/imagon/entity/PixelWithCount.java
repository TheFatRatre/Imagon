package com.cyc.imagon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: PixelWithCount
 * Package: com.cyc.imagon.entity
 * Description:
 *
 * @Author CYC
 * @Create 2024/3/25 19:37
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PixelWithCount extends Pixel {
    short count;
}
