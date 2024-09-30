package com.cyc.imagon.View;

import io.vproxy.vfx.ui.scene.VSceneRole;

/**
 * ClassName: ImageScene
 * Package: com.cyc.imagon.View
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/17 17:01
 * @Version 1.0
 */
public class ImageScene extends AbstractVScene {
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
