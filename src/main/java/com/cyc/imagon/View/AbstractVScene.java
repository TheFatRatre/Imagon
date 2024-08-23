package com.cyc.imagon.View;

import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;

/**
 * ClassName: AbstractVScene
 * Package: com.cyc.imagon.application.VFX
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/4 22:29
 * @Version 1.0
 */
public abstract class AbstractVScene extends VScene {
    public AbstractVScene(VSceneRole role) {
        super(role);
    }

    public abstract String title();
}
