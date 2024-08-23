package com.cyc.imagon.application.JFX;

import com.cyc.imagon.View.ViewTheme;
import io.vproxy.vfx.theme.Theme;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: FXMain
 * Package: com.cyc.imagon.application.VFX
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/17 16:27
 * @Version 1.0
 */
@SpringBootApplication
public class FXMain {
    public static void main(String[] args) {
        Theme.setTheme(new ViewTheme());
        Application.launch(ApplicationByJFX.class);
    }
}
