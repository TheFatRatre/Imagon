package com.cyc.imagon.View;

import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import lombok.var;

/**
 * ClassName: ApplicationByVFX
 * Package: com.cyc.imagon
 * Description:
 *
 * @Author CYC
 * @Create 2024/8/4 21:48
 * @Version 1.1
 */
public class InitScene extends AbstractVScene{
    public InitScene(){
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();

        var label = new ThemeLabel("Welcome to Imagon") {{
            FontManager.get().setFont(this, settings -> settings.setSize(40));
        }};
        getContentPane().getChildren().add(label);
        FXUtils.observeWidthHeightCenter(getContentPane(), label);
    }

    @Override
    public String title() {
        return "Inmagon";
    }
}
