package com.cyc.imagon.config;

import com.cyc.imagon.entity.Image;
import com.cyc.imagon.main.MainModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: BeanConfig
 * Package: com.cyc.imagon.config
 * Description:
 *
 * @Author CYC
 * @Create 2024/10/28 14:12
 * @Version 1.0
 */
@Configuration
public class BeanConfig {
    @Bean(name = "mainModule")
    public MainModule mainModule() {
        return new MainModule();
    }

}
