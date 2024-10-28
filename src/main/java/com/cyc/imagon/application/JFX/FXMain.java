package com.cyc.imagon.application.JFX;

import com.cyc.imagon.View.ViewTheme;
import io.vproxy.vfx.theme.Theme;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName: FXMain
 * Package: com.cyc.imagon.application.JFX
 * Description:
 *  Boot Entry
 * @Author CYC
 * @Create 2024/8/17 16:27
 * @Version 1.0
 */
@SpringBootApplication
public class FXMain {
    public static final ApplicationContext CONTEXT= new ClassPathXmlApplicationContext("config/applicationContext.xml");
    public static void main(String[] args) {
        Theme.setTheme(new ViewTheme());
        SpringApplication.run(ApplicationByJFX.class, args);
        Application.launch(ApplicationByJFX.class);
    }
}
