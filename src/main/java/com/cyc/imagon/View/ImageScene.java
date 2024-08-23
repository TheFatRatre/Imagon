package com.cyc.imagon.View;

import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.var;

/**
 * ClassName: ImageScene
 * Package: com.cyc.imagon.View
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/17 17:01
 * @Version 1.0
 */
public class ImageScene extends AbstractVScene{
    public ImageScene() {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
//        setBackgroundImage(ImageManager.get().load("images/img.png"));
    }

    @Override
    public String title() {
        return "Imagon";
    }

}
