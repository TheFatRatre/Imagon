package com.cyc.imagon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Measurement;

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
@Measurement(name = "PixelWithCount")
public class PixelWithCount extends Pixel {
    short count;
}
